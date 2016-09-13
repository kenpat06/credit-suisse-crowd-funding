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
    


I used Scala 2.11.8 

I chose the following libraries

    * the brand new ScalaTest 3 release.    
    * Finagle, Twitter REST API library
    * Finatra, Fast, testable, Scala services built on TwitterServer and Finagle.


See `LICENSE.txt' for the license for this source code


The server is located in the Scala object `CrowdSourceServerApp`
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
    

Peter Pilgrim
September 2016
Web: http://www.xenonique.co.uk
Blog: http://www.xenonique.co.uk/blog 
Twitter: http://twitter.com/peter_pilgrim (please follow me!)
