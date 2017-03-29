#include <iostream>
#include "value.hpp"
#include "configuration.hpp"
#include "counter.hpp"
#include "boundedCounter.hpp"

using namespace std;

int main() {
	// Tests Integer counter
	cout << "IncCounterConfig<int, IntValue<0>>:" << endl;
	typedef typename IncCounterConfig<int, IntValue<0>>::Counter DefIntCounter;
	DefIntCounter defIntCounter;
	cout << defIntCounter.Value() << endl;
	defIntCounter.Increment();
	cout << defIntCounter.Value() << endl;
	defIntCounter.Increment();
	cout << defIntCounter.Value() << endl;
	defIntCounter.Increment();
	cout << defIntCounter.Value() << endl;
	defIntCounter.Reset();
	cout << defIntCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "IncCounterConfig<int, IntValue<0>, IntValue<2>>:" << endl;
	typedef typename IncCounterConfig<int, IntValue<0>, IntValue<2>>::Counter IntIncCounter;
	IntIncCounter intIncCounter;
	cout << intIncCounter.Value() << endl;
	intIncCounter.Increment();
	cout << intIncCounter.Value() << endl;
	intIncCounter.Increment();
	cout << intIncCounter.Value() << endl;
	intIncCounter.Increment();
	cout << intIncCounter.Value() << endl;
	intIncCounter.Reset();
	cout << intIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "BoundedCounterConfig<int, IntValue<1>, IntValue<2>, IntValue<1>>:" << endl;
	typedef typename BoundedCounterConfig<int, IntValue<1>, IntValue<2>, IntValue<1>>::Counter IntBoundedCounter;
	IntBoundedCounter intBoundedCounter;
	cout << intBoundedCounter.Value() << endl;
	intBoundedCounter.Increment();
	cout << intBoundedCounter.Value() << endl;
	intBoundedCounter.Increment();
	cout << intBoundedCounter.Value() << endl;
	intBoundedCounter.Increment();
	cout << intBoundedCounter.Value() << endl;
	intBoundedCounter.Reset();
	cout << intBoundedCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests Double counter
	cout << "IncCounterConfig<double, DoubleValue_0_5>:" << endl;
	typedef typename IncCounterConfig<double, DoubleValue_0_5>::Counter DefDoubleCounter;
	DefDoubleCounter defDoubleCounter;
	cout << defDoubleCounter.Value() << endl;
	defDoubleCounter.Increment();
	cout << defDoubleCounter.Value() << endl;
	defDoubleCounter.Increment();
	cout << defDoubleCounter.Value() << endl;
	defDoubleCounter.Increment();
	cout << defDoubleCounter.Value() << endl;
	defDoubleCounter.Reset();
	cout << defDoubleCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "IncCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0>:" << endl;
	typedef typename IncCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0>::Counter DoubleIncCounter;
	DoubleIncCounter doubleIncCounter;
	cout << doubleIncCounter.Value() << endl;
	doubleIncCounter.Increment();
	cout << doubleIncCounter.Value() << endl;
	doubleIncCounter.Increment();
	cout << doubleIncCounter.Value() << endl;
	doubleIncCounter.Increment();
	cout << doubleIncCounter.Value() << endl;
	doubleIncCounter.Reset();
	cout << doubleIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "BoundedCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>:" << endl;
	typedef typename BoundedCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>::Counter DoubleBoundedCounter;
	DoubleBoundedCounter doubleBoundedCounter;
	cout << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Increment();
	cout << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Increment();
	cout << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Increment();
	cout << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Reset();
	cout << doubleBoundedCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;
	
	// Tests Int Configurator
	cout << "CounterConfigurationGenerator<intInc, IntValue<0>>:" << endl;
	typedef typename CounterConfigurationGenerator<intInc, IntValue<0>>::Counter GenIntIncCounter;
	GenIntIncCounter genIntIncCounter;
	cout << genIntIncCounter.Value() << endl;
	genIntIncCounter.Increment();
	cout << genIntIncCounter.Value() << endl;
	genIntIncCounter.Increment();
	cout << genIntIncCounter.Value() << endl;
	genIntIncCounter.Increment();
	cout << genIntIncCounter.Value() << endl;
	genIntIncCounter.Reset();
	cout << genIntIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<intIncBounded, IntValue<0>, IntValue<2>>:" << endl;
	typedef typename CounterConfigurationGenerator<intIncBounded, IntValue<0>, IntValue<2>>::Counter GenIntIncBoundCounter;
	GenIntIncBoundCounter genIntIncBoundCounter;
	cout << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Increment();
	cout << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Increment();
	cout << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Increment();
	cout << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Reset();
	cout << genIntIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varIntInc, IntValue<0>, NoBound, IntValue<2>>:" << endl;
	typedef typename CounterConfigurationGenerator<varIntInc, IntValue<0>, NoBound, IntValue<2>>::Counter GenIntVarIncCounter;
	GenIntVarIncCounter genIntVarIncCounter;
	cout << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Increment();
	cout << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Increment();
	cout << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Increment();
	cout << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Reset();
	cout << genIntVarIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varIntInc, IntValue<0>, IntValue<2>, IntValue<1>>:" << endl;
	typedef typename CounterConfigurationGenerator<varIntInc, IntValue<0>, IntValue<2>, IntValue<1>>::Counter GenIntVarIncBoundCounter;
	GenIntVarIncBoundCounter genIntVarIncBoundCounter;
	cout << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Increment();
	cout << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Increment();
	cout << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Increment();
	cout << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Reset();
	cout << genIntVarIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests Double Configurator
	cout << "CounterConfigurationGenerator<doubInc, DoubleValue_0_5>:" << endl;
	typedef typename CounterConfigurationGenerator<doubInc, DoubleValue_0_5>::Counter GenDoubleIncCounter;
	GenDoubleIncCounter genDoubleIncCounter;
	cout << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Increment();
	cout << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Increment();
	cout << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Increment();
	cout << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Reset();
	cout << genDoubleIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<doubIncBounded, DoubleValue_0_5, DoubleValue_2_0>:" << endl;
	typedef typename CounterConfigurationGenerator<doubIncBounded, DoubleValue_0_5, DoubleValue_2_0>::Counter GenDoubleIncBoundCounter;
	GenDoubleIncBoundCounter genDoubleIncBoundCounter;
	cout << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Increment();
	cout << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Increment();
	cout << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Increment();
	cout << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Reset();
	cout << genDoubleIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varDoubInc, IntValue<0>, NoBound, DoubleValue_0_5>:" << endl;
	typedef typename CounterConfigurationGenerator<varDoubInc, DoubleValue_0_5, NoBound, DoubleValue_0_5>::Counter GenDoubleVarIncCounter;
	GenDoubleVarIncCounter genDoubleVarIncCounter;
	cout << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Increment();
	cout << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Increment();
	cout << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Increment();
	cout << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Reset();
	cout << genDoubleVarIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varDoubIncBounded, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>:" << endl;
	typedef typename CounterConfigurationGenerator<varDoubIncBounded, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>::Counter GenDoubleVarIncBoundCounter;
	GenDoubleVarIncBoundCounter genDoubleVarIncBoundCounter;
	cout << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Increment();
	cout << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Increment();
	cout << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Increment();
	cout << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Reset();
	cout << genDoubleVarIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	return 0;
}