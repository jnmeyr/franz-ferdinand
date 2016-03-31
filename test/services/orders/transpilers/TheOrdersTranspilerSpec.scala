package services.orders.transpilers

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsString, JsNull, JsObject, JsValue}
import services.orders.parsers.TheOrdersParser
import services.orders.scanners.TheOrdersScanner

class TheOrdersTranspilerSpec extends FlatSpec with Matchers {

  val theOrdersTranspiler: OrdersTranspiler = new TheOrdersTranspiler(new TheOrdersScanner, new TheOrdersParser)

  val orders: JsValue = JsObject(Map(
    "a" -> JsString(""),
    "e" -> JsString(""),
    "f" -> JsString(""),
    "g" -> JsString(""),
    "i" -> JsString(""),
    "r" -> JsString(""),
    "t" -> JsString("")
  ))

  val noOrders: JsValue = JsNull

  val missingOrders: JsValue = JsObject(Map.empty[String, JsValue])

  val oneNoOrders: JsValue = JsObject(Map(
    "a" -> JsString(""),
    "e" -> JsString(""),
    "f" -> JsString(""),
    "g" -> JsString(""),
    "i" -> JsString(""),
    "r" -> JsString("")
  ))

  val oneMissingOrders: JsValue = JsObject(Map(
    "a" -> JsString(""),
    "e" -> JsString(""),
    "f" -> JsString(""),
    "g" -> JsString(""),
    "i" -> JsString(""),
    "r" -> JsString(""),
    "t" -> JsNull
  ))

  "The orders transpiler" should "be able to transpile correct orders" in {
    theOrdersTranspiler.transpile(orders) should not be (None)
  }

  "The orders transpiler" should "not be able to transpile wrong orders" in {
    theOrdersTranspiler.transpile(noOrders) should be (None)
    theOrdersTranspiler.transpile(missingOrders) should be (None)
    theOrdersTranspiler.transpile(oneNoOrders) should be (None)
    theOrdersTranspiler.transpile(oneMissingOrders) should be (None)
  }

 }
