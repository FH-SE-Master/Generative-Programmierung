#ifndef _DO_h
#define _DO_h

#include "If.hpp"

template<typename Statement, typename Condition>
struct DO {
	typedef typename Statement::Next NewStatement;
	static void exec() {
		Statement::exec();

		IF<Condition::Code<NewStatement>::RET,
			DO<NewStatement, Condition>,
			Stop
		>::RET::exec();
	}
};

#endif
