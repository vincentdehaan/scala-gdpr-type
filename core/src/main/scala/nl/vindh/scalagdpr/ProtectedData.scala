package nl.vindh.scalagdpr

import shapeless.{HList, Typeable}
import shapeless.ops.hlist
import shapeless.ops.hlist.Prepend

import scala.reflect.ClassTag

/*
* T: Type of the current value
* D: Descriptor of the data
* H: HList of the history of types necessary to compute this value
* */
class ProtectedData[T, H <: HList] private(value: T) {
  def get(justification: DataProcessingJustification[H]): T = value

  def flatMap[U, H1 <: HList](f: T => ProtectedData[U, H1])(implicit p: Prepend[H, H1]): ProtectedData[U, p.Out] =
    f(value).asInstanceOf[ProtectedData[U, p.Out]]

  def map[U](f: T => U): ProtectedData[U, H] =
    ProtectedData {
      f(value)
    }
}

object ProtectedData {
  def apply[T, H <: HList](value: T): ProtectedData[T, H] = new ProtectedData(value)
}

class DataProcessingJustification[H <: HList] {}
class WithPurpose[Purpose] {
  def withSubjects[Subjects]: WithSubjects[Purpose, Subjects] = new WithSubjects[Purpose, Subjects]
}
class WithSubjects[Purpose, Subjects] {
  def withRecipients[Recipients]: WithRecipients[Purpose, Subjects, Recipients] = new WithRecipients[Purpose, Subjects, Recipients]
}
class WithRecipients[Purpose, Subjects, Recipients] {
  def apply[H <: HList](implicit r: DataProcessingReporter[Purpose, Subjects, Recipients, H]) = new DataProcessingJustification[H]
}

object DataProcessingJustification {

  def withPurpose[T]: WithPurpose[T] = new WithPurpose[T]

}