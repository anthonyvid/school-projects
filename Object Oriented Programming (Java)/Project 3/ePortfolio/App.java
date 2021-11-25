package ePortfolio;

import java.util.Scanner;
import ePortfolio.Investment.InvalidNameException;
import ePortfolio.Investment.InvalidPriceException;
import ePortfolio.Investment.InvalidSymbolException;
import ePortfolio.Investment.InvalidQuantityException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Anthony Vidovic (1130891) CIS2430 ePortfolio Assignment 3
 */
public class App {
    public static String filename;

    /**
     * @param args
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String menuChoice, symbol, name, tempQty, tempPrice;
        int qty;
        double price;

        Portfolio portfolio = new Portfolio();

        // Check if filename is given as command line argument
        if (args.length == 0) {
            System.out.println("No filename given, generating file...");
            filename = "ePortfolio.txt";
        } else {
            filename = args[0];
        }
        loadInvestmentsFromFile(filename, portfolio);
        PortfolioGUI gui = new PortfolioGUI();
        gui.setVisible(true);

        System.out.println("\n\nWelcome to Your ePortfolio!");

        // Loop until user exits program
        while (true) {
            displayMenu();
            menuChoice = scanner.nextLine().toUpperCase();

            if (equalsFullOrFirst(menuChoice, "BUY")) {
                while (true) {
                    try {
                        System.out.print("\nEnter 1 to buy a Stock, 2 to buy a Mutualfund: ");
                        menuChoice = scanner.nextLine();

                        if (!menuChoice.trim().equals("1") && !menuChoice.trim().equals("2") || menuChoice.isBlank())
                            throw new Exception("\nInvalid option, please try again.\n");

                        // Get symbol from user
                        System.out.print("Enter in symbol: ");
                        symbol = scanner.nextLine();

                        // User wants to buy or update stock
                        if (menuChoice.equals("1")) {

                            // User entered in Mutualfund but selected Stock option
                            if (Portfolio.getInvestment(symbol) instanceof Mutualfund) {
                                System.out.println(
                                        "This is a Mutualfund that you own, please select 2 to buy more Mutualfunds.");
                                continue;
                            }

                            // Check if they already own the stock
                            if (Portfolio.getInvestment(symbol) instanceof Stock) {
                                System.out.println("\nYou own this stock!\n");

                                // Get quantity from user
                                System.out.print("Enter in the quantity you would like to buy: ");
                                tempQty = scanner.nextLine();

                                // Get price from user
                                System.out.print("Enter in the price: ");
                                tempPrice = scanner.nextLine();

                                qty = Integer.parseInt(tempQty);
                                price = Double.parseDouble(tempPrice);

                                Portfolio.getInvestment(symbol).update(qty, price);
                            } else {

                                // Get name from user
                                System.out.print("Enter in the name of the stock: ");
                                name = scanner.nextLine();

                                // Get quantity from user
                                System.out.print("Enter in the quantity you would like to buy: ");
                                tempQty = scanner.nextLine();

                                // Get price from user
                                System.out.print("Enter in the price: ");
                                tempPrice = scanner.nextLine();

                                qty = Integer.parseInt(tempQty);
                                price = Double.parseDouble(tempPrice);

                                Portfolio.investments.add(new Stock(symbol, name, qty, price, qty * price + 9.99));
                                Portfolio.addNames2HashMap(name, symbol);
                            }

                            // User wants to buy or update Mutualfunds
                        } else if (menuChoice.equals("2")) {

                            // User entered in Stock but selected Mutualfund option
                            if (Portfolio.getInvestment(symbol) instanceof Stock) {
                                System.out.println("This is a Stock that you own, please select 1 to buy more Stocks.");
                                continue;
                            }

                            // Check if they already own the Mutualfund
                            if (Portfolio.getInvestment(symbol) instanceof Mutualfund) {
                                System.out.println("\nYou own this Mutualfund!\n");

                                // Get quantity from user
                                System.out.print("Enter in the quantity you would like to buy: ");
                                tempQty = scanner.nextLine();

                                // Get price from user
                                System.out.print("Enter in the price: ");
                                tempPrice = scanner.nextLine();

                                qty = Integer.parseInt(tempQty);
                                price = Double.parseDouble(tempPrice);

                                Portfolio.getInvestment(symbol).update(qty, price);
                            } else {

                                // Get name from user
                                System.out.print("Enter in the name of the mutualfund: ");
                                name = scanner.nextLine();

                                // Get quantity from user
                                System.out.print("Enter in the quantity you would like to buy: ");
                                tempQty = scanner.nextLine();

                                // Get price from user
                                System.out.print("Enter in the price: ");
                                tempPrice = scanner.nextLine();

                                qty = Integer.parseInt(tempQty);
                                price = Double.parseDouble(tempPrice);

                                Portfolio.investments.add(new Mutualfund(symbol, name, qty, price, qty * price));
                                Portfolio.addNames2HashMap(name, symbol);
                            }

                        }
                    } catch (InvalidSymbolException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (InvalidNameException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (InvalidQuantityException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (InvalidPriceException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid entry:\nPrice or Quantity is not a number\n");
                        continue;
                    } catch (Exception e) {
                        System.out.println("\nError inputing data" + e.getMessage());
                        continue;
                    }
                    break;
                }
            } else if (equalsFullOrFirst(menuChoice, "SELL")) {
                while (true) {
                    try {
                        // Get symbol from user
                        System.out.print("Enter in symbol: ");
                        symbol = scanner.nextLine();

                        if (Portfolio.getInvestment(symbol) == null)
                            throw new Exception("Investment not found, try agian\n");

                        // Get quantity from user
                        System.out.print("Enter in the quantity you would like to sell: ");
                        tempQty = scanner.nextLine();

                        // Get price from user
                        System.out.print("Enter in the price: ");
                        tempPrice = scanner.nextLine();

                        qty = Integer.parseInt(tempQty);
                        price = Double.parseDouble(tempPrice);

                        int result = Portfolio.getInvestment(symbol).sell(qty, price);
                        if (result == 0) {
                            Portfolio.removeNamesFromHashMap(Portfolio.getInvestment(symbol));
                            Portfolio.investments.remove(Portfolio.getInvestment(symbol));
                        } else if (result == -1) {
                            System.out.println("\nInvalid option:\nQuantity entered exceeps quantity of investment\n");
                            continue;
                        }
                    } catch (InvalidPriceException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (InvalidQuantityException e) {
                        System.out.println(e.getMessage());
                        continue;
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid entry:\nPrice or Quantity is not a number\n");
                        continue;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                }
            } else if (equalsFullOrFirst(menuChoice, "UPDATE")) {
                if (Portfolio.investments.size() < 1) {
                    System.out.println("No available investments to update\n");
                } else {
                    try {
                        portfolio.updateInvestments();
                    } catch (InvalidPriceException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else if (equalsFullOrFirst(menuChoice, "GETGAIN")) {
                System.out.println("Total gain on all investments is $" + roundOffTo2DecPlaces(Portfolio.getGain()));
            } else if (equalsFullOrFirst(menuChoice, "SEARCH")) {
                String priceRange;
                String keywords;

                while (true) {
                    try {
                        // Get symbol from user
                        System.out.print("Enter in a symbol (optional): ");
                        symbol = scanner.nextLine();

                        // Get keywords from user
                        System.out.print("Enter in keywords (optional): ");
                        keywords = scanner.nextLine();

                        // Get price range from user
                        System.out.print("Enter in a price range (optional): ");
                        priceRange = scanner.nextLine();

                        Portfolio.searchInvestments(symbol, keywords, priceRange);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            } else if (equalsFullOrFirst(menuChoice, "QUIT")) {
                writeInvestmentsToFile(filename, Portfolio.investments);
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("\nInvalid Option, Try Again\n");
                continue;
            }
        }
    }

    /**
     * Prints out menu
     */
    private static void displayMenu() {
        System.out.println("---------------------------\n" + "Input a menu option below:\n" + "- Buy \n" + "- Sell \n"
                + "- Update \n" + "- GetGain \n" + "- Search \n" + "- Quit \n");
        System.out.print("->: ");
    }

