package services.orders.scanners

import models.orders.OrderKind._
import models.provinces.ProvinceId
import models.units.UnitKind._

import scalaz.Scalaz._

class TheOrdersScanner extends OrdersScanner {

  private def scanUnitKindToken(word: String): Option[UnitKindOrderToken] = {
    if ("army".equalsIgnoreCase(word) || "a".equalsIgnoreCase(word)) {
      Some(UnitKindOrderToken(Army))
    } else if ("fleet".equalsIgnoreCase(word) || "f".equalsIgnoreCase(word)) {
      Some(UnitKindOrderToken(Fleet))
    } else {
      None
    }
  }

  private def scanProvinceIdToken(word: String): Option[ProvinceIdOrderToken] = {
    ProvinceId.values.filter(_.toString.equalsIgnoreCase(word)) match {
      case values if values.size == 1 =>
        Some(ProvinceIdOrderToken(values.head))
      case _ =>
        None
    }
  }

  private def scanOrderKindToken(word: String): Option[OrderKindOrderToken] = {
    if ("holds".equalsIgnoreCase(word) || "h".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Hold))
    } else if ("moves".equalsIgnoreCase(word) || "m".equalsIgnoreCase(word) || "-".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Move))
    } else if ("convoys".equalsIgnoreCase(word) || "c".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Convoy))
    } else if ("supports".equalsIgnoreCase(word) || "s".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Support))
    } else if ("retreats".equalsIgnoreCase(word) || "r".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Retreat))
    } else if ("disbands".equalsIgnoreCase(word) || "d".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Disband))
    } else if ("builds".equalsIgnoreCase(word) || "b".equalsIgnoreCase(word)) {
      Some(OrderKindOrderToken(Build))
    } else {
      None
    }
  }

  private def scanToken(word: String): Option[OrderToken] = {
    scanUnitKindToken(word) orElse scanProvinceIdToken(word) orElse scanOrderKindToken(word)
  }

  def scan(string: String): Option[List[OrderToken]] = {
    Option(string) match {
      case Some(_) if string.isEmpty =>
        Some(Nil)
      case Some(_) =>
        string.split("\\s+").toList.map(scanToken).sequence[Option, OrderToken]
      case None =>
        None
    }
  }

}
