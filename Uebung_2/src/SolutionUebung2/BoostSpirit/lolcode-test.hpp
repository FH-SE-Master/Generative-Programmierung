#ifndef _lolcode_test_hpp_
#define _lolcode_test_hpp_

#include <iostream>
#include <string>
#include <fstream>

#include "lolcode.hpp"

using namespace std;
namespace qi = boost::spirit::qi;

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

#endif _lolcode_test_hpp_
