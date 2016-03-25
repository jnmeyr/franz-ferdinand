package models.states

import models.orders.Order
import models.states.Time._

case class State(time: Time, orders: Option[List[Order]])

object State {

  val states: IndexedSeq[State] = times.map(State(_, None))

}
