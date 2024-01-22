package psl.util

/** stringify */
def stringify[T](t: T)(using rule: Appender.Rule[T]): String =
  rule(Appender(), t).toString
