package ePortfolio;

import java.util.ArrayList;
import java.util.Scanner;
import ePortfolio.Investment.InvalidPriceException;
import java.util.HashMap;

/**
 * Portfolio Class, holds all methods and variables to manage the portfolio
 */
public class Portfolio {
    static ArrayList<Investment> investments;
    static HashMap<String, ArrayList<Integer>> hashmap;

    /**
     * Constructor for portfolio class, invokes the class and initializes the
     * arraylist and hashmap
     */
    public Portfolio() {
        Portfolio.investments = new ArrayList<Investment>();
        Portfolio.hashmap = new HashMap<String, ArrayList<Integer>>();
    }

    /**
     * Algorithm to compute the gain of the portfolio
     * 
     * @return double
     */
    public static double getGain() {
        double totalGain = 0;

        for (Investment investment : investments)
            totalGain += investment.getQty() * investment.getPrice() - investment.getFee() - investment.getBookValue();
        return totalGain;
    }

    /**
     * Gets an investment from the arraylist if it exists, otherwise returns null
     * 
     * @param symbol
     * @return Investment
     */
    public static Investment getInvestment(String symbol) {
        for (Investment investment : investments) {
            if (symbol.trim().toUpperCase().equals(investment.getSymbol().trim().toUpperCase()))
                return investment;
        }
        return null;
    }

    /**
     * Gets input for price to update the price of each investment
     * 
     * @throws InvalidPriceException
     */
    public void updateInvestments() throws InvalidPriceException {
        double newPrice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Updating Investments...\n");
        for (Investment investment : investments) {
            System.out.print(
                    "Please enter in a new price for " + investment.getName() + " (" + investment.getSymbol() + "): $");
            newPrice = Double.parseDouble(sc.nextLine());
            investment.setPrice(newPrice);
        }
    }

    /**
     * The user will provide search items to search for a particllar investment in
     * their portfolio
     * 
     * @param symbol
     * @param keywords
     * @param priceRange
     * @throws Exception
     */
    public static ArrayList<Investment> searchInvestments(String symbol, String keywords, String priceRange)
            throws Exception {
        ArrayList<Investment> forGUI = new ArrayList<>();
        String tempRange[];

        // Show all investments
        if (symbol.isEmpty() && keywords.isEmpty() && priceRange.isEmpty()) {
            showInvestments(investments);
            return investments;
        }

        // Validate symbol
        if (!symbol.isBlank() && Portfolio.getInvestment(symbol) == null)
            throw new Exception("\nCannot find a stock or mutualfund with that symbol, try again\n");

        // Validate price range
        if (!priceRange.matches("(\\d*\\.*\\d*)-?(\\d*\\.*\\d)?|-(\\d*\\.*\\d)|(\\d*\\.*\\d)-"))
            throw new Exception("\nInvalid format for price range, try again\n");

        if (!priceRange.isBlank()) {
            tempRange = priceRange.split("-");

            if (tempRange.length > 1) {
                if (!tempRange[0].isBlank() && !tempRange[1].isBlank()) {
                    if (Double.parseDouble(tempRange[0]) > Double.parseDouble(tempRange[1]))
                        throw new Exception("\nInvalid price range, try again\n");
                } else if (!tempRange[0].isBlank()) {
                    if (Double.parseDouble(tempRange[0]) <= 0)
                        throw new Exception("\nPrice range must be positive, try again\n");
                } else {
                    if (Double.parseDouble(tempRange[1]) <= 0)
                        throw new Exception("\nPrice range must be positive, try again\n");
                }
            } else if (tempRange.length == 1) {
                if (Double.parseDouble(tempRange[0]) <= 0)
                    throw new Exception("\nPrice range must be positive, try again\n");
            }
        }

        // Single symbol search and nothing else
        if (!symbol.isEmpty() && keywords.isEmpty() && priceRange.isEmpty()) {
            showSingleInvestment(symbol);
            forGUI.add(getInvestment(symbol));
            return forGUI;
        }

        ArrayList<Investment> tempInvestments = new ArrayList<>();
        tempInvestments.addAll(Portfolio.investments);

        if (!keywords.isEmpty()) {
            filterKeywords(tempInvestments, keywords);
        }
        // Range given
        if (!priceRange.isEmpty()) {
            filterPriceRange(tempInvestments, priceRange);
        }

        if (!symbol.isEmpty()) {
            // Removes the stock/mutual if its symbol doesnt match given symbol
            tempInvestments
                    .removeIf(investment -> !(investment.getSymbol().toUpperCase().equals(symbol.toUpperCase())));
        }
        showInvestments(tempInvestments);
        return tempInvestments;
    }

