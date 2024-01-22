package psl.language.cpp

class Stmt extends CppASTNode

class ValueStmt extends Stmt
class DeclStmt(val decl: Decl) extends Stmt
class CompoundStmt(val stmts: List[Stmt]) extends Stmt
class ReturnStmt(val expr: Expr) extends Stmt

// Expressions
class Expr extends ValueStmt

class IntegerLiteral(val value: Int) extends Expr
object IntegerLiteral {
  def unapply(e: IntegerLiteral): Option[Int] = Some(e.value)
}

class StringLiteral(val value: String) extends Expr
object StringLiteral {
  def unapply(e: StringLiteral): Option[String] = Some(e.value)
}

// Temporal Expressions
class BinaryOperator(val lhs: Expr, val rhs: Expr, val op: String) extends Expr
object BinaryOperator {
  def unapply(e: BinaryOperator): Option[(Expr, Expr, String)] =
    Some((e.lhs, e.rhs, e.op))
}

class DeclRefExpr(val id: String) extends Expr
object DeclRefExpr {
  def unapply(e: DeclRefExpr): Option[String] = Some(e.id)
}

class CallExpr(val callee: Expr, val args: List[Expr]) extends Expr
object CallExpr {
  def unapply(e: CallExpr): Option[(Expr, List[Expr])] = Some(
    (e.callee, e.args)
  )
}

class CXXOperatorCallExpr(val lhs: Expr, val rhs: Expr, val op: String)
    extends Expr
object CXXOperatorCallExpr {
  def unapply(e: CXXOperatorCallExpr): Option[(Expr, Expr, String)] =
    Some((e.lhs, e.rhs, e.op))
}
