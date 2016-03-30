package models.countries

import models.countries.CountryId._
import models.provinces.ProvinceId._
import play.api.libs.json.{Format, Json}

case class Country(id: CountryId, provinces: List[ProvinceId])

object Country {

  val austria = Country(Austria, List(Bud, Tri, Vie))
  val england = Country(England, List(Edi, Lon, Lvp))
  val france  = Country(France,  List(Bre, Mar, Par))
  val germany = Country(Germany, List(Ber, Kie, Mun))
  val italy   = Country(Italy,   List(Nap, Rom, Ven))
  val russia  = Country(Russia,  List(Mos, Sev, Stp, War))
  val turkey  = Country(Turkey,  List(Ank, Con, Smy))

  val countries: List[Country] = List(
    austria, england, france, germany, italy, russia, turkey
  )

}
