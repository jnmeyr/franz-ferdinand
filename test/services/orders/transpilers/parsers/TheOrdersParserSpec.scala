package services.orders.transpilers.parsers

import models.orders.OrderKind._
import models.orders._
import models.provinces.ProvinceId._
import models.units.UnitKind._
import org.scalatest.{FlatSpec, Matchers}
import services.orders.transpilers.scanners.{OrderKindOrderToken, ProvinceIdOrderToken, UnitKindOrderToken}

class TheOrdersParserSpec extends FlatSpec with Matchers {

  val theOrdersParser: OrdersParser = new TheOrdersParser

  "The orders parser" should "be able to parse hold orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold))) should be (Some(List(HoldOrder(Stp))))
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold))) should be (Some(List(HoldOrder(Stp))))
  }

  "The orders parser" should "be able to parse move orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber))) should be (Some(List(MoveOrder(Stp, List(Ber)))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(MoveOrder(Ber, List(Stp)))))

    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(MoveOrder(Stp, List(Ber, Ska)))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Spa))) should be (Some(List(MoveOrder(Ber, List(Stp, Spa)))))
  }

  "The orders parser" should "be able to parse convoy orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Stp, Ber, Ska))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Ber, Stp, Ska))))
    theOrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Ber, Stp, Ska))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska))) should be (Some(List(ConvoyOrder(Ber, Stp, Ska))))
  }

  "The orders parser" should "be able to parse support orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber))) should be (Some(List(SupportOrder(Stp, Ber))))

    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (Some(List(SupportOrder(Stp, Ber, Some(Stp)))))
  }

  "The orders parser" should "be able to parse disband orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Disband))) should be (Some(List(DisbandOrder(Ber))))
    theOrdersParser(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Disband))) should be (Some(List(DisbandOrder(Ber))))
  }

  "the orders parser" should "be able to parse retreat orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Ska))) should be (Some(List(RetreatOrder(Ber, Ska))))
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Ska))) should be (Some(List(RetreatOrder(Ber, Ska))))
  }

  "the orders parser" should "be able to parse waive orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Waive))) should be (Some(List(WaiveOrder(Ber))))
  }

  "the orders parser" should "be able to parse build orders" in {
    theOrdersParser(List(ProvinceIdOrderToken(Ber), OrderKindOrderToken(Build), UnitKindOrderToken(Fleet))) should be (Some(List(BuildOrder(Ber, Fleet))))
  }

  "The orders parser" should "be able to parse correct orders" in {
    theOrdersParser(Nil) should be (Some(Nil))
    theOrdersParser(List()) should be (Some(Nil))
    theOrdersParser(List(
      ProvinceIdOrderToken(Stp), OrderKindOrderToken(Hold),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ska),
      ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber),
      ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Move), ProvinceIdOrderToken(StpNc),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Disband),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Ska),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Waive),
      ProvinceIdOrderToken(Ber), OrderKindOrderToken(Build), UnitKindOrderToken(Fleet)
    )) should be (Some(List(HoldOrder(Stp), MoveOrder(Ber, List(Stp)), MoveOrder(Ber, List(Stp, Ska)), ConvoyOrder(Ber, Stp, Ska), SupportOrder(Stp, Ber), SupportOrder(Stp, Ber, Some(StpNc)), DisbandOrder(Ber), RetreatOrder(Ber, Ska), WaiveOrder(Ber), BuildOrder(Ber, Fleet))))
  }

  "The orders parser" should "not be able to parse wrong orders" in {
    // UnitKind
    theOrdersParser(List(UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Fleet))) should be (None)

    // ProvinceId
    theOrdersParser(List(ProvinceIdOrderToken(Stp))) should be (None)

    // OrderKind
    theOrdersParser(List(OrderKindOrderToken(Hold))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Convoy))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Support))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Disband))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Retreat))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Waive))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Build))) should be (None)

    // ProvinceId UnitKind
    theOrdersParser(List(ProvinceIdOrderToken(Stp), UnitKindOrderToken(Army))) should be (None)

    // UnitKind  ProvinceId
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)

    // OrderKind UnitKind
    theOrdersParser(List(OrderKindOrderToken(Hold), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Move), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Convoy), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Support), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Disband), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Retreat), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Waive), UnitKindOrderToken(Army))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Build), UnitKindOrderToken(Army))) should be (None)

    // OrderKind ProvinceId
    theOrdersParser(List(OrderKindOrderToken(Hold), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Move), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Disband), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Waive), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(OrderKindOrderToken(Build), ProvinceIdOrderToken(Stp))) should be (None)

    // UnitKind OrderKind
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Hold))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Convoy))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Support))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Disband))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Retreat))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Waive))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), OrderKindOrderToken(Build))) should be (None)

    // [UnitKind] ProviceId OrderKind
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Retreat))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Retreat))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build))) should be (None)

    // [UnitKind] ProvinceId OrderKind [UnitKind] ProvinceId
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Build), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp))) should be (None)

    // [UnitKind] ProvinceId OrderKind [UnitKind] ProvinceId OrderKind
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), UnitKindOrderToken(Army), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), UnitKindOrderToken(Army), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move))) should be (None)

    // [UnitKind] ProvinceId OrderKind [UnitKind] ProvinceId ProvinceId
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Convoy), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
    theOrdersParser(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Support), UnitKindOrderToken(Army), ProvinceIdOrderToken(Stp), ProvinceIdOrderToken(Ska))) should be (None)
  }

}
