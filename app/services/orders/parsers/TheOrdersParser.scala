package services.orders.parsers

import models.orders.OrderKind._
import models.orders._
import models.provinces.ProvinceId._
import models.units.UnitKind._
import services.orders.scanners._

class TheOrdersParser extends OrdersParser {

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

  private val convoyOrderParser: Parser[ConvoyOrder] = (unitKindParser.? ~> provinceIdParser <~ orderKindParser(Convoy)) ~ (unitKindParser.? ~> provinceIdParser) ~ (orderKindParser(Move) ~> provinceIdParser) ^^ {
    case provinceId ~ sourceProvinceId ~ targetProvinceId => ConvoyOrder(provinceId, sourceProvinceId, targetProvinceId)
  }

  private val supportOrderParser: Parser[SupportOrder] = (unitKindParser.? ~> provinceIdParser <~ orderKindParser(Support)) ~ (unitKindParser.? ~> provinceIdParser) ~ (orderKindParser(Move) ~> provinceIdParser).? ^^ {
    case provinceId ~ sourceProvinceId ~ targetProvinceId => SupportOrder(provinceId, sourceProvinceId, targetProvinceId)
  }

  private val retreatOrderParser: Parser[RetreatOrder] = (unitKindParser.? ~> provinceIdParser <~ orderKindParser(Retreat)) ~ provinceIdParser ^^ {
    case provinceId ~ targetProvinceId => RetreatOrder(provinceId, targetProvinceId)
  }

  private val disbandOrderParser: Parser[DisbandOrder] = unitKindParser.? ~> provinceIdParser <~ orderKindParser(Disband) ^^ {
    case provinceId => DisbandOrder(provinceId)
  }

  private val buildOrderParser: Parser[BuildOrder] = (provinceIdParser <~ orderKindParser(Build)) ~ unitKindParser ^^ {
    case provinceId ~ unitKind => BuildOrder(provinceId, unitKind)
  }

  private val orderParser: Parser[Order] = holdOrderParser | moveOrderParser | convoyOrderParser | supportOrderParser | retreatOrderParser | disbandOrderParser | buildOrderParser

  private val ordersParser: Parser[List[Order]] = orderParser.*

  def parse(tokens: ListReader[OrderToken]): Option[List[Order]] = {
    ordersParser(tokens) match {
      case Success(orders, input) if input.atEnd =>
        Some(orders)
      case _ =>
        None
    }
  }

}
