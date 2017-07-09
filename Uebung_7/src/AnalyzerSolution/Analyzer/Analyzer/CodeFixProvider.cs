using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Composition;
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
using static Microsoft.CodeAnalysis.CSharp.SyntaxKind;

namespace Analyzer
{
    [ExportCodeFixProvider(LanguageNames.CSharp, Name = nameof(BraceCheckCodefixProvider)), Shared]
    public class BraceCheckCodefixProvider : CodeFixProvider
    {
        private const string title = "Add braces";

        public sealed override ImmutableArray<string> FixableDiagnosticIds
        {
            get { return ImmutableArray.Create(BraceCheckAnalyzer.DiagnosticId); }
        }

        public sealed override FixAllProvider GetFixAllProvider()
        {
            // See https://github.com/dotnet/roslyn/blob/master/docs/analyzers/FixAllProvider.md for more information on Fix All Providers
            return WellKnownFixAllProviders.BatchFixer;
        }

        public sealed override async Task RegisterCodeFixesAsync(CodeFixContext context)
        {
            var root = await context.Document.GetSyntaxRootAsync(context.CancellationToken).ConfigureAwait(false);

            // TODO: Replace the following code with your own analysis, generating a CodeAction for each fix to suggest
            var diagnostic = context.Diagnostics.First();
            var diagnosticSpan = diagnostic.Location.SourceSpan;

            // Find the type declaration identified by the diagnostic.
            var node = root.FindToken(diagnosticSpan.Start)
                .Parent.AncestorsAndSelf()
                .OfType<TypeDeclarationSyntax>().First();

            /**
             * while() { // this block statement is the parent
             *   if() {  // anchenstorsAndSelf from this if (including) on up
             *   }
             * }
             * 
             * 1. if statement
             * 2. blokc statement
             * 3. while statement
             * n. the root of the sysntax tree
             */

            // Register a code action that will invoke the fix.
            context.RegisterCodeFix(
                CodeAction.Create(
                    title: title,
                    createChangedDocument: c => AddBracesAsync(context.Document, node, c),
                    equivalenceKey: title),
                diagnostic);
        }

        /// <summary>
        /// The fix action for the issue
        /// </summary>
        /// <param name="document">the documetn where the issue occurred</param>
        /// <param name="node">the type declaration which is the node</param>
        /// <param name="cancellationToken">the token for the cancelation of this asnyc invocation</param>
        /// <returns></returns>
        private async Task<Document> AddBracesAsync(Document document, SyntaxNode node,
            CancellationToken cancellationToken)
        {
            var root = await document.GetSyntaxRootAsync(cancellationToken);
            switch (root.Kind())
            {
                case IfStatement:
                    var ifStat = (IfStatementSyntax) node;
                    var newIfStat = ifStat.WithStatement(
                        SyntaxFactory.Block(ifStat)
                    );
                    root = root.ReplaceNode(ifStat, newIfStat);
                    break;
                case ElseClause:
                    var elseStat = (ElseClauseSyntax)node;
                    var newElseStat = elseStat.WithStatement(
                        SyntaxFactory.Block(elseStat.Statement)
                    );
                    root = root.ReplaceNode(elseStat, newElseStat);
                    break;
            }

            return document.WithSyntaxRoot(root);
        }
    }
}