package models.provinces

import org.scalatest.{FlatSpec, Matchers}

class ProvinceSpec extends FlatSpec with Matchers {

  "All land routes of land provinces" should "be continuous and bidirectional" in {
    Province.provinces.collect({
      case (_, province: LandProvince) =>
        province
    }).foreach({
      case LandProvince(id, _, routes) =>
        routes shouldNot contain (id)
        routes.map(Province.province).foreach({
          case Some(LandProvince(_, _, routes)) =>
            routes should contain (id)
          case Some(CoastProvince(_, _, routes, _)) =>
            routes should contain (id)
          case _ =>
            fail()
        })
    })
  }

  "All land routes and water ways of coast provinces" should "be continuous and bidirectional" in {
    Province.provinces.collect({
      case (_, province: CoastProvince) =>
        province
    }).foreach({
      case CoastProvince(id, _, routes, ways) =>
        routes shouldNot contain (id)
        ways shouldNot contain (id)

        routes.map(Province.province).foreach({
          case Some(LandProvince(_, _, routes)) =>
            routes should contain (id)
          case Some(CoastProvince(_, _, routes, _)) =>
            routes should contain (id)
          case _ =>
            fail()
        })

        ways.map(Province.province).foreach({
          case Some(CoastProvince(_, _, _, ways)) =>
            ways should contain (id)
          case Some(WaterProvince(_, ways)) =>
            ways should contain (id)
          case _ =>
            fail()
        })
    })
  }

  "All water ways of water provinces" should "be continuous and bidirectional" in {
    Province.provinces.collect({
      case (_, province: WaterProvince) =>
        province
    }).foreach({
      case WaterProvince(id, ways) =>
        ways shouldNot contain (id)
        ways.map(Province.province).foreach({
          case Some(CoastProvince(_, _, _, ways)) =>
            ways should contain (id)
          case Some(WaterProvince(_, ways)) =>
            ways should contain (id)
          case _ =>
            fail()
        })
    })
  }

}
