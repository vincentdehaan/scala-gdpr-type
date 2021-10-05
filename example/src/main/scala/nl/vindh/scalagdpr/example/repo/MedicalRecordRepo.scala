package nl.vindh.scalagdpr.example.repo

import nl.vindh.scalagdpr.{ProtectedData, ProtectedDataSource}
import nl.vindh.scalagdpr.example.model.MedicalRecord
import shapeless.HNil

import scala.concurrent.Future

trait MedicalRecordRepo {
  def getMedicalRecordByPersonId(personId: String): Future[
    ProtectedDataSource[MedicalRecord]
  ]
}

class MockMedicalRecordRepo extends MedicalRecordRepo {
  def getMedicalRecordByPersonId(personId: String): Future[
    List[ProtectedDataSource[MedicalRecord]]
  ] =
    Future.successful {
      List(ProtectedData {
        MedicalRecord(personId, Seq("influenza", "broken leg"))
      })
    }
}