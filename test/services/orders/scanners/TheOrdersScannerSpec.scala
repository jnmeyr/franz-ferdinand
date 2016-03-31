package services.orders.scanners

import models.orders.OrderKind._
import models.provinces.ProvinceId._
import models.units.UnitKind._
import org.scalatest.{FlatSpec, Matchers}

class TheOrdersScannerSpec extends FlatSpec with Matchers {

  val theOrdersScanner: OrdersScanner = new TheOrdersScanner

  "The orders scanner" should "be able to scan unit kind tokens" in {
    theOrdersScanner.scan("army") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner.scan("Army") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner.scan("ARMY") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner.scan("a") should be (Some(List(UnitKindOrderToken(Army))))
    theOrdersScanner.scan("A") should be (Some(List(UnitKindOrderToken(Army))))

    theOrdersScanner.scan("fleet") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner.scan("Fleet") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner.scan("FLEET") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner.scan("f") should be (Some(List(UnitKindOrderToken(Fleet))))
    theOrdersScanner.scan("F") should be (Some(List(UnitKindOrderToken(Fleet))))
  }

  "The orders scanner" should "be able to scan province id tokens" in {
    theOrdersScanner.scan("ber") should be (Some(List(ProvinceIdOrderToken(Ber))))
    theOrdersScanner.scan("Ber") should be (Some(List(ProvinceIdOrderToken(Ber))))
    theOrdersScanner.scan("BER") should be (Some(List(ProvinceIdOrderToken(Ber))))

    theOrdersScanner.scan("stp-nc") should be (Some(List(ProvinceIdOrderToken(StpNc))))
    theOrdersScanner.scan("Stp-Nc") should be (Some(List(ProvinceIdOrderToken(StpNc))))
    theOrdersScanner.scan("STP-NC") should be (Some(List(ProvinceIdOrderToken(StpNc))))
  }

  "The orders scanner" should "be able to scan order kind tokens" in {
    theOrdersScanner.scan("holds") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner.scan("Holds") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner.scan("HOLDS") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner.scan("h") should be (Some(List(OrderKindOrderToken(Hold))))
    theOrdersScanner.scan("H") should be (Some(List(OrderKindOrderToken(Hold))))

    theOrdersScanner.scan("moves") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner.scan("Moves") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner.scan("MOVES") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner.scan("m") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner.scan("M") should be (Some(List(OrderKindOrderToken(Move))))
    theOrdersScanner.scan("-") should be (Some(List(OrderKindOrderToken(Move))))

    theOrdersScanner.scan("convoys") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner.scan("Convoys") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner.scan("CONVOYS") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner.scan("c") should be (Some(List(OrderKindOrderToken(Convoy))))
    theOrdersScanner.scan("C") should be (Some(List(OrderKindOrderToken(Convoy))))

    theOrdersScanner.scan("supports") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner.scan("Supports") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner.scan("SUPPORTS") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner.scan("s") should be (Some(List(OrderKindOrderToken(Support))))
    theOrdersScanner.scan("S") should be (Some(List(OrderKindOrderToken(Support))))

    theOrdersScanner.scan("retreats") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner.scan("Retreats") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner.scan("RETREATS") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner.scan("r") should be (Some(List(OrderKindOrderToken(Retreat))))
    theOrdersScanner.scan("R") should be (Some(List(OrderKindOrderToken(Retreat))))

    theOrdersScanner.scan("disbands") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner.scan("Disbands") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner.scan("DISBANDS") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner.scan("d") should be (Some(List(OrderKindOrderToken(Disband))))
    theOrdersScanner.scan("D") should be (Some(List(OrderKindOrderToken(Disband))))

    theOrdersScanner.scan("builds") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner.scan("Builds") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner.scan("BUILDS") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner.scan("b") should be (Some(List(OrderKindOrderToken(Build))))
    theOrdersScanner.scan("B") should be (Some(List(OrderKindOrderToken(Build))))
  }

  "The orders scanner" should "be able to scan correct tokens" in {
    theOrdersScanner.scan("") should be (Some(Nil))
    theOrdersScanner.scan("f ber h") should be (Some(List(UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Hold))))
    theOrdersScanner.scan("Army Stp-Nc - Ber") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Move), ProvinceIdOrderToken(Ber))))
    theOrdersScanner.scan("Stp-Nc C Stp M Stp-Sc") should be (Some(List(ProvinceIdOrderToken(StpNc), OrderKindOrderToken(Convoy), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(StpSc))))
    theOrdersScanner.scan("ARMY BER SUPPORTS FLEET STP MOVES KIE") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Support), UnitKindOrderToken(Fleet), ProvinceIdOrderToken(Stp), OrderKindOrderToken(Move), ProvinceIdOrderToken(Kie))))
    theOrdersScanner.scan("ARMY\tBER\rRetreats  \n       stp") should be (Some(List(UnitKindOrderToken(Army), ProvinceIdOrderToken(Ber), OrderKindOrderToken(Retreat), ProvinceIdOrderToken(Stp))))
    theOrdersScanner.scan("ska\n\n\n\n\n\n\n\nd") should be (Some(List(ProvinceIdOrderToken(Ska), OrderKindOrderToken(Disband))))
  }

  "The orders scanner" should "not be able to scan wrong tokens" in {
    theOrdersScanner.scan(null) should be (None)
    theOrdersScanner.scan("This is garbage") should be (None)
    theOrdersScanner.scan("ArmyHoldsBer") should be (None)
  }

}
