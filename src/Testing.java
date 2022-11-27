/*
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.*;

public class Testing {


    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;


    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;


    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    @DisplayName("Signs up")
    public void signUp() {
        String input = "2\n1\nsoham\nsoham\nsoham\n1\nsoham\nsoham\n0";
        String expectedOutput = "Welcome to Ticket Emporium\n" +
                "What would you like to do?\n" +
                "1. Sign-in\n" +
                "2. Sign up\n" +
                "What role would you like to sign up for?\n" +
                "1. Seller\n" +
                "2. Customer\n" +
                "Name: \n" +
                "Email: \n" +
                "Password: \n" +
                "Successfully Signed Up!\n" +
                "What would you like to do?\n" +
                "1. Seller Sign-in\n" +
                "2. Customer Sign-in\n" +
                "Email: \n" +
                "Password: \n" +
                "What would you like to do, soham?\n" +
                "0. Quit\n" +
                "1. Change Account Details\n" +
                "2. Access Stores\n" +
                "3. View Statistics";

        receiveInput(input);
        Main.main(new String[0]);

        String output = getOutput();
        output = output.replace("\r\n", "\n");

        Assert.assertEquals("Verify that the output matches!", expectedOutput, output);


    }



}

 */


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.DisplayName;

/**
 * Testing class
 * <p>
 * This class contains the code for testing the program, including
 * the code for all the other classes. We use JUnit test for testing.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version November 14, 2022
 */

public class Testing {

    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    @DisplayName("Signs up")
    @Test(timeout = 1000)
    public void testSignUp() {
        String input = "2\n1\nsoham\nsoham\nsoham\n1\nsoham\nsoham\n0\n";
        String expected = "Welcome to Ticket Emporium\n" +
                "What would you like to do?\n" +
                "0. Quit\n" +
                "1. Sign-in\n" +
                "2. Sign up\n" +
                "What role would you like to sign up for?\n" +
                "0. Quit\n" +
                "1. Seller\n" +
                "2. Customer\n" +
                "Name: " +
                "Email: " +
                "Password: " +
                "An account with this email already exists or you are using semicolons\n" +
                "What would you like to do?\n" +
                "0. Quit\n" +
                "1. Seller Sign-in\n" +
                "2. Customer Sign-in\n" +
                "Email: " +
                "Password: " +
                "What would you like to do, soham?\n" +
                "0. Quit\n" +
                "1. Change Account Details\n" +
                "2. Access Stores\n" +
                "3. View Statistics\n" +
                "4. View Products in Customer Shopping Carts\n";

        receiveInput(input); // call this method with the input before calling the method we wanna test
        Main.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");


        Assert.assertEquals("Doesn't match expected output", expected, actual);
    }


    @DisplayName("Customer Dashboard Works")
    @Test(timeout = 1000)
    public void testDashboard() {
        String input = "1\n2\njenny\njenny\n5\n1\ny\n3\n0\n";
        String expected = "Welcome to Ticket Emporium\n" +
                "What would you like to do?\n" +
                "0. Quit\n" +
                "1. Sign-in\n" +
                "2. Sign up\n" +
                "What would you like to do?\n" +
                "0. Quit\n" +
                "1. Seller Sign-in\n" +
                "2. Customer Sign-in\n" +
                "Email: " +
                "Password: " +
                "What would you like to do, jenny?\n" +
                "0. Quit\n" +
                "1. Change Account Details\n" +
                "2. Buy Tickets\n" +
                "3. View Purchase History\n" +
                "4. Access Shopping Cart\n" +
                "5. View Statistics\n" +
                "0. Go back\n" +
                "1. Statistics for all stores\n" +
                "2. Statistics for stores you've shopped at\n" +
                "Do you want these statistics sorted? (y/n)\n" +
                "store=14\n" +
                "basketball=17\n" +
                "bigStore=29\n" +
                "What would you like to do, jenny?\n" +
                "0. Quit\n" +
                "1. Change Account Details\n" +
                "2. Buy Tickets\n" +
                "3. View Purchase History\n" +
                "4. Access Shopping Cart\n" +
                "5. View Statistics\n" +
                "\n" +
                "Your Purchase History: \n" +
                "\n" +
                "What would you like to do, jenny?\n" +
                "0. Quit\n" +
                "1. Change Account Details\n" +
                "2. Buy Tickets\n" +
                "3. View Purchase History\n" +
                "4. Access Shopping Cart\n" +
                "5. View Statistics\n";

        receiveInput(input); // call this method with the input before calling the method we wanna test
        Main.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");


        Assert.assertEquals("Doesn't match expected output", expected, actual);
    }


}

