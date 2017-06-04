using System;

namespace Symbolic.Computation
{
    /// <summary>
    /// Utility class for double assertion.
    /// </summary>
    public static class AssertDouble
    {
        /// <summary>
        /// Asserts if the two double values are almost equal.
        /// </summary>
        /// <param name="actual">the actual value</param>
        /// <param name="expected">the almost expected value</param>
        /// <param name="epsilon">the epsilon value</param>
        /// <returns>true if almost equal, false otherwise</returns>
        public static bool AssertAlmostEqual(this double actual, double expected, double epsilon = 1.0E-12)
        {
            if (double.IsInfinity(actual))
                return actual > 0
                    ? double.IsPositiveInfinity(expected)
                    : double.IsNegativeInfinity(expected);
            return Math.Abs(actual - expected) < epsilon;
        }
    }
}