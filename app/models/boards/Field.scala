package models.boards

import models.countries.CountryId._
import models.provinces.ProvinceId._
import models.units.Unit
import play.api.libs.json.Json

case class Field(id: ProvinceId, country: Option[CountryId], unit: Option[Unit])

object Field {

  implicit val fieldFormat = Json.format[Field]

}
