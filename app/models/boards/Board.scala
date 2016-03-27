package models.boards

import models.countries.CountryId._
import models.provinces.ProvinceId._
import models.times.Phase._
import models.times.Season._
import models.times.Time
import models.units.Unit
import models.units.UnitKind._
import play.api.libs.json._

case class Board(time: Time, fields: List[Field])

object Board {

  implicit val boardFormat: Format[Board] = Json.format[Board]

  val board: Board = Board(
    Time(1901, Spring, Diplomacy),
    List(
      Field(Stp, None, None),
      Field(Ber, Some(Germany), None),
      Field(Ber, None, Some(Unit(Fleet, Germany))),
      Field(Ber, Some(Germany), Some(Unit(Fleet, Germany)))
    )
  )

}
