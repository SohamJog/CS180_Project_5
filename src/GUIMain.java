import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class GUIMain extends JComponent implements Runnable {
    private static String choice;
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel mainPanel = new JPanel();
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static JPanel signInUp;
    private static JPanel sellerSignUp;
    private static JPanel userSignUp;
    private static JPanel sellerSignIn;
    private static JPanel userSignIn;
    private static JPanel sellerDash;
    private static JPanel storesMenu;
    private static JPanel statisticsMenu;
    private static JPanel changeAccountMenu;
    private static JPanel prodStats;
    private static JPanel storeMenu;

    private static JPanel sellerOrBuyer;
    private static JPanel customerDash;
    private static JPanel market;
    private static JPanel custStats;
    private static JPanel storeStats;
    private static JPanel cart;
    private static JPanel purchaseHistory;
    private static JPanel sellerCarts;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUIMain());
    }

//    public GUIMain() {
//
//    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        ObjectInputStream ois = null;
        try {
            socket = new Socket("localhost", 4242);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to the server!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Client client = new Client();

        JFrame frame = new JFrame("Ticket Emporium");

        // Set the size of the frame
        frame.setSize(1000, 1000);

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout for the frame
        frame.setLayout(new BorderLayout());

        sellerOrBuyer = sellerOrBuyer(frame, writer, reader);

        mainPanel.setLayout(cardLayout);
        signInUp = signInUp();
        sellerSignUp = sellerSignUp(frame, writer, reader);
        userSignUp = userSignUp(frame, writer, reader);
        sellerSignIn = sellerSignIn(frame, writer, reader, ois);
        userSignIn = userSignIn(frame, writer, reader, ois);

//
        mainPanel.add(signInUp, "signInUp");
        mainPanel.add(sellerSignUp, "sellerSignUp");
        mainPanel.add(userSignUp, "userSignUp");
        mainPanel.add(sellerSignIn, "sellerSignIn");
        mainPanel.add(userSignIn, "userSignIn");
        mainPanel.add(sellerOrBuyer, "sellerOrBuyer");
//        mainPanel.add(customerDash, "customerDash");
//        mainPanel.add(market, "market");
        cardLayout.show(mainPanel, "signInUp");
        frame.add(mainPanel);

        // Create a new JPanel to hold the input fields
//        signInUp(frame);
//        frame.setVisible(true);
//        if(choice.equals("Sign up")) {
//            frame.removeAll();
//            sellerDash(frame);
//        }
//        writer.println("sellerSignin");
//        writer.flush();
//        writer.println("soham");
//        writer.flush();
//        writer.println("soham");
//        writer.flush();
//        try {
//            reader.readLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        storesMenu(writer, reader);
        frame.setVisible(true);
    }

    public static JPanel signInUp() {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("What would you like to do"));
        String[] options = {"Sign in", "Sign up"};
        JComboBox comboBox = new JComboBox(options);
        panel.add(comboBox);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        //create a jbutton to go back to seller or buyer




        // Create a submit button
        JButton submitButton = new JButton("Submit");
        bPanel.add(submitButton);
        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = (String) comboBox.getSelectedItem();
                cardLayout.show(mainPanel, "sellerOrBuyer");
            }
        });

        result.add(bPanel, BorderLayout.SOUTH);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }
    public static JPanel sellerOrBuyer(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(4, 1));

        JButton seller = new JButton("Seller");
        JButton buyer = new JButton("Buyer");

        panel.add(seller);

        panel.add(buyer);

        result.add(panel, BorderLayout.CENTER);
        seller.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (choice.equals("Sign up")) {
                    cardLayout.show(mainPanel, "sellerSignUp");
                }
                else if (choice.equals("Sign in")) {
                    cardLayout.show(mainPanel, "sellerSignIn");
                }
            }
        });
        buyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice.equals("Sign up"))
                {
                    cardLayout.show(mainPanel, "userSignUp");
                }
                else if (choice.equals("Sign in")) {
                    cardLayout.show(mainPanel, "userSignIn");
                }
            }
        });

        return result;
    }

    public static JPanel sellerSignUp(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());

        panel.setLayout(new GridLayout(3,2));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        panel.add(new JLabel("Name: "));
        panel.add(nameField);
        panel.add(new JLabel("Email: "));
        panel.add(emailField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        JButton goBack = new JButton("Go back");
        bPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signInUp");
               // pr.println("quit");
               // pr.flush();
            }

        });

        // Create a submit button
        JButton submitButton = new JButton("Submit");
        bPanel.add(submitButton);
        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's input from the fields
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();

                // Check if the user's input is valid
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    // Show an error message if the input is invalid
                    JOptionPane.showMessageDialog(f, "Please enter a valid name, email, and password.");
                } else {
                    //Jenny - call server
                    pr.println("sellerSignup");
                    pr.flush();
                    pr.println(name);
                    pr.flush();
                    pr.println(email);
                    pr.flush();
                    pr.println(password);
                    pr.flush();
                    String inp = "bananas";
                    try {
                        inp = br.readLine();
                        if (inp.equals("true")) {
                            // Show a success message
                            JOptionPane.showMessageDialog(f, "Submitted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(f, "Account already exists!");
                        }
                        cardLayout.show(mainPanel, "signInUp");
                        // Clear the input fields if the input is valid
                        nameField.setText("");
                        emailField.setText("");
                        passwordField.setText("");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }

                    System.out.println(inp);
                    // send "sellerSignup" to the server
                    // then send name, email, and password respectively to the server
                    // server sends back either "true" or success or "false" otherwise
                }
            }
        });
        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public static JPanel userSignUp(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());

        panel.setLayout(new GridLayout(3,2));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        panel.add(new JLabel("Name: "));
        panel.add(nameField);
        panel.add(new JLabel("Email: "));
        panel.add(emailField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        JButton goBack = new JButton("Go back");
        bPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signInUp");
                // pr.println("quit");
                // pr.flush();
            }

        });

        // Create a submit button
        JButton submitButton = new JButton("Submit");
        bPanel.add(submitButton);
        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's input from the fields
                String name = nameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();

                // Check if the user's input is valid
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    // Show an error message if the input is invalid
                    JOptionPane.showMessageDialog(f, "Please enter a valid name, email, and password.");
                } else {
                    //Jenny - call server
                    pr.println("userSignup");
                    pr.flush();
                    pr.println(name);
                    pr.flush();
                    pr.println(email);
                    pr.flush();
                    pr.println(password);
                    pr.flush();
                    String inp = "bananas";
                    try {
                        inp = br.readLine();
                        if (inp.equals("true")) {
                            // Show a success message
                            JOptionPane.showMessageDialog(f, "Submitted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(f, "Account already exists!");
                        }
                        cardLayout.show(mainPanel, "signInUp");
                        // Clear the input fields if the input is valid
                        nameField.setText("");
                        emailField.setText("");
                        passwordField.setText("");
                    } catch (Exception f) {
                        f.printStackTrace();
                    }

                    System.out.println(inp);
                    // send "userSignup" to the server
                    // then send name, email, and password respectively to the server
                    // server sends back either "true" or success or "false" otherwise
                }
            }
        });
        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }



    public static JPanel sellerSignIn(JFrame f, PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(2,2));
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        panel.add(new JLabel("Email: "));
        panel.add(emailField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        JButton goBack = new JButton("Go back");
        bPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signInUp");
                //pr.println("quit");
                //pr.flush();
            }

        });

        // Create a submit button
        JButton submitButton = new JButton("Submit");
        bPanel.add(submitButton);
        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's input from the fields
                String email = emailField.getText();
                String password = passwordField.getText();
                emailField.setText("");
                passwordField.setText("");
                // Check if the user's input is valid
                if (email.isEmpty() || password.isEmpty()) {
                    // Show an error message if the input is invalid
                    JOptionPane.showMessageDialog(f, "Please enter a valid name, email, and password.");
                } else {
                    //Jenny - call server
                    pr.println("sellerSignin");
                    pr.flush();
                    pr.println(email);
                    pr.flush();
                    pr.println(password);
                    pr.flush();
                    String inp = "bananas";
                    try {
                        inp = br.readLine();
                    } catch (Exception f) {
                        f.printStackTrace();
                    }

                    if(inp.equals("true")) {
                        JOptionPane.showMessageDialog(f, "Signed in successfully!");
                        sellerDash = sellerDash(pr, br, ois);
                        storesMenu = storesMenu(pr, br);
                        statisticsMenu = statisticsMenu(pr, br);
                        mainPanel.add(sellerDash, "sellerDash");
                        mainPanel.add(statisticsMenu, "statisticsMenu");
                        mainPanel.add(storesMenu, "storesMenu");
                        cardLayout.show(mainPanel, "sellerDash");
                    } else {
                        JOptionPane.showMessageDialog(f, "Incorrect email or password");
                    }
                }
            }
        });
        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }


    ///
    public static JPanel userSignIn(JFrame f, PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(2,2));
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        panel.add(new JLabel("Email: "));
        panel.add(emailField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        JButton goBack = new JButton("Go back");
        bPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signInUp");
                //pr.println("quit");
                //pr.flush();
            }

        });

        // Create a submit button
        JButton submitButton = new JButton("Submit");
        bPanel.add(submitButton);
        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's input from the fields
                String email = emailField.getText();
                String password = passwordField.getText();
                emailField.setText("");
                passwordField.setText("");
                // Check if the user's input is valid
                if (email.isEmpty() || password.isEmpty()) {
                    // Show an error message if the input is invalid
                    JOptionPane.showMessageDialog(f, "Please enter a valid name, email, and password.");
                } else {
                    //Jenny - call server
                    pr.println("userSignin");
                    pr.flush();
                    pr.println(email);
                    pr.flush();
                    pr.println(password);
                    pr.flush();
                    String inp = "bananas";
                    try {
                        inp = br.readLine();
                    } catch (Exception f) {
                        f.printStackTrace();
                    }

                    //System.out.println(inp);

                    if(inp.equals("true")) {
                        JOptionPane.showMessageDialog(f, "Signed in successfully!");
                        customerDash = customerDash(pr, br, ois);
                        mainPanel.add(customerDash, "customerDash");
                        cardLayout.show(mainPanel, "customerDash");
                    } else {
                        JOptionPane.showMessageDialog(f, "Incorrect email or password");
                        System.out.println(inp);
                    }
                }
            }
        });
        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }



    public static JPanel sellerDash(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(4, 1));
        JButton change = new JButton("Change Account Details");
        JButton stores = new JButton("Access Stores");
        JButton cart = new JButton("View Products in Shopping Carts");
        JButton stats = new JButton("View Statistics");

        JButton logOut = new JButton("Log Out");


        panel.add(stores);
        panel.add(cart);
        panel.add(change);
        panel.add(stats);
        panel.add(logOut);
        result.add(panel, BorderLayout.CENTER);
        stores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "storesMenu");
            }
        });
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signInUp");
                pr.println("quit");
                pr.flush();
            }
        });
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeAccountMenu = changeAccountMenu(pr, br, "seller");
                mainPanel.add(changeAccountMenu, "changeAccountMenu");
                cardLayout.show(mainPanel, "changeAccountMenu");
            }
        });
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "statisticsMenu");
            }
        });
        cart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellerCarts = sellerCarts(pr, br, ois);
                mainPanel.add(sellerCarts, "sellerCarts");
                cardLayout.show(mainPanel, "sellerCarts");
            }
        });
        return result;
    }

    public static JPanel sellerCarts(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        pr.println("viewProductsInCustomerShoppingCarts");
        pr.flush();
        java.util.List<String> uCart = null;
        try {
            uCart = (java.util.List<String>) ois.readObject();
            panel.setLayout(new GridLayout(uCart.size()+1, 1));
            for(String t : uCart) {
                panel.add(new JLabel(t));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton goBack = new JButton("Go back");
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        bPanel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "sellerDash");
            }
        });
        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public static JPanel changeAccountMenu(PrintWriter pr, BufferedReader br, String sellerOrBuyer) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        JButton change = new JButton("Change Name");
        JButton goBack = new JButton("Go Back");
        panel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("goBack");
