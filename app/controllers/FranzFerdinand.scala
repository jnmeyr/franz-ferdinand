package controllers

import javax.inject.Inject

import play.api.mvc._
import services.orders.{OrdersParser, OrdersScanner}

class FranzFerdinand @Inject() extends Controller {

  def orders = Action { request =>
    val orders = for {
      string <- request.body.asText
      tokens <- OrdersScanner(string)
      orders <- OrdersParser(tokens)
    } yield orders

    orders match {
      case Some(_) =>
        println(orders)
        Accepted
      case _ =>
        BadRequest
    }
  }

}
