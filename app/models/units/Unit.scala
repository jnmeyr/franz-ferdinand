package models.units

import models.countries.CountryId.CountryId
import models.units.UnitKind._
import play.api.libs.json._

sealed trait Unit {

  def unitKind: UnitKind

  def countryId: CountryId

}

case class ArmyUnit(countryId: CountryId) extends Unit {

  val unitKind = Army

}

case class FleetUnit(countryId: CountryId) extends Unit {

  val unitKind = Fleet

}

object Unit {

  def apply(unitKind: UnitKind, countryId: CountryId): Unit = {
    unitKind match {
      case Army =>
        ArmyUnit(countryId)
      case Fleet =>
        FleetUnit(countryId)
    }
  }

  def unapply(unit: Unit): (UnitKind, CountryId) = {
    unit match {
      case ArmyUnit(countryId) =>
        (Army, countryId)
      case FleetUnit(countryId) =>
        (Fleet, countryId)
    }
  }

  implicit val unitFormat: Format[Unit] = new Format[Unit] {

    override def reads(jsValue: JsValue): JsResult[Unit] = {
      (jsValue \ "kind", jsValue \ "country") match {
        case (JsDefined(kindJsValue), JsDefined(countryJsValue)) =>
          (Json.fromJson[UnitKind](kindJsValue), Json.fromJson[CountryId](countryJsValue)) match {
            case (JsSuccess(unitKind, _), JsSuccess(countryId, _)) =>
              JsSuccess(Unit(unitKind, countryId))
            case _ =>
              JsError()
          }
        case _ =>
          JsError()
      }

    }

    override def writes(unit: Unit): JsValue = {
      val (unitKind, countryId) = Unit.unapply(unit)

      JsObject(Map(
        "kind" -> Json.toJson(unitKind),
        "country" -> Json.toJson(countryId)
      ))
    }

  }

}
