package services.orders

import scala.language.implicitConversions
import scala.util.parsing.input.{NoPosition, Position, Reader}

class ListReader[T >: Null <: AnyRef](as: List[T]) extends Reader[T] {

  override def first: T = as.headOption.orNull

  override def atEnd: Boolean = as.isEmpty

  override def pos: Position = NoPosition

  override def rest: Reader[T] = new ListReader(as.tail)

}

object ListReader {

  implicit def list2listReader[T >: Null <: AnyRef](list: List[T]): ListReader[T] = new ListReader[T](list)

}