package psl.language.cpp

import psl.util.Appender._

object TypeStringifier {
  given typeRule: Rule[Type] = (app, t) =>
    t match
      case p: PrimitiveType => primitiveTypeRule(app, p)
      case q: QualType      => qualTypeRule(app, q)

  private val primitiveTypeRule: Rule[PrimitiveType] = (app, p) =>
    p match
      case IntType    => app >> "int"
      case DoubleType => app >> "double"

  private val qualTypeRule: Rule[QualType] = (app, q) =>
    val QualType(ty, isConst) = q
    app >> (if isConst then "const " else "") >> ty
}
