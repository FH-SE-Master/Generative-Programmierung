using System;
using System.CodeDom.Compiler;
using Microsoft.CSharp;

namespace Symbolic.Computation
{
    /// <summary>
    /// This class provides static methods for creating evaluation methods
    /// </summary>
    public static class FunctionalBasis
    {
        private const string ClassName = "Definitions";

        /// <summary>
        /// Compiles a terminal evaluation method
        /// </summary>
        /// <typeparam name="T">The type of the create terminal method</typeparam>
        /// <param name="methodCode">the code for the method body</param>
        /// <param name="arguments">the string array representing the methd arguments</param>
        /// <param name="compilationResults">the out parameter holdng the comiplation result</param>
        /// <returns>the created terminal evaluation delegate</returns>
        /// <summary>
        public static T CompileTerminal<T>(
            string methodCode, string[] arguments, Type resType, out CompilationResults compilationResults) where T:class
        {
            string namespaceName = typeof(TerminalEvaluation).Namespace;
            string methodName = typeof(TerminalEvaluation).Name;
            string resultType = resType.Name;
            string argumentsCode = string.Join(",", arguments);

            return Compile<T>(
                namespaceName, ClassName, methodName, resultType, argumentsCode, methodCode,
                out compilationResults);
        }

        /// <summary>
        /// Creates a functional evaluation method.
        /// </summary>
        /// <typeparam name="T">The type of the create functional method</typeparam>
        /// <param name="methodCode">the code for the method body</param>
        /// <param name="argument">the string representing the method argument</param>
        /// <param name="resType">the type of the method result</param>
        /// <param name="compilationResults">the out parameter holdng the comiplation result</param>
        /// <returns>the created functional evaluation delegate</returns>
        public static T CompileFunction<T>(
            string methodCode, string argument, Type resType, out CompilationResults compilationResults) where T : class
        {
            string namespaceName = typeof(TerminalEvaluation).Namespace;
            string methodName = typeof(TerminalEvaluation).Name;
            string resultType = resType.Name;
            string argumentsCode = argument;

            return Compile<T>(
                namespaceName, ClassName, methodName, resultType, argumentsCode, methodCode,
                out compilationResults);
        }

        /// <summary>
        /// Compiles a evaluation method
        /// </summary>
        /// <typeparam name="T">the concrete type of the compilation result</typeparam>
        /// <param name="namespaceName">the name of the namespace the owning class is in</param>
        /// <param name="className">the name of the class</param>
        /// <param name="methodName">the name of the method</param>
        /// <param name="resultType">the type of the method result</param>
        /// <param name="argumentsCode">the code representing the arguments</param>
        /// <param name="methodCode">the code of the method body</param>
        /// <param name="compilationResults">the out parameter holding the compilation result</param>
        /// <returns>the compiled evaluation method</returns>
        private static T Compile<T>(string namespaceName, string className,
            string methodName, string resultType, string argumentsCode, string methodCode,
            out CompilationResults compilationResults) where T : class
        {
            string code = Auxiliary.CompilableCode(
                namespaceName, className, methodName, resultType,
                argumentsCode, methodCode);

            var provider = new CSharpCodeProvider();
            var parameters = new CompilerParameters
            {
                GenerateInMemory = true,
                TreatWarningsAsErrors = false
            };

            var compilerResults = provider.CompileAssemblyFromSource(parameters, code);
            var result = default(T);
            if (!compilerResults.Errors.HasErrors)
            {
                var assembly = compilerResults.CompiledAssembly;
                var definitionsClass = assembly.GetType(
                    string.Format("{0}.{1}", namespaceName, className));
                var methodInfo = definitionsClass.GetMethod(methodName);
                result = Delegate.CreateDelegate(typeof(T), methodInfo) as T;
            }
            compilationResults = new CompilationResults(compilerResults, code);

            return result;
        }
    }
}