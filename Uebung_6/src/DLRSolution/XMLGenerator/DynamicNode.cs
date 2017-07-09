using System;
using System.CodeDom;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLGenerator
{
    /// <summary>
    /// This class represents a dynamic node of the xml generator
    /// </summary>
    public class DynamicNode : DynamicObject
    {
        private readonly DynamicNode _parent;
        private readonly string _name;
        private readonly string[] _value;
        private readonly List<DynamicNode> _children = new List<DynamicNode>();

        public DynamicNode BeginChildElements => new DynamicNode(null, null, this);
        public DynamicNode EndChildElements => _parent;
        
        /// <summary>
        /// Constructing a xml node.
        /// </summary>
        /// <param name="name">the tag name</param>
        /// <param name="value">the tag attributes and values.</param>
        /// <param name="parent">the parent if this is a child node</param>
        public DynamicNode(string name = null, string[] value = null, DynamicNode parent = null)
        {
            _name = name;
            _value = value ?? new string[0];
            _parent = parent;
            _parent?._children.Add(this);
        }

        /// <summary>
        /// Generates the xml structure from this node on through all of its children
        /// </summary>
        /// <returns>the generated xml structure in form of an string</returns>
        public string Generate()
        {
            var sb = new StringBuilder();

            Generate(sb);

            return sb.ToString();
        }

        /// <summary>
        /// Creates a new node for the addressed member.
        /// </summary>
        /// <param name="binder">the binder which provides the member name</param>
        /// <param name="result">the out parameter representing the newly created node</param>
        /// <returns>always true because always a new node gets generated</returns>
        public override bool TryGetMember(GetMemberBinder binder, out object result)
        {
            result = new DynamicNode(binder.Name, null, _parent);
            return true;
        }

        /// <summary>
        /// Creates a new node for the addressed method.
        /// </summary>
        /// <param name="binder">the binder providing the method name</param>
        /// <param name="args">the arguments for the method which represent the value of the node</param>
        /// <param name="result">the out parameter holding the newly create node</param>
        /// <returns>always true because always a new node gets generated</returns>
        public override bool TryInvokeMember(InvokeMemberBinder binder, object[] args, out object result)
        {
            if ((args != null) && (args.Length > 3))
            {
                throw new ArgumentException(
                    $"Member invocation with to many arguments found. maxCount=3, foundCount={args.Length}");
            }
            string[] attributes = args?.Select(arg => arg.ToString()).ToArray();

            result = new DynamicNode(binder.Name, attributes, _parent);
            return true;
        }

        /// <summary>
        /// Private helper which generates the xml string for this and all of its child nodes.
        /// </summary>
        /// <param name="sb">the string builder to append the generated nodes on</param>
        private void Generate(StringBuilder sb)
        {
            bool nameNotPresent = string.IsNullOrEmpty(_name);
            bool valuePresent = _value.Any();
            bool hasChildren = _children.Any();

            // only if the node defines a tag name, which root node doesn't
            if (!nameNotPresent)
            {
                sb.Append($"<{_name}");
                // If more than 1 valie is present the tag has a attribute
                if (valuePresent && (_value.Length >= 2))
                {
                    sb.Append($" {_value[1]}=\"");
                    // if 3 values are present then the tag attribute has a value
                    if (_value.Length == 3)
                    {
                        sb.Append(_value[2]);
                    }
                    sb.Append("\"");
                }
                // Empty tag only if no values and children are presnet
                if (!valuePresent  && !hasChildren)
                {
                    sb.Append("/>").Append(Environment.NewLine);
                }
                else
                {
                    sb.Append(">");
                }
            }
            // Add tag value if present
            if (valuePresent)
            {
                sb.Append(_value[0]);
            }

            // Generate xml for child nodes
            _children.ForEach(child => child.Generate(sb));

            // End tag if no empty tag or had children
            if (!nameNotPresent && (valuePresent || hasChildren))
            {
                sb.Append($"</{_name}>").Append(Environment.NewLine);
            }
        }
    }
}