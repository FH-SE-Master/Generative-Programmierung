using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Reflection.Emit
{
    /// <summary>
    /// Implemention of IInterception for logging the method interception.
    /// </summary>
    public class LogInterception<T> : IInterception<T>
    {
        private readonly string _typeFullName;

        public LogInterception()
        {
            var type = typeof(LogInterception<T>);
            _typeFullName = type.Namespace + "." + type.Name;
        }

        public void After(T intercepted, string methodName)
        {
            Console.WriteLine($"{_typeFullName}#After  called for '{intercepted}#{methodName}'");
        }

        public void Before(T intercepted, string methodName)
        {
            Console.WriteLine($"{_typeFullName}#Before called for '{intercepted}#{methodName}'");
        }
    }
}
