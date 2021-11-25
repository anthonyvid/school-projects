package ePortfolio;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Portfolio Class
 */
public class Portfolio {
    ArrayList<Investment> investments;
    HashMap<String, ArrayList<Integer>> hashmap;
    
    /**
     * Initialize arrayLists
     */
    public Portfolio() {
        this.investments = new ArrayList<Investment>();
        this.hashmap = new HashMap<String, ArrayList<Integer>>();
    }

    /** 
     * @return double
     */
    public double getGain() {
        double totalGain = 0;

        for (Investment investment : investments) {
            if(investment instanceof Stock) {
                totalGain += investment.getQty() * investment.getPrice() - 9.99 - investment.getBookValue();
            } else {
                totalGain += investment.getQty() * investment.getPrice() - 45 - investment.getBookValue();
            }
        }
        return totalGain;
    }

    
    /** 
     * @param symbol
     * @return Stock
     */
    public Investment getInvestment(String symbol) {
        for (Investment investment : investments) {
            if(symbol.trim().toUpperCase().equals(investment.getSymbol().trim().toUpperCase()))
                return investment;
        }
        return null;
    }

    /**
     * updateStocks
     * ----------------
     * - for each stock in the portfolio, the user will enter in a new (updated) price for it
     */
    public void updateInvestments() {
        double newPrice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Updating Investments...\n");
        for (Investment investment : investments) {
            System.out.print("Please enter in a new price for "+investment.getName()+" ("+investment.getSymbol()+"): $");
            newPrice = Double.parseDouble(sc.nextLine());
            investment.setPrice(newPrice);
        }
    }

    /**
     * searchInvestments
     * ----------------
     * - The user will provide search items to search for a particllar stock in their portfolio
     * @param: String symbol: the symbol of the stock or mutual, it will always be passed in as valid
     * @param: String keywords: a string of keywords to match the name of the investment
     * @param: String priceRange: a price range given to match the price of an investment, will always be valid
     */
    public void searchInvestments(String symbol, String keywords, String priceRange) {
        // Show all investments
        if(symbol.isEmpty() && keywords.isEmpty() && priceRange.isEmpty()) {
            showInvestments(investments);
            return;
        }
        // Single symbol search and nothing else
        if(!symbol.isEmpty() && keywords.isEmpty() && priceRange.isEmpty()) { 
            showSingleInvestment(symbol);
            return;
        }

        ArrayList<Investment> tempInvestments = new ArrayList<>();
        tempInvestments.addAll(this.investments);

        if(!keywords.isEmpty()) {
            filterKeywords(tempInvestments, keywords);
        }
        // Range given
        if(!priceRange.isEmpty()) {
            filterPriceRange(tempInvestments, priceRange);
        }
        
        if(!symbol.isEmpty()) {
            // Removes the stock/mutual if its symbol doesnt match given symbol
            tempInvestments.removeIf(investment -> !(investment.getSymbol().toUpperCase().equals(symbol.toUpperCase())));
        }
        showInvestments(tempInvestments);
    }

    /**
     * filterKeywords
     * ----------------
     * - This function will remove an investment from the given arraylists if
     *   the keywords are not all found in the investment name
     * @param: ArrayList<Investment> investmentsTemp
     * @param: String keywords: keywords to match
     */
    private void filterKeywords(ArrayList<Investment> investmentsTemp, String keywords) {
        String words[] = keywords.split("\\s+");
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Investment> temp = new ArrayList<>();
        ArrayList<ArrayList<Integer>> indexLists = new ArrayList<>();

        if(words.length == 1) {
            if(this.hashmap.get(words[0]) == null) {
                investmentsTemp.clear();
                return;
            }
            values = this.hashmap.get(words[0]);
        } else {
            for (String word : words) {
                if(this.hashmap.get(word) == null) {
                    investmentsTemp.clear();
                    return;
                }
                indexLists.add(this.hashmap.get(word));
            }
            values = findIntersection(indexLists);
        }

        for (Investment investment : investmentsTemp) {
            if(!values.contains(investmentsTemp.indexOf(investment))) {
                temp.add(investment);
            }
        }

        investmentsTemp.removeAll(temp);
    }

