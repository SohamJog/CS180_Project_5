import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * User class
 * <p>
 * This class contains the information about
 * the customer, as well as different functionalities
 * for the users to manage their account and shopping cart.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version November 14, 2022
 */

public class User {
    private String name;
    private final String email;
    private String password;
    private ArrayList<Ticket> shoppingCart;

    // constructor
    public User(String name, String email, String password) {
        synchronized (Seller.obj) {
            this.name = name;
            this.email = email;
            this.password = password;
            shoppingCart = new ArrayList<>();
            File f = new File("shoppingCart.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split("/");
                        if (contents[0].equals(email)) {
                            if (contents.length > 1) {
                                String[] tickets = contents[1].split(",");
                                for (String s : tickets) {
                                    String[] ticketInfo = s.split(";");
                                    int id = Integer.parseInt(ticketInfo[0]);
                                    double price = Double.parseDouble(ticketInfo[4]);
                                    int quantity = Integer.parseInt(ticketInfo[6]);
                                    shoppingCart.add(new Ticket(id, ticketInfo[1], ticketInfo[2], ticketInfo[3], price,
                                            ticketInfo[5], quantity));
                                }
                            }
                        }
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    // summary method that can be used when writing to file
    public String summary() {
        return String.format("%s;%s;%s", name, email, password);
    }

    // writeToFile method (returns true when write successfully, false otherwise)
    public boolean writeToFile() {
        synchronized (Seller.obj) {
            File f = new File("userInfo.txt");
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f, true))) {
                pr.print("\n" + summary());
            } catch (Exception e) {
                return false;
            }
            return true;
        } }

