using System;

namespace CloningGenerator
{
    // using var = System.Int32;

    class Program
    {

        public int Franz { get; set; }
        private object Fritz;
        private A a;


        static void Main(string[] args)
        {
            int x = 42;

            // const
            int i = 8, j = 2;

            if (x < 5)
            {
                if (x > 0)
                    Console.WriteLine("awesome!");

                x = 0;

                try
                {
                    Console.WriteLine(i + j);
                }
                catch { }
            }

            switch (1)
            {
                case 2:
                    break;
            }
        }
    
    }
}
