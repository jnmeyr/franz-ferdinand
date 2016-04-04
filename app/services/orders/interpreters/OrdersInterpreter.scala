package services.orders.interpreters

import com.google.inject.ImplementedBy
import models.countries.CountryId.CountryId
import models.orders.Order
import models.provinces.ProvinceId.ProvinceId
import models.provinces.Provinces
import models.times.Time

@ImplementedBy(classOf[TheOrdersInterpreter])
trait OrdersInterpreter {

  def apply(time: Time, provinces: Map[ProvinceId, Provinces], orders: Map[CountryId, List[Order]]): Map[ProvinceId, Provinces]

}
