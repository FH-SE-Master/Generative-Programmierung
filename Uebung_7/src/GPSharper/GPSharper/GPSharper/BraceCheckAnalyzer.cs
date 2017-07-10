using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Threading;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Diagnostics;

namespace GPSharper
{
    [DiagnosticAnalyzer(LanguageNames.CSharp)]
    public class BraceCheckAnalyzer : DiagnosticAnalyzer
    {
        public const string DiagnosticId = "BraceCheckAnalyzer";
        internal static readonly LocalizableString Title = "BraceCheckAnalyzer Title";
        internal static readonly LocalizableString MessageFormat = "{0} should use braces";
        internal static readonly LocalizableString Description = "Single embedded statements should be in braces";
        internal const string Category = "BraceCheckAnalyzer Category";

        internal static DiagnosticDescriptor Rule = new DiagnosticDescriptor(DiagnosticId, Title, MessageFormat, Category, DiagnosticSeverity.Warning, true, Description);

        public override ImmutableArray<DiagnosticDescriptor> SupportedDiagnostics { get { return ImmutableArray.Create(Rule); } }

        public override void Initialize(AnalysisContext context)
        {
            context.RegisterSyntaxNodeAction(AnalyzeNode, SyntaxKind.IfStatement, SyntaxKind.ElseClause);
        }

        private static void AnalyzeNode(SyntaxNodeAnalysisContext context)
        {
            var ifStat = context.Node as IfStatementSyntax;
            if (ifStat != null)
            {
                if (ifStat.Statement.Kind() != SyntaxKind.Block)
                {
                    var diagnostic = Diagnostic.Create(Rule, ifStat.IfKeyword.GetLocation(), "If statement");
                    context.ReportDiagnostic(diagnostic);
                }
            }
            var elseStat = context.Node as ElseClauseSyntax;
            if (elseStat != null)
            {
                if (elseStat.Statement.Kind() != SyntaxKind.Block && elseStat.Statement.Kind() != SyntaxKind.IfStatement)
                {
                    var diagnostic = Diagnostic.Create(Rule, elseStat.ElseKeyword.GetLocation(), "Else statement");
                    context.ReportDiagnostic(diagnostic);
                }
            }
        }
    }
}
