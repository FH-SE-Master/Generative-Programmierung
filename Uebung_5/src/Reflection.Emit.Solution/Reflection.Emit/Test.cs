using System;

namespace Reflection.Emit
{
    /// <summary>
    /// Implemention for testing proxied instance.
    /// </summary>
    public class Test : ITest, ISecondTest
    {
        public int DoStuff(int i, int j)
        {
            return i + j;
        }

        public string DoOtherStuff()
        {
            return "DoOtherStuff called";
        }
    }
}