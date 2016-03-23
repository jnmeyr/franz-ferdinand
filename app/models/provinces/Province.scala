package models.provinces

import models.provinces.ProvinceId._
import models.provinces.ProvinceKind._
import play.api.libs.json._

case class Province(id: ProvinceId, kind: ProvinceKind) {

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

  val ber:   Province = Province(Ber,   Land)
  val bul:   Province = Province(Bul,   Land)
  val bulEc: Province = Province(BulEc, Water)
  val bulSc: Province = Province(BulSc, Water)
  val kie:   Province = Province(Kie,   Land)
  val ska:   Province = Province(Ska,   Water)
  val spa:   Province = Province(Spa,   Land)
  val spaNc: Province = Province(SpaNc, Water)
  val spaSc: Province = Province(SpaSc, Water)
  val stp:   Province = Province(Stp,   Land)
  val stpNc: Province = Province(StpNc, Water)
  val stpSc: Province = Province(StpSc, Water)

  val provinces: List[Province] = List(
    ber, bul, bulEc, bulSc, kie, ska, spa, spaNc, spaSc, stp, stpNc, stpSc
  )

}
