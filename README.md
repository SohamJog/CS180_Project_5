# CS180_Project_4

## Instructions - How to run and compile:
- Step 1 - Clone or download the souce code from this repository
- Step 2 - Run the main method in the Main.java using a IDE or terminal
- Step 3 - You will see directions printed out on the console once you run the main method. Simply follow the directions and choose the options you want to proceed!

## Submissions:
- [Code]: Armanya Maheshwari submitted on Vocareum
- [Report]: Ajay Bestrapalli submitted on BrightSpace

## Class Explainations:
    
### Main Class
The main class contains the main method that interacts with the user by asking for input on the console, taking in the inputs, process the data (calling different methods), and prints out the results on the console.

### User Class
The User class contains the information about the user, including name(String), email(String), password(String), and shoppingCart(ArrayList). It also contains the following functionalities:

- login:
  - Allows the users to log into their account with email and password. If the account exists and the email and password are correct, returns a User object with corresponding information, returns null otherwise.
  - Testing: tested in the main method + test cases in Testing class

- signUp:
  - Allows the users to sign up for an account with their name, email, and address. Returns true when successfully sign up and false if the account already exists.
  - Testing: tested in the main method + test cases in Testing class

- addToCart:
  - Allows the users to add items (tickets) to their shopping cart. This method also updates the text files that store the related information.
  - Testing: tested in the main method + test cases in Testing class
  
- removeFromCart:
  - Allows the users to remove items (tickets) from their cart. This method also updates the text files that store the related information.
  - Testing: tested in the main method + test cases in Testing class
  
- buyTicket:
  - Allows the user to buy a ticket. This method updates the user's shoopping cart (since they have bought it) and updates the related files.
  - Testing: tested in the main method + test cases in Testing class

- displayPastTransactions:
  - Displays the order history (the tickets bought by the user).
  - Testing: tested in the main method + test cases in Testing class
  
- displayShoppingCart:
  - Displays the items in the user's shopping cart (including the product name, price, quantity available, description, and more).
  - Testing: tested in the main method + test cases in Testing class
  
- changePassword:
  - Allows the user to change their password. This updates the files that store the user information.
  - Testing: tested in the main method + test cases in Testing class
  
- changeName:
  - Allows the user to change their name. This updates the files that store the user information.
  - Testing: tested in the main method + test cases in Testing class

- deleteAccount:
  - Allows the users to delete their accounts. This updates the files that store the user information.
  - Testing: tested in the main method + test cases in Testing class

- updateFile:
  - Updates the files that store the user information with the latest information.
  - Testing: tested in the main method (called in the changePassword and changeName method) + test cases in Testing class

- writeToFile: 
  - Similar to updateFile. Used for writing the userinformation to files.
  - Testing: tested in the main method (called in the signUp method) + test cases in Testing class
  
- summary:
  - Returns a String of the summary of the user.
  - Testing: tested in the main method (called in the writeToFile method) + test cases in Testing class

### Seller Class
The Seller class contains the information about the seller, including name(String), email(String), password(String), storeNames(ArrayList), and stores(ArrayList). It also contains the following functionalities:

- login:
  - Allows the sellers to log into their account with email and password. If the account exists and the email and password are correct, returns a Seller object with corresponding information, returns null otherwise.
  - Testing: tested in the main method + test cases in Testing class

- signUp:
  - Allows the sellers to sign up for an account with their name, email, and address. Returns true when successfully sign up and false if the account already exists.
  - Testing: tested in the main method + test cases in Testing class
  
- createStore:
  - Allows the sellers to create a store with a store name.
  - Testing: tested in the main method + test cases in Testing class
  
- changePassword:
  - Allows the sellers to change their password. This updates the files that store the seller information.
  - Testing: tested in the main method + test cases in Testing class
  
- changeName:
  - Allows the sellers to change their name. This updates the files that store the seller information.
  - Testing: tested in the main method + test cases in Testing class
  
- deleteAccount:
  - Allows the sellers to delete their accounts. This updates the files that store the seller information.
  - Testing: tested in the main method + test cases in Testing class
  
- shoppingCart:
  - Prints the items in the customers' shopping cart for the seller to view.
  - Testing: tested in the main method + test cases in Testing class
  
- summary:
  - Returns a string of the seller's summary (including the name, email, password, and the name of the stores owned by that seller).
  - Testing: tested in the main method (called in the writeToFile method) + test cases in Testing class
  
- writeToFile: 
  - Write the seller information returned by the summary method to the text file.
  - Testing: tested in the main method (called in the signUp method) + test cases in Testing class
  
- newStoreWrite:
  - Writes the newly created store information into the file.
  - Testing: tested in the main method (called in the createStore method) + test cases in Testing class

- customerList:
  - Lists the past trasactions for every store.
  - Testing: tested in the main method + test cases in Testing class
  
- updateFile:
  - Updates the files that store the seller information with the latest information.
  - Testing: tested in the main method (called in the changePassword and changeName method) + test cases in Testing class

### Store Class
The store class contains the information about the store, including its name (String) and the tickets it has (ArrayList). It also includes the following functionalities:

- newTickets:
  - Allows the sellers to create new tickets in their store with provided information (such as the name of the ticket, the price, and more). This updates the files storing the ticket and store information.
  - Testing: tested in the main method + test cases in Testing class
  
- deleteTickets:
  - Allows the sellers to delete tickets in their stores. This updates the files storing the ticket and store information.
  - Testing: tested in the main method + test cases in Testing class

- getRevenue:
  - Get the total revenue of the store.
  - Testing: tested in the main method + test cases in the Testing class

### Ticket Class
The Ticket class contains the information about the Ticket, including ticket id(int), sellerName(String), the name of the ticket(String), quantity(int), storeName(String), price(double), and description(String). It also contains the following functionalities:

- toFile:
  - Return a string summary of the ticket that is used for writing to files.
  - Testing: tested in the main method (called in the writeToFile method and changeInfo method) + test cases in Testing class

- changeInfo:
  - Update the file that stores ticket information with the most-updated info.
  - Testing: tested in the main method + test cases in Testing class
  
- toProduct:
  - Prints the product on the console (we use it for displaying the products in the menu).
  - Testing: tested in the main method + test cases in Testing class

- toString:
  - Prints all the details about the product on the console.
  - Testing: tested in the main method + test cases in Testing class

### Testing Class
The Testing class allows to check if the application runs without errors and check various scenarios to ensure the application runs smoothly.
