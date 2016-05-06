package services.orders.analyzers.mappers

import models.orders.Orders._
import models.orders._
import models.countries.Country
import models.provinces.Provinces._
import models.times.Phase._
import models.times.Time

import scalaz.Scalaz._

class TheOrdersMapper extends OrdersMapper {

  override def apply(time: Time, provinces: Provinces, orders: Orders): Orders = {
    time.phase match {
      case Diplomacy =>
        provinces.flatMap({
          case (provinceId, (_, Some(unit), _)) =>
            orders.get(unit.countryId).get.filter(_.provinceId == provinceId) match {
              case order :: Nil =>
                Some(Map(unit.countryId -> List(order)))
              case _ =>
                Some(Map(unit.countryId -> List(HoldOrder(provinceId))))
            }
          case _ =>
            None
        }).foldLeft(Orders.orders)(_ |+| _)
      case Resolution =>
        provinces.flatMap({
          case (provinceId, (_, _, Some(unit))) =>
            orders.get(unit.countryId).get.filter(_.provinceId == provinceId) match {
              case order :: Nil =>
                Some(Map(unit.countryId -> List(order)))
              case _ =>
                Some(Map(unit.countryId -> List(DisbandOrder(provinceId))))
            }
          case _ =>
            None
        }).foldLeft(Orders.orders)(_ |+| _)
      case Adjustment =>
        provinces.flatMap({
          case (provinceId, (Some(countryId), None, _)) if Country.country(countryId).hasProvince(provinceId) =>
            orders.get(countryId).get.filter(_.provinceId == provinceId) match {
              case order :: Nil =>
                Some(Map(countryId -> List(order)))
              case _ =>
                Some(Map(countryId -> List(WaiveOrder(provinceId))))
            }
          case _ =>
            None
        }).foldLeft(Orders.orders)(_ |+| _)
    }
  }

}
