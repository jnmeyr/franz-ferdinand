package services.orders.analyzers.reducers

import models.countries.CountryId._
import models.orders._
import models.provinces.ProvinceId._
import models.provinces.Provinces
import models.times.Phase._
import models.times.Season._
import models.times.Time
import models.units.UnitKind._
import models.units.{ArmyUnit, FleetUnit}
import org.scalatest.{FlatSpec, Matchers}

class TheOrdersReducerSpec extends FlatSpec with Matchers {

  val theOrdersReducer = new TheOrdersReducer

  "The orders reducer" should "not be able to reduce orders during a diplomacy phase" in {
    val time = Time(1901, Fall, Diplomacy)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersReducer(time, provinces, orders) should be (orders)
  }

  "The orders reducer" should "not be able to reduce orders during a resolution phase" in {
    val time = Time(1901, Fall, Resolution)
    val provinces = Provinces.emptyProvinces
    val orders = Orders.orders

    theOrdersReducer(time, provinces, orders) should be (orders)
  }

  "The orders reducer" should "able to keep too little build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Rum -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Ser -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to keep build orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Rum -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Ser -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Army),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Army),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to reduce too many build orders by unit kind during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Rum -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Ser -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army),
        BuildOrder(Tri, Army)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Army),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to reduce too many build orders by province id during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Rum -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Ser -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Tri, Fleet),
        BuildOrder(Vie, Army)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        BuildOrder(Vie, Army),
        WaiveOrder(Tri)
      )
    ))
  }

  "The orders reducer" should "able to enlarge too little disband orders by province distance during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Pie -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Pie),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to enlarge too little disband orders by unit kind during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Tyr -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to enlarge too little disband orders by province id during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Tyr -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Gal),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to keep disband orders during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Pie -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (orders)
  }

  "The orders reducer" should "able to reduce too many disband orders by province distance during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Pie -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr),
        DisbandOrder(Pie),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Pie),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to reduce too many disband orders by unit kind during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Pie -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr),
        DisbandOrder(Gal),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Adr),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

  "The orders reducer" should "able to reduce too many disband orders by province id during an adjustment phase" in {
    val time = Time(1901, Fall, Adjustment)
    val provinces = Provinces.emptyProvinces ++ Map(
      Vie -> (Some(Austria), None,                     None),
      Tri -> (Some(Austria), None,                     None),
      Adr -> (Some(Austria), Some(FleetUnit(Austria)), None),
      Gal -> (Some(Austria), Some(ArmyUnit(Austria)),  None),
      Tyr -> (Some(Austria), Some(ArmyUnit(Austria)),  None)
    )
    val orders = Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Tyr),
        DisbandOrder(Gal),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    )

    theOrdersReducer(time, provinces, orders) should be (Orders.orders ++ Map(
      Austria -> List(
        DisbandOrder(Gal),
        WaiveOrder(Tri),
        WaiveOrder(Vie)
      )
    ))
  }

}