//                pr.flush();
                if (sellerOrBuyer.equals("seller")) {
                    cardLayout.show(mainPanel, "sellerDash");
                } else if (sellerOrBuyer.equals("customer")) {
                    cardLayout.show(mainPanel, "customerDash");
                }
            }
        });
        JButton pswd = new JButton("Change Password");
        JButton dlt = new JButton("Delete Account");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter your new name");
                if(name != null) {
                    pr.println("changeName");
                    pr.flush();
                    pr.println(name);
                    pr.flush();
                    JOptionPane.showMessageDialog(panel, "Name changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid name");
                }
            }
        });
        pswd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pswd = JOptionPane.showInputDialog("Enter your new password");
                if(pswd != null) {
                    pr.println("changePassword");
                    pr.flush();
                    pr.println(pswd);
                    pr.flush();
                    JOptionPane.showMessageDialog(panel, "Password changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid password");
                }
            }
        });
        dlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("deleteAccount");
                pr.flush();
                JOptionPane.showMessageDialog(panel, "Account deleted");
                cardLayout.show(mainPanel, "signInUp");
            }
        });
        panel.add(change);
        panel.add(pswd);
        panel.add(dlt);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel ticketMenu(PrintWriter pr, BufferedReader br, int index) {
        pr.println(index);
        pr.flush();
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        try {
            panel.add(new JLabel(br.readLine() + " " + br.readLine() + " " + br.readLine() + " " + br.readLine() + " " + br.readLine() + " " + br.readLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTextField quantity = new JTextField("Enter Number of Tickets Here!", 20);
        panel.add(quantity);
        JButton add = new JButton("Add to Cart");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("addToCart");
                pr.flush();

                pr.println(quantity.getText());
                pr.flush();
                try {
                    String inp = br.readLine();
                    if(inp.equals("true")) {
                        JOptionPane.showMessageDialog(null, "Added to cart!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
                        pr.println("goBack");
                        pr.flush();
                        cardLayout.show(mainPanel, "market");
                    }
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                market = market(pr, br, "false", "false");
                mainPanel.add(market, "market");
                cardLayout.show(mainPanel, "market");
            }
        });
        panel.add(add);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(goBack);
        result.add(panel, BorderLayout.CENTER);
        result.add(buttonPanel, BorderLayout.SOUTH);
        return result;
    }

    public static JPanel statisticsMenu(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("goBack");
//                pr.flush();
                cardLayout.show(mainPanel, "sellerDash");
            }
        });
        panel.add(goBack);
        JButton store = new JButton("View Statistics by Store");
        store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeStats = storeStats(pr, br, "n");
                mainPanel.add(storeStats, "storeStats");
                cardLayout.show(mainPanel, "storeStats");
            }
        });
        JButton product = new JButton("View Statistics by Product");
        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prodStats = prodStats(pr, br, "n");
                mainPanel.add(prodStats, "prodStats");
                cardLayout.show(mainPanel, "prodStats");
            }
        });
        JButton customer = new JButton("View Statistics by Customer");
        customer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                custStats = custStats(pr, br, "n");
                mainPanel.add(custStats, "custStats");
                cardLayout.show(mainPanel, "custStats");
            }
        });
        panel.add(store);
        panel.add(product);
        panel.add(customer);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel storesMenu(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        pr.println("listStores");
        pr.flush();
        JButton newStore = new JButton("Add new store");
        newStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter the name of the new store");
                if (name != null) {
                    if(name.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Store name cannot be empty");
                    } else {
                        pr.println("createNewStore");
                        pr.flush();
                        pr.println(name);
                        pr.flush();
                        try {
                            if (br.readLine().equals("true")) {
                                JOptionPane.showMessageDialog(panel, "Store created successfully!");
                                storesMenu = storesMenu(pr, br);
                                mainPanel.add(storesMenu, "storesMenu");
                                cardLayout.show(mainPanel, "storesMenu");
                            } else {
                                JOptionPane.showMessageDialog(panel, "Store with that name already exists!");
                            }
                        } catch (Exception f) {
                            f.printStackTrace();
                        }
                    }
                }
            }
        });
        JButton goBack = new JButton("Go Back");
        panel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("goBack");