    // login method (returns a User object when login successfully, null otherwise)
    public static User login(String email, String password) {
        synchronized (Seller.obj) {
            File f = new File("userInfo.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] info = line.split(";");
                        if (info[1].equals(email) && info[2].equals(password)) {
                            return new User(info[0], info[1], info[2]);
                        }
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        }}

    public static boolean signUp(String name, String email, String password) {
        synchronized (Seller.obj) {
            if (!name.contains(";") && !email.contains(";") && !password.contains(";")) {
                File f = new File("userInfo.txt");
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    boolean exist = false;
                    String line = br.readLine();
                    while (line != null) {
                        if (!line.equals("")) {
                            String[] info = line.split(";");
                            if (info[1].equals(email)) {
                                exist = true;
                                break;
                            }
                        }
                        line = br.readLine();
                    }
                    if (!exist) {
                        User user = new User(name, email, password);
                        user.writeToFile();
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        }}

    public void updateFile() {
        synchronized (Seller.obj) {
            ArrayList<String> lines = new ArrayList<>();
            this.name = name;
            File f = new File("userInfo.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (contents[1].equals(email)) {
                        line = name + ";" + email + ";" + password;
                    }
                    lines.add(line);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.print("\n" + line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    public boolean addToCart(Ticket ticket, int quantity) {
        synchronized (Seller.obj) {
            if (quantity > ticket.getQuantity()) {
                return false;
            }

            Ticket toBuy = new Ticket(ticket);
            toBuy.setQuantity(quantity);
            Ticket toKeep = new Ticket(ticket);
            toKeep.setQuantity(ticket.getQuantity() - toBuy.getQuantity());

            shoppingCart.add(toBuy);
            //debug
            //System.out.println(shoppingCart);
            ArrayList<String> lines = new ArrayList<>();
            File f = new File("shoppingCart.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                boolean found = false;
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split("/");
                        if (contents[0].equals(email)) {
                            if (contents.length == 1) {
                                line = email + "/" + toBuy.toFile();
                            } else {
                                line += "," + toBuy.toFile();
                            }
                            found = true;
                        }
                    }
                    lines.add(line);
                    line = br.readLine();
                }
                if (!found) {
                    lines.add(email + "/" + toBuy.toFile());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            f = new File("availableTickets.txt");
            lines.clear();

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {

                    if (!(line.equals(ticket.toFile()))) {
                        lines.add(line);
                    }
                    line = br.readLine();
                }
                if (toKeep.getQuantity() != 0) {
                    lines.add(toKeep.toFile());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } }

//    // version with quantity
//    public boolean removeFromCart(Ticket ticket, int quantity) {
//        //shoppingCart.remove(ticket);
//        if(quantity>ticket.getQuantity()) {
//            return false;
//        }
//        shoppingCart.remove(ticket);
//        Ticket toRemove = new Ticket(ticket);
//        toRemove.setQuantity(quantity);
//        Ticket toKeep = new Ticket(ticket);
//        toKeep.setQuantity(ticket.getQuantity()-quantity);
//
//        if(ticket.getQuantity()!=quantity) {
//            shoppingCart.add(toKeep);
//        }
//
//
//        //writes the updated cart, Start of Jenny's work
//        ArrayList<String> lines = new ArrayList<>();
//        File f = new File("shoppingCart.txt");
//        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//            String line =  br.readLine();
//            while (line != null) {
//                if (!line.equals("")) {
//                    String[] contents = line.split("/");
//                    if (contents[0].equals(email)) {
//                        if (contents.length > 1) {
//                            if (shoppingCart.size() > 0) {
//                                line = email + "/" + shoppingCart.get(0).toFile();
//                                for (int i = 1; i < shoppingCart.size(); i++) {
//                                    line += "," + shoppingCart.get(i).toFile();
//                                }
//                            } else {
//                                line = email + "/";
//                            }
//                        }
//                    }
//                }
//                lines.add(line);
//                line = br.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
//            for (String line : lines) {
//                pr.println(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        lines.clear();
//        f = new File("availableTickets.txt");
//        int id = toRemove.getId();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//            String line =  br.readLine();
//            while (line != null) {
//                String[] inp = line.split(";");
//                int ticketId = Integer.parseInt(inp[0]);
//
//                if(ticketId==id) {
//                    int finalQuantity = Integer.parseInt(inp[6]);
//                    finalQuantity+=quantity;
//                    line = "";
//                    inp[6] = Integer.toString(finalQuantity);
//                    for(int i=0;i<inp.length;i++) {
//                        line+=inp[i];
//                        if(i!=inp.length-1) {
//                            line+=";";
//                        }
//                    }
//                    id = -1;
//                }
//                lines.add(line);
//                line = br.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
//            for (String line : lines) {
//                pr.println(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }

    // version without quantity
    public boolean removeFromCart(Ticket ticket) {
        synchronized (Seller.obj) {
            shoppingCart.remove(ticket);

            ArrayList<String> lines = new ArrayList<>();
            File f = new File("shoppingCart.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split("/");
                        if (contents[0].equals(email)) {
                            if (contents.length > 1) {
                                if (shoppingCart.size() > 0) {
                                    line = email + "/" + shoppingCart.get(0).toFile();
                                    for (int i = 1; i < shoppingCart.size(); i++) {
                                        line += "," + shoppingCart.get(i).toFile();
                                    }
                                } else {
                                    line = email + "/";
                                }
                            }
                        }
                    }
                    lines.add(line);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            lines.clear();
            f = new File("availableTickets.txt");
            int id = ticket.getId();

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                boolean found = false;
                while (line != null) {
                    String[] inp = line.split(";");
                    int ticketId = Integer.parseInt(inp[0]);

                    if (ticketId == id) {
                        int finalQuantity = Integer.parseInt(inp[6]);
                        finalQuantity += ticket.getQuantity();
                        line = "";
                        inp[6] = Integer.toString(finalQuantity);
                        for (int i = 0; i < inp.length; i++) {
                            line += inp[i];
                            if (i != inp.length - 1) {
                                line += ";";
                            }
                        }
                        id = -1;
                        found = true;
                    }
                    lines.add(line);
                    line = br.readLine();
                }
                if (!found) {
                    lines.add(ticket.toFile());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }}


    public boolean buyTicket(Ticket ticket) {
        //check if ticket exists in the shopping cart
        //remove from shopping cart,
        //add to pastTransactions.txt
        synchronized (Seller.obj) {
            boolean ok = false;
            for (int i = 0; i < shoppingCart.size(); i++) {
                if (shoppingCart.get(i).equals(ticket)) {
                    shoppingCart.remove(ticket);
                    ok = true;
                }
            }
            if (!ok) {
                return false;
            }

            ArrayList<String> lines = new ArrayList<>();
            File f = new File("shoppingCart.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split("/");
                        if (contents[0].equals(email)) {
                            if (contents.length > 1) {
                                if (shoppingCart.size() > 0) {
                                    line = email + "/" + shoppingCart.get(0).toFile();
                                    for (int i = 1; i < shoppingCart.size(); i++) {
                                        line += "," + shoppingCart.get(i).toFile();
                                    }
                                } else {
                                    line = email + "/";
                                }
                            }
                        }
                    }
                    lines.add(line);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            lines.clear();
            f = new File("pastTransactions.txt");

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    lines.add(line);
                    line = br.readLine();
                }
                String s = String.format("%d;%s;%s;%s;%s;%.2f;%s;%d", ticket.getId(), ticket.getName(),
                        ticket.getSellerEmail(),
                        email,
                        ticket.getStoreName(),
                        ticket.getPrice(), ticket.getDescription(), ticket.getQuantity());
                lines.add(s);

            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }}

    public Map customerStoreDash(boolean sort) {
        synchronized (Seller.obj) {
            File f = new File("pastTransactions.txt");
            Map<Integer, String> stores = new HashMap<>();
            String[] ticketInfo;
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    ticketInfo = line.split(";");
                    if (ticketInfo[3].equals(email))
                        stores.merge(Integer.parseInt(ticketInfo[7]), ticketInfo[4], Integer::sum);
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (sort) {
                Map<String, Integer>
            }
            return stores;
        }
    }

    public ArrayList<String> displayPastTransactions() {
        synchronized (Seller.obj) {
            ArrayList<String> ret = new ArrayList<>();
            File f = new File("pastTransactions.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] inp = line.split(";");
                        if (inp[3].equals(email)) {
                            ret.add(String.format("Name: %s\nSeller: %s\nStore: %s\nPrice: %s\nDescription: %s\nQuantity: %s\n\n",
                                    inp[1], inp[2], inp[4], inp[5],
                                    inp[6], inp[7]));
                        }
                    }
                    line = br.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        } }


    public String displayShoppingCart() {
        String result = "-----------------\n";
        String dash = "-----------------\n";
        if (shoppingCart.size() == 0) {
            result = "";
        } else {
            int count = 1;
            for (Ticket t : shoppingCart) {
                result += count + ". " + t.getName() + "\nPrice: " + t.getPrice() + "\nQuantity: " +
                        t.getQuantity() + "\nDescription: " + t.getDescription() + "\n" + dash;
                count++;
            }
        }
        return result;
    }

    public void changePassword(String newPassword) {
        this.password = password;
        updateFile();
    }

    public void changeName(String name) {
        this.name = name;
        updateFile();
    }

    public void deleteAccount() {
        synchronized (Seller.obj) {
            ArrayList<String> lines = new ArrayList<>();
            File f = new File("userInfo.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split(";");
                        if (!contents[1].equals(email)) {
                            lines.add(line);
                        }
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f))) {
                for (String line : lines) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}

    // getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Ticket> getShoppingCart() {
        return shoppingCart;
    }
}
