package models.provinces

import models.countries.CountryId._
import models.provinces.ProvinceId._
import models.units.Unit
import models.units.UnitKind._
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class P(id: ProvinceId, country: Option[CountryId], unit: Option[Unit])

case class Provinces(provinces: List[P])

object Provinces {

  val provinceReads: Reads[P] = Json.reads[P]

  implicit val provinceWrites: Writes[P] = (
    (JsPath \ "id").write[ProvinceId] and
    (JsPath \ "country").write[Option[CountryId]] and
    (JsPath \ "unit").write[Option[Unit]]
  )(unlift(P.unapply))

  implicit val provinceFormat: Format[P] = Format(provinceReads, provinceWrites)

  implicit val provincesFormat: Format[Provinces] = Json.format[Provinces]

  val provinces: Provinces = Provinces(
    List(
      P(StpNc, None, None),
      P(Ber, Some(Germany), None),
      P(Ber, None, Some(Unit(Fleet, Germany))),
      P(Ber, Some(Germany), Some(Unit(Fleet, Germany)))
    )
  )

}
