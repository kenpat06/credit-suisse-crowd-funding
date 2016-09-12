package uk.co.xenonique.clients.cs.crowdfund.loan


/**
  * The type LoanRequest
  *
  * @author Peter Pilgrim (peter)
  */

case class LoanRequest( val amount: BigDecimal, val duration: Int )

case class LoanOffer( val amount: BigDecimal, val apr: BigDecimal)

case class LoanOfferBundle(val loanRequestId: Int, val loanOffers: List[LoanOffer] = List())

case class CurrentOfferResponse(val loanRequestId: Int, val amount: BigDecimal, val apr: BigDecimal )