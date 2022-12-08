import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GUIMain extends JComponent implements Runnable {
    private static String choice;
    private static CardLayout cardLayout = new CardLayout();
    private static JPanel mainPanel = new JPanel();
    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;

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

        Client client = new Client();

        JFrame frame = new JFrame("Ticket Emporium");

        // Set the size of the frame
        frame.setSize(400, 300);

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout for the frame
        frame.setLayout(new BorderLayout());

        mainPanel.setLayout(cardLayout);
        JPanel signInUp = signInUp();
        JPanel signUp = signUp(frame, writer, reader);
        JPanel signIn = signIn(frame, writer, reader);
//        JPanel customerDash = customerDash();
//        JPanel market = market(writer, reader);
        mainPanel.add(signInUp, "signInUp");
        mainPanel.add(signUp, "signUp");
        mainPanel.add(signIn, "signIn");
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
                if (choice.equals("Sign up")) {
                    cardLayout.show(mainPanel, "signUp");
                } else if (choice.equals("Sign in")) {
                    cardLayout.show(mainPanel, "signIn");
                }
            }
        });

        result.add(bPanel, BorderLayout.SOUTH);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }
    public static JPanel sellerOrBuyer() {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(2, 1));
        JButton change = new JButton("Seller");
        JButton stores = new JButton("Buyer");

        panel.add(stores);

        panel.add(change);

        result.add(panel, BorderLayout.CENTER);

        return result;
    }

    public static JPanel signUp(JFrame f, PrintWriter pr, BufferedReader br) {
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

    public static JPanel signIn(JFrame f, PrintWriter pr, BufferedReader br) {
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
                        JPanel sellerDash = sellerDash(pr, br);
                        JPanel storesMenu = storesMenu(pr, br);
                        JPanel statisticsMenu = statisticsMenu(pr, br);
                        JPanel changeAccountMenu = changeAccountMenu(pr, br);
                        mainPanel.add(sellerDash, "sellerDash");
                        mainPanel.add(statisticsMenu, "statisticsMenu");
                        mainPanel.add(storesMenu, "storesMenu");
                        mainPanel.add(changeAccountMenu, "changeAccountMenu");
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

    public static JPanel sellerDash(PrintWriter pr, BufferedReader br) {
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
        return result;
    }

    public static JPanel changeAccountMenu(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        JButton change = new JButton("Change Name");
        JButton stores = new JButton("Change Password");
        JButton cart = new JButton("Delete Account");
        JButton goBack = new JButton("Go Back");
        panel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "sellerDash");
            }
        });
        panel.add(change);
        panel.add(stores);
        panel.add(cart);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel statisticsMenu(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(3, 1));
        JButton change = new JButton("View Statistics by Store");
        JButton stores = new JButton("View Statistics by Product");
        JButton cart = new JButton("View Statistics by Customer");
        JButton goBack = new JButton("Go Back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "sellerDash");
            }
        });
        panel.add(goBack);
        panel.add(change);
        panel.add(stores);
        panel.add(cart);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel storesMenu(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        pr.println("listStores");
        pr.flush();
        JButton newStore = new JButton("Add new store");
        JButton goBack = new JButton("Go Back");
        panel.add(goBack);
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("goBack");
                pr.flush();
                cardLayout.show(mainPanel, "sellerDash");
            }
        });
        try {
            int numStores = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(numStores+1, 1));
            panel.add(newStore);
            for(int i = 0; i < numStores; i++) {
                String storeName = br.readLine();
                JButton store = new JButton(storeName);
                final int fI = i;
                store.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(fI);
                        pr.println("enterStore");
                        pr.flush();
                        pr.println(fI);
                        pr.flush();
                        try {
                            //add store menu
                            JPanel storeMenu = storeMenu(pr, br);
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

    public static JPanel customerDash(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        panel.setLayout(new GridLayout(5, 1));
        JButton change = new JButton("Buy Tickets");
        JButton stores = new JButton("Shopping Cart");
        JButton cart = new JButton("Change Account Info");
        JButton history = new JButton("View Purchase History");
        JButton stats = new JButton("View Statistics");
        JButton logOut = new JButton("Log Out");
        panel.add(logOut);
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println("quit");
                pr.flush();
                cardLayout.show(mainPanel, "signInUp");
            }
        });
        panel.add(change);
        panel.add(stores);
        panel.add(cart);
        panel.add(history);
        panel.add(stats);
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel market(PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        JPanel result = new JPanel(new BorderLayout());
        pr.println("displayMarketplace");
        pr.flush();
        JButton newStore = new JButton("Add new store");
        try {
            int numStores = Integer.parseInt(br.readLine());
            panel.setLayout(new GridLayout(numStores+1, 1));
            panel.add(newStore);
            for(int i = 0; i < numStores; i++) {
                String storeName = br.readLine();
                JButton store = new JButton(storeName);

                panel.add(store);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.add(panel, BorderLayout.CENTER);
        return result;
    }

    public static JPanel storeMenu(PrintWriter pr, BufferedReader br) {
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
        try {
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

    }

}
