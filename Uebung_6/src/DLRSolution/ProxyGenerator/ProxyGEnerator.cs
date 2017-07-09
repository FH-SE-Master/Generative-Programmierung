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
<<<<<<< HEAD
=======
    /// <summary>
    /// This class is the proxy generator.
    /// </summary>
>>>>>>> 93b4458fa8c581c49f115008bef9b3a0197e2b3e
    public class ProxyGenerator : DynamicMetaObject
    {
        private readonly IInterception _interceptor;

<<<<<<< HEAD
=======
        /// <summary>
        /// Constructs a proxy generator
        /// </summary>
        /// <param name="expression">the expression to proxy</param>
        /// <param name="obj">the object the method gets called</param>
        /// <param name="interceptor">the interceptor, can be null</param>
>>>>>>> 93b4458fa8c581c49f115008bef9b3a0197e2b3e
        public ProxyGenerator(Expression expression, object obj, IInterception interceptor) : base(expression,
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
            // only if interception is provided
            if (_interceptor != null)
            {
                // original expressions
                var origExpr = origObj.Expression;
                var origVal = Expression.Parameter(origExpr.Type);

                // before/after expressions
                var beforeExpr = Expression.Call(Expression.Constant(_interceptor),
                    typeof(IInterception).GetMethod(nameof(IInterception.Before)));
                var afterExpr = Expression.Call(Expression.Constant(_interceptor),
                    typeof(IInterception).GetMethod(nameof(IInterception.After)));

                // new execution block expression
                var block = Expression.Block(
                    new[] {origVal},
                    beforeExpr,
                    Expression.Assign(origVal, origExpr),
                    afterExpr,
                    origVal
                );

                // proceed expression
                var proceedExpressions = Expression.Lambda(Expression.Block(new ParameterExpression[] { }, block));

                // around expression
                var aroundExpr = Expression.Call(Expression.Constant(_interceptor),
                    typeof(IInterception).GetMethod(nameof(IInterception.Around)),
                    new List<Expression>
                    {
                        proceedExpressions,
                        Expression.NewArrayInit(typeof(object),
                            args.Select(x => Expression.Convert(x.Expression, typeof(object))))
                    });

                return new DynamicMetaObject(aroundExpr, origObj.Restrictions);
            }

            return origObj;
        }
    }
}