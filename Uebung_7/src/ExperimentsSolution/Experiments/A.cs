namespace CloningGenerator
{
    public partial class A : DeepCloneable
    {
        public int i;
        public B b;

        public A() { }

        A(A original, Cloner cloner) : base(original, cloner)
        {
            this.i = original.i;
            this.b = cloner.Clone(original.b);
        }

        public override IDeepCloneable Clone(Cloner cloner)
        {
            return new A(this, cloner);
        }
    }
}
