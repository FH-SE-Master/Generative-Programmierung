#include <iostream>
#include <chrono>
#include "fibonacci.hpp"
#include "statement.hpp"

using namespace std;

// templated function for measuirng 
template<typename Func>
void measure(Func func) {
	auto start = chrono::high_resolution_clock::now();
	func();
	auto end = chrono::high_resolution_clock::now();

	auto duration = chrono::duration_cast<chrono::duration<double>> (end - start);
	cout << "duration: " << fixed << duration.count() << endl << "----------------------------------" << endl;
}

int main() {
	static const int n = 40;

	// measure recursive call at runtime
	measure([&]() { cout << "Recursive call:" << endl << "fibonacci(" << n << ") = " << fibonacci(n) << endl; });
	// measure compile time calculation
	measure([&]() { cout << "Compile time call:" << "fibonacci<" << n << ">::RET = " << Fibonacci<n>::RET << endl; } );

	// iterative recursive calls
	measure([&]() {
		for (int i = 0; i < n; i++) {
			measure([&]() {cout << "Iterative call: " << endl << "fibonacci(" << i << ") = " << fibonacci(i) << endl; });
		}
	});

	// measure compile time call with Do template
	cout << "Compile time call with Do - If statement: " << endl;
	measure([&]() {
		DO<FibonacciStatement<10>, FibonacciEndCondition<40>>::exec();
	});

	return 0;
}