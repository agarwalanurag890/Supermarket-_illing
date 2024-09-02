import java.io.*;
import java.util.Scanner;

class Bill {
    private String item;
    private int rate, quantity;

    public Bill() {
        this.item = "";
        this.rate = 0;
        this.quantity = 0;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public int getRate() {
        return rate;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class SupermarketBillingSystem {
    private static final String FILE_PATH = "D:/Bill.txt";

    private static void addItem(Bill b) {
        Scanner scanner = new Scanner(System.in);
        boolean close = false;

        while (!close) {
            System.out.println("\t1. Add.");
            System.out.println("\t2. Close.");
            System.out.print("\tEnter Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("\tEnter Item Name: ");
                String item = scanner.nextLine();
                b.setItem(item);

                System.out.print("\tEnter Rate Of Item: ");
                int rate = scanner.nextInt();
                b.setRate(rate);

                System.out.print("\tEnter Quantity Of Item: ");
                int quantity = scanner.nextInt();
                b.setQuantity(quantity);

                try (FileWriter fw = new FileWriter(FILE_PATH, true);
                     BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write("\t" + b.getItem() + " : " + b.getRate() + " : " + b.getQuantity());
                    bw.newLine();
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("\tError: File Can't Open!");
                }

                System.out.println("\tItem Added Successfully");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {
                close = true;
                System.out.println("\tBack To Main Menu!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void printBill() {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        boolean close = false;

        while (!close) {
            System.out.println("\t1. Add Bill.");
            System.out.println("\t2. Close Session.");
            System.out.print("\tEnter Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("\tEnter Item: ");
                String item = scanner.nextLine();

                System.out.print("\tEnter Quantity: ");
                int quant = scanner.nextInt();

                File inputFile = new File(FILE_PATH);
                File tempFile = new File("D:/Bill_Temp.txt");

                try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                     BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

                    String line;
                    boolean found = false;

                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(" : ");
                        if (parts.length == 3) {
                            String itemName = parts[0].trim();
                            int itemRate = Integer.parseInt(parts[1].trim());
                            int itemQuant = Integer.parseInt(parts[2].trim());

                            if (item.equals(itemName)) {
                                found = true;
                                if (quant <= itemQuant) {
                                    int amount = itemRate * quant;
                                    System.out.println("\t Item | Rate | Quantity | Amount");
                                    System.out.println("\t" + itemName + "\t " + itemRate + "\t " + quant + "\t " + amount);
                                    count += amount;
                                    itemQuant -= quant;
                                    line = itemName + " : " + itemRate + " : " + itemQuant;
                                } else {
                                    System.out.println("\tSorry, " + item + " Ended!");
                                }
                            }
                        }
                        bw.write(line);
                        bw.newLine();
                    }

                    if (!found) {
                        System.out.println("\tItem Not Available!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                inputFile.delete();
                tempFile.renameTo(inputFile);
            } else if (choice == 2) {
                close = true;
                System.out.println("\tCounting Total Bill");
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("\t Total Bill ----------------- : " + count);
        System.out.println("\tThanks For Shopping!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bill b = new Bill();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\tWelcome To Super Market Billing System");
            System.out.println("\t**************************************");
            System.out.println("\t\t1. Add Item.");
            System.out.println("\t\t2. Print Bill.");
            System.out.println("\t\t3. Exit.");
            System.out.print("\t\tEnter Choice: ");
            int val = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (val == 1) {
                addItem(b);
            } else if (val == 2) {
                printBill();
            } else if (val == 3) {
                exit = true;
                System.out.println("\tGood Luck!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
