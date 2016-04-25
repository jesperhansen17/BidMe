# BidMe

This repository is for BidMe which is an project in the course Application Development for Android at Malmö Högskola.
This application consist of a Android application and a Java application. Using the Android application the user are able to interact and alter the Java application. The Java application is the view and do only show information to the user.

We are a group of five. And the group members are Petter Olsson, Mario Abou-raad, Karl Philip Karlsted, Kevin Kha and Jesper Hansen


## Introduction
The concept with the auction application is to simplify and automate the auction procedure such as handling the items that are up for sale and collecting and managing the bids from interested buyers. This is done with the help of a public screen and second screen that is an application for Android smartphones.

### Description
When the user shows up at the auction the user must download the Android application because the auction it controlled from the application. First the user needs to add themselves to the auction room by enter a username and some kind of identification that the user is in the room where the auction will be held. Once logged in the user have the choice to either add an item to the auction or just choose to bid on the item they want. Once the auction starts the first added item is shown on the main screen with a clock counting down. When the clock reaches zero the highest bidder is the winner of the bidding. To prevent that a user can put in a bid in the last second we add 10 seconds to the clock when a user add a new bid.  

### Android Application 
The Android application will contain several fragments. When the user starts up the application a login GUI is presented where the user add themselves to the bidding room. The main GUI can the user choose to add an item to the auction or choose to bid to a current item. Below is an concept image of the bidding GUI and the GUI for adding items. In the bidding GUI a clock counting down and the current price for the item is shown. The user have the choice to increase and decrease the bid and also confirm the bid, the amount for decreasing and increasing is not set yet. In the adding GUI the user can add item to the auction, we will start implementing that the user only will be able to add books. But we will add more items in future implementation.

![Android Screen](https://github.com/FriendlyAppDA401A/BidMe/blob/master/images/bidme_item.png)

### Public Screen
The public screen and also the main screen is used as an information container. The main screen should preferably be showed on a big such as a projector. The main screen will show information about the object that is currently being put for sale. Such as what kind of object it is, who is the seller, description of the item, starting price. In the center of the main screen we will show a picture of the item such as an cover of book or movie. Below the picture information about the highest bid and a clock ticking down towards zero. The screen will also show a bid history of all the bids on that particular item.
Below is an concept picture of how the app can look (not final).

![Main Screen](https://github.com/FriendlyAppDA401A/BidMe/blob/master/images/mainScreen.jpg)


   