    /**
     * This function will remove an investment from the given arraylists if the
     * keywords are not all found in the investment name
     * 
     * @param investmentsTemp
     * @param keywords
     */
    private static void filterKeywords(ArrayList<Investment> investmentsTemp, String keywords) {
        String words[] = keywords.split("\\s+");
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Investment> temp = new ArrayList<>();
        ArrayList<ArrayList<Integer>> indexLists = new ArrayList<>();

        if (words.length == 1) {
            if (Portfolio.hashmap.get(words[0]) == null) {
                investmentsTemp.clear();
                return;
            }
            values = Portfolio.hashmap.get(words[0]);
        } else {
            for (String word : words) {
                if (Portfolio.hashmap.get(word) == null) {
                    investmentsTemp.clear();
                    return;
                }
                indexLists.add(Portfolio.hashmap.get(word));
            }
            values = findIntersection(indexLists);
        }

        for (Investment investment : investmentsTemp) {
            if (!values.contains(investmentsTemp.indexOf(investment))) {
                temp.add(investment);
            }
        }

        investmentsTemp.removeAll(temp);
    }

    /**
     * This function will remove an investment from the given arraylists if the
     * price of the investment is not in the range
     * 
     * @param investmentsTemp
     * @param priceRange
     */
    private static void filterPriceRange(ArrayList<Investment> investmentsTemp, String priceRange) {
        if (priceRange.matches("\\d+")) {
            for (Investment investment : Portfolio.investments) {
                if (investment.getPrice() != Double.parseDouble(priceRange))
                    investmentsTemp.remove(investment);
            }
        } else if (priceRange.matches("\\d+\\-")) {
            priceRange = priceRange.replace("-", "");
            for (Investment investment : Portfolio.investments) {
                if (investment.getPrice() < Double.parseDouble(priceRange))
                    investmentsTemp.remove(investment);
            }
        } else if (priceRange.matches("\\-\\d+")) {
            priceRange = priceRange.replace("-", "");
            for (Investment investment : Portfolio.investments) {
                if (investment.getPrice() > Double.parseDouble(priceRange))
                    investmentsTemp.remove(investment);
            }
        } else if (priceRange.matches("\\d+\\-\\d+")) {
            double low = Double.parseDouble(priceRange.split("-")[0]);
            double high = Double.parseDouble(priceRange.split("-")[1]);
            for (Investment investment : Portfolio.investments) {
                if (investment.getPrice() < low || investment.getPrice() > high)
                    investmentsTemp.remove(investment);
            }
        }
    }

    /**
     * Finds the intersection of n lists
     * 
     * @param list
     * @return ArrayList<Integer>
     */
    private static ArrayList<Integer> findIntersection(ArrayList<ArrayList<Integer>> list) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            list.get(0).retainAll(list.get(i));
        }
        result = list.get(0);
        return result;
    }

    /**
     * Prints all items in the given lists
     * 
     * @param investments
     */
    public static void showInvestments(ArrayList<Investment> investments) {
        for (Investment investment : investments) {
            System.out.println(investment.toString());
        }
    }

    /**
     * Prints out a single investment if the symbol matches one
     * 
     * @param symbol
     */
    public static void showSingleInvestment(String symbol) {
        Investment investment = getInvestment(symbol);

        if (investment != null) {
            System.out.println(investment.toString());
        } else {
            System.out.println("Symbol not found\n");
        }
    }

    /**
     * Method that adds an investment to the hashmap
     * 
     * @param name
     * @param symbol
     */
    public static void addNames2HashMap(String name, String symbol) {
        String names[] = name.trim().split("\\s+");

        for (String key : names) {
            Investment investment = getInvestment(symbol);
            if (Portfolio.hashmap.containsKey(key)) {
                ArrayList<Integer> indices = Portfolio.hashmap.get(key);
                if (investment != null) {
                    indices.add(Portfolio.investments.indexOf(investment));
                    Portfolio.hashmap.put(key, indices);
                }
            } else {
                ArrayList<Integer> indices = new ArrayList<>();
                indices.add(Portfolio.investments.indexOf(investment));
                Portfolio.hashmap.put(key, indices);
            }
        }
    }

    /**
     * Method that removes a name from the hashmap
     * 
     * @param investment
     */
    public static void removeNamesFromHashMap(Investment investment) {

        int index = Portfolio.investments.indexOf(investment);
        ArrayList<Integer> tempList = new ArrayList<>();
        ArrayList<String> tempStrings = new ArrayList<>();

        for (HashMap.Entry<String, ArrayList<Integer>> entry : Portfolio.hashmap.entrySet()) {
            String key = entry.getKey();
            ArrayList<Integer> list = entry.getValue();
            System.out.println(list.indexOf(index) + " " + index);
            if (list.contains(index))
                tempList.add(index);

            list.removeAll(tempList);

            if (list.size() == 0)
                tempStrings.add(key);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > index) {
                    int tempNum = list.get(i);
                    tempNum--;
                    list.set(i, tempNum);
                }
            }
        }
        tempStrings.forEach(key -> {
            Portfolio.hashmap.remove(key);
        });
        tempStrings.clear();
    }
}
