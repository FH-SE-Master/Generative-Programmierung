#ifndef _json_test_hpp_
#define _json_test_hpp_

#include <iostream>
#include <string>
#include <fstream>

#include "json.hpp"

using namespace std;
namespace qi = boost::spirit::qi;

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

#endif _json_test_hpp_
