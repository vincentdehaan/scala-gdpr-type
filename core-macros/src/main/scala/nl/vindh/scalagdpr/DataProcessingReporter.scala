package nl.vindh.scalagdpr

import shapeless.HList

import scala.reflect.macros.blackbox.Context

class DataProcessingReporter[Purpose, Subjects, Recipients, H] {}

object DataProcessingReporter {

  implicit def reporter[Purpose, Subjects, Recipients, H <: HList]: DataProcessingReporter[Purpose, Subjects, Recipients, H] =
  macro report_impl[Purpose, Subjects, Recipients, H]

  // https://stackoverflow.com/questions/15649720
  def report_impl[Purpose, Subjects, Recipients, H](c: Context): c.Expr[DataProcessingReporter[Purpose, Subjects, Recipients, H]] = {
    import c.universe._
    val TypeApply(_, purposeTree :: subjectsTree :: recipientsTree :: hTree :: Nil) = c.macroApplication

    // TODO: analyze the real type tree instead of the string representation
    val dataTypes = hTree.toString.split(" :: ").dropRight(1).map(dt => if(dt.contains("\"")) dt.split("\"").tail.head else dt)

    val report =
      s"""
         |>>> Data processing:
         |Purpose: ${purposeTree.toString()}
         |Subjects: ${subjectsTree.toString()}
         |Recipients: ${recipientsTree.toString()}
         |Data types: ${dataTypes.mkString(", ")}
         |""".stripMargin

    println(report)
    c.Expr(q"new nl.vindh.scalagdpr.DataProcessingReporter[$purposeTree, $subjectsTree, $recipientsTree, $hTree]")
  }

}
