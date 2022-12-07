import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientThread implements Runnable {
    Socket socket = null;
    BufferedReader br = null;
    PrintWriter pr = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            pr = new PrintWriter(socket.getOutputStream());
            pr.println("Connected");
            pr.flush();

            // 1 - what would you like to do? (sign in or sign up)
            String signInUp;
            do { // an option to quit - not sure if needed tho
                signInUp = br.readLine();
                if (signInUp.equals("sellerSignup")) {
                    String name = br.readLine();
                    String email = br.readLine();
                    String password = br.readLine();
                    if (Seller.signUp(name, email, password)) {
                        pr.println("true");
                        pr.flush();
                    } else {
                        pr.println("false");
                        pr.flush();
                    }
                } else if (signInUp.equals("userSignup")) {
                    String name = br.readLine();
                    String email = br.readLine();
                    String password = br.readLine();
                    if (User.signUp(name, email, password)) {
                        pr.println("true");
                        pr.flush();
                    } else {
                        pr.println("false");
                        pr.flush();
                    }
                } else if (signInUp.equals("sellerSignin")) {
                    String email = br.readLine();
                    String password = br.readLine();
                    Seller seller = Seller.login(email, password);
                    if (seller != null) {
                        pr.println("true");
                        pr.flush();
                        String action;
                        do {
                            action = br.readLine();
                            if (action.equals("changeName")) {
                                String newName = br.readLine();
                                seller.changeName(newName);
                            } else if (action.equals("changePassword")) {
                                String newPassword = br.readLine();
                                seller.changePassword(newPassword);
                            } else if (action.equals("deleteAccount")) {
                                seller.deleteAccount();
                            } else if (action.equals("listStores")) {
                                ArrayList<Store> stores = seller.getStores();
                                pr.println(stores.size());
                                pr.flush();
                                for (Store s : stores) {
                                    pr.println(s.getName());
                                    pr.flush();
                                }
                            } else if (action.equals("createNewStore")) {
                                String storeName = br.readLine();
                                if (seller.createStore(storeName)) {
                                    pr.println("true");
                                    pr.flush();
                                } else {
                                    pr.println("false");
                                    pr.flush();
                                }
                            } else if (action.equals("enterStore")) {
                                int choice2 = Integer.parseInt(br.readLine());
                                Store store = seller.getStores().get(choice2);
                                ArrayList<Ticket> storeProducts = store.getTickets();
                                pr.println(storeProducts.size());
                               // System.out.println(store.getName());
                                pr.flush();
                                for (int i = 0; i < storeProducts.size(); i++) {
                                    pr.println(storeProducts.get(i));
                                    pr.flush();
                                }


                                String storeAction;
                                do {
                                    storeAction = br.readLine();
                                    if (storeAction.equals("newTicket")) {
                                        try {
                                            String zero = br.readLine();
                                            double one = Double.parseDouble(br.readLine());
                                            String two = br.readLine();
                                            int three = Integer.parseInt(br.readLine());
                                            store.newTickets(zero, seller.getEmail(), one, two, three);
                                            pr.println("true");
                                            pr.flush();
                                        } catch (Exception e) {
                                            pr.println("false");
                                            pr.flush();
                                        }
                                    } else if (storeAction.equals("accessTicket")) {
                                        int ticketNumber = Integer.parseInt(br.readLine());
                                        Ticket t = store.getTickets().get(ticketNumber);
                                        pr.println(t);
                                        pr.flush();

                                        String ticketAction;
                                        do {
                                            ticketAction = br.readLine();
                                            if (ticketAction.equals("deleteTicket")) {
                                                store.deleteTickets(t.getId());
                                            } else if (ticketAction.equals("changeTicketName")) {
                                                String newTicketName = br.readLine();
                                                t.changeInfo(newTicketName, t.getPrice(), t.getDescription(), t.getQuantity());
                                            } else if (ticketAction.equals("changeTicketPrice")) {
                                                double newTicketPrice = Double.parseDouble(br.readLine());
                                                t.changeInfo(t.getName(), newTicketPrice, t.getDescription(), t.getQuantity());
                                            } else if (ticketAction.equals("changeTicketDescription")) {
                                                String newTicketDescription = br.readLine();
                                                t.changeInfo(t.getName(), t.getPrice(), newTicketDescription, t.getQuantity());
                                            } else if (ticketAction.equals("changeTicketQuantity")) {
                                                int newTicketQuantity = Integer.parseInt(br.readLine());
                                                t.changeInfo(t.getName(), t.getPrice(), t.getDescription(), newTicketQuantity);
                                            }
                                        } while(!ticketAction.equals("goBack"));
                                    }
                                } while (!storeAction.equals("goBack"));
                            } else if (action.equals("viewStoreStatistics")) {
                                pr.println(seller.getStores().size());
                                pr.flush();
                                for (Store s : seller.getStores()) {
                                    pr.println(s.getName() + "\n____________\nRevenue: $" + s.getRevenue() + "\nCustomer List:");
                                    pr.flush();
                                    s.getCustomerList().forEach((key) -> {
                                        pr.println(key);
                                        pr.flush();
                                    });
                                    pr.println();
                                    pr.flush();
                                }
                                pr.println();
                                pr.flush();
                            } else if (action.equals("viewCustomerStatics")) {
                                String sort = br.readLine();
                                if (sort.equals("y")) {
                                    customerStats(seller.getEmail(), true, pr);
                                } else {
                                    customerStats(seller.getEmail(), false, pr);
                                }
                            } else if (action.equals("viewProductStatistics")) {
                                String sort = br.readLine();
                                if (sort.equals("y")) {
                                    productStats(seller.getEmail(), true, pr);
                                } else {
                                    productStats(seller.getEmail(), false, pr);
                                }
                            } else if (action.equals("viewProductsInCustomerShoppingCarts")) {
                                ArrayList<String> sCart = seller.shoppingCart();
                                pr.println(sCart.size());
                                pr.flush();
                                for (String s : sCart) {
                                    pr.println(s);
                                    pr.flush();
                                }
                            }
                        } while (!action.equals("quit")); // when to send quit??
                    } else {
                        pr.println("false");
                        pr.flush();
                    }
                } else if (signInUp.equals("userSignin")) {
                    String email = br.readLine();
                    String password = br.readLine();
                    User user = User.login(email, password);
                    if (user != null) {
                        pr.println("true");
                        pr.flush();
                        String action;
                        // hello guys
                        do {
                            action = br.readLine();
                            if (action.equals("changeName")) {
                                String newName = br.readLine();
                                user.changeName(newName);
                            } else if (action.equals("changePassword")) {
                                String newPassword = br.readLine();
                                user.changePassword(newPassword);
                            } else if (action.equals("deleteAccount")) {
                                user.deleteAccount();
                            } else if (action.equals("displayMarketplace")) {
                                ArrayList<Ticket> market;
                                String search = "";
                                Ticket product;
                                market = displayMarketplace(false, "");
                                String choice2;
                                do {
                                    choice2 = br.readLine();
                                    pr.println(market.size());
                                    pr.flush();
                                    for (int i = 0; i < market.size(); i++) {
                                        pr.println("" + (i + 3) + ". " + market.get(i) + "\n");
                                        pr.flush();
                                    }
                                    if (choice2.equals("sort")) {
                                        market = displayMarketplace(true, search);
                                    } else if (choice2.equals("search")) {
                                        String searchWord = br.readLine();
                                        market = displayMarketplace(false, searchWord);
                                    } else if (choice2.equals("accessTicket")) {
                                        int t = Integer.parseInt(br.readLine());
                                        product = market.get(t - 3);
                                        pr.println(product.toProduct());
                                        pr.flush();
                                        String choice3 = br.readLine();
                                        if (choice3.equals("addToCart")) {
                                            int quantity = Integer.parseInt(br.readLine());
                                            if (user.addToCart(product, quantity)) {
                                                pr.println("true");
                                                pr.flush();
                                            } else {
                                                pr.println("false");
                                                pr.flush();
                                            }
                                            market = displayMarketplace(false, search);
                                        }
                                    }
                                } while (!choice2.equals("goBack"));
                            } else if (action.equals("purchaseHistory")) {
                                pr.println(user.displayPastTransactions());
                                pr.flush();
                            } else if (action.equals("displayShoppingCart")) {
                                pr.println(user.displayShoppingCart());
                                pr.flush();
                                pr.println(user.getShoppingCart().size());
                                pr.flush();
                            } else if (action.equals("removeItem")) {
                                int choice3 = Integer.parseInt(br.readLine());
                                user.removeFromCart(user.getShoppingCart().get(choice3 - 1));
                            } else if (action.equals("checkout")) {
                                while (user.getShoppingCart().size() > 0) {
                                    user.buyTicket(user.getShoppingCart().get(0));
                                }
                            } else if (action.equals("statisticsForAllStores")) {
                                String sort = br.readLine();
                                if (sort.equals("y")) {
                                    storeDash(true, pr);
                                } else {
                                    storeDash(false, pr);
                                }
                            } else if (action.equals("statisticsForStoresShopped")) {
                                String sort = br.readLine();
                                if (sort.equals("y")) {
                                    user.customerStoreDash(true, pr);
                                } else {
                                    user.customerStoreDash(false, pr);
                                }
                            }
                        } while (!action.equals("quit"));
                    } else {
                        pr.println("false");
                        pr.flush();
                    }
                }
            } while (!signInUp.equals("quit"));
            pr.close();
            br.close();
            socket.close(); // put these into a finally?
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void storeDash(boolean sort, PrintWriter pr) {
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
        pr.println(stores.size());
        pr.flush();
        if (sort) {
            stores.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach((key) -> {
                        pr.println(key);
                        pr.flush();
                    });
        } else {
            for (String key : stores.keySet()) {
                pr.println(key + "=" + stores.get(key));
                pr.flush();
            }
        }
    }

    public static void customerStats(String seller, boolean sort, PrintWriter pr) {
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
        pr.println(customers.size());
        pr.flush();
        if (sort) {
            customers.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach((key) -> {
                        pr.println(key);
                        pr.flush();
                    });
        } else {
            for (String key : customers.keySet()) {
                pr.println(key + "=" + customers.get(key));
                pr.flush();
            }
        }
    }

    public static void productStats(String seller, boolean sort, PrintWriter pr) {
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
        pr.println(products.size());
        pr.flush();
        if (sort) {
            products.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach((key) -> {
                        pr.println(key);
                        pr.flush();
                    });
        } else {
            for (String key : products.keySet()) {
                pr.println(key + "=" + products.get(key));
                pr.flush();
            }
        }
    }
}
