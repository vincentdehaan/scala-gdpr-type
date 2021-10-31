package nl.vindh.scalagdpr.example

import akka.actor.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import cats.{Id, Monad}
import cats.data.OptionT
import nl.vindh.scalagdpr.example.api.ReportApi
import nl.vindh.scalagdpr.{DataProcessingJustification, ProtectedDataT}
import nl.vindh.scalagdpr.example.repo.{MockMedicalRecordRepo, MockPersonRepo}
import nl.vindh.scalagdpr.example.service.ReportService
import shapeless.HNil

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn

object Main extends App {
  implicit val system = ActorSystem("my-system")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val personRepo = new MockPersonRepo
  val medicalRecordRepo = new MockMedicalRecordRepo

  val reportService = new ReportService(personRepo, medicalRecordRepo)

  val reportApi = new ReportApi(reportService)

  val bindingFuture = Http().newServerAt("localhost", 8080).bind(reportApi.route)

  println(s"Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}