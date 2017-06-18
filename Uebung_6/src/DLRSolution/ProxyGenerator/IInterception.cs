using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProxyGenerator
{
   public  interface IInterception
   {
       void Before();

       void After();

       object Around(Delegate proceed, object[] args);
   }
}
