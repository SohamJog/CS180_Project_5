import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;

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

            // 1 - what would you like to do? (sign in or sign up)
            String signInUp = br.readLine();
            if (signInUp.equals("sellerSignup")) {
                String name = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                if (Seller.signUp(name, email, password)) {
                    pr.println("true");
                } else {
                    pr.println("false");
                }
            } else if (signInUp.equals("userSignup")) {
                String name = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                if (User.signUp(name, email, password)) {
                    pr.println("true");
                } else {
                    pr.println("false");
                }
            } else if (signInUp.equals("sellerSignin")) {
                String email = br.readLine();
                String password = br.readLine();
                Seller seller = Seller.login(email, password);
                if (seller != null) {
                    pr.println("true");
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
                            for (Store s : stores) {
                                pr.println(s.getName());
                            }
                        } else if (action.equals("createNewStore")) {
                            String storeName = br.readLine();
                            if (seller.createStore(storeName)) {
                                pr.println("true");
                            } else {
                                pr.println("false");
                            }
                        } else if (action.equals("enterStore")) {
                            int choice2 = Integer.parseInt(br.readLine());
                            Store store = seller.getStores().get(choice2 -2);
                            ArrayList<Ticket> storeProducts = store.getTickets();
                            for (int i = 0; i < storeProducts.size(); i++) {
                                pr.println((i + 2) + ". " + storeProducts.get(i));
                            }
                            String storeAction = br.readLine();
                            if (storeAction.equals("newTicket")) {
                                try {
                                    String zero = br.readLine();
                                    double one = Double.parseDouble(br.readLine());
                                    String two = br.readLine();
                                    int three = Integer.parseInt(br.readLine());
                                    store.newTickets(zero, seller.getEmail(), one, two, three);
                                    pr.println("true");
                                } catch (Exception e) {
                                    pr.println("false");
                                }
                            } else if (storeAction.equals("accessTicket")) {
                                int ticketNumber = Integer.parseInt(br.readLine());
                                Ticket t = store.getTickets().get(ticketNumber - 2);
                                pr.println(t);
                                String ticketAction = br.readLine();
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
                            }
                        }
                        action = br.readLine();
                    }
                } else {
                    pr.println("false");
                }
            } else if (signInUp.equals("userSignin")) {
                String email = br.readLine();
                String password = br.readLine();
                User user = User.login(email, password);
                if (user != null) {
                    pr.println("true");

                } else {
                    pr.println("false");
                }
            }
            // remember to close the reader and writer (and socket?)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
