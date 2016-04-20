package services.orders.filters

import models.countries.Country
import models.orders._
import models.orders.Orders.Orders
import models.provinces._
import models.provinces.Provinces.Provinces
import models.times.Time
import models.times.Phase._
import models.units.UnitKind.Army

class TheOrdersFilter extends OrdersFilter {

  override def apply(time: Time, provinces: Provinces, orders: Orders): Orders = orders.map({
    case (countryId, orders) =>
      val country: Country = Country.country(countryId)
      (countryId, orders.filter({
        case HoldOrder(provinceId) =>
          val province = Province.province(provinceId)
          time.phase match {
            case Diplomacy =>
              provinces.get(province.provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  true
                case _ =>
                  false
              }
            case _ =>
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
        case DisbandOrder(provinceId) =>
          val province = Province.province(provinceId)
          time.phase match {
            case Resolution =>
              provinces.get(province.provinceId) match {
                case Some((_, _, Some(unit))) if unit.countryId == country.countryId =>
                  true
                case _ =>
                  false
              }
            case Adjustment if Provinces.provinceUnitDifference(provinces, country) < 0 =>
              provinces.get(province.provinceId) match {
                case Some((_, Some(unit), _)) if unit.countryId == country.countryId =>
                  true
                case _ =>
                  false
              }
            case _ =>
              false
          }
        case BuildOrder(provinceId, unitKind) =>
          val province = Province.province(provinceId)
          time.phase match {
            case Adjustment if Provinces.provinceUnitDifference(provinces, country) > 0 =>
              if (country.hasProvince(province.provinceId)) {
                provinces.get(province.provinceId) match {
                  case Some((Some(countryId), None, _)) if countryId == country.countryId =>
                    (province, unitKind) match {
                      case (LandProvince(_, _, _), Army) =>
                        true
                      case (CoastProvince(_, _, _, _), _) =>
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
          val province = Province.province(provinceId)
          time.phase match {
            case Adjustment =>
              provinces.get(province.provinceId) match {
                case Some((Some(countryId), None, _)) if countryId == country.countryId =>
                  country.hasProvince(province.provinceId)
                case _ =>
                  false
              }
            case _ =>
              false
          }
      }))
  })

}
