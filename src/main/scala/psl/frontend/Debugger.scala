package psl.frontend

import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.RegexParsers

val debugParsingFlag: Boolean = true

trait DebugableParser extends RegexParsers with PackratParsers {

  trait Wrapped {
    def !!![T](p: Parser[T]): Wrap[T]
  }

  class StringWrapper(name: String) extends Wrapped {
    def !!![T](p: Parser[T]): Wrap[T] = new Wrap(name, p)
  }

  class Wrap[+T](name: String, parser: Parser[T]) extends Parser[T] {
    def apply(in: Input): ParseResult[T] = {
      if (debugParsingFlag) {
        val first = in.first
        val pos = in.pos
        val offset = in.offset
        val t = parser.apply(in)
        println(
          name + ".apply for token " + first +
            " at position " + pos + " offset " + offset + " returns " + t + "\n"
        )
        t
      } else parser.apply(in)
    }
  }

  implicit def toWrapped(name: String): Wrapped = new StringWrapper(name)
}