    /**
     * Rounds a number to 2 decimal places
     * 
     * @param val
     * @return String
     */
    public static String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val);
    }

    /**
     * Checks if the source string equals the target string, also valid if the
     * source equals the first character of target
     * 
     * @param source
     * @param target
     * @return true for a match, false for no match
     */
    private static boolean equalsFullOrFirst(String source, String target) {
        if (source.equals(target) || source.equals(String.valueOf(target.charAt(0))))
            return true;
        return false;
    }

    /**
     * wties investments in arraylist to the given filename from command line
     * argument
     * 
     * @param filename
     * @param investments
     */
    public static void writeInvestmentsToFile(String filename, ArrayList<Investment> investments) {
        String type;

        try {
            PrintWriter fileWriter = new PrintWriter(filename, "UTF-8");

            for (Investment investment : investments) {
                type = investment instanceof Stock ? "stock" : "mutualfund";
                fileWriter.println("type = " + type.trim());
                fileWriter.println("symbol = " + investment.getSymbol().trim());
                fileWriter.println("name = " + investment.getName().trim());
                fileWriter.println("quantity = " + investment.getQty());
                fileWriter.println("price = " + investment.getPrice());
                fileWriter.println("bookValue = " + investment.getBookValue() + "\n");
            }

            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing to file...\n ");
        }
    }

    /**
     * Loads investments from the given file into the arraylist and hashmap
     * 
     * @param filename
     * @param portfolio
     */
    public static void loadInvestmentsFromFile(String filename, Portfolio portfolio) {
        String type, symbol, name;
        int qty;
        double price, bookValue;

        try {
            File f = new File(filename);
            Scanner fileScan = new Scanner(f);

            if (f.exists() && f.length() != 0) {
                System.out.println("\nData found in file, loading into investments...\n\n");

                // Put all investments into investment array
                int count = 0;
                String[] investmentInfo = new String[6];

                while (fileScan.hasNextLine()) {
                    String line = fileScan.nextLine().trim().toLowerCase();

                    if (line.length() == 0)
                        continue;

                    investmentInfo[count] = line;

                    if (count == 5) {
                        count = 0;

                        type = investmentInfo[0].contains("stock") ? "stock" : "mutualfund";
                        symbol = investmentInfo[1].split("=")[1].toLowerCase().trim();
                        name = investmentInfo[2].trim().split("=")[1].toLowerCase();
                        qty = Integer.parseInt(investmentInfo[3].trim().split("=")[1].trim());
                        price = Double.parseDouble(investmentInfo[4].trim().split("=")[1].trim());
                        bookValue = Double.parseDouble(investmentInfo[5].trim().split("=")[1].trim());

                        if (type.equals("stock")) {
                            Portfolio.investments.add(new Stock(symbol, name, qty, price, bookValue));
                        } else {
                            Portfolio.investments.add(new Mutualfund(symbol, name, qty, price, bookValue));
                        }

                        Portfolio.addNames2HashMap(name, symbol);
                    } else {
                        count++;
                    }
                }
            } else {
                System.out.println("\nData not found in file  or file not created yet, Continuing with program...\n\n");
            }
            fileScan.close();
        } catch (Exception e) {
            System.out.println("File given not yet created");
        }
    }
}