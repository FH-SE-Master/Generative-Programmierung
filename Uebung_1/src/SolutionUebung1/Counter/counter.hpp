#ifndef _counter_h
#define _counter_h

// Counter template
template<typename Configuration>
class Counter {

protected:
	// Custom Type to shorten call path
	typedef typename Configuration::Config Config;
	// Custom Type to shorten call path
	typedef typename Config::ValueType ValueType;

protected:
	// The held value reference
	ValueType *value;

public:
	// Initialize value for given ValueType
	Counter() {
		Reset();
	}

	// delete held value
	~Counter() {
		delete value;
	}

	// Get actual value
	ValueType &Value() const {
		return (*value);
	}

	// Increment value by by one 
	void Increment() {
		(*value)++;
	}

	// Reset value by deleting old and creating new one
	void Reset() {
		if (value != nullptr) {
			delete value;
		}
		value = new ValueType(Config::InitVal::value);
	}
};

#endif
