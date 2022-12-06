import java.io.*;
import java.util.ArrayList;

/**
 * Ticket class
 * <p>
 * This class contains the information about
 * the ticket, as well as different functionalities
 * allowing the sellers to manage their tickets.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version November 14, 2022
 */

class Ticket {

    private int id;
    private String sellerEmail;

    private String name;

    private int quantity;

    private String storeName;

    private double price;
    private String description;


    public Ticket(Ticket ticket) {
        this.id = ticket.id;
        this.name = ticket.name;
        this.sellerEmail = ticket.sellerEmail;
        this.storeName = ticket.storeName;
        this.price = ticket.price;
        this.description = ticket.description;
        this.quantity = ticket.quantity;
    }

    public Ticket(int id, String name, String sellerEmail, String storeName, double price, String description,
                  int quantity) {
        this.id = id;
        this.sellerEmail = sellerEmail;
        this.price = price;
        this.description = description;
        this.storeName = storeName;
        this.name = name;
        this.quantity = quantity;
    }

    //make sure unsold tickets have buyer named as " "
    public String toFile() {

        String ret = String.format("%d;%s;%s;%s;%.2f;%s;%d", id, name, sellerEmail, storeName, price, description,
                quantity);

        return ret;
    }

    //change the price and/ or description


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean changeInfo(String newName, double newPrice, String newDescription, int newQuantity) {
        synchronized (Seller.obj) {
            if (!newName.contains(";") && !newDescription.contains(";")) {
                String describe = toFile();
                setName(newName);
                setDescription(newDescription);
                setPrice(newPrice);
                setQuantity(newQuantity);
                ArrayList<String> templist = new ArrayList<String>();
                try (BufferedReader br = new BufferedReader(new FileReader("availableTickets.txt"))) {
                    String line = br.readLine();

                    while (line != null) {
                        if (!line.equals(describe)) {
                            templist.add(line);
                        }
                        line = br.readLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (quantity != 0) {
                    templist.add(toFile());
                }

                try (PrintWriter pw = new PrintWriter(new FileWriter("availableTickets.txt"))) {

                    for (String s : templist) {
                        pw.println(s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("Semicolon is not allowed here.");
                return false;
            }
        } }

    public String toProduct() {

        String ret = "";


        ret = String.format("Name: %s\nSeller: %s\nStore Name: %s\nDescription: %s\nPrice: %.2f\nQuantity: %d"
                , name,
                sellerEmail,
                storeName, description, price, quantity);

        return ret;
    }

    public String toString() {
        String ret = "";


        ret = String.format("Name: %s\nSeller: %s\nStore Name: %s\nPrice: %.2f"
                , name,
                sellerEmail,
                storeName, price);

        return ret;
    }


    public void writeToFile(String fileName) {
        synchronized (Seller.obj) {
            ArrayList<String> templist = new ArrayList<String>();
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line = br.readLine();

                while (line != null) {

                    templist.add(line);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {

                for (String s : templist) {
                    pw.println(s);
                }
                pw.println(toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setsellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


}