//                pr.flush();
                cardLayout.show(mainPanel, "sellerDash");
            }
        });

        try {
            int numStores = Integer.parseInt(br.readLine());
            System.out.println(numStores);
            panel.setLayout(new GridLayout(numStores+1, 1));
            panel.add(newStore);
            for(int i = 0; i < numStores; i++) {
                String storeName = br.readLine();
                JButton store = new JButton(storeName);
                final int fI = i;
                store.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //System.out.println(fI);
//                        pr.println("enterStore");
//                        pr.flush();
//                        pr.println(fI);
//                        pr.flush();
                        try {
                            //add store menu
                            storeMenu = storeMenu(pr, br, Integer.toString(fI));
                            mainPanel.add(storeMenu, "storeMenu");
                            cardLayout.show(mainPanel, "storeMenu");


//                            int inp = Integer.parseInt(br.readLine());
//                            for(int i=0;i<inp;i++) {
//                                System.out.println(
//                                        br.readLine()
//                                );
//                            }
                        }
                        catch (Exception f) {
                            f.printStackTrace();
                        }

                    }
                });
                panel.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel customerDash(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(5, 1));
        JButton change = new JButton("Buy Tickets");
        JButton cartB = new JButton("Shopping Cart");
        cartB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cart = cartMenu(pr, br, ois);
                mainPanel.add(cart, "cart");
                cardLayout.show(mainPanel, "cart");
            }
        });
        JButton account = new JButton("Change Account Info");
        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeAccountMenu = changeAccountMenu(pr, br, "customer");
                mainPanel.add(changeAccountMenu, "changeAccountMenu");
                cardLayout.show(mainPanel, "changeAccountMenu");
            }
        });
        JButton history = new JButton("View Purchase History");
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseHistory = purchaseHistory(pr, br, ois);
                mainPanel.add(purchaseHistory, "purchaseHistory");
                cardLayout.show(mainPanel, "purchaseHistory");
            }
        });
        JButton stats = new JButton("View Statistics");
        JButton logOut = new JButton("Log Out");
        panel.add(logOut);

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                market = market(pr, br, "false", "false");
                mainPanel.add(market, "market");
                cardLayout.show(mainPanel, "market");
            }
        });

