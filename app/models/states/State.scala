package models.states

import models.orders.Orders.Orders
import models.times.Time
import models.times.Time._

case class State(time: Time, orders: Option[Orders])

object State {

  val states: IndexedSeq[State] = times.map(State(_, None))

}
