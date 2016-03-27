package models.times

import play.api.libs.json._

object Season extends Enumeration {

  type Season = Value

  val Spring = Value("S")
  val Fall   = Value("F")

  implicit val seasonFormat: Format[Season] = new Format[Season] {

    def reads(jsValue: JsValue): JsResult[Season]  = JsSuccess(Season.withName(jsValue.as[String]))

    def writes(season: Season): JsString = JsString(season.toString)

  }

}
