using System;
using System.Collections;
using System.Linq;
using System.Reflection;
using System.Reflection.Emit;
using Reflection.Emit;

namespace Reflection.Emit
{
    /// <summary>
    /// This class provides methods for generating a proxy for any object instance.
    /// </summary>
    public static class ProxyGenerator
    {
        private static readonly string AssemblyName = typeof(ProxyGenerator).Namespace + ".Proxy";
        private static readonly string ModuleName = AssemblyName;
        private static readonly string AssemblyFullName = AssemblyName + ".dll";
        public const string WrappedFieldName = "proxied";
        public const string InterceptorFieldName = "interceptor";

        /// <summary>
        /// Creates the proxied object for the given object.
        /// </summary>
        /// <typeparam name="T">the type of the given and proxied object</typeparam>
        /// <param name="obj">the object to proxy</param>
        /// <returns>the proxied object</returns>
        public static T Create<T>(T obj)
        {
            return Create<T>(obj, null);
        }

        /// <summary>
        /// Creates the proxied object for the given object and applies the interceptor to the methods 
        /// accessible by the Type T.
        /// </summary>
        /// <typeparam name="T">the type of the given and proxied object</typeparam>
        /// <param name="obj">the object to proxy</param>
        /// <param name="interceptor">the interceptor applied to the proxied object method invocations</param>
        /// <returns>the proxied object</returns>
        public static T Create<T>(T obj, IInterception<T> interceptor)
        {
            var wrappedType = obj.GetType();
            var interceptorType = typeof(IInterception<T>);
            FieldBuilder wrappedFieldBuilder, interceptorFieldBuilder;

            #region Create dynamic assembly

            var assemblyBuilder = AppDomain.CurrentDomain.DefineDynamicAssembly(new AssemblyName(AssemblyName),
                AssemblyBuilderAccess.RunAndSave);

            var moduleBuilder = assemblyBuilder.DefineDynamicModule(ModuleName, AssemblyFullName);

            #endregion

            #region Create type builder

            var typeBuilder = moduleBuilder.DefineType(
                wrappedType.FullName + "Proxy", wrappedType.Attributes, wrappedType, wrappedType.GetInterfaces());

            #endregion

            #region Create wrapped and interceptor field builder

            wrappedFieldBuilder = typeBuilder.DefineField(
                WrappedFieldName, wrappedType, FieldAttributes.Private | FieldAttributes.InitOnly);
            interceptorFieldBuilder = typeBuilder.DefineField(
                InterceptorFieldName, interceptorType, FieldAttributes.Private | FieldAttributes.InitOnly);

            #endregion

            #region Create proxy constructor
            
            var constructorBuilder = typeBuilder.DefineConstructor(
                MethodAttributes.Public | MethodAttributes.HideBySig,
                CallingConventions.Standard, new[] {wrappedType, interceptorType});

            var il = constructorBuilder.GetILGenerator();
            // call this.base()
            il.Emit(OpCodes.Ldarg_0);
            il.Emit(OpCodes.Call, wrappedType.GetConstructor(new Type[0]));

            // store argument obj into wrapped
            il.Emit(OpCodes.Ldarg_0); // this
            il.Emit(OpCodes.Ldarg_1); // wrapped
            il.Emit(OpCodes.Stfld, wrappedFieldBuilder);
            il.Emit(OpCodes.Ldarg_0); // this
            il.Emit(OpCodes.Ldarg_2); // interceptor
            il.Emit(OpCodes.Stfld, interceptorFieldBuilder);
            il.Emit(OpCodes.Ret);

            #endregion

            #region Create proxied methods

            // only intercept interface method visible to interceptor
            IList interceptableInterfaces;
            if (typeof(T).IsInterface)
            {
                interceptableInterfaces = new ArrayList()
                {
                    typeof(T)
                };
            }
            else
            {
                interceptableInterfaces = new ArrayList(wrappedType.GetInterfaces());
            }

            foreach (var interfaceType in wrappedType.GetInterfaces())
            {
                foreach (var method in interfaceType.GetMethods())
                {
                    var methodInfo = wrappedType.GetMethod(method.Name);
                    var parameterTypes = methodInfo
                        .GetParameters()
                        .Select(x => x.ParameterType)
                        .ToArray();
                    var methodBuilder = typeBuilder.DefineMethod(
                        methodInfo.Name,
                        methodInfo.Attributes,
                        methodInfo.ReturnType,
                        parameterTypes);

                    il = methodBuilder.GetILGenerator();

                    // before interceptor call if interceptor is present
                    if ((interceptor != null) && (interceptableInterfaces.Contains(interfaceType)))
                    {
                        il.Emit(OpCodes.Ldarg_0); // load this
                        il.Emit(OpCodes.Ldfld, interceptorFieldBuilder); // load interceptor field
                        il.Emit(OpCodes.Ldarg_0); // load this
                        il.Emit(OpCodes.Ldfld, wrappedFieldBuilder);     // load wrapper field
                        il.Emit(OpCodes.Ldstr, methodInfo.Name);         // load intercepted method name
                        il.Emit(OpCodes.Callvirt, interceptorFieldBuilder.FieldType.GetMethod(nameof(IInterception<T>.Before)));
                    }

                    il.Emit(OpCodes.Ldarg_0); // load this
                    il.Emit(OpCodes.Ldfld, wrappedFieldBuilder); // load wrapper field
                    // load all parameters
                    for (byte i = 1; i <= parameterTypes.Length; i++)
                    {
                        il.Emit(OpCodes.Ldarg_S, i);
                    }
                    il.Emit(OpCodes.Callvirt, wrappedFieldBuilder.FieldType.GetMethod(methodInfo.Name));

                    var localBuilder = il.DeclareLocal(methodInfo.ReturnType);
                    il.Emit(OpCodes.Stloc, localBuilder);
                    il.Emit(OpCodes.Ldloc, localBuilder);
                    
                    // after interceptor call if interceptor is present
                    if ((interceptor != null) && (interceptableInterfaces.Contains(interfaceType)))
                    {
                        il.Emit(OpCodes.Ldarg_0); // load this
                        il.Emit(OpCodes.Ldfld, interceptorFieldBuilder); // load interceptor field
                        il.Emit(OpCodes.Ldarg_0); // load this
                        il.Emit(OpCodes.Ldfld, wrappedFieldBuilder);     // load wrapper field
                        il.Emit(OpCodes.Ldstr, methodInfo.Name);         // load intercepted method name
                        il.Emit(OpCodes.Callvirt, interceptorFieldBuilder.FieldType.GetMethod(nameof(IInterception<T>.After)));
                    }

                    il.Emit(OpCodes.Ret);
                }
            }

            #endregion

            var createdType = typeBuilder.CreateType();
            assemblyBuilder.Save(AssemblyFullName);
            return (T) Activator.CreateInstance(createdType, obj, interceptor);
        }
    }
}