package models.orders

import models.countries.CountryId._

object Orders {

  val orders: Map[CountryId, List[Order]] = Map(
    Austria -> Nil,
    England -> Nil,
    France  -> Nil,
    Germany -> Nil,
    Italy   -> Nil,
    Russia  -> Nil,
    Turkey  -> Nil
  )

}
