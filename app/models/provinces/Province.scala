package models.provinces

import models.provinces.ProvinceId._
import play.api.libs.json._

sealed trait Province {

  def provinceId: ProvinceId

  def supply: Boolean

  def routes: List[ProvinceId]

  def ways: List[ProvinceId]

  override def equals(any: Any) = any match {
    case that: Province =>
      (this.provinceId, that.provinceId) match {
        case (Stp, StpNc) | (StpNc, Stp) | (Stp, StpSc) | (StpSc, Stp) | (StpNc, StpSc) | (StpSc, StpNc) =>
          true
        case (Spa, SpaNc) | (SpaNc, Spa) | (Spa, SpaSc) | (SpaSc, Spa) | (SpaNc, SpaSc) | (SpaSc, SpaNc) =>
          true
        case (Bul, BulEc) | (BulEc, Bul) | (Bul, BulSc) | (BulSc, Bul) | (BulEc, BulSc) | (BulSc, BulEc) =>
          true
        case (thisProvinceId, thatProvinceId) if thisProvinceId == thatProvinceId =>
          true
        case _ =>
          false
      }
    case _ =>
      false
  }

}

case class LandProvince(provinceId: ProvinceId, supply: Boolean, routes: List[ProvinceId]) extends Province {

  val ways: List[ProvinceId] = Nil

}

case class CoastProvince(provinceId: ProvinceId, supply: Boolean, routes: List[ProvinceId], ways: List[ProvinceId]) extends Province

case class WaterProvince(provinceId: ProvinceId, ways: List[ProvinceId]) extends Province {

  val supply: Boolean = false

  val routes: List[ProvinceId] = Nil

}

object Province {

