namespace CloningGenerator {
  public abstract class DeepCloneable : IDeepCloneable {
    protected DeepCloneable(DeepCloneable original, Cloner cloner) {
      cloner.RegisterClonedObject(original, this);
    }
    protected DeepCloneable() { }

    public object Clone() {
      return Clone(new Cloner());
    }
    public abstract IDeepCloneable Clone(Cloner cloner);
  }
}
