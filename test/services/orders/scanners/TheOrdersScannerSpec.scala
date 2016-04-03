package services.orders.scanners

import models.orders.OrderKind._
import models.provinces.ProvinceId._
import models.units.UnitKind._
import org.scalatest.{FlatSpec, Matchers}

class TheOrdersScannerSpec extends FlatSpec with Matchers {

  val theOrdersScanner: OrdersScanner = new TheOrdersScanner

  "The orders scanner" should "be able to scan unit kind tokens" in {
    theOrdersScanner("army") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner("Army") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner("ARMY") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner("a") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner("A") should be (Some(List(UnitKindOrderToken(Army))))

    theOrdersScanner("fleet") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner("Fleet") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner("FLEET") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner("f") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner("F") should be (Some(List(UnitKindOrderToken(Fleet))))
  }

  "The orders scanner" should "be able to scan province id tokens" in {
    theOrdersScanner("ber") should be (Some(List(ProvinceIdOrderToken(Ber))))
    theOrdersScanner("Ber") should be (Some(List(ProvinceIdOrderToken(Ber))))
    theOrdersScanner("BER") should be (Some(List(ProvinceIdOrderToken(Ber))))

    theOrdersScanner("stp-nc") should be (Some(List(ProvinceIdOrderToken(StpNc))))
    theOrdersScanner("Stp-Nc") should be (Some(List(ProvinceIdOrderToken(StpNc))))
    theOrdersScanner("STP-NC") should be (Some(List(ProvinceIdOrderToken(StpNc))))
  }

  "The orders scanner" should "be able to scan order kind tokens" in {
    theOrdersScanner("holds") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner("Holds") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner("HOLDS") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner("h") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner("H") should be (Some(List(OrderKindOrderToken(Hold))))

    theOrdersScanner("moves") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner("Moves") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner("MOVES") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner("m") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner("M") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner("-") should be (Some(List(OrderKindOrderToken(Move))))

    theOrdersScanner("convoys") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner("Convoys") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner("CONVOYS") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner("c") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner("C") should be (Some(List(OrderKindOrderToken(Convoy))))

    theOrdersScanner("supports") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner("Supports") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner("SUPPORTS") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner("s") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner("S") should be (Some(List(OrderKindOrderToken(Support))))

    theOrdersScanner("retreats") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner("Retreats") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner("RETREATS") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner("r") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner("R") should be (Some(List(OrderKindOrderToken(Retreat))))

    theOrdersScanner("disbands") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner("Disbands") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner("DISBANDS") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner("d") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner("D") should be (Some(List(OrderKindOrderToken(Disband))))

    theOrdersScanner("builds") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner("Builds") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner("BUILDS") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner("b") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner("B") should be (Some(List(OrderKindOrderToken(Build))))
  }

  "The orders scanner" should "be able to scan correct tokens" in {
    theOrdersScanner("") should be (Some(Nil))
    theOrdersScanner("f ber h") should be (Some(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))))
    theOrdersScanner("Army Stp-Nc - Ber") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber))))
    theOrdersScanner("Stp-Nc C Stp M Stp-Sc") should be (Some(List(ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(StpSc))))
    theOrdersScanner("ARMY BER SUPPORTS FLEET STP MOVES KIE") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Kie))))
    theOrdersScanner("ARMY\tBER\rRetreats  \n       stp") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Stp))))
    theOrdersScanner("ska\n\n\n\n\n\n\n\nd") should be (Some(List(ProvinceIdOrderToken(Ska), OrderKindOrderToken(Disband))))
  }

  "The orders scanner" should "not be able to scan wrong tokens" in {
    theOrdersScanner(null) should be (None)
    theOrdersScanner("This is garbage") should be (None)
    theOrdersScanner("ArmyHoldsBer") should be (None)
  }

}
