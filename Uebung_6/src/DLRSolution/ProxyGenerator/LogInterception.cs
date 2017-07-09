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
        public bool ProceedEnabled { get; set; } 
        public bool EnforceResultEnabled { get; set; } 
        public object EnforcedResult { get; set; } 
        
        public void Before()
        {
            Console.WriteLine("Before called");
        }

        public void After()
        {
            Console.WriteLine("After called");
        }

        public object Around(Delegate proceed, object[] args)
        {
            object result = null;
            Console.WriteLine("around (before)");

            if (ProceedEnabled)
            {
                Console.WriteLine("around (procceding)");
                result = proceed.DynamicInvoke();
                if (EnforceResultEnabled)
                {
                    Console.WriteLine("around (enforced result)");
                    result = EnforcedResult;
                }
                else
                {
                    Console.WriteLine("around (result not enforced)");
                }
                Console.WriteLine("around (proceeded)");
            }
            else
            {
                Console.WriteLine("around (proceed disabled)");
            }

            Console.WriteLine("around (after)");

            return result;
        }
    }
}