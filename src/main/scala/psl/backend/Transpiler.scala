package psl.backend

import scala.collection.mutable.ListBuffer

import psl.language._
import cpp._
import _root_.psl.language.psl.RootDecl

/** Transpiler Note that cpp is default
  */

object Transpiler {

  def run(ast: psl.RootDecl): Program =
    val pre = List(IncludeProc("bits/stdc++.h"))
    val tu = transpile(ast)
    Program(pre.toList, tu)

  def transpile(ast: psl.RootDecl): TranslationUnitDecl =
    TranslationUnitDecl(ast.nodes.map(transpile))

  def transpile(node: psl.PslASTNode): CppASTNode =
    node match
      case d: psl.Decl => transpile(d)
      case s: psl.Stmt => transpile(s)

  def transpile(decl: psl.Decl): Decl =
    decl match
      case d: psl.VarDecl      => transpile(d)
      case d: psl.ParmDecl     => transpile(d)
      case d: psl.FunctionDecl => transpile(d)
      case _: RootDecl =>
        throw Exception("RootDecl should be already transpiled")

  def transpile(vd: psl.VarDecl): VarDecl =
    VarDecl(vd.name, transpile(vd.qty), vd.init.map(transpile))

  def transpile(stmt: psl.Stmt): Stmt =
    stmt match // why not exaustive?
      case psl.DeclStmt(decl) => DeclStmt(transpile(decl))
      case psl.CompoundStmt(stmts) =>
        CompoundStmt(stmts.map(transpile))
      case psl.ReturnStmt(expr) => ReturnStmt(transpile(expr))
      case e: psl.Expr          => transpile(e)

  def transpile(expr: psl.Expr): Expr =
    expr match
      case psl.IntegerLiteral(value) => IntegerLiteral(value)
      case psl.StringLiteral(value)  => StringLiteral(value)
      case psl.Println(expr) =>
        CXXOperatorCallExpr(
          CXXOperatorCallExpr(
            DeclRefExpr("std::cout"),
            transpile(expr),
            "<<"
          ),
          StringLiteral("\\n"),
          "<<"
        )

  def transpile(ty: psl.QualType): QualType =
    val psl.QualType(t, isConst) = ty
    QualType(transpile(t), isConst)

  def transpile(ty: psl.PrimitiveType): PrimitiveType =
    ty match
      case psl.IntType    => IntType
      case psl.DoubleType => DoubleType

  def transpile(fd: psl.FunctionDecl): FunctionDecl =
    FunctionDecl(
      fd.name,
      transpile(fd.qty),
      fd.params.map(transpile),
      transpile(fd.body)
    )

  def transpile(cs: psl.CompoundStmt): CompoundStmt =
    CompoundStmt(cs.stmts.map(transpile))

  def transpile(pd: psl.ParmDecl): ParmVarDecl =
    ParmVarDecl(pd.name, transpile(pd.qty), pd.init.map(transpile))

  def transpile(rt: psl.ReturnStmt): ReturnStmt =
    ReturnStmt(transpile(rt.expr))

}
