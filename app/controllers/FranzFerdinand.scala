package controllers

import javax.inject.{Inject, Singleton}

import models.games.GameId.GameId
import models.provinces.Provinces
import models.times.Phase.Phase
import models.times.Season.Season
import models.times.Time
import models.times.Year.Year
import play.api.libs.json.Json
import play.api.mvc._
import services.orders.transpilers.OrdersTranspiler
import services.stores.Store

import scala.language.implicitConversions

@Singleton
class FranzFerdinand @Inject() (store: Store, ordersTranspiler: OrdersTranspiler) extends Controller {

  def time(gameId: GameId) = Action {
    println("time: " + gameId)

    store.getGame(gameId)

    Ok(Json.toJson(Time.times.head))
  }

  def provinces(gameId: GameId, year: Year, season: Season, phase: Phase) = Action {
    println("provinces: " + gameId + ": " + year + "/" + season + "/" + phase)

    val time: Time = Time(year, season, phase)

    Ok
  }

  def provinces(gameId: GameId) = Action {
    println("provinces: " + gameId)

    Ok(Json.toJson(Provinces.provinces))
  }

  def orders(gameId: GameId) = Action(parse.tolerantJson) { request =>
    println("orders: " + gameId + ": " + request.body)

    ordersTranspiler(request.body) match {
      case Some(orders) =>
        println(orders)
        Ok(Json.toJson(Time.times.tail.head))
      case _ =>
        BadRequest
    }
  }

}
