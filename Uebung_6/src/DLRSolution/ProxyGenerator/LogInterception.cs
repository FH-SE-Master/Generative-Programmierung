using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProxyGenerator
{
    /// <summary>
    /// The log interceptor implementation for IInterception
    /// </summary>
    public class LogInterception : IInterception
    {
        public void Before()
        {
            Console.WriteLine("Before called");
        }

        public void After()
        {
            Console.WriteLine("Before called");
        }

        public object Around(Delegate proceed, object[] args)
        {
            object result = null;
            Console.WriteLine("around (before)");

            if ((!args.Any() || args[0].Equals("gp")))
            {
                Console.WriteLine("procceding");
                result = proceed.DynamicInvoke();
                if (result != null && (int) result < 10)
                {
                    result = 10;
                }
                Console.WriteLine("proceeded");
            }

            Console.WriteLine("around (after)");

            return result;
        }
    }
}