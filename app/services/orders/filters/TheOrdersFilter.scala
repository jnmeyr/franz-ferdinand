package services.orders.filters

import models.countries.Country
import models.orders._
import models.orders.Orders.Orders
import models.provinces.Provinces.Provinces
import models.times.Time
import models.times.Phase._

class TheOrdersFilter extends OrdersFilter {

  override def apply(time: Time, provinces: Provinces, orders: Orders): Orders = orders.map({
    case (countryId, orders) =>
      (countryId, orders.filter({
        case HoldOrder(provinceId) =>
          if (time.phase == Diplomacy) {
            provinces.get(provinceId) match {
              case Some((_, Some(unit), _)) if unit.countryId == countryId =>
                true
              case _ =>
                false
            }
          } else {
            false
          }
        case MoveOrder(_, _) =>
          false // TODO
        case ConvoyOrder(_, _, _) =>
          false // TODO
        case SupportOrder(_, _, _) =>
          false // TODO
        case RetreatOrder(_, _) =>
          false // TODO
        case DisbandOrder(_) =>
          false // TODO
        case BuildOrder(_, _) =>
          false // TODO
        case WaiveOrder(provinceId) =>
          if (time.phase == Adjustment) {
            provinces.get(provinceId) match {
              case Some((Some(otherCountryId), None, _)) if otherCountryId == countryId =>
                Country.country(countryId).provinces.contains(provinceId)
              case _ =>
                false
            }
          } else {
            false
          }
      }))
  })

}
