package psl.backend

import psl.language._
import _root_.psl.language.psl.Stmt
import scala.collection.mutable.ListBuffer
import _root_.psl.language.psl.Expr

object Transpiler {
  def transpile(ast: psl.Program): cpp.Program =
    val importStmts: List[cpp.Preproc] = List( // TODO
      cpp.Preproc.ImportPreproc("bits/stdc++.h")
    )
    cpp.Program(
      ast.stmts.map(transpile)
    )

  def transpile(stmt: psl.Stmt): cpp.Stmt = stmt match
    case psl.Stmt.ExprStmt(e) => cpp.Stmt.ExprStmt(transpile(e))

  def transpile(expr: psl.Expr): cpp.Expr = expr match
    case psl.Expr.PrintExpr(e) =>
      cpp.Expr.BinaryOperator(
        cpp.Expr.IdExpr("std::cout"),
        transpile(e),
        "<<"
      )
    case psl.Expr.IntegerLiteral(l) => cpp.Expr.IntegerLiteral(l)
}
