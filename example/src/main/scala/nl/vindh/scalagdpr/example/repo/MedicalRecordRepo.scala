package nl.vindh.scalagdpr.example.repo

import nl.vindh.scalagdpr.ProtectedData
import nl.vindh.scalagdpr.example.model.MedicalRecord
import shapeless.HNil

import scala.concurrent.Future

trait MedicalRecordRepo {
  def getMedicalRecordByPersonId(personId: String): Future[
    ProtectedData[MedicalRecord, shapeless.::["MedicalRecord", HNil]]
  ]
}

class MockMedicalRecordRepo extends MedicalRecordRepo {
  def getMedicalRecordByPersonId(personId: String): Future[
    ProtectedData[MedicalRecord, shapeless.::["MedicalRecord",  HNil]]
  ] =
    Future.successful {
      ProtectedData {
        MedicalRecord(personId, Seq("influenza", "broken leg"))
      }
    }
}