  val provinces: Map[ProvinceId, Province] = Map(
    Adr   -> WaterProvince(Adr,                                                    List(Alb, Apu, Ion, Tri, Ven)),
    Aeg   -> WaterProvince(Aeg,                                                    List(BulSc, Con, Eas, Gre, Ion, Smy)),
    Alb   -> CoastProvince(Alb,   false, List(Gre, Ser, Tri),                      List(Adr, Gre, Ion, Tri)),
    Ank   -> CoastProvince(Ank,   true,  List(Arm, Con, Smy),                      List(Arm, Bla, Con)),
    Apu   -> CoastProvince(Apu,   false, List(Nap, Rom, Ven),                      List(Adr, Ion, Nap, Ven)),
    Arm   -> CoastProvince(Arm,   false, List(Ank, Sev, Smy, Syr),                 List(Ank, Bla, Sev)),
    Bal   -> WaterProvince(Bal,                                                    List(Ber, Bot, Den, Kie, Lvn, Pru, Swe)),
    Bar   -> WaterProvince(Bar,                                                    List(Nor, Nwg, StpNc)),
    Bel   -> CoastProvince(Bel,   true,  List(Bur, Hol, Pic, Ruh),                 List(Eng, Hol, Nth, Pic)),
    Ber   -> CoastProvince(Ber,   true,  List(Kie, Mun, Pru, Sil),                 List(Bal, Kie, Pru)),
    Bla   -> WaterProvince(Bla,                                                    List(Ank, Arm, BulEc, Con, Rum, Sev)),
    Boh   -> LandProvince (Boh,   false, List(Gal, Mun, Sil, Tyr, Vie)),
    Bot   -> WaterProvince(Bot,                                                    List(Bal, Fin, Lvn, StpSc, Swe)),
    Bre   -> CoastProvince(Bre,   true,  List(Gas, Par, Pic),                      List(Eng, Gas, Mao, Pic)),
    Bud   -> LandProvince (Bud,   true,  List(Gal, Rum, Ser, Tri, Vie)),
    Bul   -> LandProvince (Bul,   true,  List(Con, Gre, Rum, Ser)),
    BulEc -> WaterProvince(BulEc,                                                  List(Bla, Con, Rum)),
    BulSc -> WaterProvince(BulSc,                                                  List(Aeg, Con, Gre)),
    Bur   -> LandProvince (Bur,   false, List(Bel, Gas, Mar, Mun, Par, Pic, Ruh)),
    Cly   -> CoastProvince(Cly,   false, List(Edi, Lvp),                           List(Edi, Lvp, Nao, Nwg)),
    Con   -> CoastProvince(Con,   true,  List(Ank, Bul, Smy),                      List(Aeg, Ank, Bla, BulEc, BulSc, Smy)),
    Den   -> CoastProvince(Den,   true,  List(Kie, Swe),                           List(Bal, Hel, Kie, Nth, Ska, Swe)),
    Eas   -> WaterProvince(Eas,                                                    List(Aeg, Ion, Smy, Syr)),
    Edi   -> CoastProvince(Edi,   true,  List(Cly, Lvp, Yor),                      List(Cly, Nth, Nwg, Yor)),
    Eng   -> WaterProvince(Eng,                                                    List(Bel, Bre, Iri, Lon, Mao, Nth, Pic, Wal)),
    Fin   -> CoastProvince(Fin,   false, List(Nor, Stp, Swe),                      List(Bot, StpSc, Swe)),
    Gal   -> LandProvince (Gal,   false, List(Boh, Bud, Rum, Sil, Ukr, Vie, War)),
    Gas   -> CoastProvince(Gas,   false, List(Bre, Bur, Mar, Par, Spa),            List(Bre, Mao, SpaNc)),
    Gre   -> CoastProvince(Gre,   true,  List(Alb, Bul, Ser),                      List(Aeg, Alb, BulSc, Ion)),
    Hel   -> WaterProvince(Hel,                                                    List(Den, Hol, Kie, Nth)),
    Hol   -> CoastProvince(Hol,   true,  List(Bel, Kie, Ruh),                      List(Bel, Hel, Kie, Nth)),
    Ion   -> WaterProvince(Ion,                                                    List(Adr, Aeg, Alb, Apu, Eas, Gre, Nap, Tun, Tys)),
    Iri   -> WaterProvince(Iri,                                                    List(Eng, Lvp, Mao, Nao, Wal)),
    Kie   -> CoastProvince(Kie,   true,  List(Ber, Den, Hol, Mun, Ruh),            List(Bal, Ber, Den, Hel, Hol)),
    Lon   -> CoastProvince(Lon,   true,  List(Wal, Yor),                           List(Eng, Nth, Wal, Yor)),
    Lvn   -> CoastProvince(Lvn,   false, List(Mos, Pru, Stp, War),                 List(Bal, Bot, Pru, StpSc)),
    Lvp   -> CoastProvince(Lvp,   true,  List(Cly, Edi, Wal, Yor),                 List(Cly, Iri, Nao, Wal)),
    Lyo   -> WaterProvince(Lyo,                                                    List(Mar, Pie, SpaSc, Tus, Tys, Wes)),
    Mao   -> WaterProvince(Mao,                                                    List(Bre, Eng, Gas, Iri, Naf, Nao, Por, SpaNc, SpaSc, Wes)),
    Mar   -> CoastProvince(Mar,   true,  List(Bur, Gas, Pie, Spa),                 List(Lyo, Pie, SpaSc)),
    Mos   -> LandProvince (Mos,   true,  List(Lvn, Sev, Stp, Ukr, War)),
    Mun   -> LandProvince (Mun,   true,  List(Ber, Boh, Bur, Kie, Ruh, Sil, Tyr)),
    Naf   -> CoastProvince(Naf,   false, List(Tun),                                List(Mao, Tun, Wes)),
    Nao   -> WaterProvince(Nao,                                                    List(Cly, Iri, Lvp, Mao, Nwg)),
    Nap   -> CoastProvince(Nap,   true,  List(Apu, Rom),                           List(Apu, Ion, Rom, Tys)),
    Nor   -> CoastProvince(Nor,   true,  List(Fin, Stp, Swe),                      List(Bar, Nth, Nwg, Ska, StpNc, Swe)),
    Nth   -> WaterProvince(Nth,                                                    List(Bel, Den, Edi, Eng, Hel, Hol, Lon, Nor, Nwg, Ska, Yor)),
    Nwg   -> WaterProvince(Nwg,                                                    List(Bar, Cly, Edi, Nao, Nor, Nth)),
    Par   -> LandProvince (Par,   true,  List(Bre, Bur, Gas, Pic)),
    Pic   -> CoastProvince(Pic,   false, List(Bel, Bre, Bur, Par),                 List(Bel, Bre, Eng)),
    Pie   -> CoastProvince(Pie,   false, List(Mar, Tus, Tyr, Ven),                 List(Lyo, Mar, Tus)),
    Por   -> CoastProvince(Por,   true,  List(Spa),                                List(Mao, SpaNc, SpaSc)),
    Pru   -> CoastProvince(Pru,   false, List(Ber, Lvn, Sil, War),                 List(Bal, Ber, Lvn)),
    Rom   -> CoastProvince(Rom,   true,  List(Apu, Nap, Tus, Ven),                 List(Nap, Tus, Tys)),
    Ruh   -> LandProvince (Ruh,   false, List(Bel, Bur, Hol, Kie, Mun)),
    Rum   -> CoastProvince(Rum,   true,  List(Bud, Bul, Gal, Ser, Sev, Ukr),       List(Bla, BulEc, Sev)),
    Ser   -> LandProvince (Ser,   true,  List(Alb, Bud, Bul, Gre, Rum, Tri)),
    Sev   -> CoastProvince(Sev,   true,  List(Arm, Mos, Rum, Ukr),                 List(Arm, Bla, Rum)),
    Sil   -> LandProvince (Sil,   false, List(Ber, Boh, Gal, Mun, Pru, War)),
    Ska   -> WaterProvince(Ska,                                                    List(Den, Nor, Nth, Swe)),
    Smy   -> CoastProvince(Smy,   true,  List(Ank, Arm, Con, Syr),                 List(Aeg, Eas, Con, Syr)),
    Spa   -> LandProvince (Spa,   true,  List(Gas, Mar, Por)),
    SpaNc -> WaterProvince(SpaNc,                                                  List(Gas, Mao, Por)),
    SpaSc -> WaterProvince(SpaSc,                                                  List(Lyo, Mao, Mar, Por, Wes)),
    Stp   -> LandProvince (Stp,   true,  List(Fin, Lvn, Mos, Nor)),
    StpNc -> WaterProvince(StpNc,                                                  List(Bar, Nor)),
    StpSc -> WaterProvince(StpSc,                                                  List(Bot, Fin, Lvn)),
    Swe   -> CoastProvince(Swe,   true,  List(Den, Fin, Nor),                      List(Bal, Bot, Den, Fin, Nor, Ska)),
    Syr   -> CoastProvince(Syr,   false, List(Arm, Smy), List(Eas, Smy)),
    Tri   -> CoastProvince(Tri,   true,  List(Alb, Bud, Ser, Tyr, Vie, Ven),       List(Adr, Alb, Ven)),
    Tun   -> CoastProvince(Tun,   true,  List(Naf),                                List(Ion, Naf, Tys, Wes)),
    Tus   -> CoastProvince(Tus,   false, List(Pie, Rom, Ven),                      List(Lyo, Pie, Rom, Tys)),
    Tyr   -> LandProvince (Tyr,   false, List(Boh, Mun, Pie, Tri, Vie, Ven)),
    Tys   -> WaterProvince(Tys,                                                    List(Ion, Lyo, Nap, Rom, Tun, Tus, Wes)),
    Ukr   -> LandProvince (Ukr,   false, List(Gal, Mos, Rum, Sev, War)),
    Ven   -> CoastProvince(Ven,   true,  List(Apu, Pie, Rom, Tri, Tus, Tyr),       List(Adr, Apu, Tri)),
    Vie   -> LandProvince (Vie,   true,  List(Boh, Bud, Gal, Tri, Tyr)),
    Wal   -> CoastProvince(Wal,   false, List(Lon, Lvp, Yor),                      List(Eng, Iri, Lon, Lvp)),
    War   -> LandProvince (War,   true,  List(Gal, Lvn, Mos, Pru, Sil, Ukr)),
    Wes   -> WaterProvince(Wes,                                                    List(Lyo, Mao, Naf, SpaSc, Tun, Tys)),
    Yor   -> CoastProvince(Yor,   false, List(Edi, Lon, Lvp, Wal),                 List(Edi, Lon, Nth))
  )

  def province(provinceId: ProvinceId): Province = provinces.get(provinceId).get

}
