import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client {

    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to Ticket Emporium",
                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
    }public static String showHostInputDialog() {
        String university;
       String[]  options1 = {"Sign in" , "Sign up"};
        university = (String) JOptionPane.showInputDialog(null, "Select your option",
                "Browser", JOptionPane.QUESTION_MESSAGE, null,options1,
                options1[0]);
        return university;
    }              // returns a String of "sign in" or "sign up"


}

