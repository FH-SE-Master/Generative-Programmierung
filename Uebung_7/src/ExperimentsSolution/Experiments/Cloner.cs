using System.Collections.Generic;

namespace CloningGenerator {
  public sealed class Cloner {
    private Dictionary<IDeepCloneable, IDeepCloneable> cache;

    public Cloner() {
      cache = new Dictionary<IDeepCloneable, IDeepCloneable>(new ReferenceEqualityComparer<IDeepCloneable>());
    }

    public IDeepCloneable Clone(IDeepCloneable obj) {
      if (obj == null) return null;
      IDeepCloneable clone;
      if (cache.TryGetValue(obj, out clone))
        return clone;
      else
        return obj.Clone(this);
    }
    public T Clone<T>(T obj) where T : class, IDeepCloneable {
      if (obj == null) return null;
      IDeepCloneable clone;
      if (cache.TryGetValue(obj, out clone))
        return (T)clone;
      else
        return (T)obj.Clone(this);
    }

    public void RegisterClonedObject(IDeepCloneable original, IDeepCloneable clone) {
      cache.Add(original, clone);
    }
  }
}
