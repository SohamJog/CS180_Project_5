import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * GUIMain class
 * <p>
 * This class contains the code for the main GUI of the program. It contains the functions
 * to create each of the GUI panels, and also contains the main function to run the program
 * and connect the panels together.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version December 10, 2022
 */

public class GUIMain extends JComponent implements Runnable {
    private String choice;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel();
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private JPanel signInUp;
    private JPanel sellerSignUp;
    private JPanel userSignUp;
    private JPanel sellerSignIn;
    private JPanel userSignIn;
    private JPanel sellerDash;
    private JPanel storesMenu;
    private JPanel statisticsMenu;
    private JPanel changeAccountMenu;
    private JPanel prodStats;
    private JPanel storeMenu;
    private JPanel prodMenu;

    private JPanel sellerOrBuyer;
    private JPanel customerDash;
    private JPanel market;
    private JPanel custStats;
    private JPanel storeStats;
    private JPanel cart;
    private JPanel purchaseHistory;
    private JPanel sellerCarts;
    private boolean buyOrNot;

    private String currentUsername;
    private String currentPassword;
    private JPanel allStores;
    private JPanel statsMenu;
    private JPanel specStores;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUIMain());
    }


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

    public JPanel signInUp() {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        // Add an action listener to the submit button
        JButton signIn = new JButton("Sign in");
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "sellerOrBuyer");
                choice = "Sign in";
            }
        });
        JButton signUp = new JButton("Sign up");
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "sellerOrBuyer");
                choice = "Sign up";
            }
        });
        panel.add(signIn);
        panel.add(signUp);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public JPanel sellerOrBuyer(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(4, 1));

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        JButton seller = new JButton("Seller");
        JButton buyer = new JButton("Buyer");
        JButton back = new JButton("Back");

        panel.add(seller);


        panel.add(buyer);
        bPanel.add(back);

        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
        seller.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (choice.equals("Sign up")) {
                    cardLayout.show(mainPanel, "sellerSignUp");
                } else if (choice.equals("Sign in")) {
                    cardLayout.show(mainPanel, "sellerSignIn");
                }
            }
        });
        buyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice.equals("Sign up")) {
                    cardLayout.show(mainPanel, "userSignUp");
                } else if (choice.equals("Sign in")) {
                    cardLayout.show(mainPanel, "userSignIn");
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "signInUp");
            }
        });

        return result;
    }

    public JPanel sellerSignUp(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());

        panel.setLayout(new GridLayout(3, 2));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
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
                String password = new String(passwordField.getPassword());


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

    public JPanel userSignUp(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());

        panel.setLayout(new GridLayout(3, 2));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
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
                String password = new String(passwordField.getPassword());

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


    public JPanel sellerSignIn(JFrame f, PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(2, 2));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
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
                String password = new String(passwordField.getPassword());
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

                    if (inp.equals("true")) {
                        currentPassword = password;
                        currentUsername = email;

                        JOptionPane.showMessageDialog(f, "Signed in successfully!");
                        sellerDash = sellerDash(pr, br, ois);
                        storesMenu = storesMenu(pr, br, ois);
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
    public JPanel userSignIn(JFrame f, PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(2, 2));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
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
                String password = new String(passwordField.getPassword());
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

                    if (inp.equals("true")) {
                        currentUsername = email;
                        currentPassword = password;

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


    public JPanel sellerDash(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

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
        bPanel.add(logOut);
        result.add(panel, BorderLayout.CENTER);
        result.add(bPanel, BorderLayout.SOUTH);
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

    public JPanel sellerCarts(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        pr.println("viewProductsInCustomerShoppingCarts");
        pr.flush();
        java.util.List<String> uCart = null;
        try {
            uCart = (java.util.List<String>) ois.readObject();
            panel.setLayout(new GridLayout(uCart.size() + 1, 1));
            for (String t : uCart) {
                panel.add(new JLabel("<html>" + t + "</html>"));
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

    public JPanel changeAccountMenu(PrintWriter pr, BufferedReader br, String sellerOrBuyer) {
        JPanel panel = new JPanel();
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        JButton change = new JButton("Change Name");
        JButton goBack = new JButton("Go Back");
        bPanel.add(goBack);
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
                if (name != null) {
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
                if (pswd != null) {
                    pr.println("changePassword");
                    pr.flush();
                    pr.println(pswd);
                    pr.flush();
                    currentPassword = pswd;
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
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel ticketMenu(PrintWriter pr, BufferedReader br, int index) {
        buyOrNot = false;
        pr.println(index);
        pr.flush();
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        try {
            String name = br.readLine();
            String seller = br.readLine();
            String store = br.readLine();
            String description = br.readLine();
            String price = br.readLine();
            String quantityInput = br.readLine();
            panel.add(new JLabel(name + " " + seller + " " + store + " " + description + " " + " " + price + " " +
                    quantityInput));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTextField quantity = new JTextField("Enter Number of Tickets Here!", 20);
        panel.add(quantity);
        JButton add = new JButton("Add to Cart");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyOrNot = true;
                pr.println("addToCart");
                pr.flush();
                pr.println(quantity.getText());
                pr.flush();
                try {
                    String inp = br.readLine();
                    if (inp.equals("true")) {
                        JOptionPane.showMessageDialog(null, "Added to cart!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
                    }
//                    pr.println("displayMarketplace");
//                    pr.flush();
                    pr.println("goBack");
                    pr.flush();
                    market = market(pr, br, "false", "false");
                    mainPanel.add(market, "market");
                    cardLayout.show(mainPanel, "market");
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (buyOrNot == false) {
                    pr.println("clear");
                    pr.flush();
                }
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

    public JPanel statisticsMenu(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
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
        bPanel.add(goBack);
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
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel storesMenu(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        pr.println("listStores");
        pr.flush();
        JButton newStore = new JButton("Add new store");
        newStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter the name of the new store");
                if (name != null) {
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Store name cannot be empty");
                    } else {
                        pr.println("createNewStore");
                        pr.flush();
                        pr.println(name);
                        pr.flush();
                        try {
                            if (br.readLine().equals("true")) {
                                JOptionPane.showMessageDialog(panel, "Store created successfully!");
                                storesMenu = storesMenu(pr, br, ois);
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
        bPanel.add(goBack);
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
            panel.setLayout(new GridLayout(numStores + 1, 1));
            panel.add(newStore);
            for (int i = 0; i < numStores; i++) {
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
                            storeMenu = storeMenu(pr, br, ois, Integer.toString(fI));
                            mainPanel.add(storeMenu, "storeMenu");
                            cardLayout.show(mainPanel, "storeMenu");


//                            int inp = Integer.parseInt(br.readLine());
//                            for(int i=0;i<inp;i++) {
//                                System.out.println(
//                                        br.readLine()
//                                );
//                            }
                        } catch (Exception f) {
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
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel customerDash(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        panel.setLayout(new GridLayout(5, 1));
        JButton change = new JButton("Buy Tickets");
        JButton cartB = new JButton("Shopping Cart");
        cartB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //log out and log in again
                pr.println("quit");
                pr.flush();
                //log in again
                pr.println("userSignin");
                pr.flush();
                pr.println(currentUsername);
                pr.flush();
                pr.println(currentPassword);
                pr.flush();
                try {
                    br.readLine();
                } catch (Exception f) {
                    f.printStackTrace();
                }

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
        stats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statsMenu = statsMenu(pr, br, ois);
                mainPanel.add(statsMenu, "statsMenu");
                cardLayout.show(mainPanel, "statsMenu");
            }
        });
        JButton logOut = new JButton("Log Out");
        bPanel.add(logOut);

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                market = market(pr, br, "false", "false");
                mainPanel.add(market, "market");
                cardLayout.show(mainPanel, "market");
            }
        });
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
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel statsMenu(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JButton all = new JButton("Statistics for all stores");
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // jenny
                allStores = allStores(pr, br, ois, "n");
                mainPanel.add(allStores, "allStores");
                cardLayout.show(mainPanel, "allStores");
            }
        });
        JButton specific = new JButton("Statistics for stores I've shopped at");
        specific.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // jenny
                specStores = specStores(pr, br, ois, "n");
                mainPanel.add(specStores, "specStores");
                cardLayout.show(mainPanel, "specStores");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "customerDash");
            }
        });
        buttonPanel.add(goBack);
        panel.add(all);
        panel.add(specific);
        result.add(panel, BorderLayout.CENTER);
        result.add(buttonPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel allStores(PrintWriter pr, BufferedReader br, ObjectInputStream ois, String sort) {
        pr.println("statisticsForAllStores");
        pr.flush();
        pr.println(sort);
        pr.flush();
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        try {
            int size = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(size, 1));
            for (int i = 0; i < size; i++) {
                JLabel store = new JLabel(br.readLine());
                panel.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel buttonPanel = new JPanel();
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "statsMenu");
            }
        });
        JButton s = new JButton("Sort");
        s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allStores = allStores(pr, br, ois, "y");
                mainPanel.add(allStores, "allStores");
                cardLayout.show(mainPanel, "allStores");
            }
        });
        buttonPanel.add(goBack);
        buttonPanel.add(s);
        result.add(panel, BorderLayout.CENTER);
        result.add(buttonPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel specStores(PrintWriter pr, BufferedReader br, ObjectInputStream ois, String sort) {
        pr.println("statisticsForStoresShopped");
        pr.flush();
        pr.println(sort);
        pr.flush();
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        try {
            int size = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(size, 1));
            for (int i = 0; i < size; i++) {
                JLabel store = new JLabel(br.readLine());
                panel.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel buttonPanel = new JPanel();
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "statsMenu");
            }
        });
        JButton s = new JButton("Sort");
        s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specStores = specStores(pr, br, ois, "y");
                mainPanel.add(specStores, "specStores");
                cardLayout.show(mainPanel, "specStores");
            }
        });
        buttonPanel.add(goBack);
        buttonPanel.add(s);
        result.add(panel, BorderLayout.CENTER);
        result.add(buttonPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel market(PrintWriter pr, BufferedReader br, String sortOption, String searchOption) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                market = market(pr, br, "false", "false");
                mainPanel.add(market, "market");
                cardLayout.show(mainPanel, "market");
            }
        });
        if (!searchOption.equals("true")) {
            pr.println("displayMarketplace");
            pr.flush();
            pr.println(sortOption);
            pr.flush();
        }
        try {
            int numTix = Integer.parseInt(br.readLine());
            System.out.println(numTix);
            panel.setLayout(new GridLayout(numTix + 1, 1));
            panel.add(new JLabel("Marketplace"));
            for (int i = 0; i < numTix; i++) {
                String one = br.readLine();
                String two = br.readLine();
                String three = br.readLine();
                String four = br.readLine();
                JButton tix = new JButton(one + " " + two + " " + three + " " + four);
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
        buttonPanel.add(reload);
        result.add(buttonPanel, BorderLayout.SOUTH);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public JPanel storeMenu(PrintWriter pr, BufferedReader br, ObjectInputStream ois, String storeNameP) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        JButton newTix = new JButton("Add new Ticket");
        JButton goBack = new JButton("Go Back");
        bPanel.add(goBack);
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
                        JOptionPane.showMessageDialog(null, "Failed to create ticket",
                                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
                    }
                    pr.println("goBack");
                    pr.flush();
                    storeMenu = storeMenu(pr, br, ois, storeNameP);
                    mainPanel.add(storeMenu, "storeMenu");
                    cardLayout.show(mainPanel, "storeMenu");
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });

        try {
            pr.println("enterStore");
            pr.flush();
            pr.println(storeNameP);
            pr.flush();
            int numTickets = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(numTickets + 1, 1));
            panel.add(newTix);
            for (int i = 0; i < numTickets; i++) {
                String tixName = br.readLine();
                JButton tick = new JButton(tixName);
                final int fI = i;
                tick.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        System.out.println(fI);
                        pr.println("accessTicket");
                        pr.flush();
                        try {
                            prodMenu = prodMenu(pr, br, ois, fI, storeNameP);
                            mainPanel.add(prodMenu, "prodMenu");
                            cardLayout.show(mainPanel, "prodMenu");
                        } catch (Exception f) {
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
        result.add(bPanel, BorderLayout.SOUTH);
        return result;
    }

    public JPanel prodMenu(PrintWriter pr, BufferedReader br, ObjectInputStream ois, int i, String storeNameP) {
        pr.println(i);
        pr.flush();
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(6, 1));
        try {
            String t = br.readLine();
            System.out.println(t);
            if (t.contains("SStore Name")) {
                t = t.replace("SStore Name", "Store Name");
            }
            if (t.contains("SDescription")) {
                t = t.replace("SDescription", "Description");
            }
            if (t.contains("SPrice")) {
                t = t.replace("SPrice", "Price");
            }
            if (t.contains("SQuantity")) {
                t = t.replace("SQuantity", "Quantity");
            }
            panel.add(new JLabel("<html>" + t + "</html>"));
            JButton dTicket = new JButton("Delete Ticket");
            dTicket.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pr.println("deleteTicket");
                    pr.flush();
                }
            });
            panel.add(dTicket);
            JButton tName = new JButton("Change Ticket Name");
            tName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog(panel, "Enter new name",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
                    if (name != null) {
                        pr.println("changeName");
                        pr.flush();
                        pr.println(name);
                        pr.flush();
                        pr.println("goBack");
                        pr.flush();
                        pr.println("accessTicket");
                        pr.flush();
                        prodMenu = prodMenu(pr, br, ois, i, storeNameP);
                        mainPanel.add(prodMenu, "prodMenu");
                        cardLayout.show(mainPanel, "prodMenu");
                    }
                }
            });
            JButton tPrice = new JButton("Change Ticket Price");
            panel.add(tPrice);
            tPrice.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String price = JOptionPane.showInputDialog(panel, "Enter new price",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
                    try {
                        Integer.parseInt(price);
                    } catch (NumberFormatException f) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number",
                                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
                        price = null;
                    }
                    if (price != null) {
                        pr.println("changeTicketPrice");
                        pr.flush();
                        pr.println(price);
                        pr.flush();
                        pr.println("goBack");
                        pr.flush();
                        pr.println("accessTicket");
                        pr.flush();
                        prodMenu = prodMenu(pr, br, ois, i, storeNameP);
                        mainPanel.add(prodMenu, "prodMenu");
                        cardLayout.show(mainPanel, "prodMenu");
                    }
                }
            });
            JButton tDesc = new JButton("Change Ticket Description");
            panel.add(tDesc);
            tDesc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String desc = JOptionPane.showInputDialog(panel, "Enter new description",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
                    if (desc != null) {
                        pr.println("changeTicketDescription");
                        pr.flush();
                        pr.println(desc);
                        pr.flush();
                        pr.println("goBack");
                        pr.flush();
                        pr.println("accessTicket");
                        pr.flush();
                        prodMenu = prodMenu(pr, br, ois, i, storeNameP);
                        mainPanel.add(prodMenu, "prodMenu");
                        cardLayout.show(mainPanel, "prodMenu");
                    }
                }
            });
            JButton tQuant = new JButton("Change Ticket Quantity");
            panel.add(tQuant);
            tQuant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String quant = JOptionPane.showInputDialog(panel, "Enter new quantity",
                            "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
                    try {
                        Integer.parseInt(quant);
                    } catch (NumberFormatException f) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number",
                                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
                        quant = null;
                    }
                    if (quant != null) {
                        pr.println("changeTicketPrice");
                        pr.flush();
                        pr.println(quant);
                        pr.flush();
                        pr.println("goBack");
                        pr.flush();
                        pr.println("accessTicket");
                        pr.flush();
                        prodMenu = prodMenu(pr, br, ois, i, storeNameP);
                        mainPanel.add(prodMenu, "prodMenu");
                        cardLayout.show(mainPanel, "prodMenu");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                pr.println("goBack");
                pr.flush();
                storeMenu = storeMenu(pr, br, ois, storeNameP);
                mainPanel.add(storeMenu, "storeMenu");
                cardLayout.show(mainPanel, "storeMenu");
            }
        });
        buttonPanel.add(goBack);
        result.add(buttonPanel, BorderLayout.SOUTH);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public JPanel cartMenu(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("displayShoppingCart");
        pr.flush();
        java.util.List<Ticket> uCart = null;
        try {
            uCart = (java.util.List<Ticket>) ois.readObject();
            panel.setLayout(new GridLayout(uCart.size() + 1, 2));
            for (int i = 0; i < uCart.size(); i++) {
                panel.add(new JLabel("<html>" + uCart.get(i) + "</html>"));
                JButton remove = new JButton("Remove Ticket");
                int finalI = i;
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(panel,
                                "Do you want to remove this item from the shopping cart?",
                                "Remove Item",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                        if (choice == JOptionPane.YES_OPTION) {
                            pr.println("removeItem");
                            pr.flush();
                            pr.println(finalI);
                            pr.flush();
                        }

                        //log out and log in again
                        pr.println("quit");
                        pr.flush();
                        //log in again
                        pr.println("userSignin");
                        pr.flush();
                        pr.println(currentUsername);
                        pr.flush();
                        pr.println(currentPassword);
                        pr.flush();
                        try {
                            br.readLine();
                        } catch (Exception f) {
                            f.printStackTrace();
                        }

                        cart = cartMenu(pr, br, ois);
                        mainPanel.add(cart, "cart");
                        cardLayout.show(mainPanel, "cart");
                        //

                    }
                });
                panel.add(remove);
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
                customerDash = customerDash(pr, br, ois);
                mainPanel.add(customerDash, "customerDash");
                cardLayout.show(mainPanel, "customerDash");
            }
        });
        buttons.add(goBack);
        JButton checkout = new JButton("Checkout");
        java.util.List<Ticket> finalUCart = uCart;
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                pr.println("checkout");
                pr.flush();
                if (finalUCart.size() > 0) {
                    JOptionPane.showMessageDialog(panel, "Checkout successful!",
                            "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
                    cart = cartMenu(pr, br, ois);
                    mainPanel.add(cart, "cart");
                    cardLayout.show(mainPanel, "cart");
                } else {
                    JOptionPane.showMessageDialog(panel, "Nothing to checkout!",
                            "Ticket Emporium", JOptionPane.ERROR_MESSAGE);
                }

                //
                //log out and log in again
                pr.println("quit");
                pr.flush();
                //log in again
                pr.println("userSignin");
                pr.flush();
                pr.println(currentUsername);
                pr.flush();
                pr.println(currentPassword);
                pr.flush();
                try {
                    br.readLine();
                } catch (Exception f) {
                    f.printStackTrace();
                }
                customerDash = customerDash(pr, br, ois);
                mainPanel.add(customerDash, "customerDash");
                cardLayout.show(mainPanel, "customerDash");
                //
//                pr.println("displayShoppingCart");
//                pr.flush();


            }
        });
        buttons.add(checkout);
        result.add(panel, BorderLayout.CENTER);
        result.add(buttons, BorderLayout.SOUTH);
        return result;
    }

    public JPanel prodStats(PrintWriter pr, BufferedReader br, String sortOrNot) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("viewProductStatistics");
        pr.flush();
        pr.println(sortOrNot);
        pr.flush();
        JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prodStats = prodStats(pr, br, "n");
                mainPanel.add(prodStats, "prodStats");
                cardLayout.show(mainPanel, "prodStats");
            }
        });
        try {
            int rows = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(rows, 1));
            for (int i = 0; i < rows; i++) {
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
        buttons.add(reload);
        result.add(buttons, BorderLayout.SOUTH);
        return result;
    }

    public JPanel custStats(PrintWriter pr, BufferedReader br, String sortOrNot) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                custStats = custStats(pr, br, "n");
                mainPanel.add(custStats, "custStats");
                cardLayout.show(mainPanel, "custStats");
            }
        });
        pr.println("viewCustomerStatistics");
        pr.flush();
        pr.println(sortOrNot);
        pr.flush();
        try {
            int rows = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(rows, 1));
            for (int i = 0; i < rows; i++) {
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
        buttons.add(reload);
        result.add(buttons, BorderLayout.SOUTH);
        return result;
    }

    public JPanel storeStats(PrintWriter pr, BufferedReader br, String sortOrNot) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        JButton reload = new JButton("Reload");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeStats = storeStats(pr, br, "n");
                mainPanel.add(storeStats, "storeStats");
                cardLayout.show(mainPanel, "storeStats");
            }
        });
        pr.println("viewStoreStatistics");
        pr.flush();
        pr.println(sortOrNot);
        pr.flush();
        try {
            int rows = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(rows, 1));
            for (int i = 0; i < rows; i++) {
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
                panel.add(new JLabel(one + "\n" + two + "\n" + three + "\n" + customers));
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
        buttons.add(reload);
        result.add(buttons, BorderLayout.SOUTH);
        return result;

    }

    public JPanel purchaseHistory(PrintWriter pr, BufferedReader br, ObjectInputStream ois) {
        JPanel result = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        pr.println("purchaseHistory");
        pr.flush();
        java.util.List<String> uCart = null;
        try {
            uCart = (java.util.List<String>) ois.readObject();
            panel.setLayout(new GridLayout(uCart.size() + 1, 1));
            for (String t : uCart) {
                String[] temp = t.split("Seller:");
                panel.add(new JLabel(temp[0]));
                String[] temp2 = temp[1].split("Store:");
                panel.add(new JLabel("Seller:" + temp2[0]));
                String[] temp3 = temp2[1].split("Price:");
                panel.add(new JLabel("Store:" + temp3[0]));
                String[] temp4 = temp3[1].split("Description:");
                panel.add(new JLabel("Price:" + temp4[0]));
                String[] temp5 = temp4[1].split("Quantity:");
                panel.add(new JLabel("Description:" + temp5[0]));
                panel.add(new JLabel("Quantity:" + temp5[1]));
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
