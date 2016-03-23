package models.provinces

import play.api.libs.json.{Format, JsString, JsSuccess, JsValue}

object ProvinceId extends Enumeration {

  type ProvinceId = Value

  val Ber   = Value("Ber")
  val Bul   = Value("Bul")
  val BulEc = Value("Bul-Ec")
  val BulSc = Value("Bul-Sc")
  val Kie   = Value("Kie")
  val Ska   = Value("Ska")
  val Spa   = Value("Spa")
  val SpaNc = Value("Spa-Nc")
  val SpaSc = Value("Spa-Sc")
  val Stp   = Value("Stp")
  val StpNc = Value("Stp-Nc")
  val StpSc = Value("Stp-Sc")

  implicit val provinceIdFormat = new Format[ProvinceId] {

    def reads(jsValue: JsValue) = JsSuccess(ProvinceId.withName(jsValue.as[String]))

    def writes(provinceId: ProvinceId) = JsString(provinceId.toString)

  }

}
