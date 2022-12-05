import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;


import java.net.Socket;


/**
 * Main class
 * <p>
 * This class contains the main method where all the interactions
 * between the users and the program happens. Inputs are taken from
 * the user through the console, and then the methods in other classes
 * are called to process the data, and finally, the results are display
 * to the console.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version November 14, 2022
 */

// todo - IMPORTANT NOTE FOR SOHAM AND ARMANYA: WHEN YOU WANT TO PROCESS A DADA BY SENDING AN INSTRUCTION TO THE SERVER
//    (FOR EXAMPLE, changeName), MAKE SURE YOU 'ONLY' SEND IT WHEN THE PROVIDED INPUT FROM THE USER IS 'VALID', THAT IS,
//    VERIFY THE USER INPUT BEFORE SENDING THE REQUEST TO THE SERVER (SINCE THE SERVER ISN'T ABLE TO HANDLE
//    INVALID INPUT. THANK YOU!! :)

class Main {
    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;


        try {
            socket = new Socket("localhost", 4242);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Connected to the server!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /////
        Scanner scan = new Scanner(System.in);

        /*
            System.out.println("Welcome to Ticket Emporium");
            System.out.println("What would you like to do?");
            System.out.println("0. Quit\n1. Sign-in\n2. Sign up");




            int choice = getChoice(2, scan);
            int choice2;
            if (choice == 0) return; // add: send "quit" to server before returning

         */

        int choice;
        int choice2;
        Client client = new Client();
        client.showWelcomeMessageDialog();

        String x = client.showSigninSignup();
        if(x.equals("log in")) {
            choice = 1;
        }
        else {
            choice = 2;
        }
        if (choice == 2) {
                /*
                System.out.println("What role would you like to sign up for?");
                System.out.println("0. Quit\n1. Seller\n2. Customer");
                choice = getChoice(2, scan);

                 */
              // hi this is a comment
            x = client.showSignupOptions();
            if(x.equals("Seller")) {
                choice = 1;
            } else {
                choice = 2;
            }

            String name = client.enterName();
            String email = client.enterEmail();
            String password = client.enterPassword();
                /*
                System.out.print("Name: ");
                name = scan.nextLine();
                System.out.print("Email: ");
                email = scan.nextLine();
                System.out.print("Password: ");
                password = scan.nextLine();
                System.out.println(choice);

                 */
            if (choice == 1) {
                ////
                writer.println("sellerSignup");
                writer.flush();
                writer.println(name);
                writer.flush();
                writer.println(email);
                writer.flush();
                writer.println(password);
                writer.flush();
                String inp = "bananas";
                try {
                    inp = reader.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(inp);
                // send "sellerSignup" to the server
                // then send name, email, and password respectively to the server
                // server sends back either "true" or success or "false" otherwise


                if (inp.equals("true")) {
                    client.showSuccessfulSignUpDialog();
                } else {
                    client.showMistakeDialog();
                }
            } else if (choice == 2) {
                ////
                writer.println("userSignup");
                writer.flush();
                writer.println(name);
                writer.flush();
                writer.println(email);
                writer.flush();
                writer.println(password);
                writer.flush();
                String inp = "bananas";
                try {
                    inp = reader.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(inp);
                // send "userSignup" to the server
                // then send name, email, and password respectively to the server
                // server sends back either "true" or success or "false" otherwise
                if (inp.equals("true")) {
                    client.showSuccessfulSignUpDialog();
                } else {
                    client.showMistakeDialog();
                }
            }
        }


        /*
        System.out.println("What would you like to do?");
        System.out.println("0. Quit\n1. Seller Sign-in\n2. Customer Sign-in");

         */

        x = client.showSignupOptions();
        if(x.equals("Seller")) {
            choice = 1;
        }
        else {
            choice = 2;
        }
       // choice = getChoice(2, scan);
        if (choice == 1) {

            String email = client.enterEmail();
            String password = client.enterPassword();

            Seller seller = Seller.login(email, password);

            /////
            writer.println("sellerSignin");
            writer.flush();
            writer.println(email);
            writer.flush();
            writer.println(password);
            writer.flush();
            String inp = "bananas";
            try {
                inp = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(inp);


            // send "sellerSignin" to the server
            // then send email and password respectively to the server
            // server sends back either "true" or success or "false" otherwise
            if (seller == null) {
                client.showSigninError();
                return;
            }
            do {
                //System.out.println("What would you like to do, " + seller.getName() + "?");
                //System.out.println("0. Quit\n1. Change Account Details\n2. Access Stores\n3. View Statistics\n4. View Products in Customer Shopping Carts");
                String po = client.showSellerSignupOptions();
                choice = getChoice(4, scan);
                if (choice == 1) {
                    do {
                       // System.out.println("0. Go Back\n1. Change Name\n2. Change Password\n3. Delete Account");
                        String yrt = client.showBuyerSignupOptions();
                        choice2 = getChoice(3, scan);
                        if (choice2 == 1) {
                            // send "changeName"
                            // send the new name (input from the user)
                         //   System.out.println("What would you like to change your name to?");
                            // seller.changeName(scan.nextLine());
                           String name = client.changeName();
                            //
                            writer.println("changeName");
                            writer.flush();
                            writer.println(scan.nextLine());
                            writer.flush();

                            //

                        } else if (choice2 == 2) {
                            // send "changePassword"
                            // send the new password (input from the user)
                            System.out.println("What would you like to change your password to?");
                            // seller.changePassword(scan.nextLine());
                            //
                            writer.println("changePassword");
                            writer.flush();
                            writer.println(scan.nextLine());
                            writer.flush();

                            //
                        } else if (choice2 == 3) {
                            // send "deleteAccount"
                            System.out.println("Account Deleted");
                            //
                            writer.println("deleteAccount");
                            writer.flush();
                            // writer.println(scan.nextLine());
                            // writer.flush();

                            //
                            // seller.deleteAccount();
                            return;
                        }
                    } while (choice2 != 0);
                }
                if (choice == 2) {
                    // send "listStores"
                    // then the server will return each store name (one by one)
                    ArrayList<Store> stores;
                    do {
                        stores = seller.getStores();
                        System.out.println("0. Go Back\n1. Create New Store");
                        for (int i = 0; i < stores.size(); i++) {
                            System.out.println("" + (i + 2) + ". " + stores.get(i).getName());
                        }
                        choice2 = getChoice(3, scan);
                        if (choice2 == 1) {
                            // send "createNewStore"
                            // server sends back either "true" or "false"
                            System.out.println("What would you like to name your store?");

                            writer.println("createNewStore");
                            writer.flush();
                            writer.println(scan.nextLine());
                            writer.flush();

                            inp = "bananas";
                            try {
                                inp = reader.readLine();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            System.out.println(inp);

                            if (inp.equals("false")) {
                                System.out.println("A store with this name already exists");
                            }
                            ///
                        } else if (choice2 > 0) {
                            // send "enterStore"
                            // send choice2 (string number)

                            writer.println("enterStore");
                            writer.flush();
                            writer.println(Integer.toString(choice2));
                            writer.flush();

                            //
                            int choice3;
                            Store store;
                            ArrayList<Ticket> storeProducts;
                            do {
                                System.out.println("0. Go Back\n1. Add Ticket\n");
                                store = seller.getStores().get(choice2 - 2);
                                storeProducts = store.getTickets();
                                for (int i = 0; i < storeProducts.size(); i++) {
                                    // the server will send back each product, so just print the returned thing out using println
                                    System.out.println("" + (i + 2) + ". " + storeProducts.get(i) + "\n");
                                }
                                choice3 = getChoice(storeProducts.size() + 1, scan);
                                if (choice3 == 1) {
                                    // send "newTicket" to the server
                                    // send ticketInfo[0], ticketInfo[1], ticketInfo[2], ticketInfo[3] to server respectively
                                    // server returns true if succeeded, false otherwise (Improper Format)
                                    System.out.println("Please enter your ticket information in a comma separated list (name,price,description,quantity)");
                                    try {
                                        String[] ticketInfo = scan.nextLine().split(",");
                                        //
                                        writer.println("newTicket");
                                        writer.flush();
                                        writer.println(ticketInfo[0]);
                                        writer.flush();
                                        writer.println(ticketInfo[1]);
                                        writer.flush();
                                        writer.println(ticketInfo[2]);
                                        writer.flush();
                                        writer.println(ticketInfo[3]);
                                        writer.flush();

                                        inp = "bananas";
                                        try {
                                            inp = reader.readLine();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        System.out.println(inp);
                                        //
                                        //store.newTickets(ticketInfo[0], seller.getEmail(),Double.parseDouble(ticketInfo[1]), ticketInfo[2], Integer.parseInt(ticketInfo[3]));
                                    } catch (Exception e) {
                                        System.out.println("Improper Format");
                                    }
                                } else if (choice3 > 0) {
                                    // send "accessTicket" to server
                                    // send choice3 (string number)
                                    int choice4;
                                    Ticket t;
                                    do {
                                        t = store.getTickets().get(choice3 - 2);
                                        // server returns t -> just print it out
                                        System.out.println(t);
                                        System.out.println("0. Go Back\n1. Delete Ticket\n2. Change Name\n3. Change Price\n4. Change Description\n5. Change Quantity");
                                        choice4 = getChoice(5, scan);
                                        if (choice4 == 1) {
                                            // send "deleteTicket" to server
                                            store.deleteTickets(t.getId());
                                        } else if (choice4 == 2) {
                                            // send "changeTicketName" to server
                                            // send the new name to server
                                            System.out.print("New Name: ");
                                            t.changeInfo(scan.nextLine(), t.getPrice(), t.getDescription(), t.getQuantity());
                                        } else if (choice4 == 3) {
                                            // send "changeTicketPrice" to server
                                            // send new price to server
                                            System.out.print("New Price: ");
                                            t.changeInfo(t.getName(), scan.nextDouble(), t.getDescription(), t.getQuantity());
                                            scan.nextLine();
                                        } else if (choice4 == 4) {
                                            // send "changeTicketDescription" to server
                                            // send new description to server
                                            System.out.print("New Description: ");
                                            t.changeInfo(t.getName(), t.getPrice(), scan.nextLine(), t.getQuantity());
                                        } else if (choice4 == 5) {
                                            // send "changeTicketQuantity" to server
                                            // send new quantity
                                            System.out.print("New Quantity: ");
                                            t.changeInfo(t.getName(), t.getPrice(), t.getDescription(), scan.nextInt());
                                            scan.nextLine();
                                        } // add: else if (choice4 == 0) -> send "goBack" to server (I need this info. Thank you :))
                                    } while (choice4 != 0);
                                } // add: else (when choice3 is 0) -> send "goBack" to server (I need this info. Thank you :))
                            } while (choice3 != 0);
                        } // add: else if (choice2 == 0) -> send "goBack" to server (I need this info. Thank you :))
                    } while (choice2 != 0);
                }
                if (choice == 3) {
                    System.out.println("0. Go Back\n1. View Store Statistics\n2. View Customer Statistics\n3. View Product Statistics");
                    choice2 = getChoice(3, scan);
                    if (choice2 == 1) {
                        // send "viewStoreStatistics" to server
                        // the server returns the same thing as the original code returns (the code below)
                        // just have System.out.println replaced with printWriter.println
                        try {
                            writer.println("viewStoreStatistics");
                            writer.flush();
                            String input = reader.readLine();
                            while(input!=null) {
                                System.out.println(input);
                                input = reader.readLine();
                            }

                            //
                                /*
                                for (Store s : seller.getStores()) {
                                    System.out.println(s.getName() + "\n____________\nRevenue: $" + s.getRevenue() + "\nCustomer List:");
                                    s.getCustomerList().forEach(System.out::println);
                                    System.out.println();
                                }

                                 */
                            System.out.println();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    String yOrNo;
                    if (choice2 == 2) {
                        // send "viewCustomerStatics" to server
                        // then send "y" to server for sort, "n" for not sort
                        // server returns the same thing that customerStats() prints - please refer to the customerStats() method
                        // I changed the customerStats() in the ClientThread.java so that it does printWriter.println instead sout
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        customerStats(seller.getEmail(), yOrNo.equals("y"));
                    }
                    if (choice2 == 3) {
                        // send "viewProductStatistics" to server
                        // then send "y" to server for sort, "n" for not sort
                        // server returns the same thing that productStats() prints - please refer to the productStats() method
                        // I changed the productStats() in the ClientThread.java so that it does printWriter.println instead sout
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        productStats(seller.getEmail(), yOrNo.equals("y"));
                    }
                }
                if (choice == 4) {
                    // send "viewProductsInCustomerShoppingCarts" to the server
                    // server returns each item line by line -> so just print them
                    ArrayList<String> sCart = seller.shoppingCart();
                    for (String s : sCart) {
                        System.out.println(s);
                    }
                }


            } while (choice != 0);
        } else if (choice == 2) {



            // send "userSignin" to the server
            // then send email and password respectively to the server
            // server sends back either "true" or success or "false" otherwise

            String email;
            String password;
            System.out.print("Email: ");
            email = scan.nextLine();
            System.out.print("Password: ");
            password = scan.nextLine();

            ////
            writer.println("userSignup");
            writer.flush();
            writer.println(email);
            writer.flush();
            writer.println(password);
            writer.flush();
            String inp = "bananas";
            try {
                inp = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(inp);

            User user = User.login(email, password);
            if (user == null) {
                System.out.println("Incorrect email or password");
                return;
            }
            do {
                System.out.println("What would you like to do, " + user.getName() + "?");
                System.out.println("0. Quit\n1. Change Account Details\n2. Buy Tickets\n3. View Purchase History\n4. Access Shopping Cart\n5. View Statistics");
                choice = getChoice(5, scan);
                if (choice == 1) {
                    do {
                        System.out.println("0. Go Back\n1. Change Name\n2. Change Password\n3. Delete Account");
                        choice2 = getChoice(3, scan);
                        if (choice2 == 1) {
                            // send "changeName"
                            // send the new name (input from the user)

                            System.out.println("What would you like to change your name to?");
                            writer.println("changeName");
                            writer.flush();
                            writer.println(scan.nextLine());
                            writer.flush();
                            // user.changeName(scan.nextLine());
                        } else if (choice2 == 2) {
                            // send "changePassword"
                            // send the new password (input from the user)
                            System.out.println("What would you like to change your password to?");
                            writer.println("changePassword");
                            writer.flush();
                            writer.println(scan.nextLine());
                            writer.flush();
                            //user.changePassword(scan.nextLine());
                        } else if (choice2 == 3) {
                            // send "deleteAccount"
                            System.out.println("Account Deleted");
                            writer.println("deleteAccount");
                            writer.flush();
                            //user.deleteAccount();
                            return;
                        }
                    } while (choice2 != 0);
                }
                if (choice == 2) {
                    // send "displayMarketplace"
                    ArrayList<Ticket> market;
                    String search = "";
                    Ticket product;
                    market = displayMarketplace(false, "");
                    do {
                        System.out.println("0. Go Back\n1. Sort Tickets by Price\n2. Search Tickets by term");
                        // server will return these one by one, so just print out the return one by one
                        for (int i = 0; i < market.size(); i++) {
                            System.out.println("" + (i + 3) + ". " + market.get(i) + "\n");
                        }
                        choice2 = getChoice(market.size() + 2, scan);
                        if (choice2 == 1) {
                            // send "sort" to server
                            market = displayMarketplace(true, search);
                        } else if (choice2 == 2) {
                            // send "search" to server
                            // send search word to server
                            System.out.println("What would you like to search?");
                            System.out.print("Search: ");
                            search = scan.nextLine();
                            market = displayMarketplace(false, search);
                        } else if (choice2 > 0) {
                            // send "accessTicket" to server
                            // send choice2 (as a string number) to server
                            int choice3 = -1;
                            product = market.get(choice2 - 3);
                            // server returns product.toProduct -> just print it out
                            System.out.println(product.toProduct());
                            System.out.println("0. Go Back\n1. Add to Cart");
                            choice3 = getChoice(1, scan);
                            if (choice3 == 1) {
                                // send "addToCart" to server (only if the quantity is valid)
                                // send quantity to the server
                                // server returns true or false
                                int quantity = -1;
                                System.out.print("How many would you like to buy: ");
                                quantity = getChoice(product.getQuantity(), scan);
                                if (user.addToCart(product, quantity)) {
                                    System.out.println("Added to cart");
                                }
                                market = displayMarketplace(false, search);
                            } // add: else if (choice3 == 0) -> send "goBack" to server
                        }
                    } while (choice2 != 0);
                }
                if (choice == 3) {
                    // send "purchaseHistory" to server
                    // server returns user.displayPastTransactions() -> so just print it out
                    System.out.println();
                    System.out.println("Your Purchase History: ");
                    System.out.println(user.displayPastTransactions());
                }
                if (choice == 4) {
                    // send "displayShoppingCart" to server
                    do {
                        // server returns user.displayShoppingCart here -> so just print it out
                        System.out.println(user.displayShoppingCart());
                        System.out.println("0. Go Back");
                        // server then returns user.getShoppingCart().size() so that you can use it here:
                        // remember to parse it to an int!
                        if (user.getShoppingCart().size() > 0) {
                            System.out.print("1. Remove item\n2. Check Out\n");
                        }
                        choice2 = getChoice(2, scan);
                        if (choice2 == 1) {
                            // send "removeItem" to server
                            // send choice3 to server only if it's valid
                            System.out.println("Which product do you want to remove? (Enter number of ticket from above)");
                            int choice3 = getChoice(user.getShoppingCart().size(), scan);
                            user.removeFromCart(user.getShoppingCart().get(choice3 - 1));
                        }
                        if (choice2 == 2) {
                            // send "checkout" to server
                            while (user.getShoppingCart().size() > 0) {
                                user.buyTicket(user.getShoppingCart().get(0));
                            }
                            System.out.println("Transaction successful!");
                        }
                    } while (choice2 != 0);
                }
                if (choice == 5) {
                    System.out.println("0. Go back\n1. Statistics for all stores\n2. Statistics for stores you've shopped at");
                    choice2 = getChoice(2, scan);
                    if (choice2 == 1) {
                        // send "statisticsForAllStores" to server
                        // returns what the storeDash() method returns -> refers to that method
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        String yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        storeDash(yOrNo.equals("y"));
                    }
                    if (choice2 == 2) {
                        // send "statisticsForStoresShopped" to server
                        // server returns whatever the customerStoreDash returns
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        String yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        // I commented this out cuz I changed the customerStoreDash() parameters, which will cause error here
//                        user.customerStoreDash(yOrNo.equals("y"));
                    }
                }
            } while (choice != 0);
        }

    }

    public static ArrayList<Ticket> displayMarketplace ( boolean sort, String search){
        File f = new File("availableTickets.txt");
        ArrayList<Ticket> tickets = new ArrayList<>();
        String[] ticketInfo;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                if (!line.equals("")) {
                    ticketInfo = line.split(";");
                    tickets.add(new Ticket(Integer.parseInt(ticketInfo[0]), ticketInfo[1], ticketInfo[2], ticketInfo[3], Double.parseDouble(ticketInfo[4]), ticketInfo[5], Integer.parseInt(ticketInfo[6])));
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!search.equals("")) {
            for (int i = 0; i < tickets.size(); i++) {
                if (!(tickets.get(i).getName().contains(search) || tickets.get(i).getStoreName().contains(search) || tickets.get(i).getDescription().contains(search)))
                    tickets.remove(i);
            }
        }
        if (sort) {
            Collections.sort(tickets, Comparator.comparing(Ticket::getPrice));
        }
        return tickets;
    }

    public static void storeDash ( boolean sort){
        File f = new File("pastTransactions.txt");
        Map<String, Integer> stores = new HashMap<>();
        String[] ticketInfo;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                ticketInfo = line.split(";");
                stores.merge(ticketInfo[4], Integer.parseInt(ticketInfo[7]), Integer::sum);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sort) {
            stores.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(System.out::println);
        } else {
            for (String key : stores.keySet()) {
                System.out.println(key + "=" + stores.get(key));
            }
        }
    }

    public static int getChoice ( int max, Scanner scan){
        int ret = -1;
        boolean invalid = false;
        //int ok = 0;
        do {
            //System.out.println("im here");
            if (invalid) {
                System.out.println("Please enter a valid number");
            }
            try {
                String temp = scan.nextLine();
                ret = Integer.parseInt(temp);
                // scan.nextLine();
                invalid = false;
                if (ret < 0 || ret > max) {
                    //System.out.println("Please enter a valid number");
                    invalid = true;
                }
            } catch (Exception e) {
                //System.out.println("Please enter a valid number");
                invalid = true;
            }
        } while (invalid);
        return ret;
    }

    public static void customerStats (String seller,boolean sort){
        File f = new File("pastTransactions.txt");
        Map<String, Integer> customers = new HashMap<>();
        String[] ticketInfo;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                if (!line.equals("")) {
                    ticketInfo = line.split(";");
                    if (ticketInfo[2].equals(seller)) {
                        customers.merge(ticketInfo[3], Integer.parseInt(ticketInfo[7]), Integer::sum);
                    }
                    line = br.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sort) {
            customers.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(System.out::println);
        } else {
            for (String key : customers.keySet()) {
                System.out.println(key + "=" + customers.get(key));
            }
        }
    }

    public static void productStats (String seller,boolean sort){
        File f = new File("pastTransactions.txt");
        Map<String, Integer> products = new HashMap<>();
        String[] ticketInfo;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                if (!line.equals("")) {
                    ticketInfo = line.split(";");
                    if (ticketInfo[2].equals(seller)) {
                        products.merge(ticketInfo[1], Integer.parseInt(ticketInfo[7]), Integer::sum);
                    }
                    line = br.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sort) {
            products.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(System.out::println);
        } else {
            for (String key : products.keySet()) {
                System.out.println(key + "=" + products.get(key));
            }
        }
    }


}
