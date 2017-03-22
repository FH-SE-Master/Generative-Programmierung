#include <iostream>
#include "value.hpp"
#include "configuration.hpp"
#include "counter.hpp"
#include "boundedCounter.hpp"
#include "varIncrementBoundCounter.h"

using namespace std;

int main() {
	// Test for integer counter 
	cout << "Integer counter:" << endl;
	typedef typename IntCounterConfig<IntValue<0>>::Counter Counter;
	Counter counter;

	cout << counter.Value() << endl;
	counter.Increment();
	cout << counter.Value() << endl;
	counter.Increment();
	cout << counter.Value() << endl;
	counter.Increment();
	cout << counter.Value() << endl;
	counter.Increment();
	cout << counter.Value() << endl;
	counter.Increment();
	cout << counter.Value() << endl;
	counter.Reset();
	cout << counter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for bounded integer counter
	cout << "Bounded integer counter:" << endl;
	typedef typename BoundedIntCounterConfig<IntValue<0>, IntValue<2>>::Counter BoundedCounter;
	BoundedCounter boundedCounter;

	// Take a look at implemented class some mehtods are wrong
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Reset();
	cout << boundedCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for variable increment bounded integer counter
	cout << "Variable increment Bounded integer counter:" << endl;
	typedef typename VarIncrementBoundIntCounterConfig<IntValue<0>, IntValue<30>, IntValue<10>>::Counter VarIncrementBoundedCounter;
	VarIncrementBoundedCounter varIncBoundCounter;

	// Take a look at implemented class some mehtods are wrong
	cout << varIncBoundCounter.Value() << endl;
	varIncBoundCounter.Increment();
	cout << varIncBoundCounter.Value() << endl;
	varIncBoundCounter.Increment();
	cout << varIncBoundCounter.Value() << endl;
	varIncBoundCounter.Increment();
	cout << varIncBoundCounter.Value() << endl;
	varIncBoundCounter.Increment();
	cout << varIncBoundCounter.Value() << endl;
	varIncBoundCounter.Reset();
	cout << varIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	return 0;
}