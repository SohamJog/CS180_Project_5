# CS180_Project_5

## Instructions - How to run and compile:
- Step 1 - Clone or download the source code from this repository
- Step 2 - Run the GUIMain method 
- Step 3 - You will see directions printed out on the GUI once you run the GUIMain method. Simply follow the directions and choose the options you want to proceed!

## Submissions:
- [Code]: 
- [Report]: 
- [Presentation]:
## Class Explanations:
    
### GUIMain Class
The main class contains the GUIMain method that interacts with the user by asking for input , taking in the inputs through GUI and processes the data (calling different methods), and gives the respective results through GUI.
There are various JPanels which help in displaying the GUI and the user can interact with the GUI by clicking on the buttons and entering the inputs in the text fields.
These JPanels are added to the JFrame which is the main window of the GUI.
Some of the JPanels are: 
 - signInUpPanel - This panel is the first panel that is displayed to the user. It has two buttons - Sign In and Sign Up. The user can click on either of the buttons to proceed.
 - sellerOrBuyerPanel - This panel is displayed to the user after the user clicks on the Sign Up button. It has two buttons - Seller and Buyer. The user can click on either of the buttons to proceed.
### User Class
The User class contains the information about the user, including name(String), email(String), password(String), and shoppingCart(ArrayList). It also contains the following functionalities:

- login:
  - Allows the users to log into their account with email and password. If the account exists and the email and password are correct, returns a User object with corresponding information, returns null otherwise.
  - Testing: tested in the GUIMain method 

- signUp:
  - Allows the users to sign up for an account with their name, email, and address. Returns true when successfully sign up and false if the account already exists.
  - Testing: tested in the GUIMain method 

- addToCart:
  - Allows the users to add items (tickets) to their shopping cart. This method also updates the text files that store the related information.
  - Testing: tested in the GUIMain method 
  
- removeFromCart:
  - Allows the users to remove items (tickets) from their cart. This method also updates the text files that store the related information.
  - Testing: tested in the GUIMain method 
  
- buyTicket:
  - Allows the user to buy a ticket. This method updates the user's shopping cart (since they have bought it) and updates the related files.
  - Testing: tested in the GUIMain method 

- displayPastTransactions:
  - Displays the order history (the tickets bought by the user).
  - Testing: tested in the GUIMain method 
  
- displayShoppingCart:
  - Displays the items in the user's shopping cart (including the product name, price, quantity available, description, and more).
  - Testing: tested in the GUIMain method 
  
- changePassword:
  - Allows the user to change their password. This updates the files that store the user information.
  - Testing: tested in the GUIMain method 
  
- changeName:
  - Allows the user to change their name. This updates the files that store the user information.
  - Testing: tested in the GUIMain method 

- deleteAccount:
  - Allows the users to delete their accounts. This updates the files that store the user information.
  - Testing: tested in the GUIMain method 

- updateFile:
  - Updates the files that store the user information with the latest information.
  - Testing: tested in the GUIMain method (called in the changePassword and changeName method) 

- writeToFile: 
  - Similar to updateFile. Used for writing the user information to files.
  - Testing: tested in the GUIMain method (called in the signUp method) 
  
- summary:
  - Returns a String of the summary of the user.
  - Testing: tested in the GUIMain method (called in the writeToFile method) 

### Seller Class
The Seller class contains the information about the seller, including name(String), email(String), password(String), storeNames(ArrayList), and stores(ArrayList). It also contains the following functionalities:

- login:
  - Allows the sellers to log into their account with email and password. If the account exists and the email and password are correct, returns a Seller object with corresponding information, returns null otherwise.
  - Testing: tested in the GUIMain method 

- signUp:
  - Allows the sellers to sign up for an account with their name, email, and address. Returns true when successfully sign up and false if the account already exists.
  - Testing: tested in the GUIMain method 
  
- createStore:
  - Allows the sellers to create a store with a store name.
  - Testing: tested in the GUIMain method 
  
- changePassword:
  - Allows the sellers to change their password. This updates the files that store the seller information.
  - Testing: tested in the GUIMain method 
  
- changeName:
  - Allows the sellers to change their name. This updates the files that store the seller information.
  - Testing: tested in the GUIMain method 
  
- deleteAccount:
  - Allows the sellers to delete their accounts. This updates the files that store the seller information.
  - Testing: tested in the GUIMain method 
  
- shoppingCart:
  - Prints the items in the customers' shopping cart for the seller to view.
  - Testing: tested in the GUIMain method 
  
- summary:
  - Returns a string of the seller's summary (including the name, email, password, and the name of the stores owned by that seller).
  - Testing: tested in the GUIMain method (called in the writeToFile method) 
  
- writeToFile: 
  - Write the seller information returned by the summary method to the text file.
  - Testing: tested in the GUIMain method (called in the signUp method) 
  
- newStoreWrite:
  - Writes the newly created store information into the file.
  - Testing: tested in the GUIMain method (called in the createStore method) 

- customerList:
  - Lists the past transactions for every store.
  - Testing: tested in the GUIMain method 
  
- updateFile:
  - Updates the files that store the seller information with the latest information.
  - Testing: tested in the GUIMain method (called in the changePassword and changeName method) 

### Store Class
The store class contains the information about the store, including its name (String) and the tickets it has (ArrayList). It also includes the following functionalities:

- newTickets:
  - Allows the sellers to create new tickets in their store with provided information (such as the name of the ticket, the price, and more). This updates the files storing the ticket and store information.
  - Testing: tested in the GUIMain method 
  
- deleteTickets:
  - Allows the sellers to delete tickets in their stores. This updates the files storing the ticket and store information.
  - Testing: tested in the GUIMain method 

- getRevenue:
  - Get the total revenue of the store.
  - Testing: tested in the GUIMain method + test cases in the Testing class

### Ticket Class
The Ticket class contains the information about the Ticket, including ticket id(int), sellerName(String), the name of the ticket(String), quantity(int), storeName(String), price(double), and description(String). It also contains the following functionalities:

- toFile:
  - Return a string summary of the ticket that is used for writing to files.
  - Testing: tested in the GUIMain method (called in the writeToFile method and changeInfo method) 

- changeInfo:
  - Update the file that stores ticket information with the most-updated info.
  - Testing: tested in the GUIMain method 
  
- toProduct:
  - Prints the product on the console (we use it for displaying the products in the menu).
  - Testing: tested in the GUIMain method 

- toString:
  - Prints all the details about the product on the console.
  - Testing: tested in the GUIMain method 

### ClientThread Class
Creates a Thread using Runnable, allowing multiple clients to access the application independently and concurrently.

- displayMarketPlace:
 - Displays the existing marketplace to the user and allows them to sort the data.

- storeDash:
  - Displays Dashboard of past transactions and corresponding details to the seller and allows them to sort the data
 
- customerStats:
   - Displays information about customers and their data about previous information by store to the seller. Allows the seller
      to sort the data

- productStats:
   - Displays information about products , how many were sold at what price and other details in a store to the seller. Allows seller
     to sort the data.