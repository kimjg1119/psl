package psl.language.cpp

import psl.util.Appender._

object PreprocStringifier {
  given preprocRule: Rule[Preproc] = (app, nd) =>
    nd match
      case IncludeProc(path) => app >> "#include \"" >> path >> "\"\n"
}
