package services.orders

import models.orders._
import models.orders.OrderKind._
import models.provinces.ProvinceId._
import models.units.UnitKind._
import org.scalatest.{FlatSpec, Matchers}

class OrdersParserSpec extends FlatSpec with Matchers {

  "The orders parser" should "be able to parse hold orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold))) should be (Some(List(HoldOrder(Stp))))
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold))) should be (Some(List(HoldOrder(Stp))))
  }

  "The orders parser" should "be able to parse move orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber))) should be (Some(List(MoveOrder(Stp, Ber))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(MoveOrder(Ber, Stp))))
  }

  "The orders parser" should "be able to parse convoy orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Stp, Ber, Ska))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Ber, Stp, Ska))))
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Ber, Stp, Ska))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Ber, Stp, Ska))))
  }

  "The orders parser" should "be able to parse support orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))

    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
  }

  "the orders parser" should "be able to parse retreat orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Ska))) should be (Some(List(RetreatOrder(Ber, Ska))))
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Ska))) should be (Some(List(RetreatOrder(Ber, Ska))))
  }

  "The orders parser" should "be able to parse disband orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Disband))) should be (Some(List(DisbandOrder(Ber))))
    OrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Disband))) should be (Some(List(DisbandOrder(Ber))))
  }

  "the orders parser" should "be able to parse build orders" in {
    OrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Build), UnitKindOrderToken(Fleet))) should be (Some(List(BuildOrder(Ber, Fleet))))

  }

  "The orders parser" should "be able to parse correct orders" in {
    OrdersParser(Nil) should be (Some(Nil))
    OrdersParser(List()) should be (Some(Nil))
    OrdersParser(List(
      ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska),
      ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber),
      ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(StpNc),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Ska),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Disband),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Build), UnitKindOrderToken(Fleet)
    )) should be (Some(List(HoldOrder(Stp), MoveOrder(Ber, Stp), ConvoyOrder(Ber, Stp, Ska), SupportOrder(Stp, Ber), SupportOrder(Stp, Ber, Some(StpNc)), RetreatOrder(Ber, Ska), DisbandOrder(Ber), BuildOrder(Ber, Fleet))))
  }

  "The orders parser" should "not be able to parse wrong orders" in {
    // UnitKind
    OrdersParser(List(UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Fleet))) should be (None)

    // ProvinceId
    OrdersParser(List(ProvinceIdOrderToken(Stp))) should be (None)

    // OrderKind
    OrdersParser(List(OrderKindOrderToken(Hold))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Convoy))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Support))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Retreat))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Disband))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Build))) should be (None)

    // ProvinceId UnitKind
    OrdersParser(List(ProvinceIdOrderToken(Stp), UnitKindOrderToken(Army))) should be (None)

    // UnitKind  ProvinceId
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)

    // OrderKind UnitKind
    OrdersParser(List(OrderKindOrderToken(Hold), UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Move), UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Convoy), UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Support), UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Retreat), UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Disband), UnitKindOrderToken(Army))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Build), UnitKindOrderToken(Army))) should be (None)

    // OrderKind ProvinceId
    OrdersParser(List(OrderKindOrderToken(Hold), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Disband), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(OrderKindOrderToken(Build), ProvinceIdOrderToken(Stp))) should be (None)

    // UnitKind OrderKind
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Hold))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Convoy))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Support))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Retreat))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Disband))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Build))) should be (None)

    // [UnitKind] ProviceId OrderKind
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Retreat))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Retreat))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build))) should be (None)

    // [UnitKind] ProvinceId OrderKind [UnitKind] ProvinceId
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)

    // [UnitKind] ProvinceId OrderKind [UnitKind] ProvinceId OrderKind
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), UnitKindOrderToken(Army), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), UnitKindOrderToken(Army), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)

    // [UnitKind] ProvinceId OrderKind [UnitKind] ProvinceId ProvinceId
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    OrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
  }

}
