package services.orders

import models.units.UnitKind
import UnitKind.UnitKind
import models.orders.OrderKind.OrderKind
import models.provinces.ProvinceId.ProvinceId

sealed trait OrderToken

case class UnitKindOrderToken(unitKind: UnitKind) extends OrderToken

case class ProvinceIdOrderToken(provinceId: ProvinceId) extends OrderToken

case class OrderKindOrderToken(orderKind: OrderKind) extends OrderToken
