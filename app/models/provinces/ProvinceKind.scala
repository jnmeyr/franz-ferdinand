package models.provinces

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object ProvinceKind extends Enumeration {

  type ProvinceKind = Value

  val Land  = Value("L")
  val Water = Value("W")

  implicit val provinceKindFormat = new Format[ProvinceKind] {

    def reads(jsValue: JsValue) = JsSuccess(ProvinceKind.withName(jsValue.as[String]))

    def writes(provinceKind: ProvinceKind) = JsString(provinceKind.toString)

  }

}
