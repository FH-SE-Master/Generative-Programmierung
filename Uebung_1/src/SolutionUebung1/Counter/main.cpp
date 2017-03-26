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
	doubleCounter.Reset();
	cout << doubleCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for bounded integer counter
	cout << "Bounded integer counter:" << endl;
	typedef typename BoundedCounterConfig<int, IntValue<0>, IntValue<1>, IntValue<2>>::Counter BoundedCounter;
	BoundedCounter boundedCounter;
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Increment();
	cout << boundedCounter.Value() << endl;
	boundedCounter.Reset();
	cout << boundedCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for generator counter with defaults
	cout << "Generator counter with defaults:" << endl;
	typedef typename CounterConfigurationGenerator<increment, double, DoubleValue_0_5>::Config::Counter GenCounter;
	GenCounter genCounter;
	cout << genCounter.Value() << endl;
	genCounter.Increment();
	cout << genCounter.Value() << endl;
	genCounter.Increment();
	cout << genCounter.Value() << endl;
	genCounter.Reset();
	cout << genCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for generator counter with defaults
	cout << "Generator counter with defaults for double:" << endl;
	typedef typename CounterConfigurationGenerator<increment, double>::Config::Counter GenDoubleCounter;
	GenDoubleCounter genDefaultCounter;
	cout << genDefaultCounter.Value() << endl;
	genDefaultCounter.Increment();
	cout << genDefaultCounter.Value() << endl;
	genDefaultCounter.Increment();
	cout << genDefaultCounter.Value() << endl;
	genDefaultCounter.Reset();
	cout << genDefaultCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for generator counter with defaults
	cout << "Generator counter with defaults for double, start=0.5:" << endl;
	typedef typename CounterConfigurationGenerator<increment, double, DoubleValue_0_5>::Config::Counter GenDoubleStartCounter;
	GenDoubleStartCounter genDoubleStartCounter;
	cout << genDoubleStartCounter.Value() << endl;
	genDoubleStartCounter.Increment();
	cout << genDoubleStartCounter.Value() << endl;
	genDoubleStartCounter.Increment();
	cout << genDoubleStartCounter.Value() << endl;
	genDoubleStartCounter.Reset();
	cout << genDoubleStartCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests for generator counter with defaults
	cout << "Generator counter with defaults for double, start=0.5, inc=0.5:" << endl;
	typedef typename CounterConfigurationGenerator<increment, double, DoubleValue_0_5, DoubleValue_0_5>::Config::Counter GenDoublestartIncCounter;
	GenDoublestartIncCounter genDoubleStartIncCounter;
	cout << genDoubleStartIncCounter.Value() << endl;
	genDoubleStartIncCounter.Increment();
	cout << genDoubleStartIncCounter.Value() << endl;
	genDoubleStartIncCounter.Increment();
	cout << genDoubleStartIncCounter.Value() << endl;
	genDoubleStartIncCounter.Reset();
	cout << genDoubleStartIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	return 0;
}