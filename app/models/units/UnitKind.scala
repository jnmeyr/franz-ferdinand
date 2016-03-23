package models.units

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object UnitKind extends Enumeration {

  type UnitKind = Value

  val Army   = Value("A")
  val Fleet  = Value("F")

  implicit val unitKindFormat = new Format[UnitKind] {

    def reads(jsValue: JsValue) = JsSuccess(UnitKind.withName(jsValue.as[String]))

    def writes(unitKind: UnitKind) = JsString(unitKind.toString)

  }

}
