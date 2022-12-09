import java.util.ArrayList;
import java.io.*;

/**
 * Seller class
 * <p>
 * This class contains the information about
 * the seller, as well as different functionalities
 * for the sellers to manage their account, stores, ticket, and more.
 *
 * @author Tsai-Ni Chen (Jenny), Soham Jog, Armanya Maheshwari, Ajay Bestrapalli, CS180 BLK
 * @version November 14, 2022
 */

class Seller {

    private ArrayList<Store> stores;
    private ArrayList<String> storeNames;
    private String name;
    private String email;
    private String password;
    public static Object obj  = new Object ();
    public Seller(String name, String email, String password) {
       synchronized (obj) {
           this.name = name;
           this.email = email;
           this.password = password;
           storeNames = new ArrayList<>();
           stores = new ArrayList<>();
           File f = new File("seller.txt");
           try (BufferedReader br = new BufferedReader(new FileReader(f))) {
               String line = br.readLine();
               while (line != null) {
                   if (!line.equals("")) {
                       String[] info = line.split(";");
                       if (info[1].equals(email)) {
                           if (info.length > 3) {
                               String[] storeNamesFromFile = info[3].split(",");
                               for (String s : storeNamesFromFile) {
                                   storeNames.add(s);
                                   stores.add(new Store(s));
                               }
                           }
                       }
                   }
                   line = br.readLine();
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       } }

    // returns a boolean of wheter the seller was able to login
    public static Seller login(String email, String password) {
        synchronized (obj) {
            File f = new File("seller.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] info = line.split(";");
                        if (info[1].equals(email) && info[2].equals(password)) {
                            return new Seller(info[0], info[1], info[2]);
                        }
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        }}

    // Prints the items in shopping cart for a seller
    public ArrayList<String> shoppingCart() {
        synchronized (obj) {
            ArrayList<String> result = new ArrayList<>();
            File f = new File("shoppingCart.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split("/");
                        if (contents.length > 1) {
                            String[] tickets = contents[1].split(",");
                            String match = "";
                            for (String s : tickets) {
                                String[] ticketInfo = s.split(";");
                                if (ticketInfo[2].equals(email)) {
                                    if (match.equals("")) {
                                        match += "---------------------\n" + "Customer: " + contents[0] + "\nItems:\n";
                                    }
                                    match += "Name: " + ticketInfo[1] + " Price: " + ticketInfo[4] + " Quantity: " + ticketInfo[6] + "\n";
                                }
                            }
                            result.add(match);
                        }
                    }
                    line = br.readLine();
                }
                return result;
            } catch (Exception e) {
                return null;
            }
        } }

    //  Returns a boolean when a seller tries to sign up
    public static boolean signUp(String name, String email, String password) {
        synchronized (obj) {
            if (!name.contains(";") && !email.contains(";") && !password.contains(";")) {
                File f = new File("seller.txt");
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
                        Seller seller = new Seller(name, email, password);
                        seller.setStoreNames(new ArrayList<>());
                        seller.setStores(new ArrayList<>());
                        seller.writeToFile();
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            } else {
                System.out.println("Semicolon is not allowed here.");
                return false;
            }
        }}

    public String summary() {
        String result = String.format("%s;%s;%s", name, email, password);
        if (storeNames.size() > 0) {
            result += storeNames.get(0);
            for (int i = 1; i < storeNames.size(); i++) {
                result += "," + storeNames.get(i);
            }
        }
        return result;
    }

    public boolean writeToFile() {
        synchronized (obj) {
            File f = new File("seller.txt");
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f, true))) {
                pr.println(summary());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } }

    public void newStoreWrite(String email) {
        synchronized (obj) {
            ArrayList<String> lines = new ArrayList<>();
            File f = new File("seller.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (contents[1].equals(email)) {
                        line = contents[0] + ";" + contents[1] + ";" + contents[2] + ";" + storeNames.get(0);
                        for (int i = 1; i < storeNames.size(); i++) {
                            line += "," + storeNames.get(i);
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
        }}

    public boolean createStore(String name) {
        synchronized (obj) {
            File f = new File("seller.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    if (!line.equals("")) {
                        String[] contents = line.split(";");
                        if (contents.length > 3) {
                            String[] storeNames = contents[3].split(",");
                            for (String s : storeNames) {
                                if (s.equals(name)) {
                                    return false;
                                }
                            }
                        }
                    }
                    line = br.readLine();
                }
                Store newStore = new Store(name);
                storeNames.add(name);
                stores.add(newStore);
                newStoreWrite(email);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }}

    // getters and sellers

    public ArrayList<Store> getStores() {
        return stores;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public ArrayList<String> getStoreNames() {
        return storeNames;
    }

    public void setStoreNames(ArrayList<String> storeNames) {
        this.storeNames = storeNames;
    }

    public ArrayList<String> customerList(String seller, String store) {
        synchronized (obj) {
            try {
                File myObj = new File("pastTransaction.txt");
                ArrayList<String> list2 = new ArrayList<String>();
                ArrayList<String[]> list12 = new ArrayList<String[]>();
                ArrayList<String[]> list13 = new ArrayList<String[]>();
                ArrayList<String> list14 = new ArrayList<String>();
                ArrayList<String> finallist = new ArrayList<String>();
                FileReader fr = new FileReader(myObj);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                list2.add(line);
                while (line != null) {
                    line = br.readLine();
                    list2.add(line);
                }
                br.close();
                for (int y = 0; y < list2.size(); y++) {
                    list12.add(list2.get(y).split(";"));
                }
                for (int y = 0; y < list12.size(); y++) {
                    if (list12.get(y)[1].equals(seller) && list12.get(y)[3].equals(store)) {
                        list13.add(list12.get(y));
                    }
                }
                for (int p = 0; p < list13.size(); p++) {
                    if (list14.contains(list13.get(p)[2]) == false) {
                        list14.add(list13.get(p)[2]);
                    }
                }
                int[] buyernumber = new int[list14.size()];
                for (int p = 0; p < buyernumber.length; p++) {
                    buyernumber[p] = 0;
                }
                for (int p = 0; p < list13.size(); p++) {
                    for (int yo = 0; yo < list14.size(); yo++) {
                        if (list13.get(p)[2] == list14.get(yo)) {
                            buyernumber[yo] += 1;
                        }
                    }
                }
                for (int y = 0; y < list14.size(); y++) {
                    finallist.add(list14.get(y) + "," + buyernumber[y]);
                }
                return finallist;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } }

    public void updateFile() {
        synchronized (obj) {
            ArrayList<String> lines = new ArrayList<>();
            File f = new File("seller.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (contents[1].equals(email)) {
                        line = name + ";" + email + ";" + password + ";" + contents[3];

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
        }}

    public void changePassword(String newPassword) {
        synchronized (obj) {
        this.password = newPassword;
        updateFile();}
    }

    public void changeName(String name) {
        synchronized (obj) {
        this.name = name;
        updateFile();}
    }

    public void deleteAccount() {
        synchronized (obj) {
            ArrayList<String> lines = new ArrayList<>();
            File f = new File("seller.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (!contents[1].equals(email)) {
                        lines.add(line);
                    }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> lines2 = new ArrayList<>();
            File f2 = new File("availableTickets.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(f2))) {
                String line = br.readLine();
                while (line != null) {
                    String[] contents = line.split(";");
                    if (!contents[2].equals(email)) {
                        lines2.add(line);
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
            try (PrintWriter pr = new PrintWriter(new FileOutputStream(f2))) {
                for (String line : lines2) {
                    pr.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } }
}
