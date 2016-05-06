package services.orders.transpilers

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsNull, JsObject, JsString, JsValue}
import services.orders.transpilers.parsers.TheOrdersParser
import services.orders.transpilers.scanners.TheOrdersScanner

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

  "The orders transpiler" should "be able to transpile correct orders" in {
    theOrdersTranspiler(orders) shouldNot be (None)
  }

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

  "The orders transpiler" should "not be able to transpile wrong orders" in {
    theOrdersTranspiler(noOrders) should be (None)
    theOrdersTranspiler(missingOrders) should be (None)
    theOrdersTranspiler(oneNoOrders) should be (None)
    theOrdersTranspiler(oneMissingOrders) should be (None)
  }

 }
