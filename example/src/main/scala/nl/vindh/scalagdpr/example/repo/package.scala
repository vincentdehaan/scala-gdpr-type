package nl.vindh.scalagdpr.example

import nl.vindh.scalagdpr.ProtectedDataT
import shapeless.HNil

import scala.concurrent.Future

package object repo {
  type ProtectedDataSourceT[T] = ProtectedDataT[Future, T, shapeless.::[T, HNil]]
  type ProtectedDataSourceFT[F[_], T] = ProtectedDataT[Future, F[T], shapeless.::[T, HNil]]
}
