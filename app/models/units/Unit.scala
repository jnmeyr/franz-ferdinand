package models.units

import models.countries.CountryId.CountryId
import models.units.UnitKind.UnitKind
import play.api.libs.json.Json

case class Unit(kind: UnitKind, country: CountryId)

object Unit {

  implicit val unitFormat = Json.format[Unit]

}
