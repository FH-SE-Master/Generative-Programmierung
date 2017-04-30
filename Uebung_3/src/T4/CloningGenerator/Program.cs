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

            Console.WriteLine("################################################################ \n"
                              + "Before clone\n"
                              + "################################################################");
            Console.WriteLine("a: " + a.i);
            Console.WriteLine("a: " + a.i);
            Console.WriteLine("a: " + a.b.s);
            Console.WriteLine("a: " + a.b.arr[0]);
            Console.WriteLine("################################################################\n");
            
            // clone object
            var clone = (A)a.Clone();

            // change value
            a.i = 1337;
            a.b.s = "bye world";
            a.b.arr[0] = 0;

            Console.WriteLine("################################################################ \n"
                              + "After clone\n"
                              + "################################################################");
            Console.WriteLine("a: " + a.i);
            Console.WriteLine("a: " + a.i);
            Console.WriteLine("a: " + a.b.s);
            Console.WriteLine("a: " + a.b.arr[0] + "\n");

            Console.WriteLine("clone: " + clone.i);
            Console.WriteLine("clone: " + clone.i);
            Console.WriteLine("clone: " + clone.b.s);
            Console.WriteLine("clone: " + clone.b.arr[0]);
            Console.WriteLine("################################################################\n");

            Console.ReadLine();
        }
    }
}
