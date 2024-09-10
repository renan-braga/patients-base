package controllers


import javax.inject._
import models.Patient
import play.api.libs.json._
import play.api.mvc._
import repositories.PatientRepository

import scala.concurrent.{ExecutionContext, Future}

class PatientController @Inject()(val controllerComponents: ControllerComponents, patientRepo: PatientRepository)
                                 (implicit ec: ExecutionContext) extends BaseController {

  def listPatients() = Action.async {
    patientRepo.list().map {
      patients =>
        Ok(Json.toJson(patients))
    }
  }

  def getPatient(id: Long) = Action.async {
    patientRepo.findById(id).map {
      case Some(patient) => Ok(Json.toJson(patient))
      case None => NotFound
    }
  }

  def createPatient() = Action.async(parse.json) { request =>
    request.body.validate[Patient].map { patient =>
      patientRepo.create(patient).map { createdPatient =>
        Created(Json.toJson(createdPatient))
      }
    }.getOrElse(Future.successful(BadRequest("Invalid JSON")))
  }

  def updatePatient(id: Long) = Action.async(parse.json) { request =>
    request.body.validate[Patient].map {
      updatedPatient =>
        patientRepo.update(id, updatedPatient).map {
          case Some(patient) => Ok(Json.toJson(patient))
          case None => NotFound
        }
    }.getOrElse(Future.successful(BadRequest("Invalid JSON")))
  }

  def deletePatient(id: Long) = Action.async {
    patientRepo.delete(id).map { deleted =>
      if(deleted) NoContent else NotFound
    }
  }
}
