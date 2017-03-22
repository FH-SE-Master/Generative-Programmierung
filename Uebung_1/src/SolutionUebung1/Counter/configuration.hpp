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
template<typename Type, typename Init, typename Increment>
struct IncCounterConfig : public CounterConfig<Type, Init, Increment> {
	typedef IncCounterConfig Config;
	typedef Counter<Config> Counter;
};

// partial template bounded coutner config
template<typename Type, typename Init, typename Inc, typename Bound>
struct BoundedCounterConfig : public CounterConfig<Type, Init, Inc> {
	typedef Bound UpperBound;
	typedef BoundedCounterConfig Config;
	typedef BoundedCounter<Counter<Config>> Counter;
};
#endif