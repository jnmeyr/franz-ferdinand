package models.boards

import models.countries.CountryId._
import models.provinces.ProvinceId._
import models.units.Unit
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Field(id: ProvinceId, country: Option[CountryId], unit: Option[Unit])

object Field {

  val fieldReads: Reads[Field] = Json.reads[Field]

  val fieldWrites: Writes[Field] = (
    (JsPath \ "id").write[ProvinceId] and
    (JsPath \ "country").write[Option[CountryId]] and
    (JsPath \ "unit").write[Option[Unit]]
  )(unlift(Field.unapply))

  implicit val fieldFormat: Format[Field] = Format(fieldReads, fieldWrites)

}
