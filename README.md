# Credit Suisse crowd sourcing loan platform

This was my project to answer the [Credit Suisse](http://cs.com) crowd-sourcing loan platform 

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
    


I used Scala 2.11.8 and the brand new ScalaTest 3 release.
I also used Finagle, Twitter REST API library


See `LICENSE.txt' for the license for this source code



Peter Pilgrim
September 2016
Web: http://www.xenonique.co.uk
Blog: http://www.xenonique.co.uk/blog 
Twitter: http://twitter.com/peter_pilgrim (please follow me!)
