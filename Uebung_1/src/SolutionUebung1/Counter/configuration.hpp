#pragma once
#ifndef _configuration_h_
#define _configuration_h_

#include "counter.hpp"

// Integer config template
template<typename Init>
struct IntCounterConfig {
	// the held type of the value
	typedef int ValueType;
	// the config initializer
	typedef  Init InitVal;
	// the config type
	typedef IntCounterConfig Config;
	// the held counter
	typedef Counter<Config> Counter;
};

#endif