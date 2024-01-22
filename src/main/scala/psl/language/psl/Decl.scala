package psl.language.psl

import psl.frontend.Parser

sealed trait Decl extends PslASTNode

class RootDecl(val nodes: List[PslASTNode]) extends Decl
object RootDecl extends Parser.From(Parser.rootDecl) {
  def apply(nodes: List[PslASTNode]) = new RootDecl(nodes)
}
case class FunctionDecl(
    val qty: QualType,
    val name: String,
    val params: List[ParmDecl],
    val body: CompoundStmt
) extends Decl
case class VarDecl(val name: String, val qty: QualType, val init: Option[Expr])
    extends Decl
case class ParmDecl(val name: String, val qty: QualType, val init: Option[Expr])
    extends Decl
