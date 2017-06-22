using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace ProxyGenerator
{
    /// <summary>
    /// A test implementation of a proxied dnymic meta object class
    /// </summary>
    public class DynamicTest : IDynamicMetaObjectProvider
    {
        private IInterception _interceptor;

        public DynamicTest(IInterception interceptor = null)
        {
            _interceptor = interceptor;
        }

        public int DoSomething(int val, params string[] args)
        {
            Console.WriteLine($"Called: int DoSomething({val}, {string.Join(", ", args)})");
            return val;
        }

        public DynamicMetaObject GetMetaObject(Expression parameter)
        {
            return new ProxyGenerator(parameter, this, _interceptor);
        }
    }
}