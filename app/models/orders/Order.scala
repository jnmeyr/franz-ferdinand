package models.orders

import models.provinces.ProvinceId
import ProvinceId.ProvinceId

sealed trait Order {

  def provinceId: ProvinceId

}

case class HoldOrder(provinceId: ProvinceId) extends Order

case class MoveOrder(provinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order

case class ConvoyOrder(provinceId: ProvinceId, sourceProvinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order

case class SupportOrder(provinceId: ProvinceId, sourceProvinceId: ProvinceId, targetProvinceId: Option[ProvinceId] = None) extends Order

case class RetreatOrder(provinceId: ProvinceId, targetProvinceId: ProvinceId) extends Order

case class DisbandOrder(provinceId: ProvinceId) extends Order
