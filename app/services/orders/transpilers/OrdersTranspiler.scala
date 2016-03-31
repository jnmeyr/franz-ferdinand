package services.orders.transpilers

import com.google.inject.ImplementedBy
import models.countries.CountryId._
import models.orders.Order
import play.api.libs.json.JsValue

import scala.language.implicitConversions

@ImplementedBy(classOf[TheOrdersTranspiler])
trait OrdersTranspiler {

  def transpile(jsValue: JsValue): Option[Map[CountryId, List[Order]]]

}

