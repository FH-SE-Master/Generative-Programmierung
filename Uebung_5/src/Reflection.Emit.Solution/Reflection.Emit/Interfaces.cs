using System;

namespace Reflection.Emit
{
    /// <summary>
    /// Test interface for implementing class to be proxied
    /// </summary>
    public interface ITest
    {
        int DoStuff(int i, int j);
    }

    /// <summary>
    /// Second interface wich gets not intercepted
    /// </summary>
    public interface ISecondTest
    {
        string DoOtherStuff();
    }
}