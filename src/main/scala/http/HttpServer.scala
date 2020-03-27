package http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer
import http.route.CombinedRoute

import scala.concurrent.Future

class HttpServer(implicit val actorSystem: ActorSystem, materializer: Materializer) {
  val combinedRoute = new CombinedRoute()
  def start: Future[Http.ServerBinding] = Http().bindAndHandle(combinedRoute.route, "0.0.0.0")
}
