/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2016 by Peter Pilgrim, Milton Keynes, P.E.A.T UK LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Creative Commons 3.0
 * Non Commercial Non Derivation Share-alike License
 * https://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/
package uk.co.xenonique.clients.cs.crowdfund.loan

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.twitter.finagle.http.{HttpMuxer, Request, Response, Status}
import com.twitter.finagle.Service
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future, Time}

/**
  * The type CrowdSourceServerApp
  *
  * @author Peter Pilgrim (peter)
  */
object CrowdSourceServerApp extends CrowdSourceServer

/**
  * The type CrowdSourceServer
  *
  * @author Peter Pilgrim (peter)
  */
class CrowdSourceServer extends HttpServer with TwitterServer {

  override protected def defaultFinatraHttpPort: String = ":8080"
  // No longer exists: override protected def defaultTracingEnabled: Boolean = false
  override protected def defaultHttpServerName: String = "Crowd Source Loan Platform"

  override def jacksonModule = CustomFinatraJacksonModule

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add(new HelloController)
    router.add(new CreateLoanRequestController)
    router.add(new CreateLoanOfferController)
    router.add(new GetCurrentOfferController)
  }

  val counter = statsReceiver.counter("requests_counter")

  val what = flag("what", "hello", "String to return")

  val service = new Service[Request, Response] {
    def apply(request: Request) = {
      log.debug("Received a request at " + Time.now)
      counter.incr()
      val response = Response(request.version, Status.Ok)
      response.contentString = what() + "\n"
      Future.value(response)
    }
  }

}



class HelloController extends Controller {
  get("/hello") { request: Request =>
    "Crowd source loan platform says hello"
  }
}

class CreateLoanRequestController extends Controller {
  post("/loan/request") { loanRequest: LoanRequest =>
     Map( "id" -> PlatformService.createLoanRequest(loanRequest))
  }
}

class CreateLoanOfferController extends Controller {
  post("/loan/offer") { loanOfferRequest: LoanOfferRequest =>
    Map( "id" -> PlatformService.createLoanOffer(loanOfferRequest))
  }
}

class GetCurrentOfferController extends Controller {
  get("/loan/current/:id") { request: Request =>
    PlatformService.getCurrentOffer( request.params("id").toInt )
  }
}

/**
  * Start the server with the program arguments: -admin.port=:10000
  * http://localhost:10000/admin/threads
  *
NOTES:
  [1] https://github.com/shekhargulati/52-technologies-in-2016/blob/master/01-finatra/README.md
  [2] http://twitter.github.io/finatra/user-guide/getting-started/#examples
  [3] http://www.slideshare.net/samkiller/high-performance-rpc-with-finagle
  [4] http://twitter.github.io/finatra/user-guide/build-new-http-server/controller.html
  [5] http://www.slideshare.net/stevecosenza/finatra-sf-scala-47656933
  [6] https://twitter.github.io/twitter-server/Features.html#http-admin-interface
  */