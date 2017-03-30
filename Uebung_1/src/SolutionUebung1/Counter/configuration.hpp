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

template<typename Type, typename Init, typename Inc = IntValue<1>>
struct IncCounterConfig : public CounterConfig<Type, Init, Inc> {
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
	typedef typename IF<isIntInc, Counter<IncCounterConfig<int, Init, IntValue<1>>>,
		typename IF<isIntIncBounded, BoundedCounter<Counter<BoundedCounterConfig<int, Init, Bound, IntValue<1>>>>,
		typename IF<isVarIntInc, Counter<IncCounterConfig<int, Init, Inc>>,
		typename IF<isVarIntIncBounded, BoundedCounter<Counter<BoundedCounterConfig<int, Init, Bound, Inc>>>,
		typename IF<isDoubInc, Counter<IncCounterConfig<double, Init, DoubleValue_1_0>>,
		typename IF<isDoubIncBounded, BoundedCounter<Counter<BoundedCounterConfig<double, Init, Bound, DoubleValue_1_0>>>,
		typename IF<isVarDoubInc, Counter<IncCounterConfig<double, Init, Inc>>,
		BoundedCounter<Counter<BoundedCounterConfig<double, Init, Bound, Inc>>>>
			::RET>::RET>::RET>::RET>::RET>::RET>::RET Counter;
};
#endif