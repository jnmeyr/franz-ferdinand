package services.orders.transpilers

import javax.inject.Inject

import models.countries.CountryId._
import models.orders.Order
import play.api.libs.json.{JsLookupResult, JsValue}
import services.orders.parsers.OrdersParser
import services.orders.scanners.OrdersScanner


class TheOrdersTranspiler @Inject() (ordersScanner: OrdersScanner, ordersParser: OrdersParser) extends OrdersTranspiler {

  def asOrders(jsLookupResult: JsLookupResult): Option[List[Order]] = for {
    jsValue <- jsLookupResult.toOption
    string <- jsValue.asOpt[String]
    tokens <- ordersScanner(string)
    orders <- ordersParser(tokens)
  } yield orders

  def apply(jsValue: JsValue): Option[Map[CountryId, List[Order]]] = {
    val orders = Map[CountryId, Option[List[Order]]](
      Austria -> asOrders(jsValue \ "a"),
      England -> asOrders(jsValue \ "e"),
      France  -> asOrders(jsValue \ "f"),
      Germany -> asOrders(jsValue \ "g"),
      Italy   -> asOrders(jsValue \ "i"),
      Russia  -> asOrders(jsValue \ "r"),
      Turkey  -> asOrders(jsValue \ "t")
    )

    if (orders.forall({ case (_, orders) => orders.isDefined })) {
      Some(orders.collect({ case (countryId, Some(orders)) => countryId -> orders }))
    } else {
      None
    }
  }

}
