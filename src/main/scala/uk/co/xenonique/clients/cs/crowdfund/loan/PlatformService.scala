package uk.co.xenonique.clients.cs.crowdfund.loan

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

/**
  * The type PlatformService
  *
  * @author Peter Pilgrim (peter)
  */
object PlatformService {

  private val requestIdentifier = new AtomicInteger(1001)
  private val loanIdentifier = new AtomicInteger(8006004)

  private var dataset = HashMap.empty[Int,LoanOfferBundle]


  def createLoanRequest(request: LoanRequest): Int = {
    val requestId = requestIdentifier.getAndAdd(2)
    dataset += requestId -> LoanOfferBundle( requestId )
    requestId
  }

  def createLoanOffer(loanRequestId: Int, amount: BigDecimal, apr: BigDecimal): Int = {

    dataset.get(loanRequestId) match {
      case Some(bundle) => {
        println(s"bundle=$bundle")
        val requestId = loanIdentifier.getAndAdd(2)
        requestId
      }
      case _ => {
        throw new NoSuchElementException("loan request does not exist")
      }
    }

  }

  def getCurrentOffer(loanRequestId: Int): LoanOfferBundle = ???

}