import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GUIMain extends JComponent implements Runnable {
    public static String choice;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUIMain());
    }

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

        // Create a new JPanel to hold the input fields
//        signInUp(frame);
//        frame.setVisible(true);
//        if(choice.equals("Sign up")) {
//            frame.removeAll();
            sellerDash(frame);
//        }
        frame.setVisible(true);
    }

    public static void signInUp(JFrame f) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("What would you like to do"));
        String[] options = {"Sign in", "Sign up"};
        JComboBox comboBox = new JComboBox(options);
        panel.add(comboBox);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        // Create a submit button
        JButton submitButton = new JButton("Submit");
        bPanel.add(submitButton);
        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = (String) comboBox.getSelectedItem();
            }
        });

        f.add(bPanel, BorderLayout.SOUTH);
        f.add(panel, BorderLayout.CENTER);
    }

    public static boolean signUp(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
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
                    } catch (Exception f) {
                        f.printStackTrace();
                    }

                    System.out.println(inp);
                    // send "sellerSignup" to the server
                    // then send name, email, and password respectively to the server
                    // server sends back either "true" or success or "false" otherwise

                    // Clear the input fields if the input is valid
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");

                    // Show a success message
                    JOptionPane.showMessageDialog(f, "Submitted successfully!");
                }
            }
        });
        f.add(panel, BorderLayout.CENTER);
        f.add(bPanel, BorderLayout.SOUTH);
        return true;
    }

    public static boolean signIn(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        panel.add(new JLabel("Email: "));
        panel.add(emailField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

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
                    } else {
                        JOptionPane.showMessageDialog(f, "Incorrect email or password");
                    }
                }
            }
        });
        f.add(panel, BorderLayout.CENTER);
        f.add(bPanel, BorderLayout.SOUTH);
        return true;
    }

    public static void sellerDash(JFrame f) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JButton change = new JButton("Change Account Details");
        JButton stores = new JButton("Access Stores");
        JButton cart = new JButton("View Products in Shopping Carts");
        JButton stats = new JButton("View Statistics");
        panel.add(change);
        panel.add(stores);
        panel.add(cart);
        panel.add(stats);
        f.add(panel, BorderLayout.CENTER);
    }

    public static void changeAccountMenu(JFrame f, PrintWriter pr, BufferedReader br) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JButton change = new JButton("Change Account Details");
        JButton stores = new JButton("Access Stores");
        JButton cart = new JButton("View Products in Shopping Carts");
        JButton stats = new JButton("View Statistics");
        panel.add(change);
        panel.add(stores);
        panel.add(cart);
        panel.add(stats);
        f.add(panel, BorderLayout.CENTER);
    }

}
