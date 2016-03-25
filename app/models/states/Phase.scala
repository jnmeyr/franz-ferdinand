package models.states

import play.api.libs.json._

object Phase extends Enumeration {

  type Phase = Value

  val Diplomacy  = Value("D")
  val Resolution = Value("R")
  val Adjustment = Value("A")

  implicit val phaseFormat = new Format[Phase] {

    def reads(jsValue: JsValue) = JsSuccess(Phase.withName(jsValue.as[String]))

    def writes(phase: Phase) = JsString(phase.toString)

  }

}
