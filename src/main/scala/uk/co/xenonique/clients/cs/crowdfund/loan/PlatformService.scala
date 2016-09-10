package uk.co.xenonique.clients.cs.crowdfund.loan

import java.time.temporal.TemporalAmount

/**
  * The type PlatformService
  *
  * @author Peter Pilgrim (peter)
  */

object PlatformService {
  def createLoanRequest(request: LoanRequest): Int = ???

  def createLoanOffer(loanRequestId: Int, amount: BigDecimal, apr: BigDecimal): Int = ???

  def getCurrentOffer(loanRequestId: Int): LoanOfferBundle = ???

}