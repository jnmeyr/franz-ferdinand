package services.orders.filters

import models.countries.Country
import models.orders.Orders.Orders
import models.orders._
import models.provinces.Provinces.Provinces
import models.provinces._
import models.times.Phase._
import models.times.Time
import models.units.UnitKind._
import models.units.{ArmyUnit, FleetUnit}

class TheOrdersFilter extends OrdersFilter {

  override def apply(time: Time, provinces: Provinces, orders: Orders): Orders = orders.map({
    case (countryId, orders) =>
      val country: Country = Country.country(countryId)
      (countryId, orders.filter({
        case HoldOrder(provinceId) =>
          time.phase match {
            case Diplomacy =>
              provinces.get(provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  true
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case MoveOrder(provinceId, targetProvinceIds) =>
          time.phase match {
            case Diplomacy =>
              provinces.get(provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  val province = Province.province(provinceId)
                  val targetProvinces = targetProvinceIds.map(Province.province)
                  unit match {
                    case unit: ArmyUnit =>
                      targetProvinces match {
                        case targetProvince :: Nil =>
                          province.hasRoute(targetProvince)
                        case _ =>
                          Province.isConvoy(province :: targetProvinces)
                      }
                    case unit: FleetUnit =>
                      targetProvinces match {
                        case targetProvince :: Nil =>
                          province.hasWay(targetProvince)
                        case _ =>
                          false
                      }
                  }
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case ConvoyOrder(provinceId, sourceProvinceId, targetProvinceId) =>
          time.phase match {
            case Diplomacy =>
              provinces.get(provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  unit match {
                    case unit: FleetUnit =>
                      Province.hasConvoy(provinceId, sourceProvinceId, targetProvinceId)
                    case _ =>
                      false
                  }
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case SupportOrder(provinceId, sourceProvinceId, targetProvinceId) =>
          time.phase match {
            case Diplomacy =>
              provinces.get(provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  val province = Province.province(provinceId)
                  val targetProvince = Province.province(targetProvinceId.getOrElse(sourceProvinceId))
                  unit match {
                    case unit: ArmyUnit =>
                      province.hasRoute(targetProvince)
                    case unit: FleetUnit =>
                      province.hasWay(targetProvince)
                  }
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case RetreatOrder(provinceId, targetProvinceId) =>
          time.phase match {
            case Resolution =>
              provinces.get(provinceId) match {
                case Some((_, _, Some(unit))) if unit.countryId == country.countryId =>
                  val province = Province.province(provinceId)
                  unit match {
                    case unit: ArmyUnit =>
                      province.hasRoute(targetProvinceId)
                    case unit: FleetUnit =>
                      province.hasWay(targetProvinceId)
                    case _ =>
                      false
                  }
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case DisbandOrder(provinceId) =>
          time.phase match {
            case Resolution =>
              provinces.get(provinceId) match {
                case Some((_, _, Some(unit))) if unit.countryId == country.countryId =>
                  true
                case _ =>
                  false
              }
            case Adjustment if Provinces.provinceUnitDifference(provinces, country) < 0 =>
              provinces.get(provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  true
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case BuildOrder(provinceId, unitKind) =>
          time.phase match {
            case Adjustment if Provinces.provinceUnitDifference(provinces, country) > 0 =>
              if (country.hasProvince(provinceId)) {
                provinces.get(provinceId) match {
                  case Some((Some(countryId), None, _)) if countryId == country.countryId =>
                    (Province.province(provinceId), unitKind) match {
                      case (province: LandProvince, Army) =>
                        true
                      case (province: CoastProvince, _) =>
                        true
                      case _ =>
                        false
                    }
                  case _ =>
                    false
                }
              } else {
                false
              }
            case _ =>
              false
          }
        case WaiveOrder(provinceId) =>
          time.phase match {
            case Adjustment =>
              provinces.get(provinceId) match {
                case Some((Some(countryId), None, _)) if countryId == country.countryId =>
                  country.hasProvince(provinceId)
                case _ =>
                  false
              }
            case _ =>
              false
          }
      }))
  })

}
