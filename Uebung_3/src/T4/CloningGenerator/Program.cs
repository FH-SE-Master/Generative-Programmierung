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

            Console.WriteLine("####################################################### \n"
                            + "Before clone\n"
                            + "#######################################################");
            Console.WriteLine("a.i    : " + a.i);
            Console.WriteLine("a.b.s  : " + a.b.s);
            Console.WriteLine("a.b.arr: " + a.b.arr[0]);
            Console.WriteLine("#######################################################\n");
            
            // clone object
            var clone = (A)a.Clone();

            // change value
            a.i = 1337;
            a.b.s = "bye world";
            a.b.arr[0] = 0;

            Console.WriteLine("####################################################### \n"
                            + "After clone\n"
                            + "#######################################################");
            Console.WriteLine("a.i    : " + a.i);
            Console.WriteLine("a.b.s  : " + a.b.s);
            Console.WriteLine("a.b.arr: " + a.b.arr[0] + "\n");

            Console.WriteLine("clone.i    : " + clone.i);
            Console.WriteLine("clone.b.s  : " + clone.b.s);
            Console.WriteLine("clone.b.arr: " + clone.b.arr[0] + "\n");
            Console.WriteLine("#######################################################\n");

            Console.ReadLine();
        }
    }
}
