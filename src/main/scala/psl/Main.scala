package psl

import psl.frontend.Parser
import psl.backend.Transpiler
import psl.language._

@main def hello: Unit =
  val code: String =
    """print(1);"""
  val ast = psl.Program(code)
  val cppAst = Transpiler.transpile(ast)
  println(cppAst)
