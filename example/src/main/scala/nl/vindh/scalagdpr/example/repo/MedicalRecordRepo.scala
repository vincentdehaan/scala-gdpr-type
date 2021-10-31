package nl.vindh.scalagdpr.example.repo

import nl.vindh.scalagdpr.{ProtectedData, ProtectedDataSource, ProtectedDataSourceF, ProtectedDataT}
import nl.vindh.scalagdpr.example.model.MedicalRecord
import shapeless.HNil

import scala.concurrent.Future

trait MedicalRecordRepo {
  def getMedicalRecordsByPersonId(personId: String): ProtectedDataSourceFT[List, MedicalRecord]
}

class MockMedicalRecordRepo extends MedicalRecordRepo {
  def getMedicalRecordsByPersonId(personId: String): ProtectedDataSourceFT[List, MedicalRecord] =
    ProtectedDataT {
      Future.successful {
        ProtectedData {
          List(
            MedicalRecord(personId, "broken leg", "pain killers")
          )
        }
      }
    }
}