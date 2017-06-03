using System;
using System.Collections.Generic;
using System.Text;

namespace Symbolic.Computation
{
    public static class Auxiliary
    {
        public static string LineBreak = "\n";
        public static string Indent = "  ";

        #region CompilableCode

        public static string CompilableCode(string NamespaceName, string ClassName,
            string MethodName, string ResultType, string ArgumentsCode, string MethodCode)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("using System;");
            sb.Append(LineBreak);
            sb.Append("namespace ");
            sb.Append(NamespaceName);
            sb.Append("{");
            sb.Append(LineBreak);
            sb.Append(Indent + "public class ");
            sb.Append(ClassName);
            sb.Append("{");
            sb.Append(LineBreak);

            string methodsCode = MethodsCode(MethodName, ResultType, ArgumentsCode, MethodCode);

            sb.Append(methodsCode);

            sb.Append(Indent);
            sb.Append("}");
            sb.Append(LineBreak);
            sb.Append("}");
            return sb.ToString();
        }

        #endregion

        #region MethodsCode

        public static string MethodsCode(string MethodName, string ResultType, string ArgumentsCode, string MethodCode)
        {
            StringBuilder sb = new StringBuilder();

            sb.Append(ComposedIndent(2));
            sb.Append("public static ");
            sb.Append(ResultType);
            sb.Append(" ");
            sb.Append(MethodName);
            sb.Append("(");
            sb.Append(ArgumentsCode);
            sb.Append("){");
            sb.Append(LineBreak);
            string[] methodCodeLines = MethodCode.Split('\n');
            foreach (string line in methodCodeLines)
            {
                sb.Append(ComposedIndent(3));
                sb.Append(line);
                sb.Append(LineBreak);
            }
            sb.Append(ComposedIndent(2));
            sb.Append("}");
            sb.Append(LineBreak);

            return sb.ToString();
        }

        public static string ComposedIndent(int NumberOfIndentations)
        {
            StringBuilder sb = new StringBuilder(Indent);
            for (int i = 0; i < (NumberOfIndentations - 1); i++)
                sb.Append(Indent);
            return sb.ToString();
        }

        #endregion
    }
}