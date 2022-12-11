import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Server class
 * <p>
 * This class contains the code for the server side of the program. It creates new threads for
 * each client that connects to the server, and runs those threads simultaneously.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version December 10, 2022
 */

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket acceptedSocket = null;
        try {
            serverSocket = new ServerSocket(4242);
        } catch (Exception e) {
            // exception handling
            e.printStackTrace();
        }

        while (true) {
            try {
                acceptedSocket = serverSocket.accept();
                ClientThread client = new ClientThread(acceptedSocket);
                Thread thread = new Thread(client);
                thread.start();
            } catch (Exception e) {
                // exception handling
                e.printStackTrace();
            }
        }
    }
}


