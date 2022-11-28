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
            String signup = br.readLine();
            // process the Sign-up
            if (signup.equals("sellerSignup")) {
                String name = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                if (Seller.signUp(name, email, password)) {
                    pr.println("true");
                } else {
                    pr.println("false");
                }
            } else if (signup.equals("userSignup")) {
                String name = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                if (User.signUp(name, email, password)) {
                    pr.println("true");
                } else {
                    pr.println("false");
                }
            }

            // proceed to sign in
            // 2 - what would you like to do? (seller or customer sign in)
            String signin = br.readLine();
            if (signin.equals("sellerSignin")) {
                String email = br.readLine();
                String password = br.readLine();
                Seller seller = Seller.login(email, password);
                if (seller != null) {
                    pr.println("true");
//                    int choice;
//                    do {
//                        choice = Integer.parseInt(br.readLine());
//                        if (choice == 1) {
//                            int choice2;
//                            do {
//                                choice2 = Integer.parseInt(br.readLine());
//                                if (choice2 == 1) {
//                                    seller.changeName(br.readLine());
//                                } else if (choice2 == 2) {
//                                    seller.changePassword(br.readLine());
//                                } else if (choice2 == 3) {
//                                    seller.deleteAccount();
//                                    choice = 0;
//                                    break;
//                                }
//                            } while (choice2 != 0);
//                        } else if (choice == 2) {
//                            ArrayList<Store> stores;
//                            int choice2;
//                            do {
//                                stores = seller.getStores();
//                                for (Store s : stores) {
//                                    pr.println(s.getName());
//                                }
//                                choice2 = Integer.parseInt(br.readLine());
//                                // continues here
//                            } while (choice2 != 0);
//                        }
//                    } while (choice != 0);
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
                            // continues here
                        }
                        action = br.readLine();
                    }
                } else {
                    pr.println("false");
                }
            } else if (signin.equals("userSignin")) {
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
