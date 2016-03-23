package services.orders

import models.orders._
import models.units.UnitKind._
import models.orders.OrderKind._
import models.provinces.ProvinceId._

import scala.util.parsing.combinator._

trait OrdersParsers extends Parsers {

  type Elem = OrderToken

}

object OrdersParser extends OrdersParsers {

  private def unitKindParser(unitKind: UnitKind): Parser[UnitKind] = new Parser[UnitKind] {

    def apply(input: Input) = {
      input.first match {
        case UnitKindOrderToken(foundUnitKind) if unitKind == foundUnitKind =>
          Success(unitKind, input.rest)
        case _ =>
          Failure("Unable to parse unit kind " + unitKind, input)
      }
    }

  }

  private val unitKindParser: Parser[UnitKind] = new Parser[UnitKind] {

    def apply(input: Input) = {
      input.first match {
        case UnitKindOrderToken(unitKind) =>
          Success(unitKind, input.rest)
        case _ =>
          Failure("Unable to parse any unit kind", input)
      }
    }

  }

  private val provinceIdParser: Parser[ProvinceId] = new Parser[ProvinceId] {

    def apply(input: Input) = {
      input.first match {
        case ProvinceIdOrderToken(provinceId) =>
          Success(provinceId, input.rest)
        case _ =>
          Failure("Unable to parse any province id", input)
      }
    }

  }

  private def orderKindParser(orderKind: OrderKind): Parser[OrderKind] = new Parser[OrderKind] {

    def apply(input: Input) = {
      input.first match {
        case OrderKindOrderToken(foundOrderKind) if orderKind == foundOrderKind =>
          Success(orderKind, input.rest)
        case _ =>
          Failure("Unable to parse order kind " + orderKind, input)
      }
    }

  }

  private val holdOrderParser: Parser[HoldOrder] = unitKindParser.? ~> provinceIdParser <~ orderKindParser(Hold) ^^ {
    case provinceId => HoldOrder(provinceId)
  }

  private val moveOrderParser: Parser[MoveOrder] = (unitKindParser.? ~> provinceIdParser <~ orderKindParser(Move)) ~ provinceIdParser ^^ {
    case provinceId ~ targetProvinceId => MoveOrder(provinceId, targetProvinceId)
  }

  private val convoyOrderParser: Parser[ConvoyOrder] = (unitKindParser(Fleet).? ~> provinceIdParser <~ orderKindParser(Convoy)) ~ moveOrderParser ^^ {
    case provinceId ~ moveOrder => ConvoyOrder(provinceId, moveOrder)
  }

  private val supportOrderParser: Parser[SupportOrder] = (unitKindParser.? ~> provinceIdParser <~ orderKindParser(Support)) ~ (holdOrderParser | moveOrderParser | convoyOrderParser) ^^ {
    case provinceId ~ order => SupportOrder(provinceId, order)
  }

  private val orderParser: Parser[Order] = holdOrderParser | moveOrderParser | convoyOrderParser | supportOrderParser

  private val ordersParser: Parser[List[Order]] = orderParser.*

  def apply(tokens: ListReader[OrderToken]): Option[List[Order]] = {
    ordersParser(tokens) match {
      case Success(orders, input) if input.atEnd =>
        Some(orders)
      case _ =>
        None
    }
  }

}
