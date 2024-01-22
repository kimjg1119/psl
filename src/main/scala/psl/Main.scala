package psl

import psl.frontend.Parser
import psl.backend.Transpiler
import psl.util._
import psl.language._

@main def hello: Unit =
  val code: String =
    """print(1);"""
  val ast = psl.Program(code)
  val cppAst = Transpiler.transpile(ast)

  val stringifier = cpp.Stringifier()
  import stringifier.given
  println(stringify(cppAst))
