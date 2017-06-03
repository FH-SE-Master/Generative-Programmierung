using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Reflection.Emit
{
    public class TestProxy: ITest
    {

        private readonly ITest wrapped;

        public TestProxy(ITest wrapped)
        {
            this.wrapped = wrapped;
        }

        public int DoSomething(int i, int j)
        {
            return i + j;
        }
    }
}
