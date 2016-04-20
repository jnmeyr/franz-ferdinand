package models.provinces

import models.countries.Country
import models.countries.CountryId._
import models.provinces.ProvinceId._
import models.units.{Army, Fleet, _}
import play.api.libs.json._

object Provinces {

  type Provinces = Map[ProvinceId, (Option[CountryId], Option[Unit], Option[Unit])]

  implicit val provincesWrites: Writes[Provinces] = new Writes[Provinces] {

    override def writes(provinces: Provinces): JsValue = {
      JsArray(provinces.map({
        case (provinceId, (countryId, unit, dislodgedUnit)) =>
          JsObject(Map(
              "province" -> Json.toJson(provinceId),
              "country" -> Json.toJson(countryId),
              "unit" -> Json.toJson(unit),
              "dislogdedUnit" -> Json.toJson(dislodgedUnit)
          ))
      }).toSeq)
    }

  }

  val emptyProvinces: Provinces = Map(
    Adr   -> (None, None, None),
    Aeg   -> (None, None, None),
    Alb   -> (None, None, None),
    Ank   -> (None, None, None),
    Apu   -> (None, None, None),
    Arm   -> (None, None, None),
    Bal   -> (None, None, None),
    Bar   -> (None, None, None),
    Bel   -> (None, None, None),
    Ber   -> (None, None, None),
    Bla   -> (None, None, None),
    Boh   -> (None, None, None),
    Bot   -> (None, None, None),
    Bre   -> (None, None, None),
    Bud   -> (None, None, None),
    Bul   -> (None, None, None),
    BulEc -> (None, None, None),
    BulSc -> (None, None, None),
    Bur   -> (None, None, None),
    Cly   -> (None, None, None),
    Con   -> (None, None, None),
    Den   -> (None, None, None),
    Eas   -> (None, None, None),
    Edi   -> (None, None, None),
    Eng   -> (None, None, None),
    Fin   -> (None, None, None),
    Gal   -> (None, None, None),
    Gas   -> (None, None, None),
    Gre   -> (None, None, None),
    Hel   -> (None, None, None),
    Hol   -> (None, None, None),
    Ion   -> (None, None, None),
    Iri   -> (None, None, None),
    Kie   -> (None, None, None),
    Lon   -> (None, None, None),
    Lvn   -> (None, None, None),
    Lvp   -> (None, None, None),
    Lyo   -> (None, None, None),
    Mao   -> (None, None, None),
    Mar   -> (None, None, None),
    Mos   -> (None, None, None),
    Mun   -> (None, None, None),
    Naf   -> (None, None, None),
    Nao   -> (None, None, None),
    Nap   -> (None, None, None),
    Nor   -> (None, None, None),
    Nth   -> (None, None, None),
    Nwg   -> (None, None, None),
    Par   -> (None, None, None),
    Pic   -> (None, None, None),
    Pie   -> (None, None, None),
    Por   -> (None, None, None),
    Pru   -> (None, None, None),
    Rom   -> (None, None, None),
    Ruh   -> (None, None, None),
    Rum   -> (None, None, None),
    Ser   -> (None, None, None),
    Sev   -> (None, None, None),
    Sil   -> (None, None, None),
    Ska   -> (None, None, None),
    Smy   -> (None, None, None),
    Spa   -> (None, None, None),
    SpaNc -> (None, None, None),
    SpaSc -> (None, None, None),
    Stp   -> (None, None, None),
    StpNc -> (None, None, None),
    StpSc -> (None, None, None),
    Swe   -> (None, None, None),
    Syr   -> (None, None, None),
    Tri   -> (None, None, None),
    Tun   -> (None, None, None),
    Tus   -> (None, None, None),
    Tyr   -> (None, None, None),
    Tys   -> (None, None, None),
    Ukr   -> (None, None, None),
    Ven   -> (None, None, None),
    Vie   -> (None, None, None),
    Wal   -> (None, None, None),
    War   -> (None, None, None),
    Wes   -> (None, None, None),
    Yor   -> (None, None, None)
  )

  val startProvinces: Provinces = emptyProvinces ++ Map(
    Bud   -> (Some(Austria), Some(Army(Austria)),  None),
    Tri   -> (Some(Austria), Some(Fleet(Austria)), None),
    Vie   -> (Some(Austria), Some(Army(Austria)),  None),

    Edi   -> (Some(England), Some(Fleet(England)), None),
    Lon   -> (Some(England), Some(Fleet(England)), None),
    Lvp   -> (Some(England), Some(Army(England)),  None),

    Bre   -> (Some(France),  Some(Fleet(France)),  None),
    Mar   -> (Some(France),  Some(Army(France)),   None),
    Par   -> (Some(France),  Some(Army(France)),   None),

    Ber   -> (Some(Germany), Some(Army(Germany)),  None),
    Kie   -> (Some(Germany), Some(Fleet(Germany)), None),
    Mun   -> (Some(Germany), Some(Army(Germany)),  None),

    Rom   -> (Some(Italy),   Some(Army(Italy)),    None),
    Nap   -> (Some(Italy),   Some(Fleet(Italy)),   None),
    Ven   -> (Some(Italy),   Some(Army(Italy)),    None),

    Mos   -> (Some(Russia),  Some(Army(Russia)),   None),
    Sev   -> (Some(Russia),  Some(Fleet(Russia)),  None),
    Stp   -> (Some(Russia),  None,                 None),
    StpSc -> (None,          Some(Fleet(Russia)),  None),
    War   -> (Some(Russia),  Some(Army(Russia)),   None),

    Ank   -> (Some(Turkey),  Some(Fleet(Turkey)),  None),
    Con   -> (Some(Turkey),  Some(Army(Turkey)),   None),
    Smy   -> (Some(Turkey),  Some(Army(Turkey)),   None)
  )

  def provinceCount(provinces: Provinces, country: Country): Int = {
    provinces.values.count({
      case (Some(countryId), _, _) if countryId == country.countryId =>
        true
      case _ =>
        false
    })
  }

  def unitCount(provinces: Provinces, country: Country): Int = {
    provinces.values.count({
      case (_, Some(unit), _) if unit.countryId == country.countryId =>
        true
      case _ =>
        false
    })
  }

  def provinceUnitDifference(provinces: Provinces, country: Country): Int = {
    provinceCount(provinces, country) - unitCount(provinces, country)
  }

}
