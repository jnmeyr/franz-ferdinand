package services.orders.analyzers

import com.google.inject.ImplementedBy
import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time

@ImplementedBy(classOf[TheOrdersAnalyzer])
trait OrdersAnalyzer {

  def apply(time: Time, provinces: Provinces, orders: Orders): Option[Orders]

}
