package services.orders.analyzers.mappers

import com.google.inject.ImplementedBy
import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time

@ImplementedBy(classOf[TheOrdersMapper])
trait OrdersMapper {

  def apply(time: Time, provinces: Provinces, orders: Orders): Orders

}
