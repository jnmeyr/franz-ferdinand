package services.orders.scanners

import models.orders.OrderKind.OrderKind
import models.provinces.ProvinceId.ProvinceId
import models.units.UnitKind.UnitKind

sealed trait OrderToken

case class UnitKindOrderToken(unitKind: UnitKind) extends OrderToken

case class ProvinceIdOrderToken(provinceId: ProvinceId) extends OrderToken

case class OrderKindOrderToken(orderKind: OrderKind) extends OrderToken
