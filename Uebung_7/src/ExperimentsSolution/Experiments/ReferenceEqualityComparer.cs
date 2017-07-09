using System.Collections.Generic;

namespace CloningGenerator {
  public sealed class ReferenceEqualityComparer<T> : IEqualityComparer<T> where T : class {
    public bool Equals(T x, T y) {
      return object.ReferenceEquals(x, y);
    }

    public int GetHashCode(T obj) {
      if (obj == null) return 0;
      return obj.GetHashCode();
    }
  }
}
