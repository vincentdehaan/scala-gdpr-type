package nl.vindh.scalagdpr

import cats.Id
import shapeless.{HList, HNil, LabelledGeneric, Typeable}
import shapeless.ops.hlist
import shapeless.ops.hlist.{Length, Prepend}
import shapeless.ops.record._

import scala.reflect.ClassTag

/*
* T: Type of the current value
* D: Descriptor of the data
* H: HList of the history of types necessary to compute this value
* */
class ProtectedData[T, H <: HList] private[scalagdpr](private[scalagdpr] val value: T) {
  def get(justification: DataProcessingJustification[H]): T = value

  def flatMap[U, H1 <: HList](f: T => ProtectedData[U, H1])(implicit p: Prepend[H, H1]): ProtectedData[U, p.Out] =
    f(value).asInstanceOf[ProtectedData[U, p.Out]]

  def map[U](f: T => U): ProtectedData[U, H] =
    ProtectedData {
      f(value)
    }

  ///implicit class ProtectedDataWithExtract[T](pd: ProtectedData[Id, T, shapeless.::[Product, HNil]]) {
  //  def extract[FieldName <: Singleton, E](implicit gen: LabelledGeneric[T]): ProtectedData[Id, T, shapeless.::[FieldName, HNil]] = {
  //    gen.toMap
  //  }
  //}
}


object ProtectedData {
  def apply[T, H <: HList](value: T): ProtectedData[T, H] = new ProtectedData(value)
  def applyF[F[_], T, H <: HList](value: F[T]): ProtectedData[F[T], H] = new ProtectedData(value)
}

class DataProcessingJustification[H <: HList] private[scalagdpr] {}
class WithPurpose[Purpose] private[scalagdpr] {
  def withSubjects[Subjects]: WithSubjects[Purpose, Subjects] = new WithSubjects[Purpose, Subjects]
}
class WithSubjects[Purpose, Subjects] private[scalagdpr] {
  def withRecipients[Recipients]: WithRecipients[Purpose, Subjects, Recipients] = new WithRecipients[Purpose, Subjects, Recipients]
}
class WithRecipients[Purpose, Subjects, Recipients] private[scalagdpr] {
  def apply[H <: HList](implicit r: DataProcessingReporter[Purpose, Subjects, Recipients, H]) = new DataProcessingJustification[H]
}

object DataProcessingJustification {

  def withPurpose[T]: WithPurpose[T] = new WithPurpose[T]

  def apply[Purpose, Subjects, Recipients] = new Object {
    def apply[H <: HList](implicit r: DataProcessingReporter[Purpose, Subjects, Recipients, H]) = new DataProcessingJustification[H]
  }

  private[scalagdpr] def unsafeJustification[H <: HList] = new DataProcessingJustification[H] {}

}