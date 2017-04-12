#ifndef _lolcode_h_
#define _lolcode_h_

#include "boost.hpp"

namespace lolcode {
	namespace qi = boost::spirit::qi;
	namespace phoenix = boost::phoenix;

	template<typename Iterator>
	struct lolcode_grammar : qi::grammar<Iterator, qi::blank_type> {
		lolcode_grammar() : lolcode_grammar::base_type(programm) {
			programm = "HAI"
						>> *stat
						>> "KTHXBYE" >> qi::eoi;
			stat = comment;
			comment = "BTW" >> qi::lexeme[*(qi::char_ - qi::eol)];

		}

		qi::rule<Iterator, qi::blank_type> programm;
		qi::rule<Iterator, qi::blank_type> stat;
		qi::rule<Iterator, qi::blank_type> comment;
	};
}

#endif