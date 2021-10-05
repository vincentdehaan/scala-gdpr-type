package nl.vindh

import shapeless.{HList, HNil}

package object scalagdpr {
  type ProtectedDataSource[T] = ProtectedData[T, shapeless.::[T, HNil]]

  implicit class ProtectedDataWithOption[T, H <: HList](pd: ProtectedData[Option[T], H]) {
    def toOption: Option[ProtectedData[T, H]] = pd.value.map(ProtectedData(_))
  }
}
