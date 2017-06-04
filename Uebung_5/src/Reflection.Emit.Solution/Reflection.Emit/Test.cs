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

        public virtual string DoOtherStuff()
        {
            return "DoOtherStuff called";
        }
    }
}