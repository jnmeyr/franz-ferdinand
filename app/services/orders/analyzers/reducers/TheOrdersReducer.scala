package services.orders.analyzers.reducers

import models.countries.Country
import models.orders.Orders.Orders
import models.orders._
import models.provinces.Provinces
import models.provinces.Provinces._
import models.times.Phase._
import models.times.Time
import models.units.UnitKind.UnitKind

class TheOrdersReducer extends OrdersReducer {

  // TODO: adjustment: enlarge disbands to match supply (dis, f < a, abc)

  override def apply(time: Time, provinces: Provinces, orders: Orders): Orders = {
    orders.map({
      case (countryId, orders) =>
        val country: Country = Country.country(countryId)
        time.phase match {
          case Adjustment =>
            Provinces.countryProvincesUnitsDifference(provinces, country) match {
              case difference if difference < 0 =>
                val (disbandOrders, waiveOrders) = orders.partition(_.isInstanceOf[DisbandOrder])
                disbandOrders.size + difference match {
                  case difference if difference < 0 =>
                    val addedDisbandOrders = Provinces.countryUnits(provinces, country).map({
                      case (provinceId, (_, Some(unit), _)) =>
                        (provinceId, unit.unitKind)
                    }).toList.filter({
                      case (provinceId, _) =>
                        !disbandOrders.collect({
                          case disbandOrder: DisbandOrder =>
                            disbandOrder.provinceId
                        }).contains(provinceId)
                    }).sortBy({
                      case (provinceId, _) =>
                        provinceId
                    }).sortBy({
                      case (_, unitKind) =>
                        unitKind
                    })(Ordering[UnitKind].reverse).sortBy({
                      case (provinceId, _) =>
                        Provinces.countryProvinceDistance(provinceId, country)
                    }).take(-difference).map({
                      case (provinceId, _) =>
                        DisbandOrder(provinceId)
                    })
                    (countryId, addedDisbandOrders ++ orders)
                  case difference if difference > 0 =>
                    val reducedDisbandOrders = disbandOrders.collect({
                      case disbandOrder: DisbandOrder =>
                        disbandOrder
                    }).sortBy({
                      case DisbandOrder(provinceId) =>
                        provinceId
                    }).sortBy({
                      case DisbandOrder(provinceId) =>
                        provinces.get(provinceId).get._2.get.unitKind
                    })(Ordering[UnitKind].reverse).sortBy({
                      case DisbandOrder(provinceId) =>
                        Provinces.countryProvinceDistance(provinceId, countryId)
                    }).take(difference)
                    (countryId, reducedDisbandOrders ++ waiveOrders)
                  case _ =>
                    (countryId, orders)
                }
              case difference if difference > 0 =>
                val (buildOrders, waiveOrders) = orders.partition(_.isInstanceOf[BuildOrder])
                val (reducedBuildOrders, removedBuildOrders) = buildOrders.collect({
                  case buildOrder: BuildOrder =>
                    buildOrder
                }).sortBy({
                  case BuildOrder(provinceId, _) =>
                    provinceId
                }).sortBy({
                  case BuildOrder(_, unitKind) =>
                    unitKind
                }).splitAt(difference)
                val enlargedWaiveOrders = waiveOrders ++ removedBuildOrders.map({
                  case BuildOrder(provinceId, _) =>
                    WaiveOrder(provinceId)
                })
                (countryId, reducedBuildOrders ++ enlargedWaiveOrders)
              case _ =>
                (countryId, orders)
            }

          case _ =>
            (countryId, orders)
        }
    })
  }

}
