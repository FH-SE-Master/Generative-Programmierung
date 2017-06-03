using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SymbolicComputation;

namespace Symbolic.Computation
{
    public static class FuncionalBasics
    {
        private static string ClassName = "Definitions";

        public delegate double TerminalEvaluation(double[][] data,
            int sampleIndex, int variableIndex, double coefficient);

        public delegate double FunctionEvaluation(double[] parameters);

        public static TerminalEvaluation CompileTerminal(string methodCode, out CompilationResults)
        {
            string nameSpaceName = typeof(TerminalEvaluation).Namespace;
            string methodName = typeof(TerminalEvaluation).Name;
            string resultType = "double";
            string argumentCode = "double[][] data, int sampleIdx, int varIdx, double coeff";

            return Compile<TerminalEvaluation>(nameSpaceName, ClassName, methodName, resultType, argumentCode,
                methodCode, out CompilationResults);
        }


        public static FunctionEvaluation CompileFunction(string methodCode, out CompilationResults)
        {
            string nameSpaceName = typeof(TerminalEvaluation).Namespace;
            string methodName = typeof(TerminalEvaluation).Name;
            string resultType = "double";
            string argumentCode = "double[][] data, int sampleIdx, int varIdx, double coeff";

            return Compile<TerminalEvaluation>(nameSpaceName, ClassName, methodName, resultType, argumentCode,
                methodCode, out CompilationResults);
        }
        
    }
}
