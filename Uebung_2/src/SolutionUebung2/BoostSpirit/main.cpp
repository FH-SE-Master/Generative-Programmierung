#include <iostream>
#include <string>
#include <fstream>

#include "json-test.hpp"
#include "lolcode-test.hpp"

using namespace std;

int main() {
	test_lolcode("./test.lolcode");

	cout << endl << endl;

	test_json("./test.json");

	// console won't keep open in VS
	cin.get();

	return 0;
}