package models.provinces

import models.countries.CountryId._
import models.provinces.ProvinceId._
import models.units.{Army, Fleet, _}
import play.api.libs.json._

case class Provinces(provinceId: ProvinceId, country: Option[CountryId], unit: Option[Unit])

object Provinces {

  implicit val provincesWrites: Writes[Map[ProvinceId, Provinces]] = new Writes[Map[ProvinceId, Provinces]] {

    override def writes(provinces: Map[ProvinceId, Provinces]): JsValue = {
      JsArray(provinces.values.toList.map({
        case Provinces(provinceId, countryId, unit) =>
          JsObject(Map(
              "province" -> Json.toJson(provinceId),
              "country" -> Json.toJson(countryId),
              "unit" -> Json.toJson(unit)
          ))
      }))
    }

  }

  val emptyProvinces: Map[ProvinceId, Provinces] = Map(
    Adr   -> Provinces(Adr,   None, None),
    Aeg   -> Provinces(Aeg,   None, None),
    Alb   -> Provinces(Alb,   None, None),
    Ank   -> Provinces(Ank,   None, None),
    Apu   -> Provinces(Apu,   None, None),
    Arm   -> Provinces(Arm,   None, None),
    Bal   -> Provinces(Bal,   None, None),
    Bar   -> Provinces(Bar,   None, None),
    Bel   -> Provinces(Bel,   None, None),
    Ber   -> Provinces(Ber,   None, None),
    Bla   -> Provinces(Bla,   None, None),
    Boh   -> Provinces(Boh,   None, None),
    Bot   -> Provinces(Bot,   None, None),
    Bre   -> Provinces(Bre,   None, None),
    Bud   -> Provinces(Bud,   None, None),
    Bul   -> Provinces(Bul,   None, None),
    BulEc -> Provinces(BulEc, None, None),
    BulSc -> Provinces(BulSc, None, None),
    Bur   -> Provinces(Bur,   None, None),
    Cly   -> Provinces(Cly,   None, None),
    Con   -> Provinces(Con,   None, None),
    Den   -> Provinces(Den,   None, None),
    Eas   -> Provinces(Eas,   None, None),
    Edi   -> Provinces(Edi,   None, None),
    Eng   -> Provinces(Eng,   None, None),
    Fin   -> Provinces(Fin,   None, None),
    Gal   -> Provinces(Gal,   None, None),
    Gas   -> Provinces(Gas,   None, None),
    Gre   -> Provinces(Gre,   None, None),
    Hel   -> Provinces(Hel,   None, None),
    Hol   -> Provinces(Hol,   None, None),
    Ion   -> Provinces(Ion,   None, None),
    Iri   -> Provinces(Iri,   None, None),
    Kie   -> Provinces(Kie,   None, None),
    Lon   -> Provinces(Lon,   None, None),
    Lvn   -> Provinces(Lvn,   None, None),
    Lvp   -> Provinces(Lvp,   None, None),
    Lyo   -> Provinces(Lyo,   None, None),
    Mao   -> Provinces(Mao,   None, None),
    Mar   -> Provinces(Mar,   None, None),
    Mos   -> Provinces(Mos,   None, None),
    Mun   -> Provinces(Mun,   None, None),
    Naf   -> Provinces(Naf,   None, None),
    Nao   -> Provinces(Nao,   None, None),
    Nap   -> Provinces(Nap,   None, None),
    Nor   -> Provinces(Nor,   None, None),
    Nth   -> Provinces(Nth,   None, None),
    Nwg   -> Provinces(Nwg,   None, None),
    Par   -> Provinces(Par,   None, None),
    Pic   -> Provinces(Pic,   None, None),
    Pie   -> Provinces(Pie,   None, None),
    Por   -> Provinces(Por,   None, None),
    Pru   -> Provinces(Pru,   None, None),
    Rom   -> Provinces(Rom,   None, None),
    Ruh   -> Provinces(Ruh,   None, None),
    Rum   -> Provinces(Rum,   None, None),
    Ser   -> Provinces(Ser,   None, None),
    Sev   -> Provinces(Sev,   None, None),
    Sil   -> Provinces(Sil,   None, None),
    Ska   -> Provinces(Ska,   None, None),
    Smy   -> Provinces(Smy,   None, None),
    Spa   -> Provinces(Spa,   None, None),
    SpaNc -> Provinces(StpNc, None, None),
    SpaSc -> Provinces(StpSc, None, None),
    Stp   -> Provinces(Stp,   None, None),
    StpNc -> Provinces(StpNc, None, None),
    StpSc -> Provinces(StpSc, None, None),
    Swe   -> Provinces(Swe,   None, None),
    Syr   -> Provinces(Syr,   None, None),
    Tri   -> Provinces(Tri,   None, None),
    Tun   -> Provinces(Tun,   None, None),
    Tus   -> Provinces(Tus,   None, None),
    Tyr   -> Provinces(Tyr,   None, None),
    Tys   -> Provinces(Tys,   None, None),
    Ukr   -> Provinces(Ukr,   None, None),
    Ven   -> Provinces(Ven,   None, None),
    Vie   -> Provinces(Vie,   None, None),
    Wal   -> Provinces(Wal,   None, None),
    War   -> Provinces(War,   None, None),
    Wes   -> Provinces(Wes,   None, None),
    Yor   -> Provinces(Yor,   None, None)
  )

  val startProvinces: Map[ProvinceId, Provinces] = emptyProvinces ++ Map(
    Bud   -> Provinces(Bud,   Some(Austria), Some(Army(Austria))),
    Tri   -> Provinces(Tri,   Some(Austria), Some(Fleet(Austria))),
    Vie   -> Provinces(Vie,   Some(Austria), Some(Army(Austria))),

    Edi   -> Provinces(Edi,   Some(England), Some(Fleet(England))),
    Lon   -> Provinces(Lon,   Some(England), Some(Fleet(England))),
    Lvp   -> Provinces(Lvp,   Some(England), Some(Army(England))),

    Bre   -> Provinces(Bre,   Some(France),  Some(Fleet(France))),
    Mar   -> Provinces(Mar,   Some(France),  Some(Army(France))),
    Par   -> Provinces(Par,   Some(France),  Some(Army(France))),

    Ber   -> Provinces(Ber,   Some(Germany), Some(Army(Germany))),
    Kie   -> Provinces(Kie,   Some(Germany), Some(Fleet(Germany))),
    Mun   -> Provinces(Mun,   Some(Germany), Some(Army(Germany))),

    Rom   -> Provinces(Rom,   Some(Italy),   Some(Army(Italy))),
    Nap   -> Provinces(Nap,   Some(Italy),   Some(Fleet(Italy))),
    Ven   -> Provinces(Ven,   Some(Italy),   Some(Army(Italy))),

    Mos   -> Provinces(Mos,   Some(Russia),  Some(Army(Russia))),
    Sev   -> Provinces(Sev,   Some(Russia),  Some(Fleet(Russia))),
    Stp   -> Provinces(StpSc, Some(Russia),  None),
    StpSc -> Provinces(StpSc, None,          Some(Fleet(Russia))),
    War   -> Provinces(War,   Some(Russia),  Some(Army(Russia))),

    Ank   -> Provinces(Ank,   Some(Turkey),  Some(Fleet(Turkey))),
    Con   -> Provinces(Con,   Some(Turkey),  Some(Army(Turkey))),
    Smy   -> Provinces(Smy,   Some(Turkey),  Some(Army(Turkey)))
  )

}
