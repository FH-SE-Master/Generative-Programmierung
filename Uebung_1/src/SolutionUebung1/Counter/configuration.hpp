#pragma once
#ifndef _configuration_h_
#define _configuration_h_

#include "counter.hpp"
#include "boundedCounter.hpp"

struct NoVarInc {
};
struct NoBound {
};

template<typename Type, typename Init, typename Inc>
struct CounterConfig {
	typedef Type ValueType;
	typedef Init InitVal;
	typedef Inc IncVal;
};

template<typename Type, typename Init, typename Increment = IntValue<1>>
struct IncCounterConfig : public CounterConfig<Type, Init, Increment> {
	typedef IncCounterConfig Config;
	typedef Counter<Config> Counter;
};

template<typename Type, typename Init, typename Bound, typename Inc = IntValue<1>>
struct BoundedCounterConfig : public CounterConfig<Type, Init, Inc> {
	typedef Bound UpperBound;
	typedef BoundedCounterConfig Config;
	typedef BoundedCounter<Counter<Config>> Counter;
};

template<bool Condition, typename Then, typename Else>
struct IF {
	typedef Then RET;
};

template<typename Then, typename Else>
struct IF<false, Then, Else> {
	typedef Else RET;
};

enum CounterType { 
	intInc, intIncBounded, varIntInc, varIntIncBounded,
	doubInc, doubIncBounded, varDoubInc, varDoubIncBounded 
};

template<CounterType counterType, typename Init, typename Bound = NoBound, typename Inc = NoVarInc>
class CounterConfigurationGenerator {
private:
	enum {
		isIntInc = counterType == intInc,
		isIntIncBounded = counterType == intIncBounded,
		isVarIntInc = counterType == varIntInc,
		isVarIntIncBounded = counterType == varIntIncBounded,
		isDoubInc = counterType == doubInc,
		isDoubIncBounded = counterType == doubIncBounded,
		isVarDoubInc = counterType == varDoubInc,
		isVarDoubIncBounded = counterType == varDoubIncBounded
};

public:
	typedef typename IF<isIntInc, IncCounterConfig<int, Init, IntValue<1>>,
		typename IF<isIntIncBounded, BoundedCounterConfig<int, Init, Bound, IntValue<1>>,
		typename IF<isVarIntInc, IncCounterConfig<int, Init, Inc>,
		typename IF<isVarIntIncBounded, BoundedCounterConfig<int, Init, Bound, Inc>,
		typename IF<isDoubInc, IncCounterConfig<double, Init, DoubleValue_1_0>,
		typename IF<isDoubIncBounded, BoundedCounterConfig<double, Init, Bound, DoubleValue_1_0>,
		typename IF<isVarDoubInc, IncCounterConfig<double, Init, Inc>, BoundedCounterConfig<double, Init, Bound, Inc>>::RET::Counter>::RET::Counter>::RET::Counter>::RET::Counter>::RET::Counter>::RET::Counter>::RET::Counter Counter;
};
#endif