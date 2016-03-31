package services.orders.scanners

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[TheOrdersScanner])
trait OrdersScanner {

  def scan(string: String): Option[List[OrderToken]]

}
