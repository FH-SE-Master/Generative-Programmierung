namespace CloningGenerator
{
    public partial class B : DeepCloneable
    {
        public string s;
        public int[] arr;

        public B() { }
        B(B original, Cloner cloner) : base(original, cloner)
        {
            this.s = original.s;
            this.arr = original.arr;
        }

        public override IDeepCloneable Clone(Cloner cloner)
        {
            return new B(this, cloner);
        }
    }
}
