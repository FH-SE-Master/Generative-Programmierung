#include <iostream>
#include <string>

#include "calc.hpp"
#include "lolcode.hpp"

namespace qi = boost::spirit::qi;

using namespace std;

void test_simple_calc() {
	cout << "--- simple calc test ---" << endl;

	string input = "(4 / 2 + 5) * 0.5 - 1.4";
	auto begin = input.begin();
	auto end = input.end();
	calc::simple_calc_grammar<decltype(begin)> grammar;

	cout << "parsing ... " << flush;
	bool success = qi::phrase_parse(begin, end, grammar, qi::space) && begin == end;
	cout << "success = " << success << endl;
}

void test_advanced_calc() {
	cout << "--- advanced calc test ---" << endl;

	string input = "(4 / 2 + 5) * 0.5 - 1.4"; // 2.1
	double output = 0.0;
	auto begin = input.begin();
	auto end = input.end();
	calc::advanced_calc_grammar<decltype(begin)> grammar;

	cout << "parsing ... " << flush;
	bool success = qi::phrase_parse(begin, end, grammar, qi::space, output) && begin == end;
	cout << "success = " << success << endl;
	cout << "output = " << output << endl;
}

void test_lolcode() {
	cout << "--- lolcode test ---" << endl;

	string code =
		"HAI                                                                      \n"
		
		"OBTW This section declares includes the needed libs                      \n"
		"     All supported libs are included                                     \n"
		"TLDR                                                                     \n"
		
		"CAN HAS STDIO?                   BTW Include STDIO lib                   \n"
		"CAN HAS STRING?                  BTW Include STRING lib                  \n"
		"CAN HAS SOCKS?                   BTW Include SOCKS lib                   \n"
		"CAN HAS STDLIB?                  BTW Include STDLIB lib                  \n"

		"OBTW This section declares all numeric variables                         \n"
		"     Two numeric variables are declared and intialized                   \n"
		"TLDR                                                                     \n"
		
		"I HAS A num_1                    BTW declare variable num_ 1             \n"
		"I HAS A num_2                    BTW declare variable num_ 2             \n"
		"num_1 R 10.0                     BTW assign 10.0 to num_1                \n"
		"num_1 R 20                       BTW assign 10 to num_2                  \n"

		"OBTW This section declares all bool variables                            \n"
		"     Two bool variables are declared and intialized                      \n"
		"TLDR                                                                     \n"
		
		"I HAS A bool_1                   BTW declare variable bool_ 1            \n"
		"I HAS A bool_2                   BTW declare variable bool_ 2            \n"
		"bool_1 R WIN                     BTW assign WIN to bool_1                \n"
		"bool_2 R FAIL                    BTW assign FAIL to bool_2               \n"

		"OBTW This section prints the current variable values                     \n"
		"     Two bool and numeric variables are print                            \n"
		"TLDR                                                                     \n"

		"VISIBLE \"Value of num_1:\"                                              \n"
		"VISIBLE num_1                    BTW print value of num_1                \n"
		"VISIBLE \"Value of num_2:\"                                              \n"
		"VISIBLE num_2                    BTW print value of num_2                \n"
		"VISIBLE \"Value of bool_1:\"                                             \n"
		"VISIBLE bool_1                   BTW print value of bool_1               \n"
		"VISIBLE \"Value of bool_2:\"                                             \n"
		"VISIBLE bool_2                   BTW print value of bool_2               \n"

		"BTW This section performs simple arithmetic expressions nad prints them  \n"

		"VISIBLE \"1 + 2 = 3\"                                                    \n"
		"VISIBLE SUM OF 1 AN 2                                                    \n"
		"VISIBLE \"10 - 5 = 5\"                                                   \n"
		"VISIBLE DIFF OF 10 AN 5                                                  \n"
		"VISIBLE \"2 * 2.5 = 5\"                                                  \n"
		"VISIBLE PRODUKT OF 2.5 AN 2                                              \n"
		"VISIBLE \"100 / 5 = 20\"                                                 \n"
		"VISIBLE QUOSHUNT OF 100 AN 5                                             \n"

		"BTW This section performs complex arithmetic expressions nad prints them \n"

		"VISIBLE \"1 + (2 * 5) = 11\"                                             \n"
		"VISIBLE SUM OF 1 AN PRODUKT OF 2 AN 5                                    \n"
		"VISIBLE \"10 - (100 / 10) = 0\"                                          \n"
		"VISIBLE DIFF OF 10 AN QUOSHUNT OF 100 AN 10                              \n"
		"VISIBLE \"2 * (3 + 2) = 10\"                                             \n"
		"VISIBLE PRODUKT OF 2 AN SUM OF 3 AN 2                                    \n"
		"VISIBLE \"100 / (100 - 90) = 10\"                                        \n"
		"VISIBLE QUOSHUNT OF 100 AN DIFF OF 100 AN 90                             \n"

		// TODO: Add complex expresions with nested variables

		"BTW This section performs simple bool expressions nad prints them        \n"

		"VISIBLE \"WIN == WIN = WIN\"                                             \n"
		"VISIBLE BOTH SAEM WIN AN WIN                                             \n"
		"VISIBLE \"WIN != FAIL = WIL\"                                            \n"
		"VISIBLE DIFFRINT WIN AN FAIL                                             \n"
		"VISIBLE \"WIN < FAIL = FAIL\"                                            \n"
		"VISIBLE SMALLR OF WIN AN FAIL                                            \n"
		"VISIBLE \"FAIL > WIN = FAIL\"                                            \n"
		"VISIBLE BIGGR OF FAIL AN WIN                                             \n"
		
		"VISIBLE \"10 == 10 = WIN\"                                               \n"
		"VISIBLE BOTH SAEM WIN AN WIN                                             \n"
		"VISIBLE \"10 != 11 = WIN\"                                               \n"
		"VISIBLE DIFFRINT WIN AN FAIL                                             \n"
		"VISIBLE \"10 < 9 = FAIL\"                                                \n"
		"VISIBLE SMALLR OF 10 AN 9                                                \n"
		"VISIBLE \"9 > 10 = FAIL\"                                                \n"
		"VISIBLE BIGGR OF 9 AN 10                                                 \n"
		
		"VISIBLE \"WIN == (WIN == FAIL) = FAIL\"                                  \n"
		"VISIBLE BOTH SAEM WIN AN BOTH SAEM WIN AN FAIL                           \n"
		"VISIBLE \"WIN != (WIN != FAIL) = FAIL\"                                  \n"
		"VISIBLE DIFFRINT WIN AN DIFFRINT WIN AN FAIL                             \n"
		"VISIBLE \"FAIL < (WIN > FAIL) = WIN\"                                    \n"
		"VISIBLE SMALLR OF FAIL AN BIGGR OF WIN AN FAIL                           \n"
		"VISIBLE \"WIN > (WIN < FAIL) = WIN\"                                     \n"
		"VISIBLE BIGGR OF WIN AN SMALLR OF WIN AN FAIL                            \n"
		
		"VISIBLE \"10 == (10 - 10) = FAIL\"                                       \n"
		"VISIBLE BOTH SAEM 10 AN DIFF OF 10 AN 10                                 \n"
		"VISIBLE \"10 != (9 + 1) = FAIL\"                                         \n"
		"VISIBLE DIFFRINT 10 AN SUM OF 9 AN 1                                     \n"
		"VISIBLE \"10 < (1 * 11) = WIN\"                                          \n"
		"VISIBLE SMALLR OF 10 AN PRODUKT OF 1 AN 11                               \n"
		"VISIBLE \"10 > (90 / 10) = WIN\"                                         \n"
		"VISIBLE BIGGR OF 10 AN QUOSHUNT OF 90 AN 10                              \n"
		
		"VISIBLE \"End of program\"                                               \n"
		"KTHXBYE";

	auto begin = code.begin();
	auto end = code.end();
	lolcode::lolcode_grammar<decltype(begin)> grammar;

	cout << "-----------------------------------------------------" 
		 << endl
		 << "parsing ... "
		 << endl 
		 << "-----------------------------------------------------" 
		 << code 
		 << "-----------------------------------------------------"
		 << endl;
	bool success = qi::phrase_parse(begin, end, grammar, qi::blank) && begin == end;
	cout << "success = " << success << endl;
}

int main() {
	test_simple_calc();
	test_advanced_calc();
	test_lolcode();

	cin.get();
	return 0;
}