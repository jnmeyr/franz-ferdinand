package models.provinces

import models.provinces.ProvinceId._
import play.api.libs.json._

sealed trait Province {

  def id: ProvinceId

  def routes: List[ProvinceId]

  def ways: List[ProvinceId]

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

case class LandProvince(id: ProvinceId, routes: List[ProvinceId]) extends Province {

  val ways: List[ProvinceId] = Nil

}

case class CoastProvince(id: ProvinceId, routes: List[ProvinceId], ways: List[ProvinceId]) extends Province

case class WaterProvince(id: ProvinceId, ways: List[ProvinceId]) extends Province {

  val routes: List[ProvinceId] = Nil

}

object Province {

  val adr:   Province = WaterProvince(Adr,                                            List(Alb, Apu, Ion, Tri, Ven))
  val aeg:   Province = WaterProvince(Aeg,                                            List(BulSc, Con, Eas, Gre, Ion, Smy))
  val alb:   Province = CoastProvince(Alb,   List(Gre, Ser, Tri),                     List(Adr, Gre, Ion, Tri))
  val ank:   Province = CoastProvince(Ank,   List(Arm, Con, Smy),                     List(Arm, Bla, Con))
  val apu:   Province = CoastProvince(Apu,   List(Nap, Rom, Ven),                     List(Adr, Ion, Nap, Ven))
  val arm:   Province = CoastProvince(Arm,   List(Ank, Sev, Smy, Syr),                List(Ank, Bla, Sev))
  val bal:   Province = WaterProvince(Bal,                                            List(Ber, Bot, Den, Kie, Lvn, Pru, Swe))
  val bar:   Province = WaterProvince(Bar,                                            List(Nor, Nwg, StpNc))
  val bel:   Province = CoastProvince(Bel,   List(Bur, Hol, Pic, Ruh),                List(Eng, Hol, Nth, Pic))
  val ber:   Province = CoastProvince(Ber,   List(Kie, Mun, Pru, Sil),                List(Bal, Kie, Pru))
  val bla:   Province = WaterProvince(Bla,                                            List(Ank, Arm, BulEc, Con, Rum, Sev))
  val boh:   Province = LandProvince (Boh,   List(Gal, Mun, Sil, Tyr, Vie))
  val bot:   Province = WaterProvince(Bot,                                            List(Bal, Fin, Lvn, StpSc, Swe))
  val bre:   Province = CoastProvince(Bre,   List(Gas, Par, Pic),                     List(Eng, Gas, Mao, Pic))
  val bud:   Province = LandProvince (Bud,   List(Gal, Rum, Ser, Tri, Vie))
  val bul:   Province = LandProvince (Bul,   List(Con, Gre, Rum, Ser))
  val bulEc: Province = WaterProvince(BulEc,                                          List(Bla, Con, Rum))
  val bulSc: Province = WaterProvince(BulSc,                                          List(Aeg, Con, Gre))
  val bur:   Province = LandProvince (Bur,   List(Bel, Gas, Mar, Mun, Par, Pic, Ruh))
  val cly:   Province = CoastProvince(Cly,   List(Edi, Lvp),                          List(Edi, Lvp, Nao, Nwg))
  val con:   Province = CoastProvince(Con,   List(Ank, Bul, Smy),                     List(Aeg, Ank, Bla, BulEc, BulSc, Smy))
  val den:   Province = CoastProvince(Den,   List(Kie, Swe),                          List(Bal, Hel, Kie, Nth, Ska, Swe))
  val eas:   Province = WaterProvince(Eas,                                            List(Aeg, Ion, Smy, Syr))
  val edi:   Province = CoastProvince(Edi,   List(Cly, Lvp, Yor),                     List(Cly, Nth, Nwg, Yor))
  val eng:   Province = WaterProvince(Eng,                                            List(Bel, Bre, Iri, Lon, Mao, Nth, Pic, Wal))
  val fin:   Province = CoastProvince(Fin,   List(Nor, Stp, Swe),                     List(Bot, StpSc, Swe))
  val gal:   Province = LandProvince (Gal,   List(Boh, Bud, Rum, Sil, Ukr, Vie, War))
  val gas:   Province = CoastProvince(Gas,   List(Bre, Bur, Mar, Par, Spa),           List(Bre, Mao, SpaNc))
  val gre:   Province = CoastProvince(Gre,   List(Alb, Bul, Ser),                     List(Aeg, Alb, BulSc, Ion))
  val hel:   Province = WaterProvince(Hel,                                            List(Den, Hol, Kie, Nth))
  val hol:   Province = CoastProvince(Hol,   List(Bel, Kie, Ruh),                     List(Bel, Hel, Kie, Nth))
  val ion:   Province = WaterProvince(Ion,                                            List(Adr, Aeg, Alb, Apu, Eas, Gre, Nap, Tun, Tys))
  val iri:   Province = WaterProvince(Iri,                                            List(Eng, Lvp, Mao, Nao, Wal))
  val kie:   Province = CoastProvince(Kie,   List(Ber, Den, Hol, Mun, Ruh),           List(Bal, Ber, Den, Hel, Hol))
  val lon:   Province = CoastProvince(Lon,   List(Wal, Yor),                          List(Eng, Nth, Wal, Yor))
  val lvn:   Province = CoastProvince(Lvn,   List(Mos, Pru, Stp, War),                List(Bal, Bot, Pru, StpSc))
  val lvp:   Province = CoastProvince(Lvp,   List(Cly, Edi, Wal, Yor),                List(Cly, Iri, Nao, Wal))
  val lyo:   Province = WaterProvince(Lyo,                                            List(Mar, Pie, SpaSc, Tus, Tys, Wes))
  val mao:   Province = WaterProvince(Mao,                                            List(Bre, Eng, Gas, Iri, Naf, Nao, Por, SpaNc, SpaSc, Wes))
  val mar:   Province = CoastProvince(Mar,   List(Bur, Gas, Pie, Spa),                List(Lyo, Pie, SpaSc))
  val mos:   Province = LandProvince (Mos,   List(Lvn, Sev, Stp, Ukr, War))
  val mun:   Province = LandProvince (Mun,   List(Ber, Boh, Bur, Kie, Ruh, Sil, Tyr))
  val naf:   Province = CoastProvince(Naf,   List(Tun),                               List(Mao, Tun, Wes))
  val nao:   Province = WaterProvince(Nao,                                            List(Cly, Iri, Lvp, Mao, Nwg))
  val nap:   Province = CoastProvince(Nap,   List(Apu, Rom),                          List(Apu, Ion, Rom, Tys))
  val nor:   Province = CoastProvince(Nor,   List(Fin, Stp, Swe),                     List(Bar, Nth, Nwg, Ska, StpNc, Swe))
  val nth:   Province = WaterProvince(Nth,                                            List(Bel, Den, Edi, Eng, Hel, Hol, Lon, Nor, Nwg, Ska, Yor))
  val nwg:   Province = WaterProvince(Nwg,                                            List(Bar, Cly, Edi, Nao, Nor, Nth))
  val par:   Province = LandProvince (Par,   List(Bre, Bur, Gas, Pic))
  val pic:   Province = CoastProvince(Pic,   List(Bel, Bre, Bur, Par),                List(Bel, Bre, Eng))
  val pie:   Province = CoastProvince(Pie,   List(Mar, Tus, Tyr, Ven),                List(Lyo, Mar, Tus))
  val por:   Province = CoastProvince(Por,   List(Spa),                               List(Mao, SpaNc, SpaSc))
  val pru:   Province = CoastProvince(Pru,   List(Ber, Lvn, Sil, War),                List(Bal, Ber, Lvn))
  val rom:   Province = CoastProvince(Rom,   List(Apu, Nap, Tus, Ven),                List(Nap, Tus, Tys))
  val ruh:   Province = LandProvince (Ruh,   List(Bel, Bur, Hol, Kie, Mun))
  val rum:   Province = CoastProvince(Rum,   List(Bud, Bul, Gal, Ser, Sev, Ukr),      List(Bla, BulEc, Sev))
  val ser:   Province = LandProvince (Ser,   List(Alb, Bud, Bul, Gre, Rum, Tri))
  val sev:   Province = CoastProvince(Sev,   List(Arm, Mos, Rum, Ukr),                List(Arm, Bla, Rum))
  val sil:   Province = LandProvince (Sil,   List(Ber, Boh, Gal, Mun, Pru, War))
  val ska:   Province = WaterProvince(Ska,                                            List(Den, Nor, Nth, Swe))
  val smy:   Province = CoastProvince(Smy,   List(Ank, Arm, Con, Syr),                List(Aeg, Eas, Con, Syr))
  val spa:   Province = LandProvince (Spa,   List(Gas, Mar, Por))
  val spaNc: Province = WaterProvince(SpaNc,                                          List(Gas, Mao, Por))
  val spaSc: Province = WaterProvince(SpaSc,                                          List(Lyo, Mao, Mar, Por, Wes))
  val stp:   Province = LandProvince (Stp,   List(Fin, Lvn, Mos, Nor))
  val stpNc: Province = WaterProvince(StpNc,                                          List(Bar, Nor))
  val stpSc: Province = WaterProvince(StpSc,                                          List(Bot, Fin, Lvn))
  val swe:   Province = CoastProvince(Swe,   List(Den, Fin, Nor),                     List(Bal, Bot, Den, Fin, Nor, Ska))
  val syr:   Province = CoastProvince(Syr,   List(Arm, Smy), List(Eas, Smy))
  val tri:   Province = CoastProvince(Tri,   List(Alb, Bud, Ser, Tyr, Vie, Ven),      List(Adr, Alb, Ven))
  val tun:   Province = CoastProvince(Tun,   List(Naf),                               List(Ion, Naf, Tys, Wes))
  val tus:   Province = CoastProvince(Tus,   List(Pie, Rom, Ven),                     List(Lyo, Pie, Rom, Tys))
  val tyr:   Province = LandProvince (Tyr,   List(Boh, Mun, Pie, Tri, Vie, Ven))
  val tys:   Province = WaterProvince(Tys,                                            List(Ion, Lyo, Nap, Rom, Tun, Tus, Wes))
  val ukr:   Province = LandProvince (Ukr,   List(Gal, Mos, Rum, Sev, War))
  val ven:   Province = CoastProvince(Ven,   List(Apu, Pie, Rom, Tri, Tus, Tyr),      List(Adr, Apu, Tri))
  val vie:   Province = LandProvince (Vie,   List(Boh, Bud, Gal, Tri, Tyr))
  val wal:   Province = CoastProvince(Wal,   List(Lon, Lvp, Yor),                     List(Eng, Iri, Lon, Lvp))
  val war:   Province = LandProvince (War,   List(Gal, Lvn, Mos, Pru, Sil, Ukr))
  val wes:   Province = WaterProvince(Wes,                                            List(Lyo, Mao, Naf, SpaSc, Tun, Tys))
  val yor:   Province = CoastProvince(Yor,   List(Edi, Lon, Lvp, Wal),                List(Edi, Lon, Nth))

  val provinces: List[Province] = List(
    adr, aeg, alb, ank, apu, arm, bal, bar, bel, ber, bla, boh, bot, bre, bud, bul, bulEc, bulSc, bur, cly, con, den,
    eas, edi, eng, fin, gal, gas, gre, hel, hol, ion, iri, kie, lon, lvn, lvp, lyo, mao, mar, mos, mun, naf, nao, nap,
    nor, nth, nwg, par, pic, pie, por, pru, rom, ruh, rum, ser, sev, sil, ska, smy, spa, spaNc, spaSc, stp, stpNc,
    stpSc, swe, syr, tri, tun, tus, tyr, tys, ukr, ven, vie, wal, war, wes, yor
  )

  def province(id: ProvinceId): Option[Province] = provinces.find(_.id == id)

}
