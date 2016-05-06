package models.orders

import models.orders.OrderKind._
import models.provinces.ProvinceId._
import models.units.UnitKind._

sealed trait Order {

  def orderKind: OrderKind

  def provinceId: ProvinceId

}

case class HoldOrder(provinceId: ProvinceId) extends Order {

  def orderKind = Hold

}

case class MoveOrder(provinceId: ProvinceId, targetProvinceIds: List[ProvinceId]) extends Order {

  def orderKind = Move

}

case class ConvoyOrder(provinceId: ProvinceId, sourceProvinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order {

  def orderKind = Convoy

}

case class SupportOrder(provinceId: ProvinceId, sourceProvinceId: ProvinceId, targetProvinceId: Option[ProvinceId] = None) extends Order {

  def orderKind = Support

}

case class DisbandOrder(provinceId: ProvinceId) extends Order {

  def orderKind = Disband

}

case class RetreatOrder(provinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order {

  def orderKind = Retreat

}

case class WaiveOrder(provinceId: ProvinceId) extends Order {

  def orderKind = Waive

}

case class BuildOrder(provinceId: ProvinceId, unitKind: UnitKind) extends Order {

  def orderKind = Build

}
