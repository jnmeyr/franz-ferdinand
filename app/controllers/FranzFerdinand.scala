package controllers

import javax.inject.Inject

import models.boards.Board
import models.games.GameId.GameId
import play.api.libs.json.Json
import play.api.mvc._
import services.orders.{OrdersParser, OrdersScanner}

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

  def orders(gameId: GameId) = Action { request =>
    println("orders: " + gameId + ": " + request.body.asText)

    val orders = for {
      string <- request.body.asText
      tokens <- OrdersScanner(string)
      orders <- OrdersParser(tokens)
    } yield orders

    orders match {
      case Some(orders) =>
        val board = Some(Board.board) // TODO hc

        board match {
          case Some(board) =>
            Ok(Json.toJson(Board.board))
          case _ =>
            BadRequest
        }
      case _ =>
        BadRequest
    }
  }

}
