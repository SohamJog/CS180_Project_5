import java.io.*;
import java.util.ArrayList;

/**
 * Store class
 * <p>
 * This class contains the information about
 * the store, as well as different functionalities
 * for the sellers to manage their stores, create and manage
 * tickets, see the sights of their stores, and more.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version November 14, 2022
 */

public class Store {
    private String name;
    private ArrayList<Ticket> tickets;

    public Store(String name) {
       synchronized (Seller.obj) {
           this.setName(name);
           this.tickets = new ArrayList<>();
           File f = new File("availableTickets.txt");
           try (BufferedReader br = new BufferedReader(new FileReader(f))) {
               String line = br.readLine();
               while (line != null) {
                   if (!line.equals("")) {
                       String[] contents = line.split(";");
                       if (contents[3].equals(name)) {
                           int id = Integer.parseInt(contents[0]);
                           String productName = contents[1];
                           String sellerName = contents[2];
                           String storeName = contents[3];
                           double price = Double.parseDouble(contents[4]);
                           String description = contents[5];
                           int quantity = Integer.parseInt(contents[6]);
                           tickets.add(new Ticket(id, productName, sellerName, storeName, price, description, quantity));
                       }
                   }
                   line = br.readLine();
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }  }

    public void newTickets(String name, String sellerName, double price, String description, int quantity) {
        synchronized (Seller.obj) {
            File f = new File("availableTickets.txt");
            int id = 1;
            Ticket newTicket = null;
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split(";");
                        int lineId = Integer.parseInt(contents[0]);
                        if (lineId > id) {
                            id = lineId;
                        }
                    }
                    line = br.readLine();
                }
                newTicket = new Ticket(id + 1, name, sellerName, this.name, price, description, quantity);
                tickets.add(newTicket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f, true))) {
                if (newTicket != null) {
                    pr.print("\n" + newTicket.toFile());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    public void deleteTickets(int id) {
        synchronized (Seller.obj) {
            ArrayList<String> lines = new ArrayList<>();
            File f = new File("availableTickets.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (!(Integer.parseInt(contents[0]) == id)) {
                        lines.add(line);
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String l : lines) {
                    pr.println(l);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    public static String getSeller(String storeName) {
        synchronized (Seller.obj) {
            File f = new File("pastTransactions.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (contents[4].equals(storeName)) {
                        return contents[2];
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }}

    public double getRevenue() {
        synchronized (Seller.obj) {
            double revenue = 0;
            File f = new File("pastTransactions.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split(";");
                        if (contents[4].equals(name)) {
                            double price = Double.parseDouble(contents[5]);
                            int quantity = Integer.parseInt(contents[7]);
                            revenue += price * quantity;
                        }
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return revenue;
        }}

    public ArrayList<String> getCustomerList() {
        synchronized (Seller.obj) {
            ArrayList<String> customerList = new ArrayList<>();
            File f = new File("pastTransactions.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split(";");
                        if (contents[4].equals(name) && !customerList.contains(contents[3])) {
                            customerList.add(contents[3]);
                        }
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customerList;
        }}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}
