# WeBillv2.0
Second Draft of WeBill in MVC
Team Blue
Coded in MVC

**Extra Credit**

• Proper request of device permissions for 5.1 and above. (Camera and Device Location Access)

• Using/Reusing Fragments with interfaces for communication.

• Use of RecyclerView or CardView

• Good use of Menus.

• Targeting multiple locales. (Supports English and Chinese System language)

**Before you run the app**
1. Make sure you have correct local.properties in the project. 
2. Add ```MAPS_API_KEY=AIzaSyAc2nPhKlEoOLKy8s07IDBPKcNO7dTAC5U``` to local.properties
3. To make sure the receipt scanner work, your device's system must be no more than Android 11 (Not able to call camera activity if your device is Android12)

**Using WeBill**
1. To register an account, click "Sign Up?" button on home screen. (Do not enter your real personal info.)
2. After registration, you will be asked to enter a credit card info. Just use a fake card No.: 4242 4242 4242 4242. Then enter any date that's not expiring, random CVV, and random zip
3. Now you will see our beautiful app.

**Friend menu**
1. You need to add a friend first to make sure you can split bill with others.
2. To add a friend, simply click "+ add friend" button at the top right corner of "Friends" menu. You are able to search other users in our services. Here we provide (```harsh```, ```jack```, and ```lisa```) as sample friend account name for you to search.
3. You new friend will be shown as "Balance: $0.00". And you can see the summary of you balance once you have split bills with your friend.

**My Bills menu**
1. All of your added bills will be shown here, and you can filter your bills by years, in the "Account" menu

**Add Bills menu**
Method1: You can manually input infomation for your bill. We provide autofilling for the address, and date picker to enter the date.

Method2: We also implemented AI receipt scanning where all of the information could be filled automatically, by simply click the scan button and take a nice picture of your receipt!

```!!!important!!!```After taking a picture of your receipt, please wait about 10-20 seconds, our system is working hard to transfer the information, do not go to other pages or enter any information in the Bill Information card.

Once AI scanner fill the result, you are free to modify any information that is inaccurate (We guarantee the accuracy is 90%+ if you take a clear picture and the receipt is clean)

**Split Bill Page**
1. After having all of your bill information, click enter, and you will go to Split Bill Page where you can see your bill conclusion.
2. You can still modify Activity Name, Address, and Date at this page.
3. If you are using AI bill scanner, click "Items" and you will see your bill's item details
4. Enter the person who paid the bill, you should having at least one friend to enter users, and you will see a drop down menu of users for you to select
5. Next, add the person who you want to split with, you don't have to pay to yourself if you are the person who paid the bill
6. Make sure there is no remaining amount left.
7. Once finish entering the bill conclusions, click "Save". Bill information will go to "My Bills" and "Activity" menu

**Activity menu**

Here, you will able to see your spending activities by showing the location of your bill, we record everybill you spent at anywhere. Click a pin on the map, you will see a tag about your spending activities at this location. Longpress the tag, it shows you the details of this tag. You are able to see each bills you spent at this place.

**Account menu**

You are able to Reset Password, Modify Phone Number, Contact Us, and select filter for "My Bills" menu.

