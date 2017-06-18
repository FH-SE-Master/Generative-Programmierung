using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLGenerator
{
    public class XmlGenerator : DynamicObject
    {
        private readonly DynamicNode _node = new DynamicNode();

        private XmlGenerator()
        {
            // Not alolowed to be instantiated from outside this class
        }

        public static dynamic Create()
        {
            return new XmlGenerator();
        }

        /// <summary>
        /// Find existing member by name or create new one.
        /// </summary>
        /// <param name="binder">the member binder</param>
        /// <param name="result">the resulting member</param>
        /// <returns>always true, becuase will create new member if not found</returns>
        public override bool TryGetMember(GetMemberBinder binder, out object result)
        {
            result = new DynamicNode(binder.Name, null, _node);

            return true;
        }
    }
}