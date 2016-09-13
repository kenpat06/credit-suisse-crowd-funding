package uk.co.xenonique.clients.cs.crowdfund.loan

import com.twitter.finagle.http.Status
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

/**
  * The type CrowdSourceServerFeatureTest
  *
  * @author Peter Pilgrim (peter)
  */

class CrowdSourceServerFeatureTest  extends FeatureTest {
  override val server = new EmbeddedHttpServer(new CrowdSourceServer)

  "Platform service" should  {
    "greet the world politely" in {
      server.httpGet(
        path = "/hello",
        andExpect = Status.Ok)
      /* ... */
    }
  }
}
