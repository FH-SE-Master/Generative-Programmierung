#include <iostream>
#include "value.hpp"
#include "configuration.hpp"
#include "counter.hpp"
#include "boundedCounter.hpp"

using namespace std;

int main() {
	// Test for integer counter 
	cout << "Integer counter :" << endl;
	typedef typename IncCounterConfig<int, IntValue<0>, IntValue<1>>::Counter Counter;
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

	// Test for double counter 
	cout << "Double counter :" << endl;
	typedef typename IncCounterConfig<double, IntValue<0>, DoubleValue_0_5>::Counter DoubleCounter;
	DoubleCounter doubleCounter;

	cout << doubleCounter.Value() << endl;
	doubleCounter.Increment();
	cout << doubleCounter.Value() << endl;
	doubleCounter.Increment();
	cout << doubleCounter.Value() << endl;
	doubleCounter.Increment();
	cout << doubleCounter.Value() << endl;
	doubleCounter.Increment();
	cout << doubleCounter.Value() << endl;
	doubleCounter.Increment();
	cout << doubleCounter.Value() << endl;
	doubleCounter.Reset();
	cout << doubleCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for bounded integer counter
	cout << "Bounded integer counter:" << endl;
	typedef typename BoundedCounterConfig<int, IntValue<0>, IntValue<2>, IntValue<1>>::Counter BoundedCounter;
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

	return 0;
}