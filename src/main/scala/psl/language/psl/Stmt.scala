package psl.language.psl

sealed trait Stmt extends PslASTNode

class DeclStmt(val decl: Decl) extends Stmt
object DeclStmt {
  def unapply(s: DeclStmt): Option[Decl] = Some(s.decl)
}

class CompoundStmt(val stmts: List[Stmt]) extends Stmt
object CompoundStmt {
  def unapply(s: CompoundStmt): Option[List[Stmt]] = Some(s.stmts)
}

case class ReturnStmt(val expr: Expr) extends Stmt

sealed trait Expr extends Stmt

case class IntegerLiteral(val value: Int) extends Expr
case class StringLiteral(val value: String) extends Expr
case class Println(val expr: Expr) extends Expr
