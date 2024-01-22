package psl.frontend

import scala.util.parsing.combinator.*
import java.text.ParseException

case class ParseException(msg: String) extends Exception(msg)

object Parser extends RegexParsers with PackratParsers {
  import psl.language.psl._

  type P[+T] = PackratParser[T]
  class From[T](p: Parser[T]) {
    def apply(s: String): T =
      parseAll(p, s).getOrElse(throw ParseException(s"Parse error: $s"))
  }

  private val keywords = Set("print")

  /* Expr */
  lazy val expr: P[Expr] = printExpr | integerLiteral

  lazy val integerLiteral: P[Expr] = """\d+""".r ^^ { e =>
    Expr.IntegerLiteral(e)
  }

  lazy val printExpr: P[Expr] =
    "print" ~> ("(" ~> expr <~ ")") ^^ Expr.PrintExpr.apply

  /* Stmt */
  lazy val stmt: P[Stmt] = (
    expr <~ ";" ^^ Stmt.ExprStmt.apply
  )

  /* Program */
  lazy val program: P[Program] = stmt.* ^^ Program.apply
}
