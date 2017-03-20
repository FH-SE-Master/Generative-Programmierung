#ifndef _Counter_h
#define _Counter_h

template<typename Config_>
class Counter {
	public:
		typedef typename Config_::Config Config;

	private:
		typedef typename Config::ValueType ValueType;

	protected:
		ValueType *value_;

	public:
		Counter() {
			value_ = new ValueType(Config::Init::value);
		}

		~Counter() {
			delete value_;
		}

		ValueType value() const  {
			return (*value_);
		}

		void Increment() {
			(*value_)++;
		}

		void Reset() {
			delete value_;
			value_ = new ValueType();
		}
};

#endif
