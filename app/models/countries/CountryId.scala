package models.countries

import play.api.libs.json._

object CountryId extends Enumeration {

  type CountryId = Value

  val Austria = Value("A")
  val England = Value("E")
  val France  = Value("F")
  val Germany = Value("G")
  val Italy   = Value("I")
  val Russia  = Value("R")
  val Turkey  = Value("T")

  implicit val countryIdFormat = new Format[CountryId] {

    def reads(jsValue: JsValue) = JsSuccess(CountryId.withName(jsValue.as[String]))

    def writes(countryId: CountryId) = JsString(countryId.toString)

  }

}
