package uk.co.xenonique.clients.cs.crowdfund.loan

import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

import scala.collection.immutable.List

/**
  * The type PlatformServiceSpec
  *
  * @author Peter Pilgrim (peter)
  */

/**
  * The {@link PlatformServiceSpec} validates the operation and behaviour of @{link PlatformServiceSpec}
  */
@RunWith(classOf[JUnitRunner])
class PlatformServiceSpec extends FlatSpec with Matchers {

  "Platform service" should "create loan request from example 1" in {
    PlatformService.createLoanRequest( LoanRequest( 100, 1000 )) should be > (0)
  }

  "Platform service" should "create loan request from example 2" in {
    PlatformService.createLoanRequest( LoanRequest( 1000, 1000 )) should be > (0)
  }

  "Platform service" should "create loan offer from example 1" in {
    val requestId = PlatformService.createLoanRequest( LoanRequest( 100, 1000 ))

    val offerId1 = PlatformService.createLoanOffer( requestId, 100, 5)
    offerId1 should be > (0)

    val offerId2 = PlatformService.createLoanOffer( requestId, 500, 8.6 )
    offerId2 should be > (0)
  }


  "Platform service" should "retrieve current offer from example 1" in {
    val requestId = PlatformService.createLoanRequest( LoanRequest( 100, 1000 ))

    PlatformService.createLoanOffer( requestId, 100, 5 )
    PlatformService.createLoanOffer( requestId, 500, 8.6 )

    val bundle = PlatformService.getCurrentOffer(requestId)
    bundle.loanRequestId should be (requestId)
    bundle.loanOffers should be ( List( LoanOffer( 100,5), LoanOffer( 500, 8.6)))
  }
}
