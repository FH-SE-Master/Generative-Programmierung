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

namespace GPSharper
{
    [ExportCodeFixProvider(LanguageNames.CSharp, Name = nameof(BraceCheckCodeFix)), Shared]
    public class BraceCheckCodeFix : CodeFixProvider
    {
        private const string Title = "Add braces";

        public sealed override ImmutableArray<string> FixableDiagnosticIds
        {
            get { return ImmutableArray.Create(BraceCheckAnalyzer.DiagnosticId); }
        }

        public sealed override FixAllProvider GetFixAllProvider()
        {
            return WellKnownFixAllProviders.BatchFixer;
        }

        public sealed override async Task RegisterCodeFixesAsync(CodeFixContext context)
        {
            var root = await context.Document.GetSyntaxRootAsync(context.CancellationToken).ConfigureAwait(false);

            var diagnostic = context.Diagnostics.First();
            var diagnosticSpan = diagnostic.Location.SourceSpan;

            var node = root.FindToken(diagnosticSpan.Start).Parent;

            if (node is IfStatementSyntax || node is ElseClauseSyntax)
            {
                var codeAction = CodeAction.Create(Title, c => AddBracesAsync(context.Document, node, c), Title);
                context.RegisterCodeFix(codeAction, diagnostic);
            }
        }

        private async Task<Document> AddBracesAsync(Document document, SyntaxNode node, CancellationToken cancellationToken)
        {
            var root = await document.GetSyntaxRootAsync(cancellationToken).ConfigureAwait(false);

            switch (node.Kind())
            {
                case SyntaxKind.IfStatement:
                    var ifStat=(IfStatementSyntax)node;
                    var newIfStat = ifStat.WithStatement(SyntaxFactory.Block(ifStat.Statement))
                        .WithAdditionalAnnotations(Formatter.Annotation);
                    root = root.ReplaceNode(ifStat, newIfStat);
                    break;
                case SyntaxKind.ElseClause:
                    var elseStat = (ElseClauseSyntax)node;
                    var newElseSat = elseStat.WithStatement(SyntaxFactory.Block(elseStat.Statement))
                        .WithAdditionalAnnotations(Formatter.Annotation);
                    root = root.ReplaceNode(elseStat, newElseSat);
                    break;
            }
            return document.WithSyntaxRoot(root);
        }
    }
}
