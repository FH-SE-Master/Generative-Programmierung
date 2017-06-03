using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Reflection.Emit
{
    /// <summary>
    /// Specifies an interceptor which can perform before and after actions on a proxied object.
    /// </summary>
    public interface IInterception<T>
    {
        /// <summary>
        /// Action performed before the actual method execution
        /// </summary>
        /// <param name="intercepted">the intercepted object</param>
        /// <param name="methodName">the intercepted method name</param>
        void Before(T intercepted, string methodName);

        /// <summary>
        /// Action performed after the actual method execution
        /// </summary>
        /// <param name="intercepted">the intercepted object</param>
        /// <param name="methodName">the intercepted method name</param>
        void After(T intercepted, string methodName);
    }
}
