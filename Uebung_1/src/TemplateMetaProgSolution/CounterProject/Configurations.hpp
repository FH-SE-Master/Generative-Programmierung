#ifndef _Configurations_h
#define _Configurations_h

#include "Counter.hpp"

template<typename Init_>
struct IntCounterConfig {
	typedef int ValueType;
	typedef  Init_ Init;
	typedef IntCounterConfig Config;

	typedef Counter<Config> Counter;
};


#endif