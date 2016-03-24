package services.orders

import models.units.UnitKind
import UnitKind._
import models.orders.OrderKind
import models.orders.OrderKind._
import models.provinces.ProvinceId
import models.provinces.ProvinceId._
import org.scalatest.{FlatSpec, Matchers}

class OrdersScannerSpec extends FlatSpec with Matchers {

  "The orders scanner" should "be able to scan unit kind tokens" in {
    OrdersScanner("army") should be (Some(List(UnitKindOrderToken(Army))))
    OrdersScanner("Army") should be (Some(List(UnitKindOrderToken(Army))))
    OrdersScanner("ARMY") should be (Some(List(UnitKindOrderToken(Army))))
    OrdersScanner("a") should be (Some(List(UnitKindOrderToken(Army))))
    OrdersScanner("A") should be (Some(List(UnitKindOrderToken(Army))))

    OrdersScanner("fleet") should be (Some(List(UnitKindOrderToken(Fleet))))
    OrdersScanner("Fleet") should be (Some(List(UnitKindOrderToken(Fleet))))
    OrdersScanner("FLEET") should be (Some(List(UnitKindOrderToken(Fleet))))
    OrdersScanner("f") should be (Some(List(UnitKindOrderToken(Fleet))))
    OrdersScanner("F") should be (Some(List(UnitKindOrderToken(Fleet))))
  }

  "The orders scanner" should "be able to scan order kind tokens" in {
    OrdersScanner("holds") should be (Some(List(OrderKindOrderToken(Hold))))
    OrdersScanner("Holds") should be (Some(List(OrderKindOrderToken(Hold))))
    OrdersScanner("HOLDS") should be (Some(List(OrderKindOrderToken(Hold))))
    OrdersScanner("h") should be (Some(List(OrderKindOrderToken(Hold))))
    OrdersScanner("H") should be (Some(List(OrderKindOrderToken(Hold))))

    OrdersScanner("moves") should be (Some(List(OrderKindOrderToken(Move))))
    OrdersScanner("Moves") should be (Some(List(OrderKindOrderToken(Move))))
    OrdersScanner("MOVES") should be (Some(List(OrderKindOrderToken(Move))))
    OrdersScanner("m") should be (Some(List(OrderKindOrderToken(Move))))
    OrdersScanner("M") should be (Some(List(OrderKindOrderToken(Move))))
    OrdersScanner("-") should be (Some(List(OrderKindOrderToken(Move))))

    OrdersScanner("convoys") should be (Some(List(OrderKindOrderToken(Convoy))))
    OrdersScanner("Convoys") should be (Some(List(OrderKindOrderToken(Convoy))))
    OrdersScanner("CONVOYS") should be (Some(List(OrderKindOrderToken(Convoy))))
    OrdersScanner("c") should be (Some(List(OrderKindOrderToken(Convoy))))
    OrdersScanner("C") should be (Some(List(OrderKindOrderToken(Convoy))))

    OrdersScanner("supports") should be (Some(List(OrderKindOrderToken(Support))))
    OrdersScanner("Supports") should be (Some(List(OrderKindOrderToken(Support))))
    OrdersScanner("SUPPORTS") should be (Some(List(OrderKindOrderToken(Support))))
    OrdersScanner("s") should be (Some(List(OrderKindOrderToken(Support))))
    OrdersScanner("S") should be (Some(List(OrderKindOrderToken(Support))))

    OrdersScanner("retreats") should be (Some(List(OrderKindOrderToken(Retreat))))
    OrdersScanner("Retreats") should be (Some(List(OrderKindOrderToken(Retreat))))
    OrdersScanner("RETREATS") should be (Some(List(OrderKindOrderToken(Retreat))))
    OrdersScanner("r") should be (Some(List(OrderKindOrderToken(Retreat))))
    OrdersScanner("R") should be (Some(List(OrderKindOrderToken(Retreat))))

    OrdersScanner("disbands") should be (Some(List(OrderKindOrderToken(Disband))))
    OrdersScanner("Disbands") should be (Some(List(OrderKindOrderToken(Disband))))
    OrdersScanner("DISBANDS") should be (Some(List(OrderKindOrderToken(Disband))))
    OrdersScanner("d") should be (Some(List(OrderKindOrderToken(Disband))))
    OrdersScanner("D") should be (Some(List(OrderKindOrderToken(Disband))))
  }

  "The orders scanner" should "be able to scan province id tokens" in {
    OrdersScanner("ber") should be (Some(List(ProvinceIdOrderToken(Ber))))
    OrdersScanner("Ber") should be (Some(List(ProvinceIdOrderToken(Ber))))
    OrdersScanner("BER") should be (Some(List(ProvinceIdOrderToken(Ber))))

    OrdersScanner("stp-nc") should be (Some(List(ProvinceIdOrderToken(StpNc))))
    OrdersScanner("Stp-Nc") should be (Some(List(ProvinceIdOrderToken(StpNc))))
    OrdersScanner("STP-NC") should be (Some(List(ProvinceIdOrderToken(StpNc))))
  }

  "The orders scanner" should "be able to scan correct tokens" in {
    OrdersScanner("f ber h") should be (Some(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))))
    OrdersScanner("Army Stp-Nc - Ber") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber))))
    OrdersScanner("Stp-Nc C Stp M Stp-Sc") should be (Some(List(ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(StpSc))))
    OrdersScanner("ARMY BER SUPPORTS FLEET STP MOVES KIE") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Kie))))
    OrdersScanner("ARMY\tBER\rRetreats  \n       stp") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Stp))))
    OrdersScanner("ska\n\n\n\n\n\n\n\nd") should be (Some(List(ProvinceIdOrderToken(Ska), OrderKindOrderToken(Disband))))
  }

  "The orders scanner" should "not be able to scan wrong tokens" in {
    OrdersScanner(null) should be (None)
    OrdersScanner("") should be (None)
    OrdersScanner("This is garbage") should be (None)
    OrdersScanner("ArmyHoldsBer") should be (None)
  }

}