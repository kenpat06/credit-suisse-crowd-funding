package uk.co.xenonique.clients.cs.crowdfund

import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}


/**
  * The type ExampleClient
  *
  * @author Peter Pilgrim (peter)
  */

object ExampleClient extends App {
  val client: Service[http.Request, http.Response] = Http.client.newService("www.scala-lang.org:80", "50 Cent")
  val request = http.Request(http.Method.Get, "/")
  request.host = "www.scala-lang.org"
  val response: Future[http.Response] = client(request)
  Await.result(response.onSuccess { rep: http.Response =>
    println("GET success: " + rep)
  })

}
