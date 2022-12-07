import java.util.*;
import java.io.*;
import java.net.*;
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


