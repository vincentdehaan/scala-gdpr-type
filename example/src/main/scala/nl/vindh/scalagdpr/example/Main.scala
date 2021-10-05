package nl.vindh.scalagdpr.example

import cats.Monad
import cats.data.OptionT
import nl.vindh.scalagdpr.{DataProcessingJustification, ProtectedDataT}
import nl.vindh.scalagdpr.example.repo.{MockMedicalRecordRepo, MockPersonRepo}
import shapeless.HNil

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Main extends App {
  val personRepo = new MockPersonRepo
  val medicalRecordRepo = new MockMedicalRecordRepo

  val medicalReport = (for {
    person <- ProtectedDataT(personRepo.getPersonByName("Jane Doe"))
    medicalRecord <- ProtectedDataT(medicalRecordRepo.getMedicalRecordByPersonId(person.id))
  } yield {
    s"""Medical report
       |Name: ${person.name}
       |Address: ${person.address}
       |
       |Diseases: ${medicalRecord.diseases.mkString(", ")}
       |""".stripMargin
  }).value

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


  val optT =
    for {
      a <- OptionT(Future.successful[Option[Int]](Some(3)))
      b <- OptionT(f(a))
    } yield b


optT.value.foreach(println)
  Thread.sleep(400)
  def f(i: Int): Future[Option[Int]] = Future.successful(Some(i + 2))
}