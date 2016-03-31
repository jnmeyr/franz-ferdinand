package services.stores

import javax.inject.{Inject, Singleton}

import com.ibm.couchdb.{CouchDb, CouchDbApi, TypeMapping}
import models.games.Game
import models.games.GameId._
import play.api.Configuration

import scalaz.{-\/, \/-}

@Singleton
class CouchDbStore @Inject() (configuration: Configuration) extends Store {

  val couchHost: String = configuration.getString("store.couchdb.host") getOrElse "127.0.0.1"
  val couchPort: Int = configuration.getInt("store.couchdb.port") getOrElse 5984
  val couchDb: CouchDb = CouchDb(couchHost, couchPort)

  val gameName: String = "games"
  val gameMapping: TypeMapping = TypeMapping(classOf[Game] -> "Game")
  val gameApi: CouchDbApi = couchDb.db(gameName, gameMapping)

  def getGame(gameId: GameId): Option[Game] = {
    val actions = for {
      docs <- gameApi.docs.getMany.queryIncludeDocs[Game]
    } yield docs.getDocsData

    actions.attemptRun match {
      case -\/(e) =>
        println(e)
      case \/-(a) =>
        a.foreach(println(_))
    }

    None
  }

  def setGame(gameId: GameId, game: Game) = {

  }

}
