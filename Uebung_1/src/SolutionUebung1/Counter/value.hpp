#ifndef _value_h
#define _value_h

// Int value template
template<int n>
struct IntValue {
	static const int value = n;
};

struct DoubleValue_0_5 {
	static const double value;
};
const double DoubleValue_0_5::value(0.5);

#endif