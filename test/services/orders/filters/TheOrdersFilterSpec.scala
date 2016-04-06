package services.orders.filters

import models.countries.CountryId._
import models.orders._
import models.provinces.ProvinceId._
import models.provinces.Provinces
import models.times.Phase._
import models.times.Season._
import models.times.Time
import models.units.{Army, Fleet, UnitKind}
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
      Vie -> (None, Some(Army(Austria)), None)
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
      Tri -> (None, Some(Army(Austria)), None)
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
      Tri -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Vie -> (None, Some(Army(England)), None)
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
      Vie -> (None, Some(Army(Austria)), None)
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
      Vie -> (None, Some(Army(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  // TODO "The orders filter" should "be able to keep valid (...) move orders during a diplomacy phase" in {}

  // TODO "The orders filter" should "be able to filter invalid (...) move orders during a diplomacy phase" in {}

  "The orders filter" should "be able to filter move orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(Army(Austria)), None)
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
      Vie -> (None, Some(Army(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  // TODO "The orders filter" should "be able to keep valid (...) convoy orders during a diplomacy phase" in {}

  // TODO "The orders filter" should "be able to filter invalid (...) convoy orders during a diplomacy phase" in {}

  "The orders filter" should "be able to filter convoy orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  // TODO "The orders filter" should "be able to keep valid (...) support orders during a diplomacy phase" in {}

  // TODO "The orders filter" should "be able to filter invalid (...) support orders during a diplomacy phase" in {}

  "The orders filter" should "be able to filter support orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Vie -> (None, Some(Army(England)), Some(Army(Austria)))
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
      Vie -> (None, Some(Army(England)), Some(Army(Austria)))
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
      Adr -> (None, Some(Fleet(England)), Some(Fleet(Austria)))
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
      Adr -> (None, Some(Fleet(England)), Some(Fleet(Austria)))
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
      Adr -> (None, Some(Fleet(England)), None)
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
      Adr -> (None, Some(Fleet(Austria)), Some(Fleet(England)))
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
      Tri -> (None, Some(Army(England)), Some(Army(Austria)))
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
      Tri -> (None, Some(Army(England)), Some(Army(Austria)))
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
      Adr -> (None, Some(Fleet(England)), Some(Fleet(Austria)))
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
      Adr -> (None, Some(Fleet(England)), Some(Fleet(Austria)))
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
      Tri -> (None, Some(Army(England)), Some(Army(Austria)))
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
      Tri -> (None, Some(Fleet(England)), Some(Fleet(Austria)))
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
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(England)), Some(Fleet(Austria)))
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
      Adr -> (None, Some(Fleet(England)), None)
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
      Adr -> (None, Some(Fleet(Austria)), Some(Fleet(England)))
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
      Adr -> (None, Some(Fleet(Austria)), None)
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
      Vie -> (Some(Austria), Some(Army(Austria)), None)
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
      Tri -> (None, Some(Fleet(Austria)), None)
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
      Adr -> (None, Some(Fleet(England)), None),
      Tri -> (None, Some(Fleet(Austria)), None)
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
        BuildOrder(Vie, UnitKind.Army)
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
        BuildOrder(Vie, UnitKind.Army)
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
        BuildOrder(Vie, UnitKind.Army)
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
        BuildOrder(Tri, UnitKind.Army)
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
        BuildOrder(Tri, UnitKind.Fleet)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (orders)
  }

  "The orders filter" should "be able to filter invalid (supply <= |units|) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tyr -> (None, Some(Army(Austria)), None),
      Tri -> (Some(Austria), None, None),
      Vie -> (Some(Austria), Some(Army(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, UnitKind.Army)
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
        BuildOrder(Tyr, UnitKind.Army)
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
        BuildOrder(Ber, UnitKind.Army)
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
        BuildOrder(Vie, UnitKind.Army)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

  "The orders filter" should "be able to filter invalid (own unit in province) build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None),
      Vie -> (Some(Austria), Some(Army(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, UnitKind.Army)
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
        BuildOrder(Vie, UnitKind.Fleet)
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
      Vie -> (Some(Austria), Some(Army(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Vie)
      )
    )

    theOrdersFilter(time, provinces, orders) should be (Orders.orders)
  }

}
