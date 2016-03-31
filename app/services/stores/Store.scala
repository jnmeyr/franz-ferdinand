package services.stores

import com.google.inject.ImplementedBy
import models.games.Game
import models.games.GameId.GameId

@ImplementedBy(classOf[CouchDbStore])
trait Store {

  def getGame(gameId: GameId): Option[Game]

  def setGame(gameId: GameId, game: Game)

}
