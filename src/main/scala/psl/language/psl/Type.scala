package psl.language.psl

sealed trait Type

sealed trait PrimitiveType extends Type

object IntType extends PrimitiveType
object DoubleType extends PrimitiveType

case class QualType(val ty: PrimitiveType, val isConst: Boolean = false)
    extends Type
