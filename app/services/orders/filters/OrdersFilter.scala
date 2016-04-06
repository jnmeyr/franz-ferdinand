package services.orders.filters

import com.google.inject.ImplementedBy
import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time

@ImplementedBy(classOf[TheOrdersFilter])
trait OrdersFilter {

  def apply(time: Time, provinces: Provinces, orders: Orders): Orders

}
