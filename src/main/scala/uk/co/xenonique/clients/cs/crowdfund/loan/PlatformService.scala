package uk.co.xenonique.clients.cs.crowdfund.loan

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.immutable.HashMap
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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
        println(s"createLoanOffer() bundle=$bundle")
        loanOfferId
      }
      case _ => {
        throw new NoSuchElementException("loan request does not exist")
      }
    }

  }

  def getCurrentOffer(loanRequestId: Int): CurrentOfferResponse = {
    dataset.get(loanRequestId) match {
      case Some(bundle) => {
        println(s"getCurrentOffer() bundle=$bundle")
        val loanOffers = bundle.loanOffers.sortWith( (x,y) => x.apr < y.apr )
        println(s"loanOffers=$loanOffers")

        var amount = bundle.loanRequest.amount;

        var dealOffers = ListBuffer.empty[LoanOffer]
        var amountWithoutInterest: BigDecimal = 0
        var amountWithInterest: BigDecimal = 0
        for ( loanOffer <- loanOffers) {
          if ( amount > amount - loanOffer.amount ) {
            amountWithoutInterest += loanOffer.amount
            amountWithInterest += loanOffer.amount * (1.0 + loanOffer.apr / 100.0)
            amount = amount - loanOffer.amount
            dealOffers.append(LoanOffer( loanOffer.loanOfferId, loanOffer.amount, loanOffer.apr))
            println(s"amountWithInterest=$amountWithInterest, amountWithoutInterest=${amountWithoutInterest}")
          }
        }
        val apr = (amountWithInterest / amountWithoutInterest - 1) * 100
        println(s"final apr=$apr")
        CurrentOfferResponse( loanRequestId, amountWithoutInterest, apr, dealOffers.toList )
      }
      case _ => {
        throw new NoSuchElementException("loan request does not exist")
      }
    }
  }

}