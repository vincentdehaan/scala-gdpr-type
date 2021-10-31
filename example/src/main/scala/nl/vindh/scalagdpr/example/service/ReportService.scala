package nl.vindh.scalagdpr.example.service

import nl.vindh.scalagdpr.ProtectedDataT
import nl.vindh.scalagdpr.example.model.{MedicalRecord, Person}
import nl.vindh.scalagdpr.example.repo.{MedicalRecordRepo, PersonRepo}
import shapeless._
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.{:: => _}

class ReportService(personRepo: PersonRepo, medicalRecordRepo: MedicalRecordRepo) {
  def getReportByName(name: String): ProtectedDataT[Future, String, Person :: MedicalRecord :: HNil] =
    for {
      person <- personRepo.getPersonByName(name)
      records <- medicalRecordRepo.getMedicalRecordsByPersonId(person.id)
    } yield s"""Medical report
               |Name: ${person.name}
               |Address: ${person.address}
               |
               |Treatments: ${records.map(_.treatment).mkString(", ")}
               |""".stripMargin

}
