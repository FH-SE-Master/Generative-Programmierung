#ifndef _BoundedCounter_h
#define _BoundedCounter_h

#include "Counter.hpp"

template<typename BaseCounter>
class BoundedCounter : BaseCounter {

public:
	typedef typename BaseCounter::Config Config;

private:
	typedef typename Config::ValueType ValueType;

protected:
	ValueType * bound_;

public:
	BoundedCounter() {
		bound_ = new ValueType(Config::Bound::value);
	}

	~BoundedCounter() {
		delete bound_;
	}

	void Increment() {
		if (value() < bound_) BseCounter::Increment();
	}
};

template<typename Init_, typename Bound_>
struct BoundedIntCounterConfig {
	typedef int ValueType;
	typedef  Init_ Init;	
	typedef Bound_ Bound;
	typedef BoundedIntCounterConfig Config;
	typedef BoundedCounter<BoundedIntCounterConfig> Counter;
};

#endif
