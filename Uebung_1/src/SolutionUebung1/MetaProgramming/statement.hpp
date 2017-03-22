#ifndef _statement_
#define _statement_

struct Stop {
	static void exec() {}
};

// If template 
template<bool Condition, typename Then, typename Else>
struct IF {
	typedef Then RET;
};

// Then template
template<typename Then, typename Else>
struct IF<false, Then, Else> {
	typedef Else RET;
};

// Do statement
template<typename Statement, typename Condition>
struct DO {
	typedef typename Statement::Next NextStatement;
	static void exec() {
		Statement::exec();

		IF<Condition::Code<NextStatement>::RET,
			DO<NextStatement, Condition>,
			Stop
		>::RET::exec();
	}
};
#endif