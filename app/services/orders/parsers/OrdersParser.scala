package services.orders.parsers

import com.google.inject.ImplementedBy
import models.orders.Order
import services.orders.scanners.OrderToken

import scala.util.parsing.combinator.Parsers

@ImplementedBy(classOf[TheOrdersParser])
trait OrdersParser extends Parsers {

  type Elem = OrderToken

  def parse(tokens: ListReader[OrderToken]): Option[List[Order]]

}
