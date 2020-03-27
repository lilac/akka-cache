import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.{ActorMaterializer, Materializer}
import cluster.ClusterManager
import common.Constant.ActorSystemName
import http.HttpServer

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

trait Setup {
//  val config = ConfigFactory.parseFile(new File("conf/application.conf")).withFallback(ConfigFactory.load())
  implicit val actorSystem: ActorSystem = ActorSystem(ActorSystemName)
  implicit val materializer: Materializer = Materializer(actorSystem)
  val loggingAdapter: LoggingAdapter = Logging(actorSystem, "Setup")
}

object Setup extends Setup {
  def startHttpServer(): Unit = {
    implicit val ec: ExecutionContext = actorSystem.dispatcher
    val httpServer = new HttpServer()
//    actorSystem.logConfiguration()
    httpServer.start.onComplete {
      case Success(s) => loggingAdapter.info(s"Http server started at: [${s.localAddress}]")
      case Failure(f) => loggingAdapter.error(f, "Http server can't be started]")
    }
  }

  def startClusterManager(): Unit = new ClusterManager().setupCluster()
}