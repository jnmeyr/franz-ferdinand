package models.provinces

import play.api.libs.json._

object ProvinceKind extends Enumeration {

  type ProvinceKind = Value

  val Land  = Value("L")
  val Water = Value("W")

  implicit val provinceKindFormat: Format[ProvinceKind] = new Format[ProvinceKind] {

    def reads(jsValue: JsValue): JsResult[ProvinceKind] = JsSuccess(ProvinceKind.withName(jsValue.as[String]))

    def writes(provinceKind: ProvinceKind): JsString = JsString(provinceKind.toString)

  }

}
