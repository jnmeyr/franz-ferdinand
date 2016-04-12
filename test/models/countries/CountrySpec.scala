package models.countries

import org.scalatest.{FlatSpec, Matchers}

class CountrySpec extends FlatSpec with Matchers {

  "All provinces of a country" should "be unique to this country" in {
    Country.countries.foreach({
      case (countryId, Country(provinces)) =>
        provinces.foreach({
          provinceId =>
            Country.countries.filterKeys(_ != countryId).values.flatMap(_.provinces) shouldNot contain (provinceId)
        })
    })
  }

}
