using System;

namespace Symbolic.Computation {
  public static class DoubleExtensions {
    public static bool IsAlmost(this double x, double y, double epsilon = 1.0E-12) {
      if (double.IsInfinity(x))
        return x > 0
          ? double.IsPositiveInfinity(y)
          : double.IsNegativeInfinity(y);
      return Math.Abs(x - y) < epsilon;
    }
  }
}
