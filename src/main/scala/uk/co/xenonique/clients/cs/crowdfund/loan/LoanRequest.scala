package uk.co.xenonique.clients.cs.crowdfund.loan

/**
  * The type LoanRequest
  *
  * @author Peter Pilgrim (peter)
  */

case class LoanRequest( val amount: BigDecimal, val duration: Int )

case class LoanOfferRequest( val loanRequestId: Int, val amount: BigDecimal, val apr: BigDecimal)

case class CurrentOfferRequest( val loanRequestId: Int, val amount: BigDecimal, val apr: BigDecimal )