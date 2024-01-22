package psl.language.cpp

import psl.util.Appender._
import psl.util._

class Stringifier() {

  given elemRule: Rule[Elem] = (app, elem) =>
    elem match
      case elem: Program => progRule(app, elem)
      case elem: Preproc => preprocRule(app, elem)
      case elem: Expr    => exprRule(app, elem)
      case elem: Stmt    => stmtRule(app, elem)

  private val progRule: Rule[Program] = (app, prog) =>
    for preStmt <- prog.preStmts do app :> preStmt
    for stmt <- prog.stmts do app :> stmt
    app

  private val stmtRule: Rule[Stmt] = (app, stmt) =>
    stmt match
      case Stmt.ExprStmt(e) =>
        app >> e >> ";"
      case Stmt.ReturnStmt(e) =>
        app >> "return " >> e >> ";"

  private val preprocRule: Rule[Preproc] = (app, preproc) =>
    preproc match
      case Preproc.ImportPreproc(path) => app >> s"#include <$path>"

  private val exprRule: Rule[Expr] = (app, expr) =>
    expr match
      case Expr.IdExpr(name) => app >> name
      case Expr.BinaryOperator(left, right, op) =>
        app >> left >> " " >> op >> " " >> right
      case Expr.IntegerLiteral(value) => app >> value
}
