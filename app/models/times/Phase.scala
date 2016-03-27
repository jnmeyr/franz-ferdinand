package models.times

import play.api.libs.json._

object Phase extends Enumeration {

  type Phase = Value

  val Diplomacy  = Value("D")
  val Resolution = Value("R")
  val Adjustment = Value("A")

  implicit val phaseFormat : Format[Phase] = new Format[Phase] {

    def reads(jsValue: JsValue): JsResult[Phase] = JsSuccess(Phase.withName(jsValue.as[String]))

    def writes(phase: Phase): JsString = JsString(phase.toString)

  }

}
