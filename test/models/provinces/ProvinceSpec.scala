package models.provinces

import org.scalatest.{FlatSpec, Matchers}

class ProvinceSpec extends FlatSpec with Matchers {

  "All land routes of land provinces" should "be continuous and bidirectional" in {
    Province.provinces.collect({
      case (_, province: LandProvince) =>
        province
    }).foreach({
      case LandProvince(provinceId, _, routes) =>
        routes shouldNot contain (provinceId)
        routes.map(Province.province).foreach({
          case LandProvince(_, _, routes) =>
            routes should contain (provinceId)
          case CoastProvince(_, _, routes, _) =>
            routes should contain (provinceId)
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
      case CoastProvince(provinceId, _, routes, ways) =>
        routes shouldNot contain (provinceId)
        ways shouldNot contain (provinceId)

        routes.map(Province.province).foreach({
          case LandProvince(_, _, routes) =>
            routes should contain (provinceId)
          case CoastProvince(_, _, routes, _) =>
            routes should contain (provinceId)
          case _ =>
            fail()
        })

        ways.map(Province.province).foreach({
          case CoastProvince(_, _, _, ways) =>
            ways should contain (provinceId)
          case WaterProvince(_, ways) =>
            ways should contain (provinceId)
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
      case WaterProvince(provinceId, ways) =>
        ways shouldNot contain (provinceId)
        ways.map(Province.province).foreach({
          case CoastProvince(_, _, _, ways) =>
            ways should contain (provinceId)
          case WaterProvince(_, ways) =>
            ways should contain (provinceId)
          case _ =>
            fail()
        })
    })
  }

}
