package psl.language.cpp

case class Program(stmts: List[Stmt])

enum Preproc:
  case ImportPreproc(path: String)

enum Expr:
  case IdExpr(name: String)
  case BinaryOperator(left: Expr, right: Expr, op: String)
  case IntegerLiteral(value: String)

enum Stmt:
  case ExprStmt(e: Expr)
  case ReturnStmt(e: Expr)
