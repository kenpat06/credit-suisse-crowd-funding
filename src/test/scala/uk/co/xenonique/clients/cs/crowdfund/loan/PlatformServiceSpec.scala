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
    PlatformService.createLoanRequest( LoanRequest( 1000, 1000 )) should be > (0)
  }

  "Platform service" should "create loan request from example 2" in {
    PlatformService.createLoanRequest( LoanRequest( 1000, 1000 )) should be > (0)
  }

  "Platform service" should "create multiple loan requests with unique identifiers" in {
    val request1 = PlatformService.createLoanRequest( LoanRequest( 100, 1000 ))
    val request2 = PlatformService.createLoanRequest( LoanRequest( 300, 500 ))
    val request3 = PlatformService.createLoanRequest( LoanRequest( 500, 250 ))

    request1 should not be (request2)
    request2 should not be (request3)
  }

  "Platform service" should "create loan offer from example 1" in {
    val requestId = PlatformService.createLoanRequest( LoanRequest( 100, 1000 ))

    val offerId1 = PlatformService.createLoanOffer( requestId, 100, 5)
    offerId1 should be > (0)

    val offerId2 = PlatformService.createLoanOffer( requestId, 500, 8.6 )
    offerId2 should be > (0)
  }

  "Platform service" should "retrieve current offer from example 1" in {
    val requestId = PlatformService.createLoanRequest( LoanRequest( 1000, 1000 ))

    val offerId1 = PlatformService.createLoanOffer( LoanOfferRequest( requestId, 100, 5) )
    val offerId2 = PlatformService.createLoanOffer( LoanOfferRequest( requestId, 500, 8.6 ))

    val currentOffer = PlatformService.getCurrentOffer(requestId)
    currentOffer.loanRequestId should be (requestId)
    currentOffer.amount should be (600)
    currentOffer.apr should be (8)
    currentOffer.loanOffers should be ( List( LoanOffer( offerId1, 100,5), LoanOffer( offerId2, 500, 8.6)))
  }


  "Platform service" should "retrieve current offer from example 2" in {
    val requestId = PlatformService.createLoanRequest( LoanRequest( 1000, 1000 ))

    val offerId1 = PlatformService.createLoanOffer( requestId, 100, 5 )
    val offerId2 = PlatformService.createLoanOffer( requestId, 600, 6 )
    val offerId3 = PlatformService.createLoanOffer( requestId, 600, 7 )
    val offerId4 = PlatformService.createLoanOffer( requestId, 600, 8.2 )

    val currentOffer = PlatformService.getCurrentOffer(requestId)
    currentOffer.loanRequestId should be (requestId)
    currentOffer.amount should be (1000)
    currentOffer.apr should be (6.2)
    currentOffer.loanOffers should be ( List( LoanOffer( offerId1, 100,5), LoanOffer( offerId2, 600, 6), LoanOffer( offerId3, 300, 7)))
  }

  "Platform service" should "fail to create loan offer with unknown loan request id " in {
    val requestId = Math.random().toInt

    val caught =
      intercept[NoSuchElementException] {
        PlatformService.createLoanOffer( LoanOfferRequest( requestId, 100, 5) )
      }
    assert(caught.getMessage.contains("does not exist"))
  }

}
