package cluster

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.sharding.ShardRegion
import cluster.EntityActor.{CacheData, GetCachedData}

class EntityActor extends Actor with ActorLogging {
  var data: Option[String] = None

  override def receive: Receive = {
    case CacheData(id, state) =>
      log.info(s"Caching data for key: $id")
      data = Some(state)
      sender ! data
    case GetCachedData(id) =>
      log.info(s"Fetching cached data for key: $id")
      sender ! data.map(CacheData(id, _))
  }
}

object EntityActor {
  private val numberOfShards = 100

  val extractEntityId: ShardRegion.ExtractEntityId = {
    case msg@CacheData(id, _)  => (id.toString, msg)
    case msg@GetCachedData(id) => (id.toString, msg)
  }

  val extractShardId: ShardRegion.ExtractShardId = {
    case CacheData(id, _)  => (id.## % numberOfShards).toString
    case GetCachedData(id) => (id.## % numberOfShards).toString
  }

  def props: Props = Props(new EntityActor)
  case class GetCachedData(id: String)
  case class CacheData(id: String, value: String)

}