package models

import play.api.libs.json.{Json, OFormat}
import slick.jdbc.PostgresProfile.api._

case class Patient(id: Option[Long], name: String, age: Int, diagnosis: String)

object Patient {
  implicit val patientFormat: OFormat[Patient] = Json.format[Patient]
}

class PatientTable(tag: Tag) extends Table[Patient](tag, "patients") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def age = column[Int]("age")
  def diagnosis = column[String]("diagnosis")

  def * = (id.?, name, age, diagnosis) <> ((Patient.apply _).tupled, Patient.unapply)

}

object PatientTable {
  val Patients = TableQuery[PatientTable]
}