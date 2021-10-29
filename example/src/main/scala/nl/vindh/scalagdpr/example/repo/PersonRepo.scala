package nl.vindh.scalagdpr.example.repo

import cats.Id
import nl.vindh.scalagdpr._
import nl.vindh.scalagdpr.example.model.Person
import shapeless.HNil

import scala.concurrent.Future

trait PersonRepo {
  def getPersonById(id: String): Future[
    ProtectedDataSource[Person]
  ]

  def getPersonByName(name: String): Future[
    ProtectedDataSource[Person]
  ]
}

class MockPersonRepo extends PersonRepo {
  def getPersonById(id: String): Future[
    ProtectedDataSource[Person]
  ] =
    Future.successful {
      ProtectedData {
        Person(id, "John Doe", "1 Wall Street"): Id[Person]
      }
    }

  override def getPersonByName(name: String): Future[
    ProtectedDataSource[Person]
  ] =
    Future.successful {
      ProtectedData {
        Person("4", "Jane Doe", "22 Sunset Boulevard")
      }
    }
}