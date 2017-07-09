using System;

namespace CloningGenerator {
  public interface IDeepCloneable : ICloneable {
    IDeepCloneable Clone(Cloner cloner);
  }
}
