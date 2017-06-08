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
            var firstObj = new Test();
            var secondObj = new Test();
            var thirdObj = new Test();
            var firstInterceptor = new LogInterception<ITest>();
            var secondInterceptor = new LogInterception<ISecondTest>();
            var thirdInterceptor = new LogInterception<Test>();

            try
            {
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("DynamicProxy Tests ProxyGenerator.Create<ITest>():");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testNoInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                InvokeMethodsITest(firstObj);
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testWithInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                // Create proxy and apply interceptor on ITest interface methods
                firstObj = (Test)ProxyGenerator.Create(firstObj, firstInterceptor);
                InvokeMethodsITest(firstObj);
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("");
                
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("DynamicProxy Tests ProxyGenerator.Create<ISecondTest>():");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testNoInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                InvokeMethodsITest(secondObj);
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testWithInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                // Create proxy and apply interceptor on ISecondTest interface methods
                secondObj = (Test)ProxyGenerator.Create(secondObj, secondInterceptor);
                InvokeMethodsITest(secondObj);
                Console.WriteLine("-------------------------------------------------");

                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("DynamicProxy Tests ProxyGenerator.Create<Test>():");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testNoInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                InvokeMethodsITest(thirdObj);
                Console.WriteLine("-------------------------------------------------");
                Console.WriteLine("testWithInterceptor:");
                Console.WriteLine("-------------------------------------------------");
                // Create proxy and apply interceptor on Test methods
                thirdObj = ProxyGenerator.Create(thirdObj, thirdInterceptor);
                InvokeMethodsITest(thirdObj);
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
        private static void InvokeMethodsITest(Test obj)
        {
            // ITest methods 
            Console.WriteLine($"DoStuff(1, 2): {obj.DoStuff(1, 2)}");
            Console.WriteLine($"DoStuff(2, 2): {obj.DoStuff(2, 2)}");
            Console.WriteLine($"DoStuff(4, 4): {obj.DoStuff(4, 4)}");
            obj.DoVoidStuff();
            // ISecondTest methods 
            Console.WriteLine($"DoOtherStuff: {obj.DoOtherStuff()}");
            Console.WriteLine($"DoOtherStuff: {obj.DoOtherStuff()}");
            Console.WriteLine($"DoOtherStuff: {obj.DoOtherStuff()}");
            obj.DoOtherVoidStuff();
        }
    }
}