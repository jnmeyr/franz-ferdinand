package models.countries

import play.api.libs.json._

object Country extends Enumeration {

  type Country = Value

  val Austria = Value("A")
  val England = Value("E")
  val France  = Value("F")
  val Germany = Value("G")
  val Italy   = Value("I")
  val Russia  = Value("R")
  val Turkey  = Value("T")

  implicit val countryFormat = new Format[Country] {

    def reads(jsValue: JsValue) = JsSuccess(Country.withName(jsValue.as[String]))

    def writes(country: Country) = JsString(country.toString)

  }

}