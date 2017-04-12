#ifndef CALC_H
#define CALC_H

#include "boost.hpp"

namespace calc {
	//alias for boost-qi
	namespace qi = boost::spirit::qi;

	template<typename Iterator>
	struct simple_calc_grammar : qi::grammar<Iterator, qi::space_type> {
		simple_calc_grammar() :simple_calc_grammar::base_type(expr) {
			expr = term >> *(('+' >> term) | ('-' >> term));
			term = fact >> *(('*' >> fact) | ('/' >> fact));
			fact = qi::double_ | ('(' >> expr >> ')');
		}

		typedef qi::rule<Iterator, qi::space_type> rule;
		rule expr, term, fact;
	};

	template<typename Iterator>
	struct advanced_calc_grammar : qi::grammar<Iterator, double(), qi::space_type> {
		advanced_calc_grammar() :advanced_calc_grammar::base_type(expr) {
			expr =	term                  [qi::_val = qi::_1]  // assign value of term to expr
				   >> *( ('+' >> term)    [qi::_val += qi::_1] // assign value of term to expr (old value plus by new value)
					   | ('-' >> term)    [qi::_val -= qi::_1] // assign value of term to expr (old value minus by new value)
					   );
			term =  fact                  [qi::_val = qi::_1]  // assign value of fact to term
				   >> *( ('*' >> fact)    [qi::_val *= qi::_1] // assign value of fact to term (old value multiplied by new value)
					   | ('/' >> fact)    [qi::_val /= qi::_1] // assign value of fact to term (old value divided by new value)
					   );
			fact =  qi::double_           [qi::_val = qi::_1] // assign value of qi::double_ to fact
				    | ('(' 
					  >> expr             [qi::_val = qi::_1] // assign value of expr as return value of fact
					  >> ')');
		}

		typedef qi::rule < Iterator, double(), qi::space_type > rule;
		rule expr, term, fact;
	};
}

#endif