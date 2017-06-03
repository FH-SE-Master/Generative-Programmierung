using System;
using System.Collections.Generic;
using System.Text;
using System.CodeDom.Compiler;

namespace SymbolicComputation {
  public class CompilationResults {

    private CompilerResults myCompilerResults = null;

    private string myCode = null;

    private string myErrorsExplanation = null;
    public string ErrorsExplanation {
      get { return myErrorsExplanation; }
    }

    public CompilationResults(CompilerResults CompilerResults, string Code) {
      this.myCompilerResults = CompilerResults;
      this.myCode = Code;
      if (this.myCompilerResults.Errors.HasErrors) {
        string[] lines = Code.Split('\n');
        StringBuilder header = new StringBuilder();
        header.Append("An error has occurred while compiling functions and / or terminal definitions:");
        StringBuilder debugCode = new StringBuilder();
        for (int i = 0; i < lines.Length; i++) { // add line numbers
          debugCode.Append((i + 1) + " |" + lines[i] + "\n");
        }
        StringBuilder errorMessage = new StringBuilder();
        foreach (CompilerError error in this.myCompilerResults.Errors) {
          errorMessage.Append("line " + error.Line.ToString());
          errorMessage.Append(" column " + error.Column.ToString());
          errorMessage.Append(": " + error.ErrorText + "\r\n");
        }
        myErrorsExplanation = header.ToString() + "\n\n" + errorMessage.ToString() + "\n" + debugCode.ToString();
        myHasErrors = true;
      } else {
        myHasErrors = false;
        myErrorsExplanation = null;
      }
    }

    private bool myHasErrors;
    public bool HasErrors {
      get { return myHasErrors; }
    }

  }

}
