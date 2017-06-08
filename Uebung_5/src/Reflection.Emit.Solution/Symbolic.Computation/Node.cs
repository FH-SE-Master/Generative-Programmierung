using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Remoting.Messaging;
using System.Text;
using System.Threading.Tasks;

namespace Symbolic.Computation
{
    #region Supported Evaluation methods
    public delegate double TerminalEvaluation(double[][] data, int variableIndex, int sampleIndex, double coefficient);

    public delegate double FunctionEvaluation(double[] parameters);
    #endregion

    #region Node specification
    /// <summary>
    /// Interface which specifies a Node.
    /// </summary>
    public interface INode
    {
        double Evaluate(double[][] data, int sampleIdx);
    }

    /// <summary>
    /// The abstract implementation of the INode interface which encapsulates the common members
    /// </summary>
    public abstract class BaseNode<T> : INode where T : class
    {
        protected readonly T Evaluation;

        /// <summary>
        /// Sets the evaluation for the node
        /// </summary>
        /// <param name="evaluation">to evaluation for this node</param>
        protected BaseNode(T evaluation)
        {
            Evaluation = evaluation ?? throw new ArgumentException("Node must hold an evaluation");
        }

        /// <summary>
        /// Evaluates the subtree started from this node.
        /// </summary>
        /// <param name="data">the data for the terminal evaluation</param>
        /// <param name="sampleIdx">the idx of the data sample contained in Data[][]</param>
        /// <returns></returns>
        public abstract double Evaluate(double[][] data, int sampleIdx);
    }
    #endregion

    #region Node implementation
    /// <summary>
    /// Node implementation for functional evaluations
    /// </summary>
    public class FunctionalNode : BaseNode<FunctionEvaluation>
    {
        private readonly List<INode> _children;

        /// <summary>
        /// Constructs a functional node.
        /// </summary>
        /// <param name="evaluation">the functional evaluation for this node</param>
        /// <param name="children">the children of the functional node</param>
        public FunctionalNode(FunctionEvaluation evaluation, List<INode> children) : base(evaluation)
        {
            this._children = children
                             ?? throw new ArgumentException("Children list must not be null on a functional node");
            if (children.Count == 0)
            {
                throw new ArgumentException("Children list must not be empty on a functional node");
            }
        }

        public override double Evaluate(double[][] data, int sampleIdx)
        {
            return Evaluation(_children.Select(child => child.Evaluate(data, sampleIdx)).ToArray());
        }
    }

    /// <summary>
    /// Node implementation for terminal evaluations
    /// </summary>
    public class TerminalNode : BaseNode<TerminalEvaluation>
    {
        private readonly int _variableIdx;
        private readonly double _coefficient;

        /// <summary>
        /// Constructs a Node for a terminal evaluation
        /// </summary>
        /// <param name="evaluation">the terminal evaluation bound to this node</param>
        /// <param name="variableIdx">the index of the variable</param>
        /// <param name="coefficient">the coefficient for the terminal evaluation</param>
        public TerminalNode(TerminalEvaluation evaluation, int variableIdx, double coefficient) : base(evaluation)
        {
            this._variableIdx = variableIdx;
            this._coefficient = coefficient;
        }

        public override double Evaluate(double[][] data, int sampleIdx)
        {
            return Evaluation(data, _variableIdx, sampleIdx, _coefficient);
        }
    }
    #endregion
}