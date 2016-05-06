package models.orders

import models.countries.CountryId._

object Orders {

  type Orders = Map[CountryId, List[Order]] // TODO why not set of orders?

  val orders: Orders = Map(
    Austria -> Nil,
    England -> Nil,
    France  -> Nil,
    Germany -> Nil,
    Italy   -> Nil,
    Russia  -> Nil,
    Turkey  -> Nil
  )

}
