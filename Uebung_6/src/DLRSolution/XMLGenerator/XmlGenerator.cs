using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLGenerator
{
    /// <summary>
    /// The class represneting the dynamic object for the xml generator
    /// </summary>
    public class XmlGenerator : DynamicObject
    {
        /// <summary>
        /// The root node
        /// </summary>
        private readonly DynamicNode _node = new DynamicNode();

        private XmlGenerator()
        {
            // Not alolowed to be instantiated from outside this class
        }

        /// <summary>
        /// Factory method for creating the XML generator instance
        /// </summary>
        /// <returns></returns>
        public static dynamic Create()
        {
            return new XmlGenerator();
        }

        /// <summary>
        /// Find existing member by name or create new one.
        /// </summary>
        /// <param name="binder">the binder providing the member name</param>
        /// <param name="result">out parameter holding the newly create node</param>
        /// <returns>always true, because will always create a new node</returns>
        public override bool TryGetMember(GetMemberBinder binder, out object result)
        {
            result = new DynamicNode(binder.Name, null, _node);

            return true;
        }
    }
}