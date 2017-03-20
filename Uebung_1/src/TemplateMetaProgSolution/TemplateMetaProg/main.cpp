#include <iostream>
#include <chrono>
#include "fibonacci.hpp"
#include "If.hpp"
#include "Do.hpp"

	using namespace std;

	template<typename Func>
	void measure(Func func) {
		auto start = chrono::high_resolution_clock::now();
		func();
		auto end = chrono::high_resolution_clock::now();

		auto duration = chrono::duration_cast<chrono::duration<double>> (end - start);
		cout << "duration: " << fixed << duration.count() << endl;
	}

	int main() {
		static const int n = 40;
		//measure([&]() { cout << "Fib of 10: " << fibonacci(n) << endl; });
		//measure([&]() { cout << "Fibonacci<" << n << ">::RET = " << Fibonacci<n>::RET << endl; } );

		measure([&]() {
			for (int i = 10; i < 40; i++) {
				cout << "Fibonacci(" << i << ")" << fibonacci(i) << endl;
			}
		});

		measure([&]() {
			DO<FibonacciStatement<10>, FibonacciEndCondition<40>>::exec();
		});

		return 0;
	}