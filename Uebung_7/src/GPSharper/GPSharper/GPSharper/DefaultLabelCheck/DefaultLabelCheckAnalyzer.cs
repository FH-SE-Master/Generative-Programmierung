using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Threading;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Diagnostics;

namespace GPSharper.DefaultLabelCheck
{
    [DiagnosticAnalyzer(LanguageNames.CSharp)]
    public class DefaultLabelCheckAnalyzer : DiagnosticAnalyzer
    {
        public const string DiagnosticId = "GP_UE7_B";
        internal static readonly LocalizableString Title = "Default case label missing";
        internal static readonly LocalizableString MessageFormat = "Default case label missing";
        internal const string Category = "Usage";

        internal static DiagnosticDescriptor Rule = new DiagnosticDescriptor(DiagnosticId, Title, MessageFormat, Category, DiagnosticSeverity.Warning, true);

        public override ImmutableArray<DiagnosticDescriptor> SupportedDiagnostics { get { return ImmutableArray.Create(Rule); } }

        public override void Initialize(AnalysisContext context)
        {
            context.RegisterSyntaxNodeAction(AnalyzeNode, SyntaxKind.SwitchStatement);
        }

        private void AnalyzeNode(SyntaxNodeAnalysisContext context)
        {
            var switchStat = (SwitchStatementSyntax)context.Node;

            var caseLabels = switchStat.Sections.SelectMany(s => s.Labels);

            if (!caseLabels.Any(l => l is DefaultSwitchLabelSyntax))
            {
                var diagnostic = Diagnostic.Create(Rule, switchStat.SwitchKeyword.GetLocation());
                context.ReportDiagnostic(diagnostic);
            }
        }
    }
}