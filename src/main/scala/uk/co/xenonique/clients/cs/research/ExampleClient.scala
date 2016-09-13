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
package uk.co.xenonique.clients.cs.research

import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}


/**
  * The type ExampleClient
  *
  * @author Peter Pilgrim (peter)
  */

object ExampleClient extends App {
  val client: Service[http.Request, http.Response] = Http.client.newService("www.scala-lang.org:80", "50 Cent")
  val request = http.Request(http.Method.Get, "/")
  request.host = "www.scala-lang.org"
  val response: Future[http.Response] = client(request)
  Await.result(response.onSuccess { rep: http.Response =>
    println("GET success: " + rep)
  })

}
