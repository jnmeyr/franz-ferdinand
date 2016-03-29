package controllers

import javax.inject.Inject

import models.boards.Board
import models.countries.CountryId._
import models.games.GameId.GameId
import models.orders.Order
import play.api.libs.json.{JsLookupResult, Json}
import play.api.mvc._
import services.orders.{OrdersParser, OrdersScanner}

import scala.language.implicitConversions

class FranzFerdinand @Inject() extends Controller {

  def board(gameId: GameId) = Action {
    println("board: " + gameId)

    val board = Some(Board.board) // TODO hc

    board match {
      case Some(board) =>
        Ok(Json.toJson(Board.board))
      case _ =>
        BadRequest
    }
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
      if (map.forall({case (_, value) => value.isDefined})) {
        Some(map.collect({ case (key, Some(value)) => key -> value}))
      } else {
        None
      }
    }

    val orders: Option[Map[CountryId, List[Order]]] = Map[CountryId, Option[List[Order]]](
      Austria -> request.body \ "austria",
      England -> request.body \ "england",
      France -> request.body \ "france",
      Germany -> request.body \ "germany",
      Italy -> request.body \ "italy",
      Russia -> request.body \ "russia",
      Turkey -> request.body \ "turkey"
    )

    if (orders.isDefined) {
      val board = Some(Board.board) // TODO hc

      board match {
        case Some(board) =>
          Ok(Json.toJson(Board.board))
        case _ =>
          BadRequest
      }
    } else {
      BadRequest
    }
  }

}
