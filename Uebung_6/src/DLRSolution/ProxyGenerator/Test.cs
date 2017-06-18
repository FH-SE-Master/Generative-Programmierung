using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProxyGenerator
{
    public class Test
    {
        private readonly object _data;

        public Test(object data = null)
        {
            _data = data;
        }

        public void DoSomething()
        {
            Console.WriteLine("Did something");
        }

        public object GetData()
        {
            Console.WriteLine("Before getter returns result");
            return _data;
        }
    }
}
