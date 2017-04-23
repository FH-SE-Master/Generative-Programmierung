using CloningGenerator;
using System;

namespace ClassGenerator
{
    class Program
    {
        static void Main(string[] args)
        {
            var a = new A();
            var b = new B
            {
                s = "Hello world",
                arr = new[] { 1, 2, 3 }
            };

            a.b = b;
            var clone = (A)a.Clone();

            Console.WriteLine(a.i);
            Console.WriteLine(a.b.s);
            Console.WriteLine(a.b.arr[0]);

            a.i = 1337;
            a.b.s = "bye world";
            a.b.arr[0] = 0;

            Console.WriteLine(clone.i);
            Console.WriteLine(clone.b.s);
            Console.WriteLine(clone.b.arr[0]);

            Console.ReadLine();
        }
    }
}
