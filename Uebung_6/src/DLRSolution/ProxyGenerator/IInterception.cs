using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProxyGenerator
{
    /// <summary>
    /// This interface specifies an interceptor
    /// </summary>
    public interface IInterception
    {
        /// <summary>
        /// Called before the method invocation
        /// </summary>
        void Before();

        /// <summary>
        /// Called after the method invocation
        /// </summary>
        void After();

        /// <summary>
        /// Around method which acn prevent method to be called
        /// </summary>
        /// <param name="proceed">the delegate representing the object the mthod was called on</param>
        /// <param name="args">the mthod arguments</param>
        /// <returns>the result, null if tis an void method</returns>
        object Around(Delegate proceed, object[] args);
    }
}