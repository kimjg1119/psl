package psl.frontend

import scala.util.parsing.combinator.*
import java.text.ParseException

case class ParseException(msg: String) extends Exception(msg)

object Parser extends DebugableParser {
  import psl.language.psl._

  type P[+T] = PackratParser[T]
  class From[T](p: Parser[T]) {
    def apply(s: String): T =
      parseAll(p, s).getOrElse(throw ParseException(s"Parse error: $s"))
  }

  private val keywords = Set("println", "int", "double", "return")

  lazy val id: P[String] =
    """[a-zA-Z_][a-zA-Z0-9_]*""".r.withFilter(!keywords.contains(_))

  /* Type */
  lazy val qualType: P[QualType] = "QualType" !!! (primitiveType ^^ { t =>
    QualType.apply(t, false)
  })
  lazy val primitiveType = intType | doubleType
  lazy val intType = "int" ^^ { _ => IntType }
  lazy val doubleType = "double" ^^ { _ => DoubleType }

  /* Stmt */
  lazy val stmt: P[Stmt] =
    (expr | declStmt | compoundStmt | returnStmt) <~ opt(";")

  lazy val declStmt: P[DeclStmt] = decl ^^ { d => DeclStmt.apply(d) }
  lazy val compoundStmt: P[CompoundStmt] =
    "{" ~> rep(stmt) <~ "}" ^^ { s => CompoundStmt.apply(s) }
  lazy val returnStmt: P[ReturnStmt] = "return" ~> expr ^^ { e =>
    ReturnStmt.apply(e)
  }

  /* Expr */
  lazy val expr: P[Expr] = (printlnExpr | integerLiteral | stringLiteral)

  lazy val integerLiteral: P[Expr] = """\d+""".r ^^ { e =>
    IntegerLiteral(e.toInt)
  }
  lazy val stringLiteral: P[Expr] = """"[^"]*"""".r ^^ { e =>
    StringLiteral(e.substring(1, e.length - 1))
  }

  lazy val printlnExpr: P[Expr] =
    "println" ~> ("(" ~> expr <~ ")") ^^ { e => Println.apply(e) }

  /* Decl */
  lazy val decl: P[Decl] =
    "FunctionDecl" !!! functionDecl | "VarDecl" !!! varDecl
  lazy val rootDecl: P[RootDecl] = rep(stmt) ^^ { r => RootDecl.apply(r) }
  lazy val parmDecl: P[ParmDecl] = qualType ~ id ^^ { case qty ~ name =>
    ParmDecl.apply(name, qty, init = None)
  }
  lazy val functionDecl: P[FunctionDecl] =
    qualType ~ id ~ ("(" ~> repsep(parmDecl, ",") <~ ")") ~ compoundStmt ^^ {
      case qty ~ name ~ params ~ body =>
        FunctionDecl.apply(qty, name, params, body)
    }
  lazy val varDecl: P[VarDecl] = "NotImplemented".r ^^ { v =>
    VarDecl.apply(v, null, null)
  }
}
