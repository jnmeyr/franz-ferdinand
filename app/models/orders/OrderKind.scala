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

  implicit val orderKindFormat = new Format[OrderKind] {

    def reads(jsValue: JsValue) = JsSuccess(OrderKind.withName(jsValue.as[String]))

    def writes(orderKind: OrderKind) = JsString(orderKind.toString)

  }

}