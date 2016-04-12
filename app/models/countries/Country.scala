package models.countries

import models.countries.CountryId._
import models.provinces.ProvinceId._

case class Country(provinces: List[ProvinceId]) {

  def hasProvince(provinceId: ProvinceId): Boolean = provinces.contains(provinceId)

}

object Country {

  val countries: Map[CountryId, Country] = Map(
    Austria -> Country(List(Bud, Tri, Vie)),
    England -> Country(List(Edi, Lon, Lvp)),
    France  -> Country(List(Bre, Mar, Par)),
    Germany -> Country(List(Ber, Kie, Mun)),
    Italy   -> Country(List(Nap, Rom, Ven)),
    Russia  -> Country(List(Mos, Sev, Stp, War)),
    Turkey  -> Country(List(Ank, Con, Smy))
  )

  def country(countryId: CountryId): Country = countries.get(countryId).get

}
