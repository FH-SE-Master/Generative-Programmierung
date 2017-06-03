using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Reflection.Emit;
using System.Text;
using System.Threading.Tasks;

namespace Reflection.Emit
{
    public static class ProxyGenerator
    {

        private const string AssemblyName = "ProxaSeembly";
        private const string ModuleName = "ProxyModule";
        private const string WrappedFieldName= "wrapped";

        public static object Create(object obj)
        {
            var t = obj.GetType();
            
            // create aseembly
            var assemblyBuilder = AppDomain.CurrentDomain.DefineDynamicAssembly(new AssemblyName(AssemblyName), AssemblyBuilderAccess.RunAndSave);
            
            // create module
            var moduleBuilder = assemblyBuilder.DefineDynamicModule(ModuleName, AssemblyName + ".dll");
            
            // Create proxied type
            var typeBuilder = moduleBuilder.DefineType(t.FullName + "_proxy", t.Attributes, typeof(object), t.GetInterfaces());
            
            // generate wrapped field
            var fieldBuilder = typeBuilder.DefineField(WrappedFieldName, t, FieldAttributes.Private | FieldAttributes.InitOnly);

            // generate constructor
            /**
             * hide-by-sig-and-name
             * hide-by-name: Compiler only looks at anme and not signature
             * 
             * public class Base {
             *     public void Foo();
             * }
             * 
             * public class Dervied: Base() {
             *     public void Foo(string foo);
             * }
             * 
             * Base b = new Derived();
             * b.Foo();
             */
            var constructorBuilder = typeBuilder.DefineConstructor(MethodAttributes.Public | MethodAttributes.HideBySig,
                CallingConventions.Standard, new [] {t});

            #region emit ctor code
            var il = constructorBuilder.GetILGenerator();
            // 1. Call base class constructor
            il.Emit(OpCodes.Ldarg_0); // Access this
            il.Emit(OpCodes.Call, typeof(object).GetConstructor(new Type[0])); // 2. Call defeault constructor of object
            // 3. Store argument in field
            il.Emit(OpCodes.Ldarg_0); // Access this
            il.Emit(OpCodes.Ldarg_1); // First argument of method (construcor)
            il.Emit(OpCodes.Stfld, fieldBuilder); // store references in field
            il.Emit(OpCodes.Ret); // Return of method 
            #endregion


            #region generate methods
            foreach (var interfaceType in t.GetInterfaces())
            {
                foreach (var method in interfaceType.GetMethods())
                {
                    var methodInfo = t.GetMethod(method.Name);
                    var parameterTypes = methodInfo.GetParameters().Select(x => x.ParameterType).ToArray();
                    var methodBuilder = typeBuilder.DefineMethod(methodInfo.Name, methodInfo.Attributes, methodInfo.ReturnType, parameterTypes);

                    // Generate code for methods
                    il = methodBuilder.GetILGenerator();
                    il.Emit(OpCodes.Ldarg_0);
                    il.Emit(OpCodes.Ldfld, fieldBuilder); // load field
                    // Load arguments
                    for (byte i = 1; i <= parameterTypes.Length; i++)
                    {
                        il.Emit(OpCodes.Ldarg_S, i);
                    }
                    // Call method via wrapped 
                    il.Emit(OpCodes.Callvirt, fieldBuilder.FieldType.GetMethod(method.Name));
                    // Create local variable
                    var localBuilder = il.DeclareLocal(methodInfo.ReturnType);
                    il.Emit(OpCodes.Stloc, localBuilder);
                    il.Emit(OpCodes.Ldloc, localBuilder);
                    il.Emit(OpCodes.Ret);
                }
            }
            #endregion

            var createdType = typeBuilder.CreateType();
            assemblyBuilder.Save(AssemblyName + ".dll");

            return Activator.CreateInstance(createdType, obj);
        }
    }
}
