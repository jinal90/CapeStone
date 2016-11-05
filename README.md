
# CapeStone Stage2 - Feast Menu
A revolutionary new Food Ordering Menu app for particular restaurant - easy ordering menu, with description of food items with picture and price. The app will work on concept of Google Nearby API. Here the device with the restaurant manager/waiter acts as subscriber and device at table acts as publisher.
What happens when customer visits any restaurant? Customer sits at table, looks at menu and waits for waiter to take their order. And if customer wants to add items to order then they need to call waiter again. Even at time of ordering food, customer is not used to all dishes so they usually ask waiter about taste and ingredients of food item before ordering any new item.
This app will provide a seamless food ordering experience at restaurant. Devices will be kept at tables. App will have menu of food items served at that particular restaurant. Customers can glance at the menu, see details like ingredients, taste and type of food items with pictures and directly order from device. Once the customer confirms order, the details of order are received by device that is with restaurant manager/waiter along with table number. No paper, no confusion, no delays and at the end a consolidated check for customer is ready. That’s it, the app does it all.

#### ScreenShots

![Setup Screen](/screens/Screen1.png?raw=true "Setup Screen")
![Restaurant Menu Screen](/screens/Screen2.png?raw=true "Restaurant Menu Screen")
![Table Wise Order Screen](/screens/Screen3.png?raw=true "Table Wise Order Screen")
![Nearby Connections Permission Screen](/screens/NearByPermission.png?raw=true "Nearby Connections Permission Screen")

#### Tech

This App uses a number of open source projects to work properly:

* [Butterknife] - Butter Knife by jakewharton for view binding
* [Picasso] - Picasso allows for hassle-free image loading in your application—often in one line of code!
* [Google GSON] - To convert Json to java pojos or vice versa.
* [Google Nearby] - Nearby Messages API to publish and subscribe messages to proximity devices.
* [Firebase AdMob] - AdMob by Google is a way to improve app monetization with targeted, in-app advertising.
* [Clans FAB] - To display animated Floating Action Menu.
* Material Design Support, AppbarLayout, Floating Action Buttons, RecyclerView, Cards and CoordinatorLayout.


### Basic Functionality
This applications works on concept of Google Nearby Messaging API. 
It is basically designed for two types of users.

#### Restaurant Customer

* A customer at Food Feasta Restaurant can book a table, view food menu, order food items, call waiter, ask for drinking water view current order and generate cheque/bill using this app.
* A customer acts as a publisher of messages (App sends Food orders placed by customer at table to restaurant manager)

#### Restaurant Manager

* A manager at Food Feasta Restaurant can view orders currently placed at tables by customers and table availability using this app.
* A manager acts as a subscriber of messages (App receives Food orders placed by customer at table and displays it to restaurant manager)


### Build and Run Requirements

#### Generate API Key for AdMob

Follow steps mentioned on below website to generated AdMob App Key for initialization and Ad unit id for AdView..
URL : https://firebase.google.com/docs/admob/android/quick-start


* Oracle JDK 1.8
* Support Android 4.0.3 and Above (API 15)




### Tools used to develop
* Android Studio 2.2

[BUTTERKNIFE]: <http://jakewharton.github.io/butterknife/>
[Google GSON]: <https://github.com/google/gson>
[Google Nearby]: <https://developers.google.com/nearby/>
[Firebase AdMob]: <https://firebase.google.com/docs/admob/>
[Picasso]: <http://square.github.io/picasso>
[Clans FAB]: <https://github.com/Clans/FloatingActionButton>
