package psl.language.psl

import psl.frontend.Parser

case class Program(stmts: List[Stmt])
object Program extends Parser.From(Parser.program)

enum Expr:
  case PrintExpr(e: Expr)
  case IntegerLiteral(l: String)

enum Stmt:
  case ExprStmt(e: Expr)
