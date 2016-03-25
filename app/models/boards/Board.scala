package models.boards

import models.states.Time
import play.api.libs.json._

case class Board(time: Time, fields: List[Field])

object Board {

  implicit val boardFormat = Json.format[Board]

}
