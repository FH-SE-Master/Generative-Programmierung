using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Reflection.Emit
{
    public interface ITest
    {
        int DoSomething(int i, int j);
    }

    public class Test : ITest
    {
        public int DoSomething(int i, int j)
        {
            return i + j;
        }
    }

    public class Program 
    {
        public static void Main(string[] args)
        {
            var test = new Test();
            Console.WriteLine($"Via test object: {test.DoSomething(1,2)}");

            var proxy = (ITest) ProxyGenerator.Create(test);
            Console.WriteLine($"Via proxy object: {proxy.DoSomething(1, 2)}");

            Console.ReadLine();
        }
    }
}
