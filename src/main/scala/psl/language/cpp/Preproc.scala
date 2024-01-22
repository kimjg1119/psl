package psl.language.cpp

trait Preproc extends CppASTNode

case class IncludeProc(val path: String) extends Preproc
