package nl.vindh.scalagdpr.example.repo

import nl.vindh.scalagdpr.{ProtectedData, ProtectedDataSource, ProtectedDataSourceF}
import nl.vindh.scalagdpr.example.model.MedicalRecord
import shapeless.HNil

import scala.concurrent.Future

trait MedicalRecordRepo {
  def getMedicalRecordByPersonId(personId: String): Future[
    ProtectedDataSourceF[List, MedicalRecord]
  ]
}

class MockMedicalRecordRepo extends MedicalRecordRepo {
  def getMedicalRecordsByPersonId(personId: String): Future[
    ProtectedDataSourceF[List, MedicalRecord]
  ] =
    Future.successful {ProtectedData {
      List(
        MedicalRecord("broken leg", "pain killers")
      )}
    }

  def getMedicalRecordsByPersonIdSync(personId: String):
    ProtectedDataSourceF[List, MedicalRecord] =
    ProtectedData {
      List(
        MedicalRecord("broken leg", "pain killers")
      )}
}