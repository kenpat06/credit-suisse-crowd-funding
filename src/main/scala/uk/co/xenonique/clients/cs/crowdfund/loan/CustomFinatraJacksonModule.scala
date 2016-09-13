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