package models.orders

import models.provinces.ProvinceId._
import models.units.UnitKind._

sealed trait Order {

  def provinceId: ProvinceId

}

case class HoldOrder(provinceId: ProvinceId) extends Order

case class MoveOrder(provinceId: ProvinceId, targetProvinceIds: List[ProvinceId]) extends Order

case class ConvoyOrder(provinceId: ProvinceId, sourceProvinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order

case class SupportOrder(provinceId: ProvinceId, sourceProvinceId: ProvinceId, targetProvinceId: Option[ProvinceId] = None) extends Order

case class RetreatOrder(provinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order

case class DisbandOrder(provinceId: ProvinceId) extends Order

case class BuildOrder(provinceId: ProvinceId, unitKind: UnitKind) extends Order

case class WaiveOrder(provinceId: ProvinceId) extends Order
