package models.provinces

import play.api.libs.json._

import scala.language.implicitConversions

object ProvinceId extends Enumeration {

  type ProvinceId = Value

  val Adr   = Value("Adr")
  val Aeg   = Value("Aeg")
  val Alb   = Value("Alb")
  val Ank   = Value("Ank")
  val Apu   = Value("Apu")
  val Arm   = Value("Arm")
  val Bal   = Value("Bal")
  val Bar   = Value("Bar")
  val Bel   = Value("Bel")
  val Ber   = Value("Ber")
  val Bla   = Value("Bla")
  val Boh   = Value("Boh")
  val Bot   = Value("Bot")
  val Bre   = Value("Bre")
  val Bud   = Value("Bud")
  val Bul   = Value("Bul")
  val BulEc = Value("Bul-Ec")
  val BulSc = Value("Bul-Sc")
  val Bur   = Value("Bur")
  val Cly   = Value("Cly")
  val Con   = Value("Con")
  val Den   = Value("Den")
  val Eas   = Value("Eas")
  val Edi   = Value("Edi")
  val Eng   = Value("Eng")
  val Fin   = Value("Fin")
  val Gal   = Value("Gal")
  val Gas   = Value("Gas")
  val Gre   = Value("Gre")
  val Hel   = Value("Hel")
  val Hol   = Value("Hol")
  val Ion   = Value("Ion")
  val Iri   = Value("Iri")
  val Kie   = Value("Kie")
  val Lon   = Value("Lon")
  val Lvn   = Value("Lvn")
  val Lvp   = Value("Lvp")
  val Lyo   = Value("Lyo")
  val Mao   = Value("Mao")
  val Mar   = Value("Mar")
  val Mos   = Value("Mos")
  val Mun   = Value("Mun")
  val Naf   = Value("Naf")
  val Nao   = Value("Nao")
  val Nap   = Value("Nap")
  val Nor   = Value("Nor")
  val Nth   = Value("Nth")
  val Nwg   = Value("Nwg")
  val Par   = Value("Par")
  val Pic   = Value("Pic")
  val Pie   = Value("Pie")
  val Por   = Value("Por")
  val Pru   = Value("Pru")
  val Rom   = Value("Rom")
  val Ruh   = Value("Ruh")
  val Rum   = Value("Rum")
  val Ser   = Value("Ser")
  val Sev   = Value("Sev")
  val Sil   = Value("Sil")
  val Ska   = Value("Ska")
  val Smy   = Value("Smy")
  val Spa   = Value("Spa")
  val SpaNc = Value("Spa-Nc")
  val SpaSc = Value("Spa-Sc")
  val Stp   = Value("Stp")
  val StpNc = Value("Stp-Nc")
  val StpSc = Value("Stp-Sc")
  val Swe   = Value("Swe")
  val Syr   = Value("Syr")
  val Tri   = Value("Tri")
  val Tun   = Value("Tun")
  val Tus   = Value("Tus")
  val Tyr   = Value("Tyr")
  val Tys   = Value("Tys")
  val Ukr   = Value("Ukr")
  val Ven   = Value("Ven")
  val Vie   = Value("Vie")
  val Wal   = Value("Wal")
  val War   = Value("War")
  val Wes   = Value("Wes")
  val Yor   = Value("Yor")

  implicit def provinceId(province: Province): ProvinceId = province.provinceId

  implicit val provinceIdFormat: Format[ProvinceId] = new Format[ProvinceId] {

    def reads(jsValue: JsValue): JsResult[ProvinceId] = JsSuccess(ProvinceId.withName(jsValue.as[String]))

    def writes(provinceId: ProvinceId): JsString = JsString(provinceId.toString)

  }

}
