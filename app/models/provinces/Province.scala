package models.provinces

import models.provinces.ProvinceId._
import models.provinces.ProvinceKind._
import play.api.libs.json._

case class Province(id: ProvinceId, kind: ProvinceKind, routes: List[ProvinceId], ways: List[ProvinceId]) {

  override def equals(any: Any) = any match {
    case that: Province =>
      (this.id, that.id) match {
        case (Stp, StpNc) | (StpNc, Stp) | (Stp, StpSc) | (StpSc, Stp) | (StpNc, StpSc) | (StpSc, StpNc) =>
          true
        case (Spa, SpaNc) | (SpaNc, Spa) | (Spa, SpaSc) | (SpaSc, Spa) | (SpaNc, SpaSc) | (SpaSc, SpaNc) =>
          true
        case (Bul, BulEc) | (BulEc, Bul) | (Bul, BulSc) | (BulSc, Bul) | (BulEc, BulSc) | (BulSc, BulEc) =>
          true
        case (thisId, thatId) if thisId == thatId =>
          true
        case _ =>
          false
      }
    case _ =>
      false
  }

}

object Province {

  implicit val provinceFormat = Json.format[Province]

  val adr:   Province = Province(Adr,   Water, Nil,                                     List(Alb, Apu, Ion, Tri, Ven))
  val aeg:   Province = Province(Aeg,   Water, Nil,                                     List(BulSc, Con, Eas, Gre, Ion, Smy))
  val alb:   Province = Province(Alb,   Land,  List(Gre, Ser, Tri),                     List(Adr, Gre, Ion, Tri))
  val ank:   Province = Province(Ank,   Land,  List(Arm, Con, Smy),                     List(Arm, Bla, Con))
  val apu:   Province = Province(Apu,   Land,  List(Nap, Rom, Ven),                     List(Adr, Ion, Nap, Ven))
  val arm:   Province = Province(Arm,   Land,  List(Ank, Sev, Smy, Syr),                List(Ank, Bla, Sev))
  val bal:   Province = Province(Bal,   Water, Nil,                                     List(Ber, Bot, Den, Kie, Lvn, Pru, Swe))
  val bar:   Province = Province(Bar,   Water, Nil,                                     List(Nor, Nwg, StpNc))
  val bel:   Province = Province(Bel,   Land,  List(Bur, Hol, Pic, Ruh),                List(Eng, Hol, Nth, Pic))
  val ber:   Province = Province(Ber,   Land,  List(Kie, Mun, Pru, Sil),                List(Bal, Kie, Pru))
  val bla:   Province = Province(Bla,   Water, Nil,                                     List(Ank, Arm, BulEc, Con, Rum, Sev))
  val boh:   Province = Province(Boh,   Land,  List(Gal, Mun, Sil, Tyr, Vie),           Nil)
  val bot:   Province = Province(Bot,   Water, Nil,                                     List(Bal, Fin, Lvn, StpSc, Swe))
  val bre:   Province = Province(Bre,   Land,  List(Gas, Par, Pic),                     List(Eng, Gas, Mao, Pic))
  val bud:   Province = Province(Bud,   Land,  List(Gal, Rum, Ser, Tri, Vie),           Nil)
  val bul:   Province = Province(Bul,   Land,  List(Con, Gre, Rum, Ser),                Nil)
  val bulEc: Province = Province(BulEc, Water, Nil,                                     List(Bla, Con, Rum))
  val bulSc: Province = Province(BulSc, Water, Nil,                                     List(Aeg, Con, Gre))
  val bur:   Province = Province(Bur,   Land,  List(Bel, Gas, Mar, Mun, Par, Pic, Ruh), Nil)
  val cly:   Province = Province(Cly,   Land,  List(Edi, Lvp),                          List(Edi, Lvp, Nao, Nwg))
  val con:   Province = Province(Con,   Land,  List(Ank, Bul, Smy),                     List(Aeg, Ank, Bla, BulEc, BulSc, Smy))
  val den:   Province = Province(Den,   Land,  List(Kie, Swe),                          List(Bal, Hel, Kie, Nth, Ska, Swe))
  val eas:   Province = Province(Eas,   Water, Nil,                                     List(Aeg, Ion, Smy, Syr))
  val edi:   Province = Province(Edi,   Land,  List(Cly, Lvp, Yor),                     List(Cly, Nth, Nwg, Yor))
  val eng:   Province = Province(Eng,   Water, Nil,                                     List(Bel, Bre, Iri, Lon, Mao, Nth, Pic, Wal))
  val fin:   Province = Province(Fin,   Land,  List(Nor, Stp, Swe),                     List(Bot, StpSc, Swe))
  val gal:   Province = Province(Gal,   Land,  List(Boh, Bud, Rum, Sil, Ukr, Vie, War), Nil)
  val gas:   Province = Province(Gas,   Land,  List(Bre, Bur, Mar, Par, Spa),           List(Bre, Mao, SpaNc))
  val gre:   Province = Province(Gre,   Land,  List(Alb, Bul, Ser),                     List(Aeg, Alb, BulSc, Ion))
  val hel:   Province = Province(Hel,   Water, Nil,                                     List(Den, Hol, Kie, Nth))
  val hol:   Province = Province(Hol,   Land,  List(Bel, Kie, Ruh),                     List(Bel, Hel, Kie, Nth))
  val ion:   Province = Province(Ion,   Water, Nil,                                     List(Adr, Aeg, Alb, Apu, Eas, Gre, Nap, Tun, Tys))
  val iri:   Province = Province(Iri,   Water, Nil,                                     List(Eng, Lvp, Mao, Nao, Wal))
  val kie:   Province = Province(Kie,   Land,  List(Ber, Den, Hol, Mun, Ruh),           List(Bal, Ber, Den, Hel, Hol))
  val lon:   Province = Province(Lon,   Land,  List(Wal, Yor),                          List(Eng, Nth, Wal, Yor))
  val lvn:   Province = Province(Lvn,   Land,  List(Mos, Pru, Stp, War),                List(Bal, Bot, Pru, StpSc))
  val lvp:   Province = Province(Lvp,   Land,  List(Cly, Edi, Wal, Yor),                List(Cly, Iri, Nao, Wal))
  val lyo:   Province = Province(Lyo,   Water, Nil,                                     List(Mar, Pie, SpaSc, Tus, Tys, Wes))
  val mao:   Province = Province(Mao,   Water, Nil,                                     List(Bre, Eng, Gas, Iri, Naf, Nao, Por, SpaNc, SpaSc, Wes))
  val mar:   Province = Province(Mar,   Land,  List(Bur, Gas, Pie, Spa),                List(Lyo, Pie, SpaSc))
  val mos:   Province = Province(Mos,   Land,  List(Lvn, Sev, Stp, Ukr, War),           Nil)
  val mun:   Province = Province(Mun,   Land,  List(Ber, Boh, Bur, Kie, Ruh, Sil, Tyr), Nil)
  val naf:   Province = Province(Naf,   Land,  List(Tun),                               List(Mao, Tun, Wes))
  val nao:   Province = Province(Nao,   Water, Nil,                                     List(Cly, Iri, Lvp, Mao, Nwg))
  val nap:   Province = Province(Nap,   Land,  List(Apu, Rom),                          List(Apu, Ion, Rom, Tys))
  val nor:   Province = Province(Nor,   Land,  List(Fin, Stp, Swe),                     List(Bar, Nth, Nwg, Ska, StpNc, Swe))
  val nth:   Province = Province(Nth,   Water, Nil,                                     List(Bel, Den, Edi, Eng, Hel, Hol, Lon, Nor, Nwg, Ska, Yor))
  val nwg:   Province = Province(Nwg,   Water, Nil,                                     List(Bar, Cly, Edi, Nao, Nor, Nth))
  val par:   Province = Province(Par,   Land,  List(Bre, Bur, Gas, Pic),                Nil)
  val pic:   Province = Province(Pic,   Land,  List(Bel, Bre, Bur, Par),                List(Bel, Bre, Eng))
  val pie:   Province = Province(Pie,   Land,  List(Mar, Tus, Tyr, Ven),                List(Lyo, Mar, Tus))
  val por:   Province = Province(Por,   Land,  List(Spa),                               List(Mao, SpaNc, SpaSc))
  val pru:   Province = Province(Pru,   Land,  List(Ber, Lvn, Sil, War),                List(Bal, Ber, Lvn))
  val rom:   Province = Province(Rom,   Land,  List(Apu, Nap, Tus, Ven),                List(Nap, Tus, Tys))
  val ruh:   Province = Province(Ruh,   Land,  List(Bel, Bur, Hol, Kie, Mun),           Nil)
  val rum:   Province = Province(Rum,   Land,  List(Bud, Bul, Gal, Ser, Sev, Ukr),      List(Bla, BulEc, Sev))
  val ser:   Province = Province(Ser,   Land,  List(Alb, Bud, Bul, Gre, Rum, Tri),      Nil)
  val sev:   Province = Province(Sev,   Land,  List(Arm, Mos, Rum, Ukr),                List(Arm, Bla, Rum))
  val sil:   Province = Province(Sil,   Land,  List(Ber, Boh, Gal, Mun, Pru, War),      Nil)
  val ska:   Province = Province(Ska,   Water, Nil,                                     List(Den, Nor, Nth, Swe))
  val smy:   Province = Province(Smy,   Land,  List(Ank, Arm, Con, Syr),                List(Aeg, Eas, Con, Syr))
  val spa:   Province = Province(Spa,   Land,  List(Gas, Mar, Por),                     Nil)
  val spaNc: Province = Province(SpaNc, Water, Nil,                                     List(Gas, Mao, Por))
  val spaSc: Province = Province(SpaSc, Water, Nil,                                     List(Lyo, Mao, Mar, Por, Wes))
  val stp:   Province = Province(Stp,   Land,  List(Fin, Lvn, Mos, Nor),                Nil)
  val stpNc: Province = Province(StpNc, Water, Nil,                                     List(Bar, Nor))
  val stpSc: Province = Province(StpSc, Water, Nil,                                     List(Bot, Fin, Lvn))
  val swe:   Province = Province(Swe,   Land,  List(Den, Fin, Nor),                     List(Bal, Bot, Den, Fin, Nor, Ska))
  val syr:   Province = Province(Syr,   Land,  List(Arm, Smy),                          List(Eas, Smy))
  val tri:   Province = Province(Tri,   Land,  List(Alb, Bud, Ser, Tyr, Vie, Ven),      List(Adr, Alb, Ven))
  val tun:   Province = Province(Tun,   Land,  List(Naf),                               List(Ion, Naf, Tys, Wes))
  val tus:   Province = Province(Tus,   Land,  List(Pie, Rom, Ven),                     List(Lyo, Pie, Rom, Tys))
  val tyr:   Province = Province(Tyr,   Land,  List(Boh, Mun, Pie, Tri, Vie, Ven),      Nil)
  val tys:   Province = Province(Tys,   Water, Nil,                                     List(Ion, Lyo, Nap, Rom, Tun, Tus, Wes))
  val ukr:   Province = Province(Ukr,   Land,  List(Gal, Mos, Rum, Sev, War),           Nil)
  val ven:   Province = Province(Ven,   Land,  List(Apu, Pie, Rom, Tri, Tus, Tyr),      List(Adr, Apu, Tri))
  val vie:   Province = Province(Vie,   Land,  List(Boh, Bud, Gal, Tri, Tyr),           Nil)
  val wal:   Province = Province(Wal,   Land,  List(Lon, Lvp, Yor),                     List(Eng, Iri, Lon, Lvp))
  val war:   Province = Province(War,   Land,  List(Gal, Lvn, Mos, Pru, Sil, Ukr),      Nil)
  val wes:   Province = Province(Wes,   Water, Nil,                                     List(Lyo, Mao, Naf, SpaSc, Tun, Tys))
  val yor:   Province = Province(Yor,   Land,  List(Edi, Lon, Lvp, Wal),                List(Edi, Lon, Nth))

  val provinces: List[Province] = List(
    adr, aeg, alb, ank, apu, arm, bal, bar, bel, ber, bla, boh, bot, bre, bud, bul, bulEc, bulSc, bur, cly, con, den,
    eas, edi, eng, fin, gal, gas, gre, hel, hol, ion, iri, kie, lon, lvn, lvp, lyo, mao, mar, mos, mun, naf, nao, nap,
    nor, nth, nwg, par, pic, pie, por, pru, rom, ruh, rum, ser, sev, sil, ska, smy, spa, spaNc, spaSc, stp, stpNc,
    stpSc, swe, syr, tri, tun, tus, tyr, tys, ukr, ven, vie, wal, war, wes, yor
  )

  def province(id: ProvinceId): Option[Province] = provinces.find(_.id == id)

}
