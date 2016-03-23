package services.orders

import models.orders._
import models.orders.OrderKind._
import models.provinces.ProvinceId._
import models.units.UnitKind._
import org.scalatest.{FlatSpec, Matchers}

class OrdersParserSpec extends FlatSpec with Matchers {

  "The orders parser" should "be able to parse hold orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold))) should be
      Some(List(HoldOrder(Stp)))
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold))) should be
      Some(List(HoldOrder(Stp)))

    OrdersParser(List(ProvinceIdOrderToken(Stp))) should be
      None
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be
      None
  }

  "The orders parser" should "be able to parse move orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber))) should be
      Some(List(MoveOrder(Stp, Ber)))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be
      Some(List(MoveOrder(Ber, Stp)))

    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be
      None
    OrdersParser(List(OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be
      None
    OrdersParser(List(ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ber))) should be
      None
  }

  "The orders parser" should "be able to parse convoy orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(ConvoyOrder(Stp, MoveOrder(Ber, Ska))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(ConvoyOrder(Stp, MoveOrder(Ber, Ska))))
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(ConvoyOrder(Stp, MoveOrder(Ber, Ska))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(ConvoyOrder(Stp, MoveOrder(Ber, Ska))))

    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      None
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ska))) should be
      None
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be
      None
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be
      None
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      None
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be
      None
  }

  "The orders parser" should "be able to parse support orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))) should be
      Some(List(SupportOrder(Stp, HoldOrder(Ber))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))) should be
      Some(List(SupportOrder(Stp, HoldOrder(Ber))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))) should be
      Some(List(SupportOrder(Stp, HoldOrder(Ber))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))) should be
      Some(List(SupportOrder(Stp, HoldOrder(Ber))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be
      Some(List(SupportOrder(Stp, MoveOrder(Ber, Stp))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be
      Some(List(SupportOrder(Stp, MoveOrder(Ber, Stp))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be
      Some(List(SupportOrder(Stp, MoveOrder(Ber, Stp))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be
      Some(List(SupportOrder(Stp, MoveOrder(Ber, Stp))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(SupportOrder(Stp, ConvoyOrder(Ber, MoveOrder(StpNc, Ska)))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(SupportOrder(Stp, ConvoyOrder(Ber, MoveOrder(StpNc, Ska)))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(SupportOrder(Stp, ConvoyOrder(Ber, MoveOrder(StpNc, Ska)))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be
      Some(List(SupportOrder(Stp, ConvoyOrder(Ber, MoveOrder(StpNc, Ska)))))

    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support))) should be
      None
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber))) should be
      None
  }

}