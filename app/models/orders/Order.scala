package models.orders

import models.provinces.ProvinceId
import ProvinceId.ProvinceId

sealed trait Order {

  def provinceId: ProvinceId

}

case class HoldOrder(provinceId: ProvinceId) extends Order

case class MoveOrder(provinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order

case class ConvoyOrder(provinceId: ProvinceId, moveOrder: MoveOrder) extends Order

case class SupportOrder(provinceId: ProvinceId, order: Order) extends Order
