package psl.language.cpp

sealed trait Elem

case class Program(preStmts: List[Preproc], stmts: List[Stmt]) extends Elem

enum Preproc extends Elem:
  case ImportPreproc(path: String)

enum Expr extends Elem:
  case IdExpr(name: String)
  case BinaryOperator(left: Expr, right: Expr, op: String)
  case IntegerLiteral(value: String)

enum Stmt extends Elem:
  case ExprStmt(e: Expr)
  case ReturnStmt(e: Expr)
