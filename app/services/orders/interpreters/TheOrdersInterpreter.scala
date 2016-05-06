package services.orders.interpreters

import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time

class TheOrdersInterpreter extends OrdersInterpreter {

  override def apply(time: Time, provinces: Provinces, orders: Orders): Option[Provinces] = Some(provinces)

}
