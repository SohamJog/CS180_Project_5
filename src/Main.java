import java.util.*;
import java.io.*;

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

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Ticket Emporium");
        System.out.println("What would you like to do?");
        System.out.println("0. Quit\n1. Sign-in\n2. Sign up");
        int choice = getChoice(2, scan);
        int choice2;
        if (choice == 0) return;
        if (choice == 2) {
            System.out.println("What role would you like to sign up for?");
            System.out.println("0. Quit\n1. Seller\n2. Customer");
            choice = getChoice(2, scan);
            if (choice == 0) return;
            String name;
            String email;
            String password;
            System.out.print("Name: ");
            name = scan.nextLine();
            System.out.print("Email: ");
            email = scan.nextLine();
            System.out.print("Password: ");
            password = scan.nextLine();
            if (choice == 1) {
                if (Seller.signUp(name, email, password)) {
                    System.out.println("Successfully Signed Up!");
                } else {
                    System.out.println("An account with this email already exists or you are using semicolons");
                }
            } else if (choice == 2) {
                if (User.signUp(name, email, password)) {
                    System.out.println("Successfully Signed Up!");
                } else {
                    System.out.println("An account with this email already exists or you are using semicolons");
                }
            }
        }
        System.out.println("What would you like to do?");
        System.out.println("0. Quit\n1. Seller Sign-in\n2. Customer Sign-in");
        choice = getChoice(2, scan);
        if (choice == 1) {
            String email;
            String password;
            System.out.print("Email: ");
            email = scan.nextLine();
            System.out.print("Password: ");
            password = scan.nextLine();
            Seller seller = Seller.login(email, password);
            if (seller == null) {
                System.out.println("Incorrect email or password");
                return;
            }
            do {
                System.out.println("What would you like to do, " + seller.getName() + "?");
                System.out.println("0. Quit\n1. Change Account Details\n2. Access Stores\n3. View Statistics\n4. View Products in Customer Shopping Carts");
                choice = getChoice(4, scan);
                if (choice == 1) {
                    do {
                        System.out.println("0. Go Back\n1. Change Name\n2. Change Password\n3. Delete Account");
                        choice2 = getChoice(3, scan);
                        if (choice2 == 1) {
                            System.out.println("What would you like to change your name to?");
                            seller.changeName(scan.nextLine());
                        } else if (choice2 == 2) {
                            System.out.println("What would you like to change your password to?");
                            seller.changePassword(scan.nextLine());
                        } else if (choice2 == 3) {
                            System.out.println("Account Deleted");
                            seller.deleteAccount();
                            return;
                        }
                    } while (choice2 != 0);
                }
                if (choice == 2) {
                    ArrayList<Store> stores;
                    do {
                        stores = seller.getStores();
                        System.out.println("0. Go Back\n1. Create New Store");
                        for (int i = 0; i < stores.size(); i++) {
                            System.out.println("" + (i + 2) + ". " + stores.get(i).getName());
                        }
                        choice2 = getChoice(3, scan);
                        if (choice2 == 1) {
                            System.out.println("What would you like to name your store?");
                            if (!seller.createStore(scan.nextLine())) {
                                System.out.println("A store with this name already exists");
                            }
                        } else if (choice2 > 0) {
                            int choice3;
                            Store store;
                            ArrayList<Ticket> storeProducts;
                            do {
                                System.out.println("0. Go Back\n1. Add Ticket\n");
                                store = seller.getStores().get(choice2 - 2);
                                storeProducts = store.getTickets();
                                for (int i = 0; i < storeProducts.size(); i++) {
                                    System.out.println("" + (i + 2) + ". " + storeProducts.get(i) + "\n");
                                }
                                choice3 = getChoice(storeProducts.size() + 1, scan);
                                if (choice3 == 1) {
                                    System.out.println("Please enter your ticket information in a comma separated list (name,price,description,quantity)");
                                    try {
                                        String[] ticketInfo = scan.nextLine().split(",");
                                        store.newTickets(ticketInfo[0], seller.getEmail(), Double.parseDouble(ticketInfo[1]), ticketInfo[2], Integer.parseInt(ticketInfo[3]));
                                    } catch (Exception e) {
                                        System.out.println("Improper Format");
                                    }
                                } else if (choice3 > 0) {
                                    int choice4;
                                    Ticket t;
                                    do {
                                        t = store.getTickets().get(choice3 - 2);
                                        System.out.println(t);
                                        System.out.println("0. Go Back\n1. Delete Ticket\n2. Change Name\n3. Change Price\n4. Change Description\n5. Change Quantity");
                                        choice4 = getChoice(5, scan);
                                        if (choice4 == 1) {
                                            store.deleteTickets(t.getId());
                                        } else if (choice4 == 2) {
                                            System.out.print("New Name: ");
                                            t.changeInfo(scan.nextLine(), t.getPrice(), t.getDescription(), t.getQuantity());
                                        } else if (choice4 == 3) {
                                            System.out.print("New Price: ");
                                            t.changeInfo(t.getName(), scan.nextDouble(), t.getDescription(), t.getQuantity());
                                            scan.nextLine();
                                        } else if (choice4 == 4) {
                                            System.out.print("New Description: ");
                                            t.changeInfo(t.getName(), t.getPrice(), scan.nextLine(), t.getQuantity());
                                        } else if (choice4 == 5) {
                                            System.out.print("New Quantity: ");
                                            t.changeInfo(t.getName(), t.getPrice(), t.getDescription(), scan.nextInt());
                                            scan.nextLine();
                                        }
                                    } while (choice4 != 0);
                                }
                            } while (choice3 != 0);
                        }
                    } while (choice2 != 0);
                }
                if (choice == 3) {
                    System.out.println("0. Go Back\n1. View Store Statistics\n2. View Customer Statistics\n3. View Product Statistics");
                    choice2 = getChoice(3, scan);
                    if (choice2 == 1) {
                        for (Store s : seller.getStores()) {
                            System.out.println(s.getName() + "\n____________\nRevenue: $" + s.getRevenue() + "\nCustomer List:");
                            s.getCustomerList().forEach(System.out::println);
                            System.out.println();
                        }
                        System.out.println();
                    }
                    String yOrNo;
                    if (choice2 == 2) {
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        customerStats(seller.getEmail(), yOrNo.equals("y"));
                    }
                    if (choice2 == 3) {
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
                    ArrayList<String> sCart = seller.shoppingCart();
                    System.out.println(sCart.get(sCart.size() - 1));
                }
            } while (choice != 0);
        } else if (choice == 2) {
            String email;
            String password;
            System.out.print("Email: ");
            email = scan.nextLine();
            System.out.print("Password: ");
            password = scan.nextLine();
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
                            System.out.println("What would you like to change your name to?");
                            user.changeName(scan.nextLine());
                        } else if (choice2 == 2) {
                            System.out.println("What would you like to change your password to?");
                            user.changePassword(scan.nextLine());
                        } else if (choice2 == 3) {
                            System.out.println("Account Deleted");
                            user.deleteAccount();
                            return;
                        }
                    } while (choice2 != 0);
                }
                if (choice == 2) {
                    ArrayList<Ticket> market;
                    String search = "";
                    Ticket product;
                    market = displayMarketplace(false, "");
                    do {
                        System.out.println("0. Go Back\n1. Sort Tickets by Price\n2. Search Tickets by term");
                        for (int i = 0; i < market.size(); i++) {
                            System.out.println("" + (i + 3) + ". " + market.get(i) + "\n");
                        }
                        choice2 = getChoice(market.size() + 2, scan);
                        if (choice2 == 1) {
                            market = displayMarketplace(true, search);
                        } else if (choice2 == 2) {
                            System.out.println("What would you like to search?");
                            System.out.print("Search: ");
                            search = scan.nextLine();
                            market = displayMarketplace(false, search);
                        } else if (choice2 > 0) {
                            int choice3 = -1;
                            product = market.get(choice2 - 3);
                            System.out.println(product.toProduct());
                            System.out.println("0. Go Back\n1. Add to Cart");
                            choice3 = getChoice(1, scan);
                            if (choice3 == 1) {
                                int quantity = -1;
                                System.out.print("How many would you like to buy: ");
                                quantity = getChoice(product.getQuantity(), scan);
                                if (user.addToCart(product, quantity)) {
                                    System.out.println("Added to cart");
                                }
                                market = displayMarketplace(false, search);
                            }
                        }
                    } while (choice2 != 0);
                }
                if (choice == 3) {
                    System.out.println();
                    System.out.println("Your Purchase History: ");
                    System.out.println(user.displayPastTransactions());
                }
                if (choice == 4) {
                    do {
                        System.out.println(user.displayShoppingCart());
                        System.out.println("0. Go Back");
                        if (user.getShoppingCart().size() > 0) {
                            System.out.print("1. Remove item\n2. Check Out\n");
                        }
                        choice2 = getChoice(2, scan);
                        if (choice2 == 1) {
                            System.out.println("Which product do you want to remove? (Enter number of ticket from above)");
                            int choice3 = getChoice(user.getShoppingCart().size(), scan);
                            user.removeFromCart(user.getShoppingCart().get(choice3 - 1));
                        }
                        if (choice2 == 2) {
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
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        String yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        storeDash(yOrNo.equals("y"));
                    }
                    if (choice2 == 2) {
                        System.out.println("Do you want these statistics sorted? (y/n)");
                        String yOrNo = scan.nextLine();
                        while (!yOrNo.equals("y") && !yOrNo.equals("n")) {
                            System.out.println("Please enter a valid answer");
                            yOrNo = scan.nextLine();
                        }
                        user.customerStoreDash(yOrNo.equals("y"));
                    }
                }
            } while (choice != 0);
        }

    }

    public static ArrayList<Ticket> displayMarketplace(boolean sort, String search) {
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

    public static void storeDash(boolean sort) {
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

    public static int getChoice(int max, Scanner scan) {
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

    public static void customerStats(String seller, boolean sort) {
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

    public static void productStats(String seller, boolean sort) {
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