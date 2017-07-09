using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Threading;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Diagnostics;

namespace Analyzer
{
    /// <summary>
    /// The first analyzer
    /// </summary>
    [DiagnosticAnalyzer(LanguageNames.CSharp)]
    public class BraceCheckAnalyzer : DiagnosticAnalyzer
    {
        public const string DiagnosticId = "GP01";

        // You can change these strings in the Resources.resx file. If you do not want your analyzer to be localize-able, you can use regular strings for Title and MessageFormat.
        // See https://github.com/dotnet/roslyn/blob/master/docs/analyzers/Localizing%20Analyzers.md for more on localization
        private static readonly LocalizableString Title = "{0} should use braces";

        // The message in case of broken style
        private static readonly LocalizableString MessageFormat = "{0} should use braces";

        private static readonly LocalizableString Description =
            "Single embedded statements shouzld be surrounded by braces";

        private const string Category = "Style";

        // Defined rule with its metadata
        private static DiagnosticDescriptor Rule = new DiagnosticDescriptor(DiagnosticId, Title, MessageFormat,
            Category,
            DiagnosticSeverity.Warning, isEnabledByDefault: true, description: Description);

        // Create rule array
        public override ImmutableArray<DiagnosticDescriptor> SupportedDiagnostics
        {
            get { return ImmutableArray.Create(Rule); }
        }

        // Initialze  the anlyzation context
        public override void Initialize(AnalysisContext context)
        {
            // TODO: Consider registering other actions that act on syntax instead of or in addition to symbols
            // See https://github.com/dotnet/roslyn/blob/master/docs/analyzers/Analyzer%20Actions%20Semantics.md for more information
            context.RegisterSyntaxNodeAction(AnalyzeNode, SyntaxKind.IfStatement, SyntaxKind.ElseClause);
        }

        private static void AnalyzeNode(SyntaxNodeAnalysisContext context)
        {
            Diagnostic diagnostic = null;
            var ifStat = context.Node as IfStatementSyntax;
            var ifStateKind = ifStat?.Statement.Kind();
            if (ifStateKind.HasValue && ifStateKind != SyntaxKind.Block)
            {
                diagnostic = Diagnostic.Create(Rule, ifStat.IfKeyword.GetLocation(), "If statements");
            }

            var elseStat = context.Node as ElseClauseSyntax;
            var elseStateKind = elseStat?.Statement.Kind();
            if (elseStateKind.HasValue && elseStateKind != SyntaxKind.Block && elseStateKind != SyntaxKind.IfStatement)
            {
                diagnostic = Diagnostic.Create(Rule, elseStat.ElseKeyword.GetLocation(), "Else statements");
            }

            if (diagnostic != null)
            {
                context.ReportDiagnostic(diagnostic);
            }
        }
    }
}