package psl.language.cpp

trait CppASTNode

case class Program(pre: List[Preproc], ast: TranslationUnitDecl)
