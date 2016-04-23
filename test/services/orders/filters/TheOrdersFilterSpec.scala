package services.orders.filters

import models.countries.CountryId._
import models.orders._
import models.provinces.ProvinceId._
import models.provinces.Provinces
import models.times.Phase._
import models.times.Season._
import models.times.Time
import models.units._
import models.units.UnitKind._
import org.scalatest.{FlatSpec, Matchers}

class TheOrdersFilterSpec extends FlatSpec with Matchers {

  val theOrdersFilter: OrdersFilter = new TheOrdersFilter

  "The orders filter" should "be able to handle empty orders" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (own army in land province) hold orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (own army in coast province) hold orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (own fleet in coast province) hold orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (own fleet in water province) hold orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (no unit in province) hold orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign unit in province) hold orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(England)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter hold orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter hold orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid (army from land to land province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army from land to coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army from coast to land province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Vie))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army from coast to coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Alb))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army from coast to coast province over water provinces) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Adr, Apu))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet from coast to coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Alb))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet from coast to water province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Adr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet from water to coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Adr, List(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet from water to water province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Adr, List(Ion))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (no unit in province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign unit in province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(England)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from coast to water province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Adr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from coast to coast province over land province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Bud, Rum))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from coast to coast province over coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Ven, Apu))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from land to unreachable land province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Ukr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from land to unreachable coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Ven))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from coast to unreachable land province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Gal))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from coast to unreachable coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Gre))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from coast to coast province over unreachable water provinces) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Ion, Alb))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from coast to land province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Bud))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from coast to coast province over water provinces) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Adr, Apu))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from coast to unreachable coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Gre))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from coast to unreachable water province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Tri, List(Ion))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from water to unreachable coast province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Adr, List(Gre))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from water to unreachable water province) move orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Adr, List(Eas))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter move orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter move orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (no unit in province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign unit in province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(England)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in land province from land to land province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Vie, Bud, Gal)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in land province from land to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Vie, Bud, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in land province from coast to land province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Vie, Tri, Bud)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in land province from coast to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tyr -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Tyr, Tri, Ven)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in coast province from land to land province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Tri, Tyr, Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in coast province from land to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Tri, Tyr, Ven)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in coast province from coast to land province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Tri, Ven, Tyr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army in coast province from coast to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Tri, Ven, Alb)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in coast province from coast to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Alb -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Alb, Tri, Gre)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in coast province from coast to water province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Alb -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Alb, Tri, Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in coast province from water to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Alb -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Alb, Ion, Gre)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in coast province from water to water province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Alb -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Alb, Adr, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in water province from coast to unreachable coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Nap)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in water province from coast to water province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in water province from water to coast province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Ion, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in water province from water to water province) convoy orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ion -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Ion, Adr, Aeg)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter convoy orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter convoy orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid (army from land province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Gal)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army from coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army to land province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Bul, Some(Ser))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army to coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Ser, Some(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet from coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ven -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Ven, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet from water province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Ven, Some(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet to coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ven -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Ven, Adr, Some(Tri))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet to water province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ven -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Ven, Tri, Some(Adr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (no unit in province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Ven, Tri, Some(Adr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign unit in province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ven -> (None, Some(FleetUnit(England)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Ven, Tri, Some(Adr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from unreachable land province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, War)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from unreachable coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Alb)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army from water province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Tri, Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army to unreachable land province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Pru, Some(War))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army to unreachable coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Bud -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Bud, Ser, Some(Alb))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army to water province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Tri, Alb, Some(Adr))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from land province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Bud)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from unreachable coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Gre)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet from unreachable water province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Aeg)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet to land province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Tri, Some(Bud))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet to unreachable coast province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Alb, Some(Gre))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet to unreachable water province) support orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Ion, Some(Aeg))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter support orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter support orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter retreat orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid (army to land target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(England)), Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Vie, Tyr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army to coast target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(England)), Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Vie, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet to coast target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), Some(FleetUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Tri)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet to water target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), Some(FleetUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (no dislodged unit in province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign dislodged unit in province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), Some(FleetUnit(England)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army to unreachable land target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(England)), Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Tri, Gal)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army to unreachable coast target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(England)), Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Tri, Gre)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet to unreachable coast target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), Some(FleetUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Gre)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet to unreachable water target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), Some(FleetUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Aeg)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (army to water target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(ArmyUnit(England)), Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Tri, Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet to land target province) retreat orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (None, Some(FleetUnit(England)), Some(FleetUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Tri, Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter retreat orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter disband orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid disband orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), Some(FleetUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (no dislodged unit in province) disband orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign dislodged unit in province) disband orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), Some(FleetUnit(England)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid disband orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (supply >= |units|) disband orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (no unit in province) disband orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, None, None),
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (foreign unit in province) disband orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(England)), None),
      Tri -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter build orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter build orders during a resolution phase" in {
    val time = Time(1901, Fall, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid (army in land province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (army in coast province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to keep valid (fleet in coast province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Fleet)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (supply <= |units|) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tyr -> (None, Some(ArmyUnit(Austria)), None),
      Tri -> (Some(Austria), None, None),
      Vie -> (Some(Austria), Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (province has no supply) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tyr -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tyr, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (province is no home province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ber -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Ber, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (province is foreign) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(England), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (own unit in province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None),
      Vie -> (Some(Austria), Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (fleet in land province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Fleet)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter waive orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter waive orders during a resolution phase" in {
    val time = Time(1901, Fall, Resolution)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Adr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to keep valid waive orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (province has no supply) waive orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tyr -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tyr)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (province is no home province) waive orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Ber -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Ber)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (province is foreign) waive orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(England), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (own unit in province) waive orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

}
