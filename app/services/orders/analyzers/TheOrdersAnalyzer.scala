package services.orders.analyzers

import javax.inject.Inject

import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time
import services.orders.analyzers.filters.OrdersFilter
import services.orders.analyzers.mappers.OrdersMapper
import services.orders.analyzers.reducers.OrdersReducer

class TheOrdersAnalyzer @Inject() (ordersFilter: OrdersFilter, ordersMapper: OrdersMapper, ordersReducer: OrdersReducer) extends OrdersAnalyzer {

  override def apply(time: Time, provinces: Provinces, orders: Orders): Option[Orders] = {
    Some(ordersReducer(time, provinces, ordersMapper(time, provinces, ordersFilter(time, provinces, orders))))
  }

}
