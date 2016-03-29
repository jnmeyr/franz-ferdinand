package models.states

import models.countries.CountryId.CountryId
import models.orders.Order
import models.times.Time
import Time._

case class State(time: Time, orders: Option[Map[CountryId, List[Order]]])

object State {

  val states: IndexedSeq[State] = times.map(State(_, None))

}
