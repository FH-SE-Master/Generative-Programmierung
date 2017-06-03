using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Remoting.Messaging;
using System.Text;
using System.Threading.Tasks;

namespace Symbolic.Computation
{
    /// <summary>
    /// This class represents a node in a syntax tree where a node can be a non terminal node or terminal node.
    /// </summary>
    public class Node
    {
        private readonly FunctionEvaluation functionEvaluation;
        private readonly TerminalEvaluation terminalEvaluation;
        private readonly int variableIdx;
        private readonly double coefficient;
        private readonly List<Node> children;

        /// <summary>
        /// Constructs a Node for a terminal evaluation
        /// </summary>
        /// <param name="terminalEvaluation">the terminal evaluation bound to this node</param>
        /// <param name="variableIdx">the index of the variable</param>
        /// <param name="coefficient">the coefficient for the terminal evaluation</param>
        public Node(TerminalEvaluation terminalEvaluation, int variableIdx, double coefficient)
        {
            this.terminalEvaluation = terminalEvaluation
                                      ?? throw new ArgumentException("terminalEvaluation must not be null");
            this.variableIdx = variableIdx;
            this.coefficient = coefficient;
        }

        /// <summary>
        /// Constructs a Node for a functional evaluation
        /// </summary>
        /// <param name="functionEvaluation">the functional evaluation bound to this node</param>
        /// <param name="children">the list of children representing the partial tree from this node on</param>
        public Node(FunctionEvaluation functionEvaluation, List<Node> children)
        {
            this.functionEvaluation = functionEvaluation
                                      ?? throw new ArgumentException("functionalEvaluation must not be null");
            this.children = children
                            ?? throw new ArgumentException("Children list must not be null on a functional node");
            if (children.Count == 0)
            {
                throw new ArgumentException("Children list must not be empty on a functional node");
            }
        }

        /// <summary>
        /// Evaluates the subtree started from this node.
        /// </summary>
        /// <param name="data">the data for the terminal evaluation</param>
        /// <param name="sampleIdx">the idx of the data sample contained in Data[][]</param>
        /// <returns></returns>
        public double Evaluate(double[][] data, int sampleIdx)
        {
            return terminalEvaluation?.Invoke(data, variableIdx, sampleIdx, coefficient)
                   ?? functionEvaluation(children.Select(child => child.Evaluate(data, sampleIdx)).ToArray());
        }
    }
}