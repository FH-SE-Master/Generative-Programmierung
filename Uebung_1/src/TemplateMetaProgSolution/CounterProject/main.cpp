#include <iostream>
#include "Values.hpp"
#include "Configurations.hpp"
#include "Counter.hpp"
#include "BoundedCounter.hpp"

using namespace std;

int main() {
	typedef typename IntCounterConfig<IntValue<0>>::Counter Counter;
	Counter counter;

	cout << counter.value() << endl;
	counter.Increment();
	cout << counter.value() << endl;
	counter.Reset();
	cout << counter.value() << endl;

	typedef typename BoundedIntCounterConfig<IntValue<0>, IntValue<2>>::Counter BoundedCounter;
	BoundedCounter boundedCounter;

	// Take a look at implemented class some mehtods are wrong
	cout << counter.value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.value() << endl;
	boundedCounter.Reset();
	cout << boundedCounter.value() << endl;


	return 0;
}