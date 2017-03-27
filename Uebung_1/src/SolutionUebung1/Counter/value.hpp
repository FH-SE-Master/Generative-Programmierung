#ifndef _value_h
#define _value_h

template<int n>
struct IntValue {
	static const int value = n;
};
struct DoubleValue_1_0 {
	static const double value;
};
struct DoubleValue_0_5 {
	static const double value;
};
struct DoubleValue_2_0 {
	static const double value;
};

const double DoubleValue_0_5::value(0.5);
const double DoubleValue_1_0::value(1.0);
const double DoubleValue_2_0::value(2.0);

#endif