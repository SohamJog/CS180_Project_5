import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client {
    public static void showSuccessfullSignUpDialog() {
        JOptionPane.showMessageDialog(null, "Connected Successfully",
                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showMistakeDialog() {
        JOptionPane.showMessageDialog(null, "Sorry , An account with this Email already exists or you have used semicolon",
                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to Ticket Emporium",
                "Ticket Emporium", JOptionPane.INFORMATION_MESSAGE);
    }public static String showSigninSignup() {
        String university;
       String[]  options1 = {"Sign in" , "Sign up"};
        university = (String) JOptionPane.showInputDialog(null, "Select your option",
                "Ticket Emporium", JOptionPane.QUESTION_MESSAGE, null,options1,
                options1[0]);
        return university;
    }              // returns a String of "sign in" or "sign up"
    public static String showSignupOptions() {
        String university;
        String[]  options1 = {"Seller" , "Customer"};
        university = (String) JOptionPane.showInputDialog(null, "Select your option",
                "Ticket Emporium", JOptionPane.QUESTION_MESSAGE, null,options1,
                options1[0]);
        return university;
    }

    public static String enterName() {
        String search;

        search = JOptionPane.showInputDialog(null, "Enter your Name",
                "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
        return search;
    }
    public static String enterEmail() {
        String search;

        search = JOptionPane.showInputDialog(null, "Enter your Email",
                "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
        return search;
    }
    public static String enterPassword() {
        String search;

        search = JOptionPane.showInputDialog(null, "Enter your Password",
                "Ticket Emporium", JOptionPane.QUESTION_MESSAGE);
        return search;
    }
    public static void main(String[] args) {
        showWelcomeMessageDialog();
        System.out.println(showSigninSignup());
    }




}

