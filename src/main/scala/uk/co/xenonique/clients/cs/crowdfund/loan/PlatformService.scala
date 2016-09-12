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

        val dealOffers = ListBuffer.empty[LoanOffer]
        var amountWithoutInterest: BigDecimal = 0
        var amountWithInterest: BigDecimal = 0
        for ( loanOffer <- loanOffers) {
          println(s"amount=$amount, loanOffer=${loanOffer}")
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
            println(s">>> borrowAmount=$borrowAmount, amountWithInterest=$amountWithInterest, amountWithoutInterest=${amountWithoutInterest}")
          }
        }
        val apr = (amountWithInterest / amountWithoutInterest - 1) * 100
        println(s">>>>>> final apr=$apr\n")
        CurrentOfferResponse( loanRequestId, amountWithoutInterest, apr, dealOffers.toList )
      }
      case _ => {
        throw new NoSuchElementException("loan request does not exist")
      }
    }
  }

}