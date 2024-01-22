package psl.language.cpp

import psl.util.Appender._

object Stringifier {
  import DeclStringifier.given
  import StmtStringifier.given
  import PreprocStringifier.given

  given programRule: Rule[Program] = (app, prog) =>
    for pre <- prog.pre do app :> pre
    app >> prog.ast

  given nodeRule: Rule[CppASTNode] = (app, nd) =>
    nd match
      case d: Decl    => app >> d
      case s: Stmt    => app >> s
      case p: Preproc => app >> p
}
