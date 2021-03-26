package nl.vindh.scalagdpr.example

import nl.vindh.scalagdpr.DataProcessingJustification
import nl.vindh.scalagdpr.example.repo.{MockMedicalRecordRepo, MockPersonRepo}
import shapeless.HNil

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  val personRepo = new MockPersonRepo
  val medicalRecordRepo = new MockMedicalRecordRepo

  val medicalReport = for {
    protectedPerson <- personRepo.getPersonById("3")
    protectedMedicalRecord <- medicalRecordRepo.getMedicalRecordByPersonId("3")
  } yield {
    for {
      person <- protectedPerson
      medicalRecord <- protectedMedicalRecord
    } yield {
      s"""Medical report
         |Name: ${person.name}
         |Address: ${person.address}
         |
         |Diseases: ${medicalRecord.diseases.mkString(", ")}
         |""".stripMargin
    }
  }

  medicalReport.foreach {
    protectedReport => println(
      protectedReport.get(
        DataProcessingJustification
          .withPurpose["Some reporting"]
          .withSubjects["Patients"]
          .withRecipients["Other doctors"].apply
      )
    )
  }
}