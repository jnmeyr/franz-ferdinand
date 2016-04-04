package controllers

import javax.inject.{Inject, Singleton}

import models.games.GameId.GameId
import models.provinces.Provinces
import models.times.Time
import models.times.Season._
import models.times.Phase._
import play.api.libs.json.Json
import play.api.mvc._
import services.orders.interpreters.OrdersInterpreter
import services.orders.transpilers.OrdersTranspiler
import services.stores.Store

import scala.language.implicitConversions

@Singleton
class FranzFerdinand @Inject() (store: Store, ordersTranspiler: OrdersTranspiler, ordersInterpreter: OrdersInterpreter) extends Controller {

  def time(gameId: GameId) = Action {
    println("time: " + gameId)

    store.getGame(gameId)

    Ok(Json.toJson(Time.times.head))
  }

  def provinces(gameId: GameId) = Action {
    println("provinces: " + gameId)

    Ok(Json.toJson(Provinces.startProvinces))
  }

  def orders(gameId: GameId) = Action(parse.tolerantJson) { request =>
    println("orders: " + gameId + ": " + request.body)

    ordersTranspiler(request.body) match {
      case Some(orders) =>
        println(orders)
        val provinces = ordersInterpreter(Time(1901, Spring, Diplomacy), Provinces.startProvinces, orders)
        println(provinces)

        Ok(Json.toJson(Time.times.tail.head))
      case _ =>
        BadRequest
    }
  }

}
