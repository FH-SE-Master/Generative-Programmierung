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
		"HAI\n"
		"BTW i'm a comment\n"
		"I   HAS  A    bolsche            BTW declare variable\n"
		"bolsche R WIN                    BTW assign value\n"
		"VISIBLE bolsche                  BTW print value\n"
		"I   HAS  A    answer             BTW declare variable\n"
		"answer R 10.0                    BTW assign value\n"
		"VISIBLE answer                   BTW print value\n"
		"answer R SUM OF 10.0 AN 10.0     BTW add two values and assign result\n"
		"VISIBLE answer                   \n"
		"OBTW This is a multiline comment \n"
		"and this is the second line TLDR \n"
		"VISIBLE SUM OF 1 AN SUM OF 2 AN 2                BTW Nested Addition\n"
		"VISIBLE DIFF OF 10 AN DIFF OF 10 AN 5            BTW Nested Substraction\n"
		"VISIBLE PRODUKT OF 10 AN PRODUKT OF 10 AN 10     BTW Nested Produkt\n"
		"VISIBLE QUOSHUNT OF 100 AN PRODUKT OF 100 AN 10  BTW Nested Division\n"
		"VISIBLE \"iz coffeh tiem!\"\n"
		"KTHXBYE";

	auto begin = code.begin();
	auto end = code.end();
	lolcode::lolcode_grammar<decltype(begin)> grammar;

	cout << "parsing ... " << endl << code << endl;
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