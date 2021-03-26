package nl.vindh.scalagdpr.example.repo

import nl.vindh.scalagdpr.ProtectedData
import nl.vindh.scalagdpr.example.model.Person
import shapeless.HNil

import scala.concurrent.Future

trait PersonRepo {
  def getPersonById(id: String): Future[
    ProtectedData[Person, shapeless.::["Person", HNil]]
  ]
}

class MockPersonRepo extends PersonRepo {
  def getPersonById(id: String): Future[
    ProtectedData[Person, shapeless.::["Person", HNil]]
  ] =
    Future.successful {
      ProtectedData {
        Person(id, "John Doe", "Wall Street 1")
      }
    }
}