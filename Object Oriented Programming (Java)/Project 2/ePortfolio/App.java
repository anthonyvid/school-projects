package ePortfolio;
import java.util.Scanner;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.LinkedHashSet;

/**
 * Anthony Vidovic
 * 1130891
 * CIS2430 ePortfolio Assignment 2
 */
public class App {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String menuChoice;
        String symbol;
        String name;
        int qty;
        double price;

        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> emails1 = new ArrayList<>();

        Portfolio portfolio = new Portfolio();

        // Check if filename is given as command line argument
        if(args.length == 0) {
            System.out.println("No filename given, program terminating...");
            scanner.close();
            System.exit(0);
        }

        
        try {
            File f = new File(args[0]);
            
            Scanner fileScan = new Scanner(f);
            
            if(f.exists() && f.length() != 0) {
                System.out.println("\nData found in file, loading into investments...\n\n");

                //put all investments into investment array 
                int count = 0;
                String[] investmentInfo = new String[6];
                int x = 0;

                while(fileScan.hasNextLine()) {  
                    
                    String line = fileScan.nextLine().trim().toLowerCase(); 

                    if(x == 0) {
                        if(!line.isBlank()) {
                        emails.add(line);
                        } else {
                            x = 1;
                        }
                    } else {
                        emails1.add(line);
                    }
                    
                    
                    // if(line.length() == 0) continue;
                    // investmentInfo[count] = line;

                    // if(count == 5) {
                    //     count = 0;

                    //     String type = investmentInfo[0].contains("stock") ? "stock" : "mutualfund";
                    //     symbol = investmentInfo[1].split("=")[1].toLowerCase().trim();
                    //     name = investmentInfo[2].trim().split("=")[1].toLowerCase();
                    //     qty = Integer.parseInt(investmentInfo[3].trim().split("=")[1].trim());
                    //     price = Double.parseDouble(investmentInfo[4].trim().split("=")[1].trim());
                    //     Double bookValue = Double.parseDouble(investmentInfo[5].trim().split("=")[1].trim());

                    //     if(type.equals("stock")) {
                    //         portfolio.investments.add(new Stock(symbol, name, qty, price, bookValue));
                    //     } else {
                    //         portfolio.investments.add(new Mutualfund(symbol, name, qty, price, bookValue));
                    //     }

                    //     portfolio.addNames2HashMap(name, symbol);
                    // } else {
                    //     count++;
                    // }
                }
                


                System.out.println("Total number of students in class: " + emails.size());
                System.out.println("Number of students that signed: " + emails1.size());

                ArrayList<String> temp = new ArrayList<>();

                for (String k : emails1) {
                    if(emails.contains(k)) {
                        temp.add(k);
                    }
                }

                emails.removeAll(temp);

                System.out.println("Number of students that need to sign: " + emails.size());

                for (String string : emails) {
                    System.out.println(string);
                }

            } else {
                System.out.println("\nData not found in file  or file not created yet, Continuing with program...\n\n");
            }
            fileScan.close();
        } catch (Exception e) {
            System.out.println("File given not yet created" + e);
        }

        System.out.println("\n\nWelcome to Your ePortfolio!");

