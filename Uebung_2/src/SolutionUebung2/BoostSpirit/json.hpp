#ifndef _json_hpp_
#define _json_hpp_

#include "boost.hpp"
#include <map>
#include <string>

namespace json_parser {

	namespace qi = boost::spirit::qi;
	namespace phoenix = boost::phoenix;

	template<typename Iterator>
	struct json_grammar : qi::grammar<Iterator, qi::space_type> {
		json_grammar() : json_grammar::base_type(json) {
			
			json    =   object 
				      | array;

			object  =    qi::lit('{')
				      >> -member
				      >> qi::lit('}');

			array   =    qi::lit('[')
				      >> -element
				      >> qi::lit(']');

			member  =    pair 
				      >> *(qi::lit(',') >> pair);

			element =    value 
				      >> *(qi::lit(',') >> value);

			pair    =    literal
				      >> qi::lit(':')
				      >> value;

			value   =   literal
				      | qi::double_
				      | qi::true_
				      | qi::false_
				      | qi::lit("null")
				      | object
				      | array;

			literal = qi::lexeme['"' >> *(qi::char_ - '"') >> '"'];
		};

		qi::rule<Iterator, qi::space_type> json, object, array, element, value, pair, member;
		qi::rule<Iterator, std::string(), qi::space_type> literal;
	};
}

#endif _json_hpp_