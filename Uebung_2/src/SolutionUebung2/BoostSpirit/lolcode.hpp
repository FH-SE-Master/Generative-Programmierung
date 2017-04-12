#ifndef lolcode_h
#define lolcode_h

#include "boost.hpp"
#include <map>
#include <string>

namespace lolcode {

	namespace qi = boost::spirit::qi;
	namespace phoenix = boost::phoenix;

	std::map<std::string, double> var_dict; // we need something for NUMBAR and TROOF, see boost::variant
											// http://www.boost.org/doc/libs/1_63_0/doc/html/boost/variant.html

	struct new_variable {
		void operator()(std::string const &var) const {
			var_dict[var] = 0.0;
		}
	};

	void set_variable(std::string const &var, double const &val) {
		var_dict[var] = val;
	}

	struct print_variable {
		void operator()(std::string const &var) const {
			std::cout << var_dict[var] << std::endl;
		}
	};

	template<typename Iterator>
	struct lolcode_grammar : qi::grammar<Iterator, qi::blank_type> {
		lolcode_grammar() : lolcode_grammar::base_type(program) {
			program = "HAI" >> qi::eol >> *stat >> "KTHXBYE" >> *qi::eol;
			stat = -(vardecl
				| assignment
				| addition
				| visible
				)
				>> -comment
				>> qi::eol;
			vardecl = qi::string("I") // "I" >> "HAS" would not work, wrong operator overload would be used
				>> "HAS"
				>> "A"
				>> ident[new_variable()]; // "new_variable.operator(qi::_1)"
			assignment = ident[qi::_a = qi::_1] // local variable "_a" for rule "assignment", see rule declaration below
				>> "R"
				>> mathexpr[phoenix::bind(&set_variable, qi::_a, qi::_1)]; // "set_variable(qi::_a, qi::_1)"
			addition = qi::lit("SUM")
				>> "OF"
				>> mathexpr[qi::_val = qi::_1]
				>> "AN"
				>> mathexpr[qi::_val += qi::_1];
			visible = "VISIBLE"
				>> (mathexpr[std::cout << qi::_1 << std::endl]
					| literal[std::cout << qi::_1 << std::endl]
					| ident[print_variable()] // "print_variable.operator(qi::_1)"
					);
			mathexpr = qi::double_ | addition;
			comment = ("BTW" >> qi::lexeme[*(qi::char_ - qi::eol)]) 
				    | ("OBTW" >> qi::lexeme[*((qi::char_ | qi::eol) - "TLDR")] >> "TLDR");
			ident = qi::lexeme[qi::alpha >> *(qi::alnum | '_')];
			literal = qi::lexeme['"' >> *(qi::char_ - '"') >> '"'];
		}

		qi::rule<Iterator, qi::blank_type> program;
		qi::rule<Iterator, qi::blank_type> stat;
		qi::rule<Iterator, qi::blank_type> vardecl;
		qi::rule<Iterator, qi::blank_type, qi::locals<std::string>> assignment; // locals of type string defined for this rule
																				// http://www.boost.org/doc/libs/1_63_0/libs/spirit/doc/html/spirit/qi/reference/parser_concepts/nonterminal.html#spirit.qi.reference.parser_concepts.nonterminal.locals
		qi::rule<Iterator, double(), qi::blank_type> addition;
		qi::rule<Iterator, qi::blank_type> visible;
		qi::rule<Iterator, double(), qi::blank_type> mathexpr;
		qi::rule<Iterator, qi::blank_type> comment;
		qi::rule<Iterator, std::string(), qi::blank_type> ident;
		qi::rule<Iterator, std::string(), qi::blank_type> literal;
	};
}

#endif