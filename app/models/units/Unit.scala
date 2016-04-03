package models.units

import models.countries.CountryId.CountryId
import models.units.UnitKind.UnitKind
import play.api.libs.json._

sealed trait Unit {

  def countryId: CountryId

}

case class Army(countryId: CountryId) extends Unit

case class Fleet(countryId: CountryId) extends Unit

object Unit {

  def apply(unitKind: UnitKind, countryId: CountryId): Unit = {
    unitKind match {
      case UnitKind.Army =>
        Army(countryId)
      case UnitKind.Fleet =>
        Fleet(countryId)
    }
  }

  def unapply(unit: Unit): (UnitKind, CountryId) = {
    unit match {
      case Army(countryId) =>
        (UnitKind.Army, countryId)
      case Fleet(countryId) =>
        (UnitKind.Fleet, countryId)
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
