package services.orders.analyzers.reducers

import com.google.inject.ImplementedBy
import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time

@ImplementedBy(classOf[TheOrdersReducer])
trait OrdersReducer {

  def apply(time: Time, provinces: Provinces, orders: Orders): Orders

}
