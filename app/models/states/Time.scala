package models.states

import models.states.Phase._
import models.states.Season._
import play.api.libs.json.Json

case class Time(year: Int, season: Season, phase: Phase)

object Time {

  implicit val timeFormat = Json.format[Time]

  val times: IndexedSeq[Time] = for {
    year   <- Range(1901, 1921)
    season <- List(Spring, Fall)
    phase  <- if (season == Spring) List(Diplomacy, Resolution) else List(Diplomacy, Resolution, Adjustment)
  } yield Time(year, season, phase)

}
