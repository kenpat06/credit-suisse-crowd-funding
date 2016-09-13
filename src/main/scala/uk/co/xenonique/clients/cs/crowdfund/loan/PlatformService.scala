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

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.immutable.HashMap
import scala.collection.mutable.ListBuffer

/**
  * The type PlatformService
  *
  * @author Peter Pilgrim (peter)
  */
object PlatformService {


  private val requestIdentifier = new AtomicInteger(1001)
  private val loanIdentifier = new AtomicInteger(8006004)

  private var dataset = HashMap.empty[Int,LoanOfferBundle]


  def createLoanRequest(loanRequest: LoanRequest): Int = {
    val requestId = requestIdentifier.getAndAdd(2)
    dataset += requestId -> LoanOfferBundle( requestId, loanRequest )
    requestId
  }

  def createLoanOffer(loanRequestId: Int, amount: BigDecimal, apr: BigDecimal): Int = {
    dataset.get(loanRequestId) match {
      case Some(bundle) => {
        val loanOfferId = loanIdentifier.getAndAdd(2)
        bundle.loanOffers = bundle.loanOffers ::: List( LoanOffer(loanOfferId, amount, apr) )
        loanOfferId
      }
      case _ => {
        throw new NoSuchElementException(s"loan request id: $loanRequestId does not exist")
      }
    }
  }

  def createLoanOffer(loanOfferRequest: LoanOfferRequest): Int =
    createLoanOffer( loanOfferRequest.loanRequestId, loanOfferRequest.amount, loanOfferRequest.apr)

  def getCurrentOffer(loanRequestId: Int): CurrentOfferResponse = {
    dataset.get(loanRequestId) match {
      case Some(bundle) => {
        val loanOffers = bundle.loanOffers.sortWith( (x,y) => x.apr < y.apr )

        var amount = bundle.loanRequest.amount;

        val dealOffers = ListBuffer.empty[LoanOffer]
        var amountWithoutInterest: BigDecimal = 0
        var amountWithInterest: BigDecimal = 0
        for ( loanOffer <- loanOffers) {
          if ( amount > 0 ) {
            val borrowAmount =
              if ( amount - loanOffer.amount > 0 )
                loanOffer.amount
              else
                amount
            amountWithoutInterest += borrowAmount
            amountWithInterest += borrowAmount * (1.0 + loanOffer.apr / 100.0)
            amount = amount - borrowAmount
            dealOffers.append(LoanOffer( loanOffer.loanOfferId, borrowAmount, loanOffer.apr))
          }
        }
        val apr = (amountWithInterest / amountWithoutInterest - 1) * 100
        CurrentOfferResponse( loanRequestId, amountWithoutInterest, apr, dealOffers.toList )
      }
      case _ => {
        throw new NoSuchElementException(s"loan request id: $loanRequestId does not exist")
      }
    }
  }

}