        while(true) {
            displayMenu();
            System.out.print("->: ");
            menuChoice = scanner.nextLine().toUpperCase();

            if(equalsFullOrFirst(menuChoice, "BUY")) { 
                
                while(true) {
                    System.out.print("\nEnter 1 to buy a Stock, 2 to buy a Mutualfund: ");
                    menuChoice = scanner.nextLine();
                    if(menuChoice.trim().equals("1") || menuChoice.trim().equals("2")) break;
                    System.out.println("Invalid Option, Try Again\n");
                }

                while(true) {
                    System.out.print("Enter in symbol: ");
                    symbol = scanner.nextLine();
                    if(!symbol.isBlank()) break;
                    System.out.println("Invalid Option, Try Again\n");
                }

                if(menuChoice.equals("1")) {

                    if(portfolio.getInvestment(symbol) instanceof Mutualfund) {
                        System.out.println("This is a Mutualfund that you own, please select 2 to buy more Mutualfunds.");
                        continue;
                    }
                    if(portfolio.getInvestment(symbol) instanceof Stock) {
                        System.out.println("\nYou own this stock!\n");
                        while(true) {
                            try {
                                System.out.print("Enter in the quantity you would like to buy: ");
                                qty = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter in the price: ");
                                price = Double.parseDouble(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("\nPlease enter numbers only - try again\n");
                                continue;
                            }

                            if(qty > 0 && price > 0) {
                                portfolio.getInvestment(symbol).update(qty, price, 9.99);
                                break;
                            }
                            System.out.println("\nInvalid Options, try again\n");
                        }
                    } else {
                        while(true) {
                            try {
                                System.out.print("Enter in the name of the stock: ");
                                name = scanner.nextLine();
                                System.out.print("Enter in the quantity you would like to buy: ");
                                qty = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter in the price: ");
                                price = Double.parseDouble(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("\nInvalid Options, try again\n");
                                continue;
                            }
                            
                            if(!name.isBlank() && qty > 0 && price > 0) {
                                portfolio.investments.add(new Stock(symbol, name, qty, price, qty * price + 9.99));
                                portfolio.addNames2HashMap(name, symbol);
                                break;
                            } 
                            System.out.println("\nInvalid Options, try again\n");
                        }
                    }
                } else if(menuChoice.equals("2")) {
                    if(portfolio.getInvestment(symbol) instanceof Stock) {
                        System.out.println("This is a Stock that you own, please select 1 to buy more Stocks.");
                        continue;
                    }
                    if(portfolio.getInvestment(symbol) instanceof Mutualfund) {
                        System.out.println("\nYou own this Mutualfund!\n");
                        while(true) {
                            try {
                                System.out.print("Enter in the quantity you would like to buy: ");
                                qty = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter in the price: ");
                                price = Double.parseDouble(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("\nPlease enter numbers only - try again\n");
                                continue;
                            }
                            
                            if(qty > 0 && price > 0) {
                                portfolio.getInvestment(symbol).update(qty, price, 0);
                                break;
                            }
                            System.out.println("\nInvalid Options, try again\n");
                        }
                    } else {
                        while(true) {
                            try {
                                System.out.print("Enter in the name of the Mutualfund: ");
                                name = scanner.nextLine();
                                System.out.print("Enter in the quantity you would like to buy: ");
                                qty = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter in the price: ");
                                price = Double.parseDouble(scanner.nextLine());
                            } catch (Exception e) {
                                System.out.println("\nInvalid Options, try again\n");
                                continue;
                            }
                            
                            if(!name.isBlank() && qty > 0 && price > 0) {
                                portfolio.investments.add(new Mutualfund(symbol, name, qty, price, qty * price));
                                portfolio.addNames2HashMap(name, symbol);
                                break;
                            } 
                            System.out.println("\nInvalid Options, try again\n");
                        }
                    }
                }
            } else if(equalsFullOrFirst(menuChoice, "SELL")) {
                while(true) {
                    System.out.print("Enter in symbol: ");
                    symbol = scanner.nextLine();
                    if(!symbol.isBlank() && portfolio.getInvestment(symbol) != null) break;
                    System.out.println("Invalid Option, Try Again\n");
                }
                if(portfolio.getInvestment(symbol) instanceof Stock) {
                    while(true) {
                        try {
                            System.out.print("Enter in the quantity you would like to sell: ");
                            qty = Integer.parseInt(scanner.nextLine());
                            System.out.print("Enter in the price: ");
                            price = Double.parseDouble(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.println("\nPlease enter numbers only - try again\n");
                            continue;
                        }

                        if(qty > 0 && price > 0) {
                            int result = portfolio.getInvestment(symbol).sell(qty, price, 9.99);
                            if(result == 0) {
                                portfolio.removeNamesFromHashMap(portfolio.getInvestment(symbol));
                                portfolio.investments.remove(portfolio.getInvestment(symbol));
                            } else if(result == -1) {
                                System.out.println("Quantity entered exceeps quantity of stock\n");
                                continue;
                            }
                        } else {
                            System.out.println("\nInvalid Options, try again\n");
                            continue;
                        }  
                        break; 
                    }
                } else {
                    while(true) {
                        try {
                            System.out.print("Enter in the quantity you would like to sell: ");
                            qty = Integer.parseInt(scanner.nextLine());
                            System.out.print("Enter in the price: ");
                            price = Double.parseDouble(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.println("\nPlease enter numbers only - try again\n");
                            continue;
                        }

                        if(qty > 0 && price > 0) {
                            int result = portfolio.getInvestment(symbol).sell(qty, price, 45);
                            if(result == 0) {
                                portfolio.removeNamesFromHashMap(portfolio.getInvestment(symbol));
                                portfolio.investments.remove(portfolio.getInvestment(symbol));
                            } else if(result == -1) {
                                System.out.println("Quantity entered exceeps quantity of stock\n");
                                continue;
                            }
                        } else {
                            System.out.println("\nInvalid Options, try again\n");
                            continue;
                        }
                        break;
                    }
                }

            } else if(equalsFullOrFirst(menuChoice, "UPDATE")) {
                if(portfolio.investments.size() < 1) {
                    System.out.println("No available stocks to update\n");
                } else {
                    portfolio.updateInvestments();
                }   
            } else if(equalsFullOrFirst(menuChoice, "GETGAIN")) {
                System.out.println("Total gain on all investments is $" + roundOffTo2DecPlaces(portfolio.getGain()));
            } else if(equalsFullOrFirst(menuChoice, "SEARCH")) {
                String priceRange;
                String keywords;
                while(true) {
                    System.out.print("Enter in a symbol (optional): ");
                    symbol = scanner.nextLine();
                    if(!symbol.isBlank() && portfolio.getInvestment(symbol) == null) {
                        System.out.println("Cannot find a stock or mutualfund with that symbol, try again\n");
                        continue;
                    }
                    System.out.print("Enter in keywords (optional): ");
                    keywords = scanner.nextLine();
                    System.out.print("Enter in a price range (optional): ");
                    priceRange = scanner.nextLine();
                    if(priceRange.matches("(\\d*\\.*\\d*)-?(\\d*\\.*\\d)?|-(\\d*\\.*\\d)|(\\d*\\.*\\d)-"))
                        break;
                    System.out.println("Invalid format for price range, try again\n");
                }
                portfolio.searchInvestments(symbol, keywords, priceRange);
            } else if(equalsFullOrFirst(menuChoice, "QUIT")) {
                writeInvestmentsToFile(args[0], portfolio.investments);
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("\nInvalid Option, Try Again\n");
                continue;
            }
        }
    }

    /**
     * displayMenu
     * ----------------
     * - prints out the menu options
     */
    private static void displayMenu() {
        System.out.println(
        "---------------------------\n"
        + "Input a menu option below:\n"
        + "- Buy \n"
        + "- Sell \n"
        + "- Update \n"
        + "- GetGain \n"
        + "- Search \n"
        + "- Quit \n"
        );
    }

    
    /** 
     * @param val
     * @return String
     */
    public static String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val);
    }

    /**
     * equalsFullOrFirst
     * ------------------
     * - Checks if the source string equals the target string
     * - Also valid if the source equals the first character of target
     * @param source
     * @param target
     * @return true for a match, false for no match
     */
    private static boolean equalsFullOrFirst(String source, String target) {
        if(source.equals(target) || source.equals(String.valueOf(target.charAt(0)))) return true;
        return false;
    }

    
    /** 
     * @param filename
     * @param investments
     */
    private static void writeInvestmentsToFile(String filename, ArrayList<Investment> investments) {
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
}