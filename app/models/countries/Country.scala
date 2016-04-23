package models.countries

import models.countries.CountryId._
import models.provinces.ProvinceId._

import scala.language.implicitConversions

case class Country(countryId: CountryId, provinces: List[ProvinceId]) {

  def hasProvince(provinceId: ProvinceId): Boolean = provinces.contains(provinceId)

}

object Country {

  val countries: Map[CountryId, Country] = Map(
    Austria -> Country(Austria, List(Bud, Tri, Vie)),
    England -> Country(England, List(Edi, Lon, Lvp)),
    France  -> Country(France,  List(Bre, Mar, Par)),
    Germany -> Country(Germany, List(Ber, Kie, Mun)),
    Italy   -> Country(Italy,   List(Nap, Rom, Ven)),
    Russia  -> Country(Russia,  List(Mos, Sev, Stp, War)),
    Turkey  -> Country(Turkey,  List(Ank, Con, Smy))
  )

  implicit def country(countryId: CountryId): Country = countries.get(countryId).get

}
