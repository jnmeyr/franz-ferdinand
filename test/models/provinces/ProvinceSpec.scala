package models.provinces

import org.scalatest.{Matchers, FlatSpec}

class ProvinceSpec extends FlatSpec with Matchers {

  "All land routes" should "be continuous and bidirectional" in {
    Province.provinces.foreach({
      case Province(id, _, routes, _) =>
        routes shouldNot contain (id)
        routes.map(Province.province).foreach({
          case Some(Province(_, _, routes, _)) =>
            routes should contain (id)
          case _ =>
            fail()
        })
    })
  }

  "All water ways" should "be continuous and bidirectional" in {
    Province.provinces.foreach({
      case Province(id, _, _, ways) =>
        ways shouldNot contain (id)
        ways.map(Province.province).foreach({
          case Some(Province(_, _, _, ways)) =>
            ways should contain (id)
          case _ =>
            fail()
        })
    })
  }

}
