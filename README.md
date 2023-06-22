# assessment-repo

Each order has six attributes namely, "Order ID","User Name","Order Time","Order Type","Quantity","Price".

Use Case : Orders Matching Engine

Criteria to match orders: Assumptions taken to Match orders

- Since we are working with Trading data and financial Point of view , we always consider the profitable order matches
- Same quantity orders
- Close a matched order
- queuing unmatched orders into order book
- Pick the first matched order in the remaining orders
- Best price match happens : the matching algorithm takes the Least BUY option and Highest SELL option while matching
- if two orders are BUY,SELL of same quantity - they are matched 
- While two orders of same Order Type are not matched for best price selection
- Result csv : has matched order iD s, Time stamp of order match, Quantity, Best Price

Approach followed:
Order Matching Engine is handled using the Map data structure where the order_book orders wait for finding an incoming order to match further.

There are two types of orders, BUY and SELL orders. A BUY order is for the price in USD you'll pay for GBP, SELL
	order is the price in USD you'll sell GBP for.
	
	Each order has the following fields:
	1. Order ID
	        - This is a unique ID in the file which is used to track an order
	2. User Name
	        - This is the user name of the user making the order
	3. Order Time
	        - This is the time, in milliseconds since Jan 1st 1970, the order was placed
	4. Order Type
	        - Either BUY or SELL
	5. Quantity
	        - The number of currency units you want to BUY or SELL
	6. Price
	        - The price you wish to sell for, this is in the lowest unit of the currency, i.e. for GBP it's in pence and for USD it's cents
	
	The matching engine must do the following:
	- It should match orders when they have the same quantity
	- If an order is matched it should be closed
	- If an order is not matched it should be kept on an "order book" and wait for an order which does match
	- When matching an order to the book the order should look for the best price
	- The best price for a BUY is the lowest possible price to BUY for
	- The best price for a SELL is the highest possible price to SELL for
	- You should always use the price from the "order book" when matching orders
	- When an order has matched you should record the IDs of both orders, the price, quantity and time of the match
	- If two orders match with the same price the first order is used
    - Orders won't have the same timestamp

    Spark-Submit :

    spark-submit --name "orderstab" --master yarn 
   --deploy-mode cluster 
   --driver-memory 1g 
   --executor-memory 1g
   --executor-cores 4 
   --class com.interview.orderstab 
   file:///C:/Users/subha/IdeaProjects/sparkfirst/target/sparkfirst-1.0-SNAPSHOT
  
