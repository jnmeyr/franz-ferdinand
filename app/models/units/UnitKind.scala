package models.units

import play.api.libs.json._

object UnitKind extends Enumeration {

  type UnitKind = Value

  val Army   = Value("A")
  val Fleet  = Value("F")

  implicit val unitKindFormat: Format[UnitKind] = new Format[UnitKind] {

    def reads(jsValue: JsValue): JsResult[UnitKind] = JsSuccess(UnitKind.withName(jsValue.as[String]))

    def writes(unitKind: UnitKind): JsString = JsString(unitKind.toString)

  }

}