//        JButton reload = new JButton("Reload");
//        reload.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // todo - jenny
//            }
//        });
//        panel.add(reload);
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("quit");
                pr.flush();
                cardLayout.show(mainPanel, "signInUp");
            }
        });
        panel.add(change);
        panel.add(cartB);
        panel.add(account);
        panel.add(history);
        panel.add(stats);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel market(PrintWriter pr, BufferedReader br, String sortOption, String searchOption) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        if (!searchOption.equals("true")) {
            pr.println("displayMarketplace");
            pr.flush();
            pr.println(sortOption);
            pr.flush();
        }
        try {
            int numTix = Integer.parseInt(br.readLine());
            System.out.println(numTix);
            panel.setLayout(new GridLayout(numTix+1, 1));
            panel.add(new JLabel("Marketplace"));
            for(int i = 0; i < numTix; i++) {
                String one = br.readLine();
                String two = br.readLine();
                String three = br.readLine();
                String four = br.readLine();
                JButton tix = new JButton(one+" "+two+" "+three+" "+four);
                final int fI = i;
                tix.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pr.println("accessTicket");
                        pr.flush();
                        JPanel ticket = ticketMenu(pr, br, fI);
                        mainPanel.add(ticket, "ticket");
                        cardLayout.show(mainPanel, "ticket");
                    }
                });
                panel.add(tix);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "customerDash");
            }
        });
        buttonPanel.add(goBack);
        JButton sort = new JButton("Sort");
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("sort");
//                pr.flush();
                try {
                    pr.println("goBack");
                    pr.flush();
                    market = market(pr, br, "true", "false");
                    mainPanel.add(market, "market");
                    cardLayout.show(mainPanel, "market");
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });
        JTextField search = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("search");
                pr.flush();
                pr.println(search.getText());
                pr.flush();
                market = market(pr, br, "false", "true");
                mainPanel.add(market, "market");
                cardLayout.show(mainPanel, "market");
            }
        });
        buttonPanel.add(sort);
        buttonPanel.add(search);
        buttonPanel.add(searchButton);
        result.add(buttonPanel, BorderLayout.SOUTH);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel storeMenu(PrintWriter pr, BufferedReader br, String storeNameP) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JButton newTix = new JButton("Add new Ticket");
        JButton goBack = new JButton("Go Back");
        panel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "storesMenu");
            }
        });
        newTix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("newTicket");
                pr.flush();
                try {
                    String name = JOptionPane.showInputDialog("Enter the name of the ticket");
                    String price = JOptionPane.showInputDialog("Enter the price of the ticket");
                    String quantity = JOptionPane.showInputDialog("Enter the quantity of the ticket");
                    String description = JOptionPane.showInputDialog("Enter the description of the ticket");
                    pr.println(name);
                    pr.flush();
                    pr.println(price);
                    pr.flush();
                    pr.println(description);
                    pr.flush();
                    pr.println(quantity);
                    pr.flush();
                    String ticketCreation = br.readLine();
                    if (ticketCreation.equals("false")) {
                        pr.println(JOptionPane.showInputDialog(panel, "Failed to add ticket",
                                "Ticket Emporium", JOptionPane.QUESTION_MESSAGE));
                    }
                    pr.println("goBack");
                    pr.flush();
                    storeMenu = storeMenu(pr, br, storeNameP);
                    mainPanel.add(storeMenu, "storeMenu");
                    cardLayout.show(mainPanel, "storeMenu"); // bug here
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });

        JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                storeMenu = storeMenu(pr, br, storeNameP);
                mainPanel.add(storeMenu, "storeMenu");
                cardLayout.show(mainPanel, "storeMenu");
            }
        });
        panel.add(reload);

        try {
            pr.println("enterStore");
            pr.flush();
            pr.println(storeNameP);
            pr.flush();
            int numTickets = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(numTickets+1, 1));
            panel.add(newTix);
            for(int i = 0; i < numTickets; i++) {
                String tixName = br.readLine();
                JButton tick = new JButton(tixName);
                final int fI = i;
                tick.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(fI);
                        pr.println("accessTicket");
                        pr.flush();
                        pr.println(fI);
                        pr.flush();
                        try {
                            int inp = Integer.parseInt(br.readLine());
                            for(int i=0;i<inp;i++) {
                                System.out.println(br.readLine());
                            }
                        }
                        catch (Exception f) {
                            f.printStackTrace();
                        }

                    }
                });
                panel.add(tick);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static void prodMenu(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        try {
            panel.add(new JLabel(br.readLine()));
            JButton dTicket = new JButton("Delete Ticket");
            dTicket.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pr.println("deleteTicket");
                    pr.flush();
                }
            });
            JButton tName = new JButton("Change Ticket Name");
            tName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pr.println("changeTicketName");
                    pr.flush();
                    pr.println(JOptionPane.showInputDialog(panel, "Enter new name",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE));
                }
            });
            JButton tPrice = new JButton("Change Ticket Price");
            tPrice.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pr.println("changeTicketPrice");
                    pr.flush();

                    pr.println(JOptionPane.showInputDialog(panel, "Enter new price",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE));
                }
            });
            JButton tDesc = new JButton("Change Ticket Description");
            tDesc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pr.println("changeTicketDescription");
                    pr.flush();
                    pr.println(JOptionPane.showInputDialog(panel, "Enter new description",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE));
                }
            });
            JButton tQuant = new JButton("Change Ticket Quantity");
            tQuant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pr.println("changeTicketQuantity");
                    pr.flush();
                    pr.println(JOptionPane.showInputDialog(panel, "Enter new quantity",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel cartMenu(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("displayShoppingCart");
        pr.flush();
        java.util.List<Ticket> uCart = null;
        try {
            uCart = (java.util.List<Ticket>) ois.readObject();
            panel.setLayout(new GridLayout(uCart.size()+1, 1));
            for(Ticket t : uCart) {
                panel.add(new JLabel(t.toString()));
                System.out.println(t.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo - jenny
            }
        });
        JPanel buttons = new JPanel(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("goBack");
//                pr.flush();
                customerDash = customerDash(pr, br, ois);
                mainPanel.add(customerDash, "customerDash");
                cardLayout.show(mainPanel, "customerDash");
            }
        });
        buttons.add(reload);
        buttons.add(goBack);
        JButton checkout = new JButton("Checkout");
        java.util.List<Ticket> finalUCart = uCart;
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("checkout");
                pr.flush();
                if(finalUCart.size() > 0) {
                    JOptionPane.showMessageDialog(panel, "Checkout successful!",
                            "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
                    cart = cartMenu(pr, br, ois);
                    mainPanel.add(cart, "cart");
                    cardLayout.show(mainPanel, "cart");
                } else {
                    JOptionPane.showMessageDialog(panel, "Nothing to checkout!",
                            "Ticket Emporium", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttons.add(checkout);
        result.add(panel, BorderLayout.CENTER);
        result.add(buttons, BorderLayout.SOUTH);
        return result;
    }

    public static JPanel prodStats(PrintWriter pr, BufferedReader br, String sortOrNot) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("viewProductStatistics");
        pr.flush();
        pr.println(sortOrNot);
        pr.flush();
        try {
            int rows = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(rows, 1));
            for(int i = 0; i < rows; i++) {
                panel.add(new JLabel(br.readLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.add(panel, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "statisticsMenu");
            }
        });
        JButton sort = new JButton("Sort");
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prodStats = prodStats(pr, br, "y");
                mainPanel.add(prodStats, "prodStats");
                cardLayout.show(mainPanel, "prodStats");
            }
        });
        buttons.add(goBack);
        buttons.add(sort);
        result.add(buttons, BorderLayout.SOUTH);
        return result;
    }

    public static JPanel custStats(PrintWriter pr, BufferedReader br, String sortOrNot) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("viewCustomerStatistics");
        pr.flush();
        pr.println(sortOrNot);
        pr.flush();
        try {
            int rows = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(rows, 1));
            for(int i = 0; i < rows; i++) {
                panel.add(new JLabel(br.readLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.add(panel, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "statisticsMenu");
            }
        });
        JButton sort = new JButton("Sort");
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                custStats = custStats(pr, br, "y");
                mainPanel.add(custStats, "custStats");
                cardLayout.show(mainPanel, "custStats");
            }
        });
        buttons.add(goBack);
        buttons.add(sort);
        result.add(buttons, BorderLayout.SOUTH);
        return result;
    }

    public static JPanel storeStats(PrintWriter pr, BufferedReader br, String sortOrNot) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("viewStoreStatistics");
        pr.flush();
        pr.println(sortOrNot);
        pr.flush();
        try {
            int rows = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(rows, 1));
            for(int i = 0; i < rows; i++) {
                String one = br.readLine();
                String two = br.readLine();
                String three = br.readLine();
                int customerNum = Integer.parseInt(br.readLine());
                String customers = "";
                if (customerNum != 0) {
                    customers += br.readLine();
                    for (int j = 1; j < customerNum; j++) {
                        customers += ", " + br.readLine();
                    }
                }
                panel.add(new JLabel( one + "\n" + two + "\n" + three + "\n" + customers));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.add(panel, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("goBack");
//                pr.flush();
                cardLayout.show(mainPanel, "statisticsMenu");
            }
        });
        JButton sort = new JButton("Sort");
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeStats = storeStats(pr, br, "y");
                mainPanel.add(storeStats, "storeStats");
                cardLayout.show(mainPanel, "storeStats");
            }
        });
        buttons.add(goBack);
        buttons.add(sort);
        result.add(buttons, BorderLayout.SOUTH);
        return result;

    }

    public static JPanel purchaseHistory(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("purchaseHistory");
        pr.flush();
        java.util.List<String> uCart = null;
        try {
            uCart = (java.util.List<String>) ois.readObject();
            panel.setLayout(new GridLayout(uCart.size()+1, 1));
            for(String t : uCart) {
                panel.add(new JLabel(t));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel buttons = new JPanel(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                pr.println("goBack");
//                pr.flush();
                cardLayout.show(mainPanel, "customerDash");
            }
        });
        buttons.add(goBack);
        result.add(buttons, BorderLayout.SOUTH);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }
}
