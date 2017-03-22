#ifndef _fibonacci_h
#define _fibonacci_h

// function which calculates fibonacci number 
int fibonacci(int n) {
	if (n <= 1) { 
		return n; 
	}
	else { 
		return fibonacci(n - 1) + fibonacci(n - 2); 
	}
}

// partial fibonacci template
template<int n>
struct Fibonacci {
	enum { RET = Fibonacci<n - 1>::RET + Fibonacci<n - 2>::RET };
};

// full fibonacci template for 0
template<>
struct Fibonacci<0> {
	enum { RET = 0 };
};

// full fibonacci template for 1
template<>
struct Fibonacci<1> {
	enum { RET = 1 };
};

// partial fibonacci condition template
template<int n>
struct FibonacciStatement {
	// here we remember the current set template value, otherwise will be lost 
	enum { current = n };
	static void exec() {
		cout << "fibonacci<" << n << ">::RET = " << Fibonacci<n>::RET << endl;
	}
	typedef FibonacciStatement<n + 1> Next;
};

// partial fibonacci end condition statement template
template<int max>
struct FibonacciEndCondition {
	template<typename Statement>
	struct Code {
		enum { RET = Statement::current <= max };
	};
};

#endif