package controllers

import javax.inject.{Inject, Singleton}

import models.games.GameId.GameId
import models.provinces.Provinces
import models.times.Time
import models.times.Season._
import models.times.Phase._
import play.api.libs.json.Json
import play.api.mvc._
import services.orders.analyzers.OrdersAnalyzer
import services.orders.interpreters.OrdersInterpreter
import services.orders.transpilers.OrdersTranspiler
import services.stores.Store

@Singleton
class FranzFerdinand @Inject() (store: Store, ordersTranspiler: OrdersTranspiler, ordersAnalyzer: OrdersAnalyzer, ordersInterpreter: OrdersInterpreter) extends Controller {

  def time(gameId: GameId) = Action {
    println("time: " + gameId)

    store.getGame(gameId)

    Ok(Json.toJson(Time.times.head))
  }

  def provinces(gameId: GameId) = Action {
    println("provinces: " + gameId)

    Ok(Json.toJson(Provinces.startProvinces)(Provinces.provincesWrites))
  }

  def orders(gameId: GameId) = Action(parse.tolerantJson) { request =>
    println("orders: " + gameId + ": " + request.body)

    val time = Time(1901, Spring, Diplomacy)
    val uninterpretedProvinces = Provinces.startProvinces

    val interpretedProvinces = for {
      unanalyzedOrders <- ordersTranspiler(request.body)
      analyzedOrders <- ordersAnalyzer(time, uninterpretedProvinces, unanalyzedOrders)
      interpretedProvinces <- ordersInterpreter(time, uninterpretedProvinces, analyzedOrders)
    } yield interpretedProvinces

    interpretedProvinces match {
      case Some(_) =>
        Ok(Json.toJson(Time.times.tail.head))
      case _ =>
        BadRequest
    }
  }

}
