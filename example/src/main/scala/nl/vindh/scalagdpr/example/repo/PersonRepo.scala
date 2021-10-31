package nl.vindh.scalagdpr.example.repo

import nl.vindh.scalagdpr._
import nl.vindh.scalagdpr.example.model.Person

import scala.concurrent.Future

trait PersonRepo {
  def getPersonByName(name: String): ProtectedDataSourceT[Person]
}

class MockPersonRepo extends PersonRepo {

  override def getPersonByName(name: String): ProtectedDataSourceT[Person] =
    ProtectedDataT {
      Future.successful(ProtectedData(Person("4", "Jane Doe", "22 Sunset Boulevard")))
    }
}