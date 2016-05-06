package services.orders.analyzers.mappers

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

class TheOrdersMapperSpec extends FlatSpec with Matchers {

  val theOrdersMapper = new TheOrdersMapper

  "The orders mapper" should "not be able to map unnecessary orders during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to map hold orders for unordered units during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders

    theOrdersMapper(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    ))
  }

  "The orders mapper" should "be able to map hold orders for multiordered units during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Tri)), MoveOrder(Vie, List(Bud))
      )
    )

    theOrdersMapper(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    ))
  }

  "The orders mapper" should "be able to keep hold orders for units during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        HoldOrder(Vie)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to keep move orders for units during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, Some(ArmyUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        MoveOrder(Vie, List(Tri))
      )
    )

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to keep convoy orders for units during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        ConvoyOrder(Adr, Tri, Apu)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to keep support orders for units during a diplomacy phase" in {
    val time = Time(1901, Spring, Diplomacy)
    val provinces = Provinces.emptyProvinces ++ Map(
      Adr -> (None, Some(FleetUnit(Austria)), None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        SupportOrder(Adr, Tri)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "not be able to map unnecessary orders during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to map disband orders for unordered dislodged units during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, None, Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders

    theOrdersMapper(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Vie)
      )
    ))
  }

  "The orders mapper" should "be able to map disband orders for multiordered dislodged units during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (None, None, Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Vie, Tri), RetreatOrder(Vie, Bud)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Vie)
      )
    ))
  }

  "The orders mapper" should "be able to keep disband orders for dislodged units during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie ->(None, None, Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Vie)
      )
    )

    theOrdersMapper(time, provinces, orders) should be(Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Vie)
      )
    ))
  }

  "The orders mapper" should "be able to keep retreat orders for dislodged units during a resolution phase" in {
    val time = Time(1901, Spring, Resolution)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie ->(None, None, Some(ArmyUnit(Austria)))
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Vie, Bud)
      )
    )

    theOrdersMapper(time, provinces, orders) should be(Orders.orders ++ Map(
      Austria -> List(
        RetreatOrder(Vie, Bud)
      )
    ))}

  "The orders mapper" should "not be able to map unnecessary orders during an adjustment phase" in {
    val time = Time(1901, Spring, Adjustment)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to map waive orders for unordered provinces during an adjustment phase" in {
    val time = Time(1901, Spring, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None, None)
    )
    val orders = Orders.orders

    theOrdersMapper(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders mapper" should "be able to map waive orders for multiordered provinces during an adjustment phase" in {
    val time = Time(1901, Spring, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Army), BuildOrder(Tri, Fleet)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri)
      )
    ))
  }

  "The orders mapper" should "be able to keep waive orders for provinces during an adjustment phase" in {
    val time = Time(1901, Spring, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

  "The orders mapper" should "be able to keep build orders for provinces during an adjustment phase" in {
    val time = Time(1901, Spring, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Tri -> (Some(Austria), None, None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Fleet)
      )
    )

    theOrdersMapper(time, provinces, orders) should be (orders)
  }

}
