#ifndef lolcode_h
#define lolcode_h

#include "boost.hpp"
#include <map>
#include <string>

namespace lolcode {

	namespace qi = boost::spirit::qi;
	namespace phoenix = boost::phoenix;

	std::map<std::string, bool> var_is_bool_dict;
	std::map<std::string, double> var_dict; // we need something for NUMBAR and TROOF, see boost::variant
											// http://www.boost.org/doc/libs/1_63_0/doc/html/boost/variant.html

	struct new_var {
		void operator()(std::string const &var) const {
			var_dict[var] = 0.0;
			var_is_bool_dict[var] = false;
		}
	};	
	
	struct get_var {
		double operator()(std::string const &var) const {
			if (var_dict.find(var) != var_dict.end()) {
				return var_dict[var];
			}
			else {
				return 0.0;
			}
		}
	};

	void set_var(std::string const &var, double const &val) {
		var_is_bool_dict[var] = false;
		var_dict[var] = val;
	}

	void set_var_bool(std::string const &var, bool const &val) {
		var_is_bool_dict[var] = true;
		var_dict[var] = (val) ? 1.0 : 0.0;
	}

	struct print_variable {
		void operator()(std::string const &var) const {
			if (var_dict.find(var) != var_dict.end()) {
				if (var_is_bool_dict[var]) {
					std::cout << ((var_dict[var] == 0.0) ? "FAIL" : "WIN") << std::endl;
				}
				else {
					std::cout << var_dict[var] << std::endl;
				}
			}
			else {
				std::cout << "Variable '" << var << "' not found";
			}
		}
	};

	template<typename Iterator>
	struct lolcode_grammar : qi::grammar<Iterator, qi::blank_type> {
		lolcode_grammar() : lolcode_grammar::base_type(program) {
			program = "HAI" >> qi::eol >> *stat >> "KTHXBYE" >> *qi::eol;
			stat = -(vardecl
				| assignment
				| arithemticExpr
				| visible
				)
				>> -comment
				>> qi::eol;
			vardecl = qi::string("I") // "I" >> "HAS" would not work, wrong operator overload would be used
				>> "HAS"
				>> "A"
				>> ident[new_var()]; // "new_var.operator(qi::_1)"
			assignment = ident[qi::_a = qi::_1] // local variable "_a" for rule "assignment", see rule declaration below
				>> "R"
				>> (boolexpr[phoenix::bind(&set_var_bool, qi::_a, qi::_1)]
			      | arithemticExpr[phoenix::bind(&set_var, qi::_a, qi::_1)]);
			addition = qi::lit("SUM")
				>> "OF"
				>> arithemticExpr[qi::_val = qi::_1]
				>> "AN"
				>> arithemticExpr[qi::_val += qi::_1];
			substraction = qi::lit("DIFF")
				>> "OF"
				>> arithemticExpr[qi::_val = qi::_1]
				>> "AN"
				>> arithemticExpr[qi::_val -= qi::_1];
			product = qi::lit("PRODUKT")
				>> "OF"
				>> arithemticExpr[qi::_val = qi::_1]
				>> "AN"
				>> arithemticExpr[qi::_val *= qi::_1];
			division = qi::lit("QUOSHUNT")
				>> "OF"
				>> arithemticExpr[qi::_val = qi::_1]
				>> "AN"
				>> arithemticExpr[qi::_val /= qi::_1];
			visible = "VISIBLE"
				>> (literal[std::cout << qi::_1 << std::endl]
					| arithemticExpr[std::cout << qi::_1 << std::endl] 
					| ident[print_variable()] // "print_variable.operator(qi::_1)"
					);
			arithemticExpr = qi::double_ | addition | substraction | product | division;
			//mathexpr = qi::double_ | addition;
			boolexpr = qi::string("WIN")[qi::_val = true]
				     | qi::string("FAIL")[qi::_val = false];
			comment = ("BTW" >> qi::lexeme[*(qi::char_ - qi::eol)]) 
				    | ("OBTW" >> qi::lexeme[*((qi::char_ | qi::eol) - "TLDR")] >> "TLDR");
			ident = qi::lexeme[qi::alpha >> *(qi::alnum | '_')];
			literal = qi::lexeme['"' >> *(qi::char_ - '"') >> '"'];
		}

		qi::rule<Iterator, qi::blank_type> program, stat, vardecl, comment, visible;
		qi::rule<Iterator, qi::blank_type, qi::locals<std::string>> assignment; // locals of type string defined for this rule
																				// http://www.boost.org/doc/libs/1_63_0/libs/spirit/doc/html/spirit/qi/reference/parser_concepts/nonterminal.html#spirit.qi.reference.parser_concepts.nonterminal.locals
		qi::rule<Iterator, double(), qi::blank_type> arithemticExpr, addition, substraction, product, division;
		qi::rule<Iterator, bool(), qi::blank_type> boolexpr;
		qi::rule<Iterator, std::string(), qi::blank_type> ident, literal;
	};
}

#endif