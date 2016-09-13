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

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy

/**
  * The type CustomFinatraJacksonModule
  *
  * @author Peter Pilgrim (peter)
  */
object CustomFinatraJacksonModule  extends FinatraJacksonModule {

  // See also: http://twitter.github.io/finatra/user-guide/json/
  override val serializationInclusion = Include.NON_EMPTY

  override val propertyNamingStrategy = CamelCasePropertyNamingStrategy
}