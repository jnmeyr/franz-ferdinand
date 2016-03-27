package models.orders

import play.api.libs.json._

object OrderKind extends Enumeration {

  type OrderKind = Value

  val Hold    = Value("H")
  val Move    = Value("M")
  val Convoy  = Value("C")
  val Support = Value("S")
  val Retreat = Value("R")
  val Disband = Value("D")

  implicit val orderKindFormat: Format[OrderKind] = new Format[OrderKind] {

    def reads(jsValue: JsValue): JsResult[OrderKind] = JsSuccess(OrderKind.withName(jsValue.as[String]))

    def writes(orderKind: OrderKind): JsString = JsString(orderKind.toString)

  }

}