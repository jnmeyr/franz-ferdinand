package services.orders.interpreters

import models.orders._
import models.provinces.Provinces
import models.provinces.ProvinceId._
import models.countries.CountryId._
import models.times.Time
import models.times.Season._
import models.times.Phase._
import models.units.{UnitKind, Fleet, Army}
import org.scalatest.{FlatSpec, Matchers}

class TheOrdersInterpreterSpec extends FlatSpec with Matchers {

  val theOrdersInterpreter: OrdersInterpreter = new TheOrdersInterpreter

  "The orders interpreter" should "be able to interprete no orders" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid hold orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> Provinces(Vie, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid hold orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> Provinces(Vie, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid move orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> Provinces(Vie, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid move orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> Provinces(Vie, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid convoy orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria))),
      Tri -> Provinces(Tri, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu),
        MoveOrder(Tri, List(Adr, Apu))
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid convoy orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria))),
      Tri -> Provinces(Tri, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu),
        MoveOrder(Tri, List(Adr, Apu))
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid support orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria))),
      Tri -> Provinces(Tri, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Tri),
        HoldOrder(Tri)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid support orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria))),
      Tri -> Provinces(Tri, None, Some(Army(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Tri),
        HoldOrder(Tri)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid retreat orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid retreat orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Adr, Ion)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid disband orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> Provinces(Adr, None, Some(Fleet(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid build orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Adr, UnitKind.Army)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Adr, UnitKind.Army)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid waive orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Adr)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

  "The orders interpreter" should "be able to ignore valid waive orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Adr)
      )
    )

    theOrdersInterpreter(time, provinces, orders) should be (provinces)
  }

}