    /**
     * filterPriceRange
     * ----------------
     * - This function will remove an investment from the given arraylists if
     *   the price of the investment is not in the range
     * @param: ArrayList<Investment> investmentsTemp
     * @param: String priceRange: price range to match
     */
    private void filterPriceRange(ArrayList<Investment> investmentsTemp, String priceRange) {
        if(priceRange.matches("\\d+")) {
            for (Investment investment : this.investments) {
                if(investment.getPrice() != Double.parseDouble(priceRange))
                    investmentsTemp.remove(investment);
            }
        } else if(priceRange.matches("\\d+\\-")) {
            priceRange = priceRange.replace("-", "");
            for (Investment investment : this.investments) {
                if(investment.getPrice() < Double.parseDouble(priceRange))
                    investmentsTemp.remove(investment);
            }
        } else if(priceRange.matches("\\-\\d+")) {
            priceRange = priceRange.replace("-", "");
            for (Investment investment : this.investments) {
                if(investment.getPrice() > Double.parseDouble(priceRange))
                    investmentsTemp.remove(investment);
            }
        } else if(priceRange.matches("\\d+\\-\\d+")) {
            double low =  Double.parseDouble(priceRange.split("-")[0]);
            double high =  Double.parseDouble(priceRange.split("-")[1]);
            for (Investment investment : this.investments) {
                if(investment.getPrice() < low && investment.getPrice() > high)
                    investmentsTemp.remove(investment);
            }
        }
    }

    
    /** 
     * @param list
     * @return ArrayList<Integer>
     */
    private ArrayList<Integer> findIntersection(ArrayList<ArrayList<Integer>> list) {
        ArrayList<Integer> result = new ArrayList<>();

        for(int i=1; i < list.size(); i++) {
            list.get(0).retainAll(list.get(i));
        }
        result = list.get(0);
        return result;
    }

    /**
     * showInvestments
     * ----------------
     * - Prints all items in the given lists
     * @param: ArrayList<Stock> stocksTemp
     * @param: ArrayList<Mutualfund> mutualsTemp
     */
    public void showInvestments(ArrayList<Investment> investments) {
        for (Investment investment : investments) {
            System.out.println("\nName: " + investment.getName().trim() + "\nSymbol: " + investment.getSymbol().trim() + "\nPrice: $" + investment.getPrice() + "\nQuantity: " + investment.getQty() + "\nBookvalue: $" + investment.getBookValue() + "\n");
        }
    }

    /**
     * showSingleInvestment
     * ----------------
     * - Prints out a single investment if the symbol matches one
     * @param: String symbolL symbol to match
     */
    public void showSingleInvestment(String symbol) {
        Investment investment = getInvestment(symbol);
    
        if(investment != null) {
            System.out.println("\nName: " + investment.getName() + "\nSymbol: " + investment.getSymbol() + "\nPrice: $" + investment.getPrice() + "\nQuantity: " + investment.getQty() + "\nBookvalue: $" + investment.getBookValue());
        } else {
            System.out.println("Symbol not found\n");
        }
    }

    
    /** 
     * @param name
     * @param symbol
     */
    public void addNames2HashMap(String name, String symbol) {
        String names[] = name.trim().split("\\s+");

        for (String key : names) {
            Investment investment = getInvestment(symbol);
            if(this.hashmap.containsKey(key)) {
                ArrayList<Integer> indices = this.hashmap.get(key);
                if(investment != null) {
                    indices.add(this.investments.indexOf(investment));
                    this.hashmap.put(key, indices);
                }
            } else {
                ArrayList<Integer> indices = new ArrayList<>();
                indices.add(this.investments.indexOf(investment));
                this.hashmap.put(key, indices);
            }
        }
    }

    
    /** 
     * @param investment
     */
    public void removeNamesFromHashMap(Investment investment) {

        int index = this.investments.indexOf(investment);
        ArrayList<Integer> tempList = new ArrayList<>();
        ArrayList<String> tempStrings = new ArrayList<>();

        for (HashMap.Entry<String, ArrayList<Integer>> entry : this.hashmap.entrySet()) {
            String key = entry.getKey();
            ArrayList<Integer> list = entry.getValue();
            System.out.println(list.indexOf(index) + " " + index);
            if(list.contains(index))
                tempList.add(index);

            list.removeAll(tempList);

            if(list.size() == 0)
                tempStrings.add(key);


            for(int i = 0; i < list.size(); i++) {
                if(list.get(i) > index) {
                    int tempNum = list.get(i);
                    tempNum--;
                    list.set(i, tempNum);
                }
            }
        }
        tempStrings.forEach(key -> {
            this.hashmap.remove(key);
        });
        tempStrings.clear();
    }
}
