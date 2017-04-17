#include <iostream>
#include <string>
#include <fstream>

#include "calc.hpp"
#include "lolcode.hpp"
#include "json.hpp"

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

void test_lolcode(string file) {
	cout << "testing lolcode parser with file " << file << endl;
	std::ifstream fileStream(file);

	string code((std::istreambuf_iterator<char>(fileStream)),
		std::istreambuf_iterator<char>());
		
	auto begin = code.begin();
	auto end = code.end();
	lolcode::lolcode_grammar<decltype(begin)> grammar;

	cout << "-----------------------------------------------------" 
		 << endl
		 << "parsing ... "
		 << endl 
		 << "-----------------------------------------------------" 
		 << endl
		 << code
		 << endl
		 << "-----------------------------------------------------"
		 << endl;

	bool success = qi::phrase_parse(begin, end, grammar, qi::blank) && begin == end;
	
	cout << endl
		 << "-----------------------------------------------------" 
		 << endl
		 << "success = " << boolalpha << success
		 << endl
		 << "-----------------------------------------------------"
		 << endl;
	fileStream.close();
}

void test_json(string file) {
	cout << "testing json parser with file " << file << endl;
	std::ifstream fileStream(file);
	std::string json((std::istreambuf_iterator<char>(fileStream)),
		std::istreambuf_iterator<char>());

	auto begin = json.begin();
	auto end = json.end();
	json_parser::json_grammar<decltype(begin)> grammar;
	cout << "-----------------------------------------------------"
		<< endl
		<< "parsing ... "
		<< endl
		<< "-----------------------------------------------------"
		<< endl
		<< json
		<< endl
		<< "-----------------------------------------------------"
		<< endl;
	
	bool success = qi::phrase_parse(begin, end, grammar, qi::space) && begin == end;
	
	cout << "success = " << boolalpha << success 
		 << endl
		 << "-----------------------------------------------------"
		 << endl;
	fileStream.close();
}

int main() {
	test_lolcode("./test.lolcode");

	cout << endl << endl;

	test_json("./test.json");

	cin.get();
	return 0;
}