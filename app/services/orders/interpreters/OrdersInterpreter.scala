package services.orders.interpreters

import com.google.inject.ImplementedBy
import models.orders.Orders.Orders
import models.provinces.ProvinceId.ProvinceId
import models.provinces.Provinces.Provinces
import models.times.Time

@ImplementedBy(classOf[TheOrdersInterpreter])
trait OrdersInterpreter {

  def apply(time: Time, provinces: Provinces, orders: Orders): Provinces

}
