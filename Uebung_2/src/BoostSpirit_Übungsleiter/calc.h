#ifndef calc_h
#define calc_h

#include "boost.h"

namespace calc {
  namespace qi = boost::spirit::qi;

  template<typename Iterator>
  struct simple_calc_grammar : qi::grammar<Iterator, qi::space_type> {
    simple_calc_grammar() : simple_calc_grammar::base_type(expr) {
      expr = term >> *('+' >> term | '-' >> term); // expr = term % (qi::lit('+') | '-');
      term = fact >> *('*' >> fact | '/' >> fact); // term = fact % (qi::lit('*') | '/');
      fact = qi::double_ | '(' >> expr >> ')';
    }

    typedef qi::rule<Iterator, qi::space_type> rule;
    rule expr, term, fact;
  };

  template<typename Iterator>
  struct advanced_calc_grammar : qi::grammar<Iterator, double(), qi::space_type> {
    advanced_calc_grammar() : advanced_calc_grammar::base_type(expr) {
      expr =    term              [qi::_val = qi::_1]
             >> *(  '+' >> term [qi::_val += qi::_1]
                  | '-' >> term [qi::_val -= qi::_1]
                 );
      term =    fact              [qi::_val = qi::_1]
             >> *(  '*' >> fact [qi::_val *= qi::_1]
                  | '/' >> fact [qi::_val /= qi::_1]
                 );
      fact =   qi::double_        [qi::_val = qi::_1]
             |     '('
                >> expr           [qi::_val = qi::_1]
                >> ')';
    }

    typedef qi::rule<Iterator, double(), qi::space_type> rule;
    rule expr, term, fact;
  };
}

#endif