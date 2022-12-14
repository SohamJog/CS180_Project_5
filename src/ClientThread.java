import java.io.*;
import java.net.Socket;
import java.util.*;
/**
 * ClientThread class
 * <p>
 * This class contains the code for the client thread, which allows creation of multiple threads.
 * This class is used to handle the client's requests. It also contains the code for the
 * functionalities of the program by reading in user commands and sending back out information
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version December 10, 2022
 */
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
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            pr.println("Connected");
            pr.flush();

            // 1 - what would you like to do? (sign in or sign up)
            String signInUp = br.readLine();
            while (!signInUp.equals("quit")) { // an option to quit - not sure if needed tho
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
                        String action = br.readLine();
                        while (!action.equals("quit")) {
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
                                pr.flush();
                                for (int i = 0; i < storeProducts.size(); i++) {
                                    pr.println(storeProducts.get(i).getName());
                                    pr.flush();
                                }
                                String storeAction = br.readLine();
                                while (!storeAction.equals("goBack")) {
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
                                        Ticket t = store.getTickets().get(ticketNumber); // armanya
                                        pr.println(t.toProduct());
                                        pr.flush();

                                        String ticketAction = br.readLine();
                                        while (!ticketAction.equals("goBack")) {
                                            if (ticketAction.equals("deleteTicket")) {
                                                store.deleteTickets(t.getId());
                                            } else if (ticketAction.equals("changeTicketName")) {
                                                String newTicketName = br.readLine();
                                                t.changeInfo(newTicketName, t.getPrice(), t.getDescription(),
                                                        t.getQuantity());
                                            } else if (ticketAction.equals("changeTicketPrice")) {
                                                double newTicketPrice = Double.parseDouble(br.readLine());
                                                t.changeInfo(t.getName(), newTicketPrice, t.getDescription(),
                                                        t.getQuantity());
                                            } else if (ticketAction.equals("changeTicketDescription")) {
                                                String newTicketDescription = br.readLine();
                                                t.changeInfo(t.getName(), t.getPrice(), newTicketDescription,
                                                        t.getQuantity());
                                            } else if (ticketAction.equals("changeTicketQuantity")) {
                                                int newTicketQuantity = Integer.parseInt(br.readLine());
                                                t.changeInfo(t.getName(), t.getPrice(), t.getDescription(),
                                                        newTicketQuantity);
                                            }
                                            ticketAction = br.readLine();
                                        }
                                    }
                                    storeAction = br.readLine();
                                }
                            } else if (action.equals("viewStoreStatistics")) {
                                pr.println(seller.getStores().size());
                                pr.flush();
                                for (Store s : seller.getStores()) {
                                    pr.println("[" + s.getName() + "]");
                                    pr.flush();
                                    pr.println("-Revenue: $" + s.getRevenue());
                                    pr.flush();
                                    pr.println("-Customer List: ");
                                    pr.flush();
                                    pr.println(s.getCustomerList().size());
                                    pr.flush();
                                    s.getCustomerList().forEach((key) -> {
                                        pr.println(key);
                                        pr.flush();
                                    });
                                }
                            } else if (action.equals("viewCustomerStatistics")) {
                                String sort = br.readLine();
                                if (sort.equals("y")) {
                                    customerStats(seller.getEmail(), true, pr);
                                } else if (sort.equals("n")) {
                                    customerStats(seller.getEmail(), false, pr);
                                }
                            } else if (action.equals("viewProductStatistics")) {
                                String sort = br.readLine();
                                if (sort.equals("y")) {
                                    productStats(seller.getEmail(), true, pr);
                                } else if (sort.equals("n")) {
                                    productStats(seller.getEmail(), false, pr);
                                }
                            } else if (action.equals("viewProductsInCustomerShoppingCarts")) {
                                oos.writeObject(seller.shoppingCart());
                                oos.flush();
                            }
                            action = br.readLine();
                        } // when to send quit??
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
                        String action = br.readLine();
                        // hello guys
                        while (!action.equals("quit")) {
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
                                String sort = br.readLine();
                                market = displayMarketplace(sort, "");
                                pr.println(market.size());
                                pr.flush();
                                for (int i = 0; i < market.size(); i++) {
                                    pr.println(market.get(i).getName());
                                    pr.flush();
                                    pr.println(market.get(i).getSellerEmail());
                                    pr.flush();
                                    pr.println(market.get(i).getStoreName());
                                    pr.flush();
                                    pr.println(market.get(i).getPrice());
                                    pr.flush();
                                }
                                String choice2 = br.readLine();
                                while (!choice2.equals("goBack")) {
//                                    if (choice2.equals("sort")) {
//                                        pr.println(market.size());
//                                        pr.flush();
//                                        market = displayMarketplace(true, search);
                                    if (choice2.equals("search")) {
                                        String searchWord = br.readLine();
                                        market = displayMarketplace("false", searchWord);
                                        pr.println(market.size());
                                        pr.flush();
                                        for (int i = 0; i < market.size(); i++) {
                                            pr.println(market.get(i).getName());
                                            pr.flush();
                                            pr.println(market.get(i).getSellerEmail());
                                            pr.flush();
                                            pr.println(market.get(i).getStoreName());
                                            pr.flush();
                                            pr.println(market.get(i).getPrice());
                                            pr.flush();
                                        }
                                    } else if (choice2.equals("accessTicket")) {


                                        System.out.println("access ticket");

                                        int t = Integer.parseInt(br.readLine());
                                        product = market.get(t);
                                        String name = "Name: " + product.getName();
                                        String seller = "Seller: " + product.getSellerEmail();
                                        String store = "Store Name: " + product.getStoreName();
                                        String description = "Description: " + product.getDescription();
                                        String price = Double.toString(product.getPrice());
                                        String quantityInput = "Quantity: " + product.getQuantity();
                                        pr.println(name);
                                        pr.flush();
                                        pr.println(seller);
                                        pr.flush();
                                        pr.println(store);
                                        pr.flush();
                                        pr.println(description);
                                        pr.flush();
                                        pr.println(price);
                                        pr.flush();
                                        pr.println(quantityInput);
                                        pr.flush();
                                        String choice3 = br.readLine();
                                        if (choice3.equals("addToCart")) {

                                            //soham
                                            System.out.println("add to cart");

                                            int quantity = 0;
                                            boolean ok = true;
                                            try {
                                                quantity = Integer.parseInt(br.readLine());
                                            } catch (NumberFormatException e) {
                                                ok = false;
                                                pr.println("false");
                                                pr.flush();
                                            }
                                            if (ok) {
                                                if (user.addToCart(product, quantity)) {
                                                    pr.println("true");
                                                    pr.flush();
                                                } else {
                                                    pr.println("false");
                                                    pr.flush();
                                                }
                                                //market = displayMarketplace("false", search);
                                            }
                                        }
                                    }
                                    choice2 = br.readLine();
                                }
                            } else if (action.equals("purchaseHistory")) {
                                oos.writeObject(user.displayPastTransactions());
                                oos.flush();
                            } else if (action.equals("displayShoppingCart")) {
                                oos.writeObject(user.getShoppingCart());
                                oos.flush();
                            } else if (action.equals("removeItem")) {
                                int choice3 = Integer.parseInt(br.readLine());
                                user.removeFromCart(user.getShoppingCart().get(choice3));
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
                            action = br.readLine();
                        }
                    } else {
                        pr.println("false");
                        pr.flush();
                    }
                }
                signInUp = br.readLine();
            }
            pr.close();
            br.close();
            socket.close(); // put these into a finally?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Ticket> displayMarketplace(String sort, String search) {
        search = search.toLowerCase();
        File f = new File("availableTickets.txt");
        ArrayList<Ticket> tickets = new ArrayList<>();
        String[] ticketInfo;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            while (line != null) {
                if (!line.equals("")) {
                    ticketInfo = line.split(";");
                    tickets.add(new Ticket(Integer.parseInt(ticketInfo[0]), ticketInfo[1], ticketInfo[2], ticketInfo[3],
                            Double.parseDouble(ticketInfo[4]), ticketInfo[5], Integer.parseInt(ticketInfo[6])));
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!search.equals("")) {
            for (int i = 0; i < tickets.size(); i++) {
                if (!(tickets.get(i).getName().toLowerCase().contains(search) ||
                        tickets.get(i).getStoreName().toLowerCase().contains(search) ||
                        tickets.get(i).getDescription().toLowerCase().contains(search))) {
                    tickets.remove(i);
                    i--;
                }

            }
        }
        if (sort.equals("true")) {
            Collections.sort(tickets, Comparator.comparing(Ticket::getPrice));
        }
        return tickets;
    }

    public static void storeDash(boolean sort, PrintWriter prNet) {
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
        prNet.println(stores.size());
        prNet.flush();
        if (sort) {
            stores.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach((key) -> {
                        prNet.println(key.toString());
                        prNet.flush();
                    });
        } else {
            stores.entrySet()
                    .forEach((key) -> {
                        prNet.println(key.toString());
                        prNet.flush();
                    });
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
