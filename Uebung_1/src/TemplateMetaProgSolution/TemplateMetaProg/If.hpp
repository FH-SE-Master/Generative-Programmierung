#ifndef _IF_h
#define _IF_h

struct Stop {
	static void exec() {}
};

template<bool Condition, typename Then, typename Else>
struct IF {
	typedef Then RET;
};

template<typename Then, typename Else>
struct IF<false, Then, Else> {
	typedef Else RET;
};

#endif