package psl.language.cpp

import psl.util.Appender._
import psl.util._

object DeclStringifier {

  import TypeStringifier.given
  import StmtStringifier.given

  given declRule: Rule[Decl] = (app, d) =>
    d match
      case t: TranslationUnitDecl => translationUnitDeclRule(app, t)
      case n: NamedDecl           => namedDeclRule(app, n)
      case d: Decl                => app // TODO: Implement

  private val translationUnitDeclRule: Rule[TranslationUnitDecl] = (app, t) =>
    // Stringifier for nodes
    import Stringifier.given
    for node <- t.nodes do app :> node
    app

  private val namedDeclRule: Rule[NamedDecl] = (app, n) =>
    n match
      case v: VarDecl      => varDeclRule(app, v)
      case f: FunctionDecl => funDeclRule(app, f)

  private val varDeclRule: Rule[VarDecl] = (app, v) =>
    v match
      case ParmVarDecl(name, qty, init) =>
        app >> qty >> " " >> name >> init
      case VarDecl(name, qty, init) =>
        app >> qty >> " " >> name >> init

  private val funDeclRule: Rule[FunctionDecl] = (app, f) =>
    app >> f.qualType >> " " >> f.name >> "("
    app >> f.paramInfo.map(stringify(_)).mkString(", ")
    app >> ")"
    app :> f.body

  private given initRule: Rule[Option[Expr]] = (app, e) =>
    e match
      case Some(expr) => app >> " = " >> expr
      case None       => app

}
