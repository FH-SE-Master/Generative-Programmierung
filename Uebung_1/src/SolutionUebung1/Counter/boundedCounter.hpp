#ifndef _boundedCounter_h
#define _boundedCounter_h

// partial template bounded counter
template<typename BaseCounter>
class BoundedCounter : public BaseCounter {

protected:
	typedef typename BaseCounter::Config Config;
	typedef typename BaseCounter::ValueType ValueType;

protected:
	ValueType bound;

public:
	BoundedCounter() : bound(Config::UpperBound::value) {
	}

	void Increment() {
		ValueType tmp = value;
		if (value < bound) {
			BaseCounter::Increment();
		}
		if (value > bound) {
			value = tmp;
		}
	}
};

#endif
