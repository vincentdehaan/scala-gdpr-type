package nl.vindh.scalagdpr

import cats.{Functor, Monad}
import shapeless.HList
import shapeless.ops.hlist.Prepend

final case class ProtectedDataT[F[_], T, H <: HList](value: F[ProtectedData[T, H]]) {
  def map[U](f: T => U)(implicit F: Functor[F]): ProtectedDataT[F, U, H] =
    ProtectedDataT(F.map(value)(pd => pd.map(f)))

  def flatMap[U, H1 <: HList](f: T => ProtectedDataT[F, U, H1])(implicit p: Prepend[H, H1], F: Monad[F]): ProtectedDataT[F, U, p.Out] =
    flatMapF(a => f(a).value).asInstanceOf[ProtectedDataT[F, U, p.Out]]

  def flatMapF[U, H1 <: HList](f: T => F[ProtectedData[U, H1]])(implicit p: Prepend[H, H1], F: Monad[F]): ProtectedDataT[F, U, p.Out] =
    ProtectedDataT(F.flatMap(value)(getProtected andThen f)).asInstanceOf[ProtectedDataT[F, U, p.Out]]

  private val getProtected = (pd: ProtectedData[T, H]) => pd.get(DataProcessingJustification.unsafeJustification)

}