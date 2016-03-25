package models.states

import play.api.libs.json._

object Season extends Enumeration {

  type Season = Value

  val Spring = Value("S")
  val Fall   = Value("F")

  implicit val seasonFormat = new Format[Season] {

    def reads(jsValue: JsValue) = JsSuccess(Season.withName(jsValue.as[String]))

    def writes(season: Season) = JsString(season.toString)

  }

}
