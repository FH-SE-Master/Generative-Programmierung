using System;

namespace Reflection.Emit
{
    /// <summary>
    /// Test programm for testing the generated proxy and interceptor
    /// </summary>
    class Program
    {
        static void Main(string[] args)
        {
            ITest firstObj = new Test();
            ISecondTest secondObj = new Test();
            var firstInterceptor = new LogInterception<ITest>();
            var secondInterceptor = new LogInterception<ISecondTest>();

            try
            {
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("DynamicProxy Tests ProxyGenerator.Create<ITest>():");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testNoInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                InvokeMethodsITest(firstObj);
                InvokeMethodsISecondText((ISecondTest)firstObj);
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testWithInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                // Create proxy an apply interceptor on ITest interface methods
                firstObj = ProxyGenerator.Create<ITest>(firstObj, firstInterceptor);
                InvokeMethodsITest(firstObj);
                InvokeMethodsISecondText((ISecondTest)firstObj);
                Console.WriteLine("-------------------------------------------------");


                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("DynamicProxy Tests ProxyGenerator.Create<ISecondTest>():");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testNoInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                InvokeMethodsITest((ITest)secondObj);
                InvokeMethodsISecondText(secondObj);
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testWithInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                // Create proxy an apply interceptor on ISecondTest interface methods
                secondObj = ProxyGenerator.Create<ISecondTest>(secondObj, secondInterceptor);
                InvokeMethodsITest((ITest)secondObj);
                InvokeMethodsISecondText(secondObj);
                Console.WriteLine("-------------------------------------------------");
            }
            catch (Exception e)
            {
                Console.WriteLine($"Exception occured: {e}");
            }
        }

        /// <summary>
        /// Performs method invocation on ITest instances.
        /// </summary>
        /// <param name="obj">the ITest instance</param>
        private static void InvokeMethodsITest(ITest obj)
        {
            Console.WriteLine($"DoStuff(1, 2): {obj.DoStuff(1, 2)}");
            Console.WriteLine($"DoStuff(2, 2): {obj.DoStuff(2, 2)}");
            Console.WriteLine($"DoStuff(4, 4): {obj.DoStuff(4, 4)}");
        }

        /// <summary>
        /// Performs method invocation on ISecondTest instances.
        /// </summary>
        /// <param name="obj">the ISecondTest instance</param>
        private static void InvokeMethodsISecondText(ISecondTest obj)
        {
            Console.WriteLine($"DoOtherStuff: {obj.DoOtherStuff()}");
            Console.WriteLine($"DoOtherStuff: {obj.DoOtherStuff()}");
            Console.WriteLine($"DoOtherStuff: {obj.DoOtherStuff()}");
        }
    }
}