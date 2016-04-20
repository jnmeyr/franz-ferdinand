package models.countries

import org.scalatest.{FlatSpec, Matchers}

class CountrySpec extends FlatSpec with Matchers {

  "All countries" should "have the correct countryId" in {
    Country.countries.foreach({
      case (countryId, country) =>
        countryId should be (country.countryId)
    })
  }

  "All provinces of a country" should "be unique to this country" in {
    Country.countries.foreach({
      case (countryId, Country(_, provinces)) =>
        provinces.foreach({
          provinceId =>
            Country.countries.filterKeys(_ != countryId).values.flatMap(_.provinces) shouldNot contain (provinceId)
        })
    })
  }

}
