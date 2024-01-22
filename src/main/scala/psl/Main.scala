package psl

import psl.frontend.Parser
import psl.backend.Transpiler
import psl.util._
import psl.language._

@main def hello: Unit =
  val code = scala.io.Source.fromFile("main.psl").mkString.trim()
  val ast = psl.RootDecl(code)
  val cppAst = Transpiler.run(ast)

  import cpp.Stringifier.given
  val pr = getPrintWriter("out.cpp")
  pr.println(stringify(cppAst))
  pr.close()
