package models.orders

object OrderKind extends Enumeration {

  type OrderKind = Value

  val Hold    = Value("H")
  val Move    = Value("M")
  val Convoy  = Value("C")
  val Support = Value("S")
  val Retreat = Value("R")
  val Disband = Value("D")
  val Build   = Value("B")
  val Waive   = Value("W")

}
