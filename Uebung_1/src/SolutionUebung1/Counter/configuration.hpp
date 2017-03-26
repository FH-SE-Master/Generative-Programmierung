#pragma once
#ifndef _configuration_h_
#define _configuration_h_

#include "counter.hpp"
#include "boundedCounter.hpp"

// Integer config template
template<typename Type, typename Init, typename Inc>
struct CounterConfig {
	typedef Type ValueType;
	typedef Init InitVal;
	typedef Inc IncVal;
};

// Integer config template
template<typename Type, typename Init, typename Increment = IntValue<1>>
struct IncCounterConfig : public CounterConfig<Type, Init, Increment> {
	typedef IncCounterConfig Config;
	typedef Counter<Config> Counter;
};

// partial template bounded coutner config
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

// Then template
template<typename Then, typename Else>
struct IF<false, Then, Else> {
	typedef Else RET;
};

enum CounterType {increment, bounded};
template<CounterType counterType = increment, typename Type = int, typename Init = IntValue<0>, typename Inc = IntValue<1>, typename Bound = IntValue<0>>
class CounterConfigurationGenerator {
private:
	enum { isBounded = counterType == bounded };

public:
	typedef typename IF<isBounded, BoundedCounterConfig<Type, Init, Bound, Inc>,
		IncCounterConfig<Type, Init, Inc>>::RET::Config Config;
};
// If will not work with nested IF. Think will need ElseIf
/*
enum CounterType { intInc, intIncBounded, varIntInc, varIntIncBounded,
doubInc, doubIncBounded, varDoubInc, varDoubIncBounded };
template<CounterType counterType, typename Init, typename Inc = IntValue<1>, typename Bound = IntValue<0>>
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
IF<isIntIncBounded, BoundedCounterConfig<int, Init, Bound, IntValue<1>>,
IF<isVarIntInc, IncCounterConfig<int, Init, Inc>,
IF<isVarIntIncBounded, BoundedCounterConfig<int, Init, Bound, Inc>,
IF<isDoubInc, IncCounterConfig<double, Init, DoubleValue_1_0>,
IF<isDoubIncBounded, BoundedCounterConfig<double, Init, Bound, DoubleValue_1_0>,
IF<isVarDoubInc, IncCounterConfig<double, Init, Inc>, BoundedCounterConfig<double, Init, Bound, Inc>>>>>>>>::RET::Config Config;
};
*/
#endif