using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Reflection.Emit
{
    /// <summary>
    /// 
    /// </summary>
    public class LogInterception<T> : IInterception<T>
    {
        public void After(T intercepted, string methodName)
        {
            Console.WriteLine($"{typeof(LogInterception<T>).FullName}#After  called for '{intercepted}#{methodName}'");
        }

        public void Before(T intercepted, string methodName)
        {
            Console.WriteLine($"{typeof(LogInterception<T>).FullName}#Before called for '{intercepted}#{methodName}'");
        }
    }
}
