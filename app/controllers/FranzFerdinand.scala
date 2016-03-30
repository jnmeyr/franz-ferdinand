package controllers

import javax.inject.Inject

import models.countries.CountryId._
import models.games.GameId.GameId
import models.orders.Order
import models.times.Season.Season
import models.times.Time
import models.times.Phase.Phase
import models.provinces.Provinces
import models.times.Year.Year
import play.api.libs.json.{JsLookupResult, Json}
import play.api.mvc._
import services.orders.{OrdersParser, OrdersScanner}

import scala.language.implicitConversions

class FranzFerdinand @Inject() extends Controller {

  def time(gameId: GameId) = Action {
    println("time: " + gameId)

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

    implicit def json2orders(jsLookupResult: JsLookupResult): Option[List[Order]] = for {
      jsValue <- jsLookupResult.toOption
      string <- jsValue.asOpt[String]
      tokens <- OrdersScanner(string)
      orders <- OrdersParser(tokens)
    } yield orders

    implicit def sequence[K, V](map: Map[K, Option[V]]): Option[Map[K, V]] = {
      if (map.forall({ case (_, value) => value.isDefined })) {
        Some(map.collect({ case (key, Some(value)) => key -> value }))
      } else {
        None
      }
    }

    val orders: Option[Map[CountryId, List[Order]]] = Map[CountryId, Option[List[Order]]](
      Austria -> request.body \ "a",
      England -> request.body \ "e",
      France -> request.body \ "f",
      Germany -> request.body \ "g",
      Italy -> request.body \ "i",
      Russia -> request.body \ "r",
      Turkey -> request.body \ "t"
    )

    if (orders.isDefined) {
      Ok(Json.toJson(Time.times.tail.head))
    } else {
      BadRequest
    }
  }

}
