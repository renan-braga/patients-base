package repositories

import javax.inject.{Inject, Singleton}
import models.{Patient, PatientTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PatientRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val Patients = PatientTable.Patients

  def list(): Future[Seq[Patient]] = db.run(Patients.result)

  def findById(id: Long): Future[Option[Patient]] = db.run(Patients.filter(_.id === id).result.headOption)

  def create(patient: Patient): Future[Patient] = db.run {
    (Patients returning Patients.map(_.id) into ((patient, id) => patient.copy(id = Some(id)))) += patient
  }

  def update(id: Long, updatedPatient: Patient): Future[Option[Patient]] = {
    val updatedPatientQuery = for { patient <- Patients if patient.id === id } yield (patient.name, patient.age, patient.diagnosis)
    db.run(updatedPatientQuery.update((updatedPatient.name, updatedPatient.age, updatedPatient.diagnosis))).map {
      case 0 => None
      case _ => Some(updatedPatient.copy(id = Some(id)))
    }
  }

  def delete(id: Long): Future[Boolean] = db.run(Patients.filter(_.id === id).delete).map(_ > 0)
}
