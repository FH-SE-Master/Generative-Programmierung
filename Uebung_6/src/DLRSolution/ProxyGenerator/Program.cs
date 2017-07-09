using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProxyGenerator
{
    /// <summary>
    /// The main program for the tests
    /// </summary>
    class Program
    {
        /// <summary>
        /// MAin holding the test code
        /// </summary>
        /// <param name="args">the arguments for the man method</param>
        static void Main(string[] args)
        {
            var interceptor = new LogInterception();
            dynamic obj = new DynamicTest();
            dynamic interceptedObj = new DynamicTest(interceptor);

            Console.WriteLine("-------------------------------------------");
            Console.WriteLine($"Val: {1} / no interceptor");
            AssertEquals(1, (int) obj.DoSomething(1, "firstArg", "secondArg", "thirdArg"));
            Console.WriteLine("-------------------------------------------");

            Console.WriteLine("-------------------------------------------");
            Console.WriteLine($"All disabled");
            interceptedObj.DoSomething(1, "firstArg", "secondArg", "thirdArg");
            Console.WriteLine("-------------------------------------------");

            Console.WriteLine("-------------------------------------------");
            Console.WriteLine($"Val: {1} / proceed enabled");
            interceptor.ProceedEnabled = true;
            AssertEquals(1, (int) interceptedObj.DoSomething(1, "firstArg", "secondArg", "thirdArg"));
            Console.WriteLine("-------------------------------------------");

            Console.WriteLine("-------------------------------------------");
            Console.WriteLine($"Val: {10} / proceed - enforce enabled");
            interceptor.ProceedEnabled = true;
            interceptor.EnforceResultEnabled = true;
            interceptor.EnforcedResult = 10;
            AssertEquals(10, (int) interceptedObj.DoSomething(11, "firstArg", "secondArg", "thirdArg"));
            Console.WriteLine("-------------------------------------------");

            Console.WriteLine("-------------------------------------------");
            Console.WriteLine($"Val: 'I am the enforced result' / proceed - enforce enabled");
            interceptor.ProceedEnabled = true;
            interceptor.EnforceResultEnabled = true;
            interceptor.EnforcedResult = "I am the enforced result";
            AssertEquals(interceptor.EnforcedResult,
                (string) interceptedObj.DoSomething(1, "firstArg", "secondArg", "thirdArg"));
            Console.WriteLine("-------------------------------------------");

            Console.WriteLine("-------------------------------------------");
            Console.WriteLine($"Val: 'null' / proceed - enforce enabled");
            interceptor.ProceedEnabled = true;
            interceptor.EnforceResultEnabled = true;
            interceptor.EnforcedResult = null;
            AssertEquals(interceptor.EnforcedResult,
                interceptedObj.DoSomething(1, "firstArg", "secondArg", "thirdArg"));
            Console.WriteLine("-------------------------------------------");
        }

        /// <summary>
        /// Asserts the results 
        /// </summary>
        /// <param name="expected">the expected value</param>
        /// <param name="actual">the actual value</param>
        private static void AssertEquals(object expected, object actual)
        {
            if (expected == null && actual == null)
            {
                Console.WriteLine($"Testing: null == null = True");
            }
            else if (expected != null && actual == null)
            {
                Console.WriteLine($"Testing: {expected} == null = False");
            }
            else if (expected == null && actual != null)
            {
                Console.WriteLine($"Testing: null == {actual} = False");
            }
            else
            {
                Console.WriteLine($"Testing: {expected} == {actual} = {expected?.Equals(actual)}");
            }
        }
    }
}