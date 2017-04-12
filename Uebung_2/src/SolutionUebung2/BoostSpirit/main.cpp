#include <iostream>
#include <string>

#include "boost.hpp"
#include "calc.hpp"
#include "lolcode.hpp"

namespace qi = boost::spirit::qi;

using namespace std;

void test_simple_calc() {
	cout << "--- simple calc test ---" << endl;

	string input = "(4 / 2 + 5) * 0.5 - 1.4"; //2.1
	auto begin = input.begin();
	auto end = input.end();

	calc::simple_calc_grammar<decltype(begin)> grammar;

	cout << "parsing..." << flush;
	bool success = qi::phrase_parse(begin, end, grammar, qi::space) && begin == end;
	cout << "success = " << success << endl;
}

void test_advanced_calc() {
	cout << "--- advanced calc test ---" << endl;

	string input = "(4 / 2 + 5) * 0.5 - 1.4"; //2.1
	double output = 0.0;

	auto begin = input.begin();
	auto end = input.end();

	calc::advanced_calc_grammar<decltype(begin)> grammar;

	cout << "parsing..." << flush;
	bool success = qi::phrase_parse(begin, end, grammar, qi::space, output) && begin == end;
	cout << "success = " << success << endl;
	cout << "output  = " << output << endl;
}

void test_lol_code_calc() {
	string code = "HAI\n"
				  //"BTW i'm a comment\n"
		          "KTHXBYE";

	auto begin = code.begin();
	auto end = code.end();

	lolcode::lolcode_grammar<decltype(begin)> grammar;

	cout << "parsing: " << flush;
	bool succcess = qi::phrase_parse(begin, end, grammar, qi::blank) && begin == end;
	cout << "success: " << succcess;
}

int main() {
	cout << "hello world" << endl;
	test_simple_calc();
	test_advanced_calc();
	test_lol_code_calc();

	getchar();
	return 0;
}