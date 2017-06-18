using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;
using Microsoft.CSharp.RuntimeBinder;

namespace ProxyGenerator
{
    /// <summary>
    /// This class is the proxy generator.
    /// </summary>
    public class ProxyGEnerator : DynamicMetaObject
    {
        private readonly IInterception _interceptor;

        /// <summary>
        /// Constructs a proxy generator
        /// </summary>
        /// <param name="expression">the expression to proxy</param>
        /// <param name="obj">the object teh method gets called</param>
        /// <param name="interceptor">the interceptor, can be null</param>
        public ProxyGEnerator(Expression expression, object obj, IInterception interceptor) : base(expression,
            BindingRestrictions.Empty, obj)
        {
            _interceptor = interceptor;
        }

        public override DynamicMetaObject BindGetMember(GetMemberBinder binder)
        {
            return WeaveAspect(base.BindGetMember(binder));
        }

        public override DynamicMetaObject BindInvokeMember(InvokeMemberBinder binder, DynamicMetaObject[] args)
        {
            return WeaveAspect(base.BindInvokeMember(binder, args), args);
        }

        private DynamicMetaObject WeaveAspect(DynamicMetaObject obj)
        {
            return WeaveAspect(obj, new DynamicMetaObject[0]);
        }

        private DynamicMetaObject WeaveAspect(DynamicMetaObject origObj, DynamicMetaObject[] args)
        {
            var origExpr = origObj.Expression;

            var localVarExpr = Expression.Parameter(origExpr.Type);
            var beforeExpr = Expression.Call(Expression.Constant(_interceptor),
                typeof(IInterception).GetMethod(nameof(IInterception.Before)));
            var afterExpr = Expression.Call(Expression.Constant(_interceptor),
                typeof(IInterception).GetMethod(nameof(IInterception.After)));

            var block = Expression.Block(
                new[] {localVarExpr},
                beforeExpr,
                Expression.Assign(localVarExpr, origExpr),
                afterExpr,
                localVarExpr
            );

            return new DynamicMetaObject(block, origObj.Restrictions);
        }
    }
}