package nl.vindh.scalagdpr.example.api

import akka.http.scaladsl.server.Directives._
import nl.vindh.scalagdpr.DataProcessingJustification
import nl.vindh.scalagdpr.example.service.ReportService
import scala.concurrent.ExecutionContext.Implicits.global

class ReportApi (reportService: ReportService) {
  val route = path("reports") {
    get {
      parameters('patientName.as[String]) { patientName =>
        complete {
          reportService.getReportByName(patientName).value.map {
            protectedReport => protectedReport.get(
              DataProcessingJustification
                .withPurpose["Generate the patient report"]
                .withSubjects["Patients"]
                .withRecipients["Other doctors"].apply
            )
          }
        }
      }
    }
  }
}
