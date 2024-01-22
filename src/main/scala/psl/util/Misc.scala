package psl.util

import java.io.File
import java.io.PrintWriter

/** stringify */
def stringify[T](t: T)(using rule: Appender.Rule[T]): String =
  rule(Appender(), t).toString

def mkdir(name: String): Unit = File(name).mkdirs

def getPrintWriter(filename: String): PrintWriter =
  val file = File(filename)
  val parent = file.getParent
  if (parent != null) mkdir(parent)
  PrintWriter(file)
