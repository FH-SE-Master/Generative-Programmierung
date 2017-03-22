#ifndef _varIncrementCounter_h
#define _varIncrementCounter_h

#include "counter.hpp"

// partial template bounded counter
template<typename BaseCounter>
class VarIncrementCounter : public BaseCounter {

private:
	// Custom Type to shorten call path
	typedef typename BaseCounter::Config::Config Config;
	// Custom Type to shorten call path
	typedef typename BaseCounter::ValueType ValueType;

protected:
	ValueType *inc;

public:
	VarIncrementCounter() : BaseCounter() {
		inc = new ValueType(Config::IncBound::value);
	}

	~VarIncrementCounter() {
		delete inc;
	}

	void Increment() {
		int tmp = (Value() + (*inc));
		delete value;
		value = new ValueType(tmp);
	}
};

// partial template bounded coutner config
template<typename InitBound, typename IncrementBound>
struct VarIncrementBoundIntCounterConfig {
	typedef int ValueType;
	typedef  InitBound InitVal;
	typedef IncrementBound IncBound;
	typedef VarIncrementBoundIntCounterConfig Config;
	typedef VarIncrementCounter<Counter<Config>> Counter;
};

#endif
