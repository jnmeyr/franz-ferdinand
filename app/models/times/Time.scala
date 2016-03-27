package models.times

import models.times.Phase._
import models.times.Season._
import play.api.libs.json.{Format, Json}

case class Time(year: Int, season: Season, phase: Phase)

object Time {

  implicit val timeFormat: Format[Time] = Json.format[Time]

  val times: IndexedSeq[Time] = for {
    year   <- Range(1901, 1921)
    season <- List(Spring, Fall)
    phase  <- if (season == Spring) List(Diplomacy, Resolution) else List(Diplomacy, Resolution, Adjustment)
  } yield Time(year, season, phase)

}
