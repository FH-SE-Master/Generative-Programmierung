#ifndef _varIncrementBoundCounter_h
#define _varIncrementBoundCounter_h

#include "counter.hpp"
#include "configuration.hpp"

// partial template bounded counter
template<typename BaseCounter>
class VarIncrementBoundCounter : public BaseCounter {

private:
	// Custom Type to shorten call path
	typedef typename BaseCounter::Config::Config Config;
	// Custom Type to shorten call path
	typedef typename BaseCounter::ValueType ValueType;

protected:
	ValueType *bound;
	ValueType *inc;

public:
	VarIncrementBoundCounter() : BaseCounter() {
		bound = new ValueType(Config::UpperBound::value);
		inc = new ValueType(Config::IncBound::value);
	}

	~VarIncrementBoundCounter() {
		delete bound;
		delete inc;
	}

	void Increment() {
		if (Value() < *bound) {
			int tmp = (Value() + (*inc));
			delete value;
			value = new ValueType(tmp);
		}
	}
};

// partial template bounded coutner config
template<typename InitBound, typename UpperBound, typename IncrementBound>
struct VarIncrementBoundIntCounterConfig {
	typedef int ValueType;
	typedef  InitBound InitVal;
	typedef IncrementBound IncBound;
	typedef UpperBound UpperBound;
	typedef VarIncrementBoundIntCounterConfig Config;
	typedef VarIncrementBoundCounter<Counter<Config>> Counter;
};

#endif
