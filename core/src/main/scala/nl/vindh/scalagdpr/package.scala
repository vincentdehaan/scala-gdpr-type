package nl.vindh

import cats.Id
import shapeless.{HList, HNil}

package object scalagdpr {
  type ProtectedDataSource[T] = ProtectedData[T, shapeless.::[T, HNil]]
  type ProtectedDataSourceF[F[_], T] = ProtectedData[F[T], shapeless.::[T, HNil]]

  //implicit class ProtectedDataWithOption[F[_], T, H <: HList](pd: ProtectedData[F, Option[T], H]) {
  //  def toOption: Option[ProtectedData[T, H]] = pd.value.map(ProtectedData(_))
  //}
}
