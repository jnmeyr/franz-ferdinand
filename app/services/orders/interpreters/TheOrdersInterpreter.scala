package services.orders.interpreters

import models.countries.CountryId.CountryId
import models.orders.Order
import models.provinces.ProvinceId.ProvinceId
import models.provinces.Provinces

class TheOrdersInterpreter extends OrdersInterpreter {

  override def apply(provinces: Map[ProvinceId, Provinces], orders: Map[CountryId, List[Order]]): Map[ProvinceId, Provinces] = provinces

}
