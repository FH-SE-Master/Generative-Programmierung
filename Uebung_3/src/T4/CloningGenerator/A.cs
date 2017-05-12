using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CloningGenerator
{
    // Test class for cloning generator
    public partial class A : DeepCloneable
    {
        public int i;
        public B b;
        public A() { }
    }
}
