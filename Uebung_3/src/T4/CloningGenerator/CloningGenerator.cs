namespace CloningGenerator
{
    public partial class A
    {
// Clone constructor
        protected A(A original, Cloner cloner) : base(original, cloner)
        {
            this.i = original.i;
            this.b = cloner.Clone(original.b);
        }

        public override IDeepCloneable Clone(Cloner cloner)
        {
            return new A(this, cloner);
        }
    }

    public partial class B
    {
// Clone constructor
        protected B(B original, Cloner cloner) : base(original, cloner)
        {
            this.s = (System.String) original.s.Clone();
            this.arr = (System.Int32[]) original.arr.Clone();
        }

        public override IDeepCloneable Clone(Cloner cloner)
        {
            return new B(this, cloner);
        }
    }
}