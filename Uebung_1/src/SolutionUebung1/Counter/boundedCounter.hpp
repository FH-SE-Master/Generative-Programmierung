#ifndef _boundedCounter_h
#define _boundedCounter_h

#include "counter.hpp"

// partial template bounded counter
template<typename BaseCounter>
class BoundedCounter : public BaseCounter {

protected:
	typedef typename BaseCounter::Config Config;
	typedef typename BaseCounter::ValueType ValueType;

protected:
	ValueType * bound;

public:
	BoundedCounter() : BaseCounter() {
		bound = new ValueType(Config::UpperBound::value);
	}

	~BoundedCounter() {
		delete bound;
	}

	void Increment() {
		if (Value() < *bound) {
			BaseCounter::Increment();
		}
	}
};

// partial template bounded coutner config
template<typename Init, typename Bound>
struct BoundedIntCounterConfig {
	typedef int ValueType;
	typedef  Init InitVal;
	typedef Bound UpperBound;
	typedef BoundedIntCounterConfig Config;
	typedef BoundedCounter<Counter<Config>> Counter;
};

#endif
