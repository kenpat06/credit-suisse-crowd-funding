# Credit Suisse crowd sourcing loan platform

This was my project to answer the [Credit Suisse](https://www.credit-suisse.com) crowd-sourcing loan platform 

The challenge

    We have been asked to build a crowd-sourcing loan platform, which allows borrowers to take out
    loans that are made of multiple loans from lenders.
    Users who want to borrow money (borrowers) will enter a loan request, which consists of the amount they want to borrow 
    and the duration they want to borrow the money for. Users want to lend money (lenders) will enter the amount of money
    they are willing lend towards this loan and the interest rate (APR) they require.
    
    The system should provide the following functionality via the API
    
    1.  Create loan request - given an tamount and duration, return an identifier for the loan request
    
    2.  Create loan offer - given a loan request identifier, amount and interest rate, return an identifier for the loan offer
    
    3.  Get current offer - given a loan request identifier, return currently offered and the combined interest rate. 
    The current offer should be the one providing the lowest combined interest rate. Partial loan offers used (i.e if the total
    offered is greater than the lon request, the current offer should be capped at the loan request amount and can use part of 
    a loan offer)
    


## Requirements

I used Scala 2.11.8 

I chose the following libraries

  * the brand new ScalaTest 2.2.6 release.    
  * Finagle, Twitter REST API library
  * Finatra, Fast, testable, Scala services built on TwitterServer and Finagle.


I choose *Finagle* for the project, because Play Framework would have been overkill. 
Finagle can be embedded into straightforward standalone application, just like 
*DropWizard* or *Spring Boot*. Finagle is also Twitter's baby. 
They use it in production with their other open source products *Finatra* and *TwitterServer*.
I also heard about Finatra at a open space conference recently and this was
the perfect test case to kick the tires. I like it, but it is fiddly and I am
sure it will improve, because a big corporation will keep on backing it.

In fact, the REST API landscape for Scala pretty much in a confusion. 
You can choose many frameworks. Here is a [Quora question from 2011](https://www.quora.com/Which-Scala-framework-is-the-best-for-REST-API-development). 
The point is that there is no de-facto clear winner or Scala standard for REST API, 
so I would advice you to choose very wisely subject ability, scope and maintainability.


See `LICENSE.txt' for the license for this source code

NB: Finatra 6.38.0 is certified against Scala 2.2.6. I chatted to one of the Finatra maintainers who said that they have yet to build against ScalaTest 3.0.0 yet.


## Building the software

The software was built with SBT.

Start with a clean build, execute SBT like so:

    > sbt clean


Compile the entire software with the following command:

    > sbt compile


To execute the unit tests in the project, use this next command:

    > sbt test
    

To run the server from SBT use the following command:

    > sbt run 



To change the administration web service port number, use the following argument 

    > sbt "run -admin.port=:10000"
    
Note: the double quotes around the SBT argument are very important! SBT is fiddly.

There is a bug in [SBT somewhere and launch programs with arguments from the command line](http://stackoverflow.com/questions/13309738/sbt-run-with-cli-arguments-from-shell). 
I found the following command, worked for me on Mac OS X

    > sbt "run-main uk.co.xenonique.clients.cs.crowdfund.loan.CrowdSourceServerApp  -admin.port=:10000"
    

For my project, I worked with [IntelliJ IDEA 2016.2.4](https://www.jetbrains.com/idea/)



## Running the server

The server is located in the Scala object `uk.co.xenonique.clients.cs.crowdfund.loan.CrowdSourceServerApp`
The connect port number is 8080.
The default administration port is 9990 for Finagle applications. I had clash with another application using that port.
The administration port can be configured to alternate port number with the command line argument `-admin.port=:10000`

Here some useful CURL commands:

    curl -i http://localhost:8080/hello

Print a basic welcome message


    curl -H "Content-Type: application/json" -i -X POST -d '{"amount": 1000,"duration":500}' http://localhost:8080/loan/request

As a borrower, this CURL command creates a loan request and returns a loan request identifier as JSON. 
 
Suppose the loan request id is 1001, we can use it: 

    curl -H "Content-Type: application/json" -i -X POST -d '{"loanRequestId": 1001, "amount": 300,"apr":5.0}' http://localhost:8080/loan/offer

The service returns the unique loan offer id.

And then another lender creates a second offer, so execute the following:

    curl -H "Content-Type: application/json" -i -X POST -d '{"loanRequestId": 1001, "amount": 500,"apr":7.5}' http://localhost:8080/loan/offer

Now if we want to get the latest offer combined bundle, then we can issue the following request:

    curl -H "Content-Type: application/json" -i -X GET  http://localhost:8080/loan/current/1001

We simply supply the loan request id as a path parameter to the REST service. 
This loan application returns a JSON object with all combined bundle offers. 
The output should be something like this:

    {
        "loanRequestId": 1001,
        "amount": 600,
        "apr": 8,
        "loanOffers": [
            {
                "loanOfferId": 8006004,
                "amount": 100,
                "apr": 5
            },
            {
                "loanOfferId": 8006006,
                "amount": 500,
                "apr": 8.6
            }
        ]
    }

The service provides computed loan amount and bundled APR and it also helpfully supplies a 
break down of the individual loan offers in the deal.
    
    
## TODO ##

  * Resolve dependency snafu with Finatra feature test or use Apache HttpClient as a stop gap
  



Any issue, please contact me below. Cheers!


Peter Pilgrim
September 2016

  * Web: http://www.xenonique.co.uk
  * Blog: http://www.xenonique.co.uk/blog 
  * Mentorship: http://www.xenonique.co.uk/mentorship
  * Consulting: http://www.xenonique.co.uk/consulting
  * Training: http://www.xenonique.co.uk/training
  * Twitter: http://twitter.com/peter_pilgrim (please follow me!)

