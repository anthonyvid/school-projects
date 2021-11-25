package ePortfolio;
import java.util.Scanner;

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
        
        Portfolio portfolio = new Portfolio();

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
                    if(portfolio.getMutual(symbol) != null) {
                        System.out.println("This is a Mutualfund that you own, please select 2 to buy more Mutualfunds.");
                        continue;
                    }
                    if(portfolio.getStock(symbol) != null) {
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
                                portfolio.getStock(symbol).update(qty, price);
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
                                portfolio.stocks.add(new Stock(symbol, name, qty, price, qty * price + 9.99));
                                break;
                            } 
                            System.out.println("\nInvalid Options, try again\n");
                        }
                    }
                } else if(menuChoice.equals("2")) {
                    if(portfolio.getStock(symbol) != null) {
                        System.out.println("This is a Stock that you own, please select 1 to buy more Stocks.");
                        continue;
                    }
                    if(portfolio.getMutual(symbol) != null) {
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
                                portfolio.getMutual(symbol).update(qty, price);
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
                                portfolio.mutuals.add(new Mutualfund(symbol, name, qty, price, qty * price));
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
                    if(!symbol.isBlank() && portfolio.getStock(symbol) != null || portfolio.getMutual(symbol) != null) break;
                    System.out.println("Invalid Option, Try Again\n");
                }
                if(portfolio.getStock(symbol) != null) {
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
                            int result = portfolio.getStock(symbol).sell(qty, price);
                            if(result == 0) {
                                portfolio.stocks.remove(portfolio.getStock(symbol));
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
                            int result = portfolio.getMutual(symbol).sell(qty, price);
                            if(result == 0) {
                                portfolio.mutuals.remove(portfolio.getMutual(symbol));
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
                while(true) {
                    System.out.print("\nEnter 1 to update Stocks, 2 to update Mutualfunds: ");
                    menuChoice = scanner.nextLine();
                    if(menuChoice.trim().equals("1") || menuChoice.trim().equals("2")) break;
                    System.out.println("Invalid Option, Try Again\n");
                }

                if(menuChoice.equals("1")) {
                    if(portfolio.stocks.size() < 1) {
                        System.out.println("No available stocks to update\n");
                    } else {
                        portfolio.updateStocks();
                    }
                } else {
                    if(portfolio.mutuals.size() < 1) {
                        System.out.println("No available mutualfunds to update\n");
                    } else {
                        portfolio.updateMutuals();
                    }
                }   
            } else if(equalsFullOrFirst(menuChoice, "GETGAIN")) {
                System.out.println("Total gain on all investments is $" + Double.toString(portfolio.getGain()));
            } else if(equalsFullOrFirst(menuChoice, "SEARCH")) {
                String priceRange;
                String keywords;
                while(true) {
                    System.out.print("Enter in a symbol (optional): ");
                    symbol = scanner.nextLine();
                    if(!symbol.isBlank() && portfolio.getStock(symbol) == null && portfolio.getMutual(symbol) == null) {
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
                scanner.close();
                System.exit(0);
            } else if(menuChoice.equals("10")) {
                portfolio.showAllInvestments();
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
}