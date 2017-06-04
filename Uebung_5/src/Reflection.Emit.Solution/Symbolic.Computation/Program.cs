using System;
using System.Collections.Generic;

namespace Symbolic.Computation
{
    /// <summary>
    /// Console application for testing the implementations
    /// </summary>
    class Program
    {
        static void Main(string[] args)
        {
            // create varaible function
            TerminalEvaluation variable = CreateTerminal(new []{
                "double[][] data",
                "int varIdx",
                "int sampleIdx",
                "double coefficient"
            }, typeof(double), "return data[varIdx][sampleIdx] * coefficient;");

            // create addition function
            FunctionEvaluation addition = CreateFunctional("double[] data", typeof(double),
                "double result = 0.0; for (int i = 0; i < data.Length; i++) result += data[i]; return result;");

            // create multiplication function
            FunctionEvaluation multiplication = CreateFunctional("double[] data", typeof(double),
                "double result = 1.0; for (int i = 0; i < data.Length; i++) result *= data[i]; return result;");

            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("Test functions:");
            Console.WriteLine("-------------------------------------------------");
            double[][] data = CreateTestData(4, 4);
            Console.WriteLine("TestEvaluation(() => addition([1,2,3]))");
            Console.WriteLine("-------------------------------------------------");
            TestEvaluation(()=> addition(new double[]{1,2,3}), 6.0);
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("TestEvaluation(() => multiplication([1,2,3]))");
            Console.WriteLine("-------------------------------------------------");
            TestEvaluation(() => multiplication(new double[] { 1, 2, 3 }), 6.0);
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("TestEvaluation(() => varaible([1,2,3], 0, 2, 2))");
            Console.WriteLine("-------------------------------------------------");
            TestEvaluation(() => variable(data, 0, 2, 2.0), (data[0][2] * 2.0));
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine();

            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("Test evaluation trees:");
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("TestExample1()");
            Console.WriteLine("-------------------------------------------------");
            TestExample1(addition, multiplication, variable);
            Console.WriteLine("-------------------------------------------------");
            Console.WriteLine("TestExample2()");
            Console.WriteLine("-------------------------------------------------");
            TestExample2(addition, multiplication, variable);
            Console.WriteLine("-------------------------------------------------");
        }

        /// <summary>
        /// Tests the evaluation methods created via CodeDOM
        /// </summary>
        /// <param name="function">the function executing the evaluation method</param>
        /// <param name="expected">the expected result</param>
        private static void TestEvaluation(Func<double> function, double expected)
        {
            double actual = function();
            Console.WriteLine($"actual:{actual} / expected: {actual} / result={actual.AssertAlmostEqual(expected)}");
        }

        /// <summary>
        /// Tests with the first formula
        /// </summary>
        private static void TestExample1(FunctionEvaluation addition, FunctionEvaluation multiplication,
            TerminalEvaluation variable)
        {
            Console.WriteLine("ADD([1.2*VAR_1], MULT([-2.9*VAR_0], [0.3*VAR_2]))");
            Console.WriteLine("-------------------------------------------------");

            const int variableCount = 3;
            const int sampleCount = 5;
            double[][] data = CreateTestData(variableCount, sampleCount);

            INode multNode = new FunctionalNode(multiplication, new List<INode>()
            {
                new TerminalNode(variable, 2, 0.3),
                new TerminalNode(variable, 0, -2.9)
            });
            INode rootNode = new FunctionalNode(addition, new List<INode>()
            {
                multNode,
                new TerminalNode(variable, 1, 1.2)
            });

            for (var sampleIdx = 0; sampleIdx < sampleCount; sampleIdx++)
            {
                double actual = rootNode.Evaluate(data, sampleIdx);
                double expected = ((1.2 * data[1][sampleIdx]) + (-2.9 * data[0][sampleIdx] * 0.3 * data[2][sampleIdx]));
                Console.WriteLine(
                    $"ADD([1.2*{data[1][sampleIdx]}], "
                    + $"MULT([-2.9*{data[0][sampleIdx]}], [0.3*{data[2][sampleIdx]}])) "
                    + $"= {actual} "
                    + $"= {expected} "
                    + $"= {actual.AssertAlmostEqual(expected)} ");
            }
        }

        /// <summary>
        /// Tests with the second formula
        /// </summary>
        private static void TestExample2(FunctionEvaluation addition, FunctionEvaluation multiplication,
            TerminalEvaluation variable)
        {
            Console.WriteLine("ADD([2*VAR_0], MULT([4*VAR_1], ADD([-2*VAR_2], MULT([4*VAR_3], [-2*VAR_4]))))");
            Console.WriteLine("-------------------------------------------------");

            const int variableCount = 5;
            const int sampleCount = 5;
            double[][] data = CreateTestData(variableCount, sampleCount);

            INode mult0Node = new FunctionalNode(multiplication, new List<INode>()
            {
                new TerminalNode(variable, 4, -2),
                new TerminalNode(variable, 3, 4)
            });
            INode add1Node = new FunctionalNode(addition, new List<INode>()
            {
                mult0Node,
                new TerminalNode(variable, 2, -2)
            });
            INode mult2Node = new FunctionalNode(multiplication, new List<INode>()
            {
                add1Node,
                new TerminalNode(variable, 1, 4)
            });
            INode rootNode = new FunctionalNode(addition, new List<INode>()
            {
                mult2Node,
                new TerminalNode(variable, 0, 2)
            });

            for (var sampleIdx = 0; sampleIdx < sampleCount; sampleIdx++)
            {
                double actual = rootNode.Evaluate(data, sampleIdx);
                double expected = ((2 * data[0][sampleIdx]) +
                                   ((4 * data[1][sampleIdx]) *
                                    (((-2) * data[2][sampleIdx]) +
                                     (4 * data[3][sampleIdx] * (-2) * data[4][sampleIdx]))));
                Console.WriteLine(
                    $"ADD([2*{data[0][sampleIdx]}], "
                    + $"MULT([4*{data[1][sampleIdx]}], "
                    + $"ADD([-2*{data[2][sampleIdx]}], "
                    + $"MULT([4*{data[3][sampleIdx]}], [-2*{data[4][sampleIdx]}])))) "
                    + $"= {actual} "
                    + $"= {expected} "
                    + $"= {actual.AssertAlmostEqual(expected)} ");
            }
        }

        /// <summary>
        /// Creates the deterministic test data array
        /// </summary>
        /// <param name="variableCount">the count of variables to create data for</param>
        /// <param name="sampleCount">the count of samples to create for the varaibles</param>
        /// <returns>the created deterministic test data array</returns>
        private static double[][] CreateTestData(int variableCount, int sampleCount)
        {
            double[][] testData = new double[variableCount][];

            for (int i = 1; i <= variableCount; i++)
            {
                int iIdx = i - 1;
                testData[iIdx] = new double[sampleCount];
                for (int j = 1; j <= sampleCount; j++)
                {
                    int jIdx = j - 1;
                    testData[iIdx][jIdx] = i * j;
                }
            }
            return testData;
        }

        /// <summary>
        /// Creates a terminal evaluation method
        /// </summary>
        /// <param name="arguments">the string[] representing the method arguments</param>
        /// <param name="resultType">the method result type</param>
        /// <param name="code">the source code of the method body</param>
        /// <returns>the created terminal evaluation method delegate</returns>
        private static TerminalEvaluation CreateTerminal(string[] arguments, Type resultType, string code)
        {
            CompilationResults compilationResults;
            TerminalEvaluation variable =FunctionalBasis.CompileTerminal(code, arguments, resultType, out compilationResults);
            if (compilationResults.HasErrors)
            {
                throw new InvalidProgramException(
                    $"Variable terminal evaluation creation failes: Error: {compilationResults.ErrorsExplanation}");
            }

            return variable;
        }

        /// <summary>
        /// Creates a functional evaluation method
        /// </summary>
        /// <param name="argument">the string representing the method argument</param>
        /// <param name="resultType">the method result type</param>
        /// <param name="code">the source code of the method body</param>
        /// <returns>the create functional evaluation method delegate</returns>
        private static FunctionEvaluation CreateFunctional(string argument, Type resultType, string code)
        {
            CompilationResults compilationResults;
            FunctionEvaluation function = FunctionalBasis.CompileFunction(code, argument, resultType, out compilationResults);
            if (compilationResults.HasErrors)
            {
                throw new InvalidProgramException(
                    $"Addition terminal evaluation failes: Error: {compilationResults.ErrorsExplanation}");
            }

            return function;
        }
    }
}