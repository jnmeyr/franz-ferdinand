package services.orders.scanners

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[TheOrdersScanner])
trait OrdersScanner {

  def apply(string: String): Option[List[OrderToken]]

}
