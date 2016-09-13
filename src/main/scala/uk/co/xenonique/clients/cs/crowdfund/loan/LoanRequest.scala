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


/**
  * The type LoanRequest
  *
  * @author Peter Pilgrim (peter)
  */

case class LoanRequest( val amount: BigDecimal, val duration: Int )

case class LoanOffer( val loanOfferId: Int, val amount: BigDecimal, val apr: BigDecimal)

case class LoanOfferRequest(val loanRequestId: Int, val amount: BigDecimal, val apr: BigDecimal)

case class LoanOfferBundle(val loanRequestId: Int, var loanRequest: LoanRequest, var loanOffers: List[LoanOffer] = List())

case class CurrentOfferResponse(val loanRequestId: Int, val amount: BigDecimal, val apr: BigDecimal, var loanOffers: List[LoanOffer] = List() )