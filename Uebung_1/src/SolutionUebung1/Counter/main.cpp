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
	cout << "Inc: " << defIntCounter.Value() << endl;
	defIntCounter.Increment();
	cout << "Inc: " << defIntCounter.Value() << endl;
	defIntCounter.Increment();
	cout << "Inc: " << defIntCounter.Value() << endl;
	defIntCounter.Increment();
	cout << "Inc: " << defIntCounter.Value() << endl;
	defIntCounter.Reset();
	cout << "Reset: " << defIntCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "IncCounterConfig<int, IntValue<0>, IntValue<2>>:" << endl;
	typedef typename IncCounterConfig<int, IntValue<0>, IntValue<2>>::Counter IntIncCounter;
	IntIncCounter intIncCounter;
	cout << "Inc: " << intIncCounter.Value() << endl;
	intIncCounter.Increment();
	cout << "Inc: " << intIncCounter.Value() << endl;
	intIncCounter.Increment();
	cout << "Inc: " << intIncCounter.Value() << endl;
	intIncCounter.Increment();
	cout << "Inc: " << intIncCounter.Value() << endl;
	intIncCounter.Reset();
	cout << "Reset: " << intIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "BoundedCounterConfig<int, IntValue<1>, IntValue<2>, IntValue<1>>:" << endl;
	typedef typename BoundedCounterConfig<int, IntValue<1>, IntValue<2>, IntValue<1>>::Counter IntBoundedCounter;
	IntBoundedCounter intBoundedCounter;
	cout << "Inc: " << intBoundedCounter.Value() << endl;
	intBoundedCounter.Increment();
	cout << "Inc: " << intBoundedCounter.Value() << endl;
	intBoundedCounter.Increment();
	cout << "Inc: " << intBoundedCounter.Value() << endl;
	intBoundedCounter.Increment();
	cout << "Inc: " << intBoundedCounter.Value() << endl;
	intBoundedCounter.Reset();
	cout << "Rest: " << intBoundedCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests Double counter
	cout << "IncCounterConfig<double, DoubleValue_0_5>:" << endl;
	typedef typename IncCounterConfig<double, DoubleValue_0_5>::Counter DefDoubleCounter;
	DefDoubleCounter defDoubleCounter;
	cout << "Inc: " << defDoubleCounter.Value() << endl;
	defDoubleCounter.Increment();
	cout << "Inc: " << defDoubleCounter.Value() << endl;
	defDoubleCounter.Increment();
	cout << "Inc: " << defDoubleCounter.Value() << endl;
	defDoubleCounter.Increment();
	cout << "Inc: " << defDoubleCounter.Value() << endl;
	defDoubleCounter.Reset();
	cout << "Reset: " << defDoubleCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "IncCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0>:" << endl;
	typedef typename IncCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0>::Counter DoubleIncCounter;
	DoubleIncCounter doubleIncCounter;
	cout << "Inc: " << doubleIncCounter.Value() << endl;
	doubleIncCounter.Increment();
	cout << "Inc: " << doubleIncCounter.Value() << endl;
	doubleIncCounter.Increment();
	cout << "Inc: " << doubleIncCounter.Value() << endl;
	doubleIncCounter.Increment();
	cout << "Inc: " << doubleIncCounter.Value() << endl;
	doubleIncCounter.Reset();
	cout << "Reset: " << doubleIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "BoundedCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>:" << endl;
	typedef typename BoundedCounterConfig<double, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>::Counter DoubleBoundedCounter;
	DoubleBoundedCounter doubleBoundedCounter;
	cout << "Inc: " << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Increment();
	cout << "Inc: " << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Increment();
	cout << "Inc: " << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Increment();
	cout << "Inc: " << doubleBoundedCounter.Value() << endl;
	doubleBoundedCounter.Reset();
	cout << "Reset: " << doubleBoundedCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;
	
	// Tests Int Configurator
	cout << "CounterConfigurationGenerator<intInc, IntValue<0>>:" << endl;
	typedef typename CounterConfigurationGenerator<intInc, IntValue<0>>::Counter GenIntIncCounter;
	GenIntIncCounter genIntIncCounter;
	cout << "Inc: " << genIntIncCounter.Value() << endl;
	genIntIncCounter.Increment();
	cout << "Inc: " << genIntIncCounter.Value() << endl;
	genIntIncCounter.Increment();
	cout << "Inc: " << genIntIncCounter.Value() << endl;
	genIntIncCounter.Increment();
	cout << "Inc: " << genIntIncCounter.Value() << endl;
	genIntIncCounter.Reset();
	cout << "Reset: " << genIntIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<intIncBounded, IntValue<0>, IntValue<2>>:" << endl;
	typedef typename CounterConfigurationGenerator<intIncBounded, IntValue<0>, IntValue<2>>::Counter GenIntIncBoundCounter;
	GenIntIncBoundCounter genIntIncBoundCounter;
	cout << "Inc: " << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Increment();
	cout << "Inc: " << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Increment();
	cout << "Inc: " << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Increment();
	cout << "Inc: " << genIntIncBoundCounter.Value() << endl;
	genIntIncBoundCounter.Reset();
	cout << "Reset: " << genIntIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varIntInc, IntValue<0>, NoBound, IntValue<2>>:" << endl;
	typedef typename CounterConfigurationGenerator<varIntInc, IntValue<0>, NoBound, IntValue<2>>::Counter GenIntVarIncCounter;
	GenIntVarIncCounter genIntVarIncCounter;
	cout << "Inc: " << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Increment();
	cout << "Inc: " << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Increment();
	cout << "Inc: " << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Increment();
	cout << "Inc: " << genIntVarIncCounter.Value() << endl;
	genIntVarIncCounter.Reset();
	cout << "Reset: " << genIntVarIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varIntIncBounded, IntValue<0>, IntValue<2>, IntValue<1>>:" << endl;
	typedef typename CounterConfigurationGenerator<varIntIncBounded, IntValue<0>, IntValue<2>, IntValue<1>>::Counter GenIntVarIncBoundCounter;
	GenIntVarIncBoundCounter genIntVarIncBoundCounter;
	cout << "Inc: " << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Increment();
	cout << "Inc: " << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Increment();
	cout << "Inc: " << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Increment();
	cout << "Inc: " << genIntVarIncBoundCounter.Value() << endl;
	genIntVarIncBoundCounter.Reset();
	cout << "Reset: " << genIntVarIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	// Tests Double Configurator
	cout << "CounterConfigurationGenerator<doubInc, DoubleValue_0_5>:" << endl;
	typedef typename CounterConfigurationGenerator<doubInc, DoubleValue_0_5>::Counter GenDoubleIncCounter;
	GenDoubleIncCounter genDoubleIncCounter;
	cout << "Inc: " << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Increment();
	cout << "Inc: " << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Increment();
	cout << "Inc: " << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Increment();
	cout << "Inc: " << genDoubleIncCounter.Value() << endl;
	genDoubleIncCounter.Reset();
	cout << "Reset: " << genDoubleIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<doubIncBounded, DoubleValue_0_5, DoubleValue_2_0>:" << endl;
	typedef typename CounterConfigurationGenerator<doubIncBounded, DoubleValue_0_5, DoubleValue_2_0>::Counter GenDoubleIncBoundCounter;
	GenDoubleIncBoundCounter genDoubleIncBoundCounter;
	cout << "Inc: " << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Increment();
	cout << "Inc: " << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Increment();
	cout << "Inc: " << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Increment();
	cout << "Inc: " << genDoubleIncBoundCounter.Value() << endl;
	genDoubleIncBoundCounter.Reset();
	cout << "Reset: " << genDoubleIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varDoubInc, IntValue<0>, NoBound, DoubleValue_0_5>:" << endl;
	typedef typename CounterConfigurationGenerator<varDoubInc, DoubleValue_0_5, NoBound, DoubleValue_0_5>::Counter GenDoubleVarIncCounter;
	GenDoubleVarIncCounter genDoubleVarIncCounter;
	cout << "Inc: " << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Increment();
	cout << "Inc: " << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Increment();
	cout << "Inc: " << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Increment();
	cout << "Inc: " << genDoubleVarIncCounter.Value() << endl;
	genDoubleVarIncCounter.Reset();
	cout << "Reset: " << genDoubleVarIncCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	cout << "CounterConfigurationGenerator<varDoubIncBounded, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>:" << endl;
	typedef typename CounterConfigurationGenerator<varDoubIncBounded, DoubleValue_0_5, DoubleValue_2_0, DoubleValue_1_0>::Counter GenDoubleVarIncBoundCounter;
	GenDoubleVarIncBoundCounter genDoubleVarIncBoundCounter;
	cout << "Inc: " << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Increment();
	cout << "Inc: " << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Increment();
	cout << "Inc: " << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Increment();
	cout << "Inc: " << genDoubleVarIncBoundCounter.Value() << endl;
	genDoubleVarIncBoundCounter.Reset();
	cout << "Reset: " << genDoubleVarIncBoundCounter.Value() << endl;
	cout << "----------------------------------" << endl << endl;

	return 0;
}