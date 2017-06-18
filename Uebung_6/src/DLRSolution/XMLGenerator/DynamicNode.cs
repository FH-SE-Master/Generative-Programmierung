using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLGenerator
{
    public class DynamicNode : DynamicObject
    {
        private readonly DynamicNode _parent;
        private readonly string _name;
        private readonly string _value;
        private readonly List<DynamicNode> _children = new List<DynamicNode>();

        public DynamicNode BeginChildElements => new DynamicNode(null, null, this);
        public DynamicNode EndChildElements => _parent;

        public DynamicNode(string name = null, string value = null, DynamicNode node = null)
        {
            _name = name;
            _value = value;
            _parent = node;
            _parent?._children.Add(this);
        }

        public string Generate()
        {
            var sb = new StringBuilder();

            Generate(sb);

            return sb.ToString();
        }

        public void Generate(StringBuilder sb)
        {
            if (!string.IsNullOrEmpty(_name))
            {
                sb.Append($"<{_name}");
                if (_children.Any())
                {
                    sb.Append("/>");
                }
                else
                {
                    sb.Append(">");
                }
                if (!string.IsNullOrEmpty(_value))
                {
                    sb.AppendLine(_value);
                }
            }
            _children.ForEach(child => child.Generate(sb));
            if (!string.IsNullOrEmpty(_name))
            {
                sb.Append($"<{_name}/>");
            }
        }

        public override bool TryGetMember(GetMemberBinder binder, out object result)
        {
            result = new DynamicNode(binder.Name, null, _parent);
            return true;
        }

        public override bool TryInvokeMember(InvokeMemberBinder binder, object[] args, out object result)
        {
            result = new DynamicNode(binder.Name, (args.Any() ? args[0] : null)?.ToString(), _parent);
            return true;
        }
    }
}