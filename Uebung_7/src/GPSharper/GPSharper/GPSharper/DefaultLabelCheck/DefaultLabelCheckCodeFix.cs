using System;
using System.Composition;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CodeFixes;
using Microsoft.CodeAnalysis.CodeActions;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Rename;
using Microsoft.CodeAnalysis.Text;
using Microsoft.CodeAnalysis.Formatting;

namespace GPSharper.DefaultLabelCheck
{
    [ExportCodeFixProvider(LanguageNames.CSharp, Name = nameof(DefaultLabelCheckCodeFix)), Shared]
    public class DefaultLabelCheckCodeFix : CodeFixProvider
    {
        private const string Title = "add default";

        public sealed override ImmutableArray<string> FixableDiagnosticIds
        {
            get { return ImmutableArray.Create(DefaultLabelCheckAnalyzer.DiagnosticId); }
        }

        public sealed override FixAllProvider GetFixAllProvider()
        {
            return WellKnownFixAllProviders.BatchFixer;
        }

        public sealed override async Task RegisterCodeFixesAsync(CodeFixContext context)
        {
            var root = await context.Document.GetSyntaxRootAsync(context.CancellationToken).ConfigureAwait(false);

            var diagnostic = context.Diagnostics.First();

            var span = diagnostic.Location.SourceSpan;

            var statement = (SwitchStatementSyntax)root.FindToken(span.Start).Parent;

            var codeAction = CodeAction.Create(Title, c => GenerateDefaultLabelAsync(context.Document, statement, c), Title);
            context.RegisterCodeFix(codeAction, diagnostic);
        }

        private async Task<Document> GenerateDefaultLabelAsync(Document document, SwitchStatementSyntax switchStat, CancellationToken cancellationToken)
        {
            var labels = SyntaxFactory.List<SwitchLabelSyntax>(new[] { SyntaxFactory.DefaultSwitchLabel() });
            var stats = SyntaxFactory.List<StatementSyntax>(new[] { SyntaxFactory.BreakStatement() });
            var sections = switchStat.Sections.Add(SyntaxFactory.SwitchSection(labels, stats));

            var newSwitchStat = switchStat.WithSections(sections)
                .WithAdditionalAnnotations(Formatter.Annotation);

            var root = await document.GetSyntaxRootAsync(cancellationToken).ConfigureAwait(false);

            var newRoot = root.ReplaceNode(switchStat, newSwitchStat);

            return document.WithSyntaxRoot(root);
        }
    }
}
