package services.orders.transpilers

import com.google.inject.ImplementedBy
import models.orders.Orders.Orders
import play.api.libs.json.JsValue

@ImplementedBy(classOf[TheOrdersTranspiler])
trait OrdersTranspiler {

  def apply(jsValue: JsValue): Option[Orders]

}
