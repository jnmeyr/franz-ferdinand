package models.provinces

import models.provinces.ProvinceId._
import org.scalatest.{FlatSpec, Matchers}

class ProvinceSpec extends FlatSpec with Matchers {

  "All provinces" should "have the correct provinceId" in {
    Province.provinces.foreach({
      case (provinceId, province) =>
        provinceId should be (province.provinceId)
    })
  }

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

  "Land provinces" should "not have a convoy" in {
    Province.isConvoy(Nil) should be (false)
    Province.isConvoy(List(Vie)) should be (false)
    Province.isConvoy(List(Vie, Tri)) should be (false)
    Province.isConvoy(List(Vie, Adr, Apu)) should be (false)
    Province.isConvoy(List(Vie, Adr, Ion, Gre)) should be (false)
  }

  "Coast provinces" should "be able to have convoys" in {
    Province.isConvoy(Nil) should be (false)
    Province.isConvoy(List(Tri)) should be (false)
    Province.isConvoy(List(Tri, Alb)) should be (false)
    Province.isConvoy(List(Tri, Adr, Apu)) should be (true)
    Province.isConvoy(List(Tri, Adr, Ion, Gre)) should be (true)
  }

  "Water provinces" should "not have a convoy" in {
    Province.isConvoy(Nil) should be (false)
    Province.isConvoy(List(Tys)) should be (false)
    Province.isConvoy(List(Tys, Rom)) should be (false)
    Province.isConvoy(List(Tys, Adr, Apu)) should be (false)
    Province.isConvoy(List(Tys, Adr, Ion, Gre)) should be (false)
  }

}
