package uk.co.xenonique.clients.cs.crowdfund.loan

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.server.TwitterServer

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

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add(new HelloController)
    router.add(new CreateLoanRequestController)
    router.add(new CreateLoanOfferController)
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


/**
  * Start the server with the program arguments: -admin.port=:10000
  * http://localhost:10000/admin/threads
  *
NOTES:
  [1] https://github.com/shekhargulati/52-technologies-in-2016/blob/master/01-finatra/README.md
  [2] http://twitter.github.io/finatra/user-guide/getting-started/#examples
  [3] http://www.slideshare.net/samkiller/high-performance-rpc-with-finagle


  */