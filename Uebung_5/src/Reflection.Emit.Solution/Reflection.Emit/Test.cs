using System;

namespace Reflection.Emit
{
    /// <summary>
    /// Implemention for testing proxied instance.
    /// Methods are marked as virtual, so that proxy class can override them.
    /// </summary>
    public class Test : ITest, ISecondTest
    {
        public virtual int DoStuff(int i, int j)
        {
            return i + j;
        }

        public virtual void DoVoidStuff()
        {
            Console.WriteLine("void DoVoidStuff() called");
        }


        public virtual string DoOtherStuff()
        {
            return "string DoOtherStuff() called";
        }

        public virtual void DoOtherVoidStuff()
        {
            Console.WriteLine("void DoOtherVoidStuff() called");
        }
    }
}