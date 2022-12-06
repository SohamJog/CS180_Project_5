import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GUIMain extends JComponent {
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

        Client client = new Client();

        JFrame frame = new JFrame("Ticket Emporium");

        // Set the size of the frame
        frame.setSize(400, 300);

        // Set the default close operation for the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout for the frame
        frame.setLayout(new BorderLayout());

        // Create a new JPanel to hold the input fields
        String option = signInUp(frame);
        frame.setVisible(true);
    }

    public static String signInUp(JFrame f) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("What would you like to do"));
        String[] options = {"Sign in", "Sign up"};
        JComboBox comboBox = new JComboBox(options);
        panel.add(comboBox);
        final String[] selectedItem = new String[1];

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the JComboBox
                selectedItem[0] = (String)comboBox.getSelectedItem();

            }
        });
        f.add(panel, BorderLayout.CENTER);
        return selectedItem[0];
    }

    public static boolean signUp(JFrame f) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        panel.add(new JLabel("Name: "));
        panel.add(nameField);
        panel.add(new JLabel("Email: "));
        panel.add(passwordField);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());

        // Create a submit button
        JButton submitButton = new JButton("Submit");

        // Add an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user's input from the fields
                String name = nameField.getText();
                String email = emailField.getText();

                // Check if the user's input is valid
                if (name.isEmpty() || email.isEmpty()) {
                    // Show an error message if the input is invalid
                    JOptionPane.showMessageDialog(f, "Please enter a valid name and email.");
                } else {
                    //Jenny - call server
                    // Clear the input fields if the input is valid
                    nameField.setText("");
                    emailField.setText("");

                    // Show a success message
                    JOptionPane.showMessageDialog(f, "Submitted successfully!");
                }
            }
        });
        f.add(panel);
        return true;
    }
}
