package psl.language.cpp

sealed class Decl extends CppASTNode

/** Represents a translation unit declaration.
  *
  * Reference:
  * https://clang.llvm.org/doxygen/classclang_1_1TranslationUnitDecl.html
  */
class TranslationUnitDecl(val nodes: List[CppASTNode]) extends Decl
object TranslationUnitDecl {
  def unapply(tu: TranslationUnitDecl) = Some(tu.nodes)
}

/** Represents a declaration with a name.
  *
  * Reference: https://clang.llvm.org/doxygen/classclang_1_1NamedDecl.html
  * @param name
  *   The name of the declaration.
  */
class NamedDecl(val name: String) extends Decl
object NamedDecl {
  def unapply(nd: NamedDecl) = Some(nd.name)
}

/** Represents a declaration that can be used as a value.
  *
  * Reference: https://clang.llvm.org/doxygen/classclang_1_1ValueDecl.html
  * @param name
  *   Refer to [[NamedDecl]].
  * @param qualType
  *   The type of the declaration.
  */
class ValueDecl(name: String, val qualType: QualType) extends NamedDecl(name)

/** Represents a declaration that can be used as a declarator.
  *
  * NOTE: This class does nothing right now.
  *
  * Reference: https://clang.llvm.org/doxygen/classclang_1_1DeclaratorDecl.html
  * @param name
  *   Refer to [[NamedDecl]].
  * @param ty
  *   Refer to [[ValueDecl]].
  */
class DeclaratorDecl(name: String, qty: QualType) extends ValueDecl(name, qty)

/** Represents a function declaration.
  *
  * Reference: https://clang.llvm.org/doxygen/classclang_1_1FunctionDecl.html
  * @param name
  *   Refer to [[NamedDecl]].
  * @param qty
  *   Refer to [[ValueDecl]].
  * @param paramInfo
  *   The parameters of the function.
  * @param body
  *   The body of the function.
  */
class FunctionDecl(
    name: String,
    qty: QualType,
    val paramInfo: List[ParmVarDecl],
    val body: CompoundStmt
) extends DeclaratorDecl(name, qty)
object FunctionDecl {
  def unapply(fd: FunctionDecl) =
    Some((fd.name, fd.qualType, fd.paramInfo, fd.body))
}

/** Represents a variable declaration.
  *
  * Reference: https://clang.llvm.org/doxygen/classclang_1_1VarDecl.html
  * @param name
  *   Refer to [[NamedDecl]].
  * @param qty
  *   Refer to [[ValueDecl]].
  * @param init
  *   The initizing expression of the variable.
  */
class VarDecl(name: String, qty: QualType, val init: Option[Expr])
    extends DeclaratorDecl(name, qty)
object VarDecl {
  def unapply(vd: VarDecl) = Some((vd.name, vd.qualType, vd.init))
}

/** Represents a parameter variable declaration.
  *
  * Reference: https://clang.llvm.org/doxygen/classclang_1_1ParmVarDecl.html
  * @param name
  *   Refer to [[NamedDecl]].
  * @param qty
  *   Refer to [[ValueDecl]].
  * @param defArg
  *   The default argument of the parameter. Also refer to [[VarDecl]].
  */
class ParmVarDecl(name: String, qty: QualType, defArg: Option[Expr])
    extends VarDecl(name, qty, defArg)
object ParmVarDecl {
  def unapply(pvd: ParmVarDecl) = Some((pvd.name, pvd.qualType, pvd.init))
}
