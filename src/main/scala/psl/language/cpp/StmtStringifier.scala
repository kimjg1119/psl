package psl.language.cpp

import psl.util.Appender._
import psl.util._

object StmtStringifier {

  import DeclStringifier.given

  given stmtRule: Rule[Stmt] = (app, stmt) =>
    stmt match
      case v: ValueStmt => valueStmtRule(app, v)
      case c: CompoundStmt =>
        app.wrap {
          c.stmts.foldLeft(app)((app, stmt) => app :> stmt >> ";")
        }
      case d: DeclStmt   => declStmtRule(app, d)
      case r: ReturnStmt => app >> "return " >> r.expr

  private val valueStmtRule: Rule[ValueStmt] = (app, v) =>
    v match
      case e: Expr => exprRule(app, e)
      case _       => app

  private val exprRule: Rule[Expr] = (app, e) =>
    e match
      case IntegerLiteral(value) => app >> value
      case StringLiteral(value)  => app >> "\"" >> value >> "\""
      case BinaryOperator(lhs, rhs, op) =>
        app >> lhs >> " " >> op >> " " >> rhs
      case DeclRefExpr(id) => app >> id
      case CallExpr(id, args) =>
        app >> id >> "("
        app >> args.map(stringify(_)).mkString(", ")
        app >> ")"
      case CXXOperatorCallExpr(lhs, rhs, op) =>
        app >> lhs >> " " >> op >> " " >> rhs

  private val declStmtRule: Rule[DeclStmt] = (app, d) => app >> d.decl
}
