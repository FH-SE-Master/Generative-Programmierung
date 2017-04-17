#ifndef lolcode_h
#define lolcode_h

#include "boost.hpp"
#include <map>
#include <string>

#define IT_VAR "IT"

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

			std::cout << std::endl << "semantics-info: created new var '" << var << std::endl;
		}
	};	
	
	double get_var(std::string const &var) {
		double result = 0.0;

		if (var_dict.find(var) != var_dict.end()) {
			result = var_dict[var];
		}

		std::cout << std::endl << "semantics-info: resolved variable '" << var << "' to value: " << result << std::endl;

		return result;
	}

	void set_var(std::string const &var, double const &val) {
		var_is_bool_dict[var] = false;
		var_dict[var] = val;

		std::cout << std::endl << "semantics-info: set numeric var '" << var << " to: " << val << std::endl;
	}

	void set_var_bool(std::string const &var, double const &val) {
		var_is_bool_dict[var] = true;
		var_dict[var] = (val != 1.0) ? 0.0 : 1.0;

		std::cout << std::endl << "semantics-info: set boolean var '" << var << " to: " << ((var_dict[var] == 0.0) ? "FAIL" : "WIN") << std::endl;
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
				std::cout << std::endl << "semantics-info: Variable '" << var << "' not found";
			}
		}
	}; 
	
	void print_bool_result(double const &_1) {
		std::cout << ((_1 == 1.0) ? "WIN" : "FAIL") << std::endl;
	}

	template<typename Iterator>
	struct lolcode_grammar : qi::grammar<Iterator, qi::blank_type> {
		lolcode_grammar() : lolcode_grammar::base_type(program) {
			program =    qi::lit("HAI") 
				      >> qi::eol 
				      >> *stat 
				      >> qi::lit("KTHXBYE")
				      >> *qi::eol;

			stat =    -(
							 includeStat
						   | vardeclStat
						   | assignmentStat
						   | arithemticExpr[phoenix::bind(&set_var, (const char *)IT_VAR, qi::_1)]
						   | boolexpr[phoenix::bind(&set_var_bool, (const char *)IT_VAR, qi::_1)]
						   | visibleStat
					   )
					   >> -comment
					   >> qi::eol;

			includeStat   =     qi::lit("CAN") 
					  		 >> qi::lit("HAS") 
							 >> include
				             >> qi::lit('?');

			vardeclStat   =     qi::string("I") // "I" >> "HAS" would not work, wrong operator overload would be used
						     >> qi::lit("HAS")
						     >> qi::lit("A")
						     >> ident[new_var()]; // "new_var.operator(qi::_1)"

			assignmentStat =    ident[qi::_a = qi::_1] // local variable "_a" for rule "assignment", see rule declaration below
				             >> qi::lit("R")
				             >> (
									  arithemticExpr[phoenix::bind(&set_var, qi::_a, qi::_1)]
								    | boolexpr[phoenix::bind(&set_var_bool, qi::_a, qi::_1)]
							    );

			// --- BEGIN: Arithmetic expressions ---
			additionStat     =    qi::lit("SUM")
							   >> qi::lit("OF")
							   >> arithExOrVar[qi::_val = qi::_1]
							   >> qi::lit("AN")
							   >> arithExOrVar[qi::_val += qi::_1];

			substractionStat =    qi::lit("DIFF")
							   >> qi::lit("OF")
							   >> arithExOrVar[qi::_val = qi::_1]
							   >> qi::lit("AN")
							   >> arithExOrVar[qi::_val -= qi::_1];

			productStat      =    qi::lit("PRODUKT")
							   >> qi::lit("OF")
							   >> arithExOrVar[qi::_val = qi::_1]
							   >> qi::lit("AN")
							   >> arithExOrVar[qi::_val *= qi::_1];

			divisionStat     =    qi::lit("QUOSHUNT")
							   >> qi::lit("OF")
							   >> arithExOrVar[qi::_val = qi::_1]
							   >> qi::lit("AN")
							   >> arithExOrVar[qi::_val /= qi::_1];

			// --- END: Arithmetic expressions ---
			// --- BEGIN: Bool expressions ---
			sameStat    =    qi::lit("BOTH")
						  >> qi::lit("SAEM")
						  >> boolOrVar[qi::_val = qi::_1]
						  >> qi::lit("AN")
					      >> boolOrVar[qi::_val = (qi::_val == qi::_1)];

			notsameStat =    qi::lit("DIFFRINT")
						  >> boolOrVar[qi::_val = qi::_1]
						  >> qi::lit("AN")
						  >> boolOrVar[qi::_val = qi::_val != qi::_1];

			smallerStat =    qi::lit("SMALLR")
						  >> qi::lit("OF")
						  >> boolOrVar[qi::_val = qi::_1]
						  >> qi::lit("AN")
						  >> boolOrVar[qi::_val = qi::_val < qi::_1];

			biggerStat  =    qi::lit("BIGGR")
						  >> qi::lit("OF")
						  >> boolOrVar[qi::_val = qi::_1]
						  >> qi::lit("AN")
						  >> boolOrVar[qi::_val = qi::_val > qi::_1];

			// --- END: Bool expressions ---
			visibleStat    =  qi::lit("VISIBLE")
						      >> 
				              (
									literal[std::cout << qi::_1]
								  | arithemticExpr[std::cout << qi::_1] 
								  | boolexpr[phoenix::bind(&print_bool_result, qi::_1)]
								  | ident[print_variable()] // "print_variable.operator(qi::_1)"
							  )
							  >> (
								      qi::char_('!')
								    | qi::eps[phoenix::ref(std::cout) << std::endl]
								  );

			arithemticExpr =  qi::double_ 
				            | additionStat 
				            | substractionStat 
				            | productStat 
				            | divisionStat;

			boolexpr      =   qi::lit("WIN")[qi::_val = 1.0]
						    | qi::lit("FAIL")[qi::_val = 0.0] 
						    | sameStat[qi::_val = qi::_1]
						    | notsameStat[qi::_val = qi::_1]
						    | smallerStat[qi::_val = qi::_1]
						    | biggerStat[qi::_val = qi::_1]
						    | arithemticExpr[qi::_val = qi::_1];

			arithExOrVar =    arithemticExpr[qi::_val = qi::_1]
				            | ident[qi::_val = phoenix::bind(&get_var, qi::_1)];  

			boolOrVar     =   boolexpr[qi::_val = qi::_1]
				            | ident[qi::_val = phoenix::bind(&get_var, qi::_1)];

			comment       = (  
								   qi::lit("BTW")
								>> qi::lexeme[*(qi::char_ - qi::eol)]
							) 
							| 
							(  
								   qi::lit("OBTW") 
								>> qi::lexeme[*((qi::char_ | qi::eol) - qi::lit("TLDR"))]
								>> qi::lit("TLDR")
							);

			ident         = qi::lexeme[qi::alpha >> *(qi::alnum | '_')];

			include       =   qi::lit("STDIO") 
							| qi::lit("STRING") 
							| qi::lit("SOCKS") 
							| qi::lit("STDLIB")
						    ;

			literal       = qi::lexeme['"' >> *(qi::char_ - '"') >> '"'];
		}

		qi::rule<Iterator, qi::blank_type> program, stat, includeStat, vardeclStat, comment, visibleStat;
		qi::rule<Iterator, qi::blank_type, qi::locals<std::string>> assignmentStat; // locals of type string defined for this rule
																				// http://www.boost.org/doc/libs/1_63_0/libs/spirit/doc/html/spirit/qi/reference/parser_concepts/nonterminal.html#spirit.qi.reference.parser_concepts.nonterminal.locals
		qi::rule<Iterator, double(), qi::blank_type> boolexpr, arithemticExpr, arithExOrVar, boolOrVar, additionStat, substractionStat, productStat, divisionStat, sameStat, notsameStat, smallerStat, biggerStat;
		qi::rule<Iterator, std::string(), qi::blank_type> include, ident, literal;

	};
}

#endif