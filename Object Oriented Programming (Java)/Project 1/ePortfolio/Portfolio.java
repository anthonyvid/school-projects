package ePortfolio;

import java.util.ArrayList;
import java.util.Scanner;

public class Portfolio {
    ArrayList<Stock> stocks;
    ArrayList<Mutualfund> mutuals;

    /**
     * Initialize arrayLists
     */
    public Portfolio() {
        stocks = new ArrayList<Stock>();
        mutuals = new ArrayList<Mutualfund>();
    }

    /** 
     * @return double
     */
    public double getGain() {
        double totalGain = 0;
        for (Mutualfund mutualfund : mutuals)
            totalGain += mutualfund.getQty() * mutualfund.getPrice() - mutualfund.REDEMPTION_FEE - mutualfund.getBookValue();
        
        for (Stock stock : stocks) 
            totalGain += stock.getQty() * stock.getPrice() - stock.COMMISSION - stock.getBookValue();

        return totalGain;
    }

    
    /** 
     * @param symbol
     * @return Stock
     */
    public Stock getStock(String symbol) {
        for (Stock stock : stocks)
            if(symbol.toUpperCase().equals(stock.getSymbol().toUpperCase()))
                return stock;
        return null;
    }

    
    /** 
     * @param symbol
     * @return Mutualfund
     */
    public Mutualfund getMutual(String symbol) {
        for (Mutualfund mutual : mutuals)
            if(symbol.toUpperCase().equals(mutual.getSymbol().toUpperCase()))
                return mutual;
        return null;
    }

    /**
     * updateStocks
     * ----------------
     * - for each stock in the portfolio, the user will enter in a new (updated) price for it
     */
    public void updateStocks() {
        double newPrice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Updating Stocks...\n");
        for (Stock stock : stocks) {
            System.out.print("Please enter in a new price for "+stock.getName()+" ("+stock.getSymbol()+"): $");
            newPrice = Double.parseDouble(sc.nextLine());
            stock.setPrice(newPrice);
        }
    }

    /**
     * updateMutuals
     * ----------------
     * - for each mutual in the portfolio, the user will enter in a new (updated) price for it
     */
    public void updateMutuals() {
        double newPrice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Updating Mutualfunds...\n");
        for (Mutualfund mutual : mutuals) {
            System.out.print("Please enter in a new price for "+mutual.getName()+" ("+mutual.getSymbol()+"): $");
            newPrice = Double.parseDouble(sc.nextLine());
            mutual.setPrice(newPrice);
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
            showAllInvestments();
            return;
        }
        // Single symbol search and nothing else
        if(!symbol.isEmpty() && keywords.isEmpty() && priceRange.isEmpty()) { 
            showSingleInvestment(symbol);
            return;
        }

        ArrayList<Stock> stocksTemp = new ArrayList<Stock>();
        ArrayList<Mutualfund> mutualsTemp = new ArrayList<Mutualfund>();
        
        // Range given
        if(!priceRange.isEmpty()) {
            filterPriceRange(stocksTemp, mutualsTemp, priceRange);
        } else { 
            // No range, need to import stocks and mutuals into temp lists
            stocksTemp.addAll(this.stocks);
            mutualsTemp.addAll(this.mutuals);
        }

        if(!keywords.isEmpty())
            filterKeywords(stocksTemp, mutualsTemp, keywords);
        if(!symbol.isEmpty()) {
            // Removes the stock/mutual if its symbol doesnt match given symbol
            stocksTemp.removeIf(stock -> !(stock.getSymbol().toUpperCase().equals(symbol.toUpperCase())));
            mutualsTemp.removeIf(mutual -> !(mutual.getSymbol().toUpperCase().equals(symbol.toUpperCase())));
        }
        showInvestments(stocksTemp, mutualsTemp);
    }

    /**
     * filterKeywords
     * ----------------
     * - This function will remove an investment from the given arraylists if
     *   the keywords are not all found in the investment name
     * @param: ArrayList<Stock> stocksTemp
     * @param: ArrayList<Mutualfund> mutualsTemp
     * @param: String keywords: keywords to match
     */
    private void filterKeywords(ArrayList<Stock> stocksTemp, ArrayList<Mutualfund> mutualsTemp, String keywords) {
        ArrayList<String> tempStrings = new ArrayList<String>();
        ArrayList<Stock> invalidStocks = new ArrayList<Stock>();
        ArrayList<Mutualfund> invalidMutuals = new ArrayList<Mutualfund>();
        int length = keywords.split("\\W+").length;
        boolean valid = true;

        for(int i=0; i < length; i++) {
            tempStrings.add(keywords.split("\\W+")[i]);
        }
        for (Stock stock : stocksTemp) {
            valid = true;
            for (int i = 0; i < tempStrings.size(); i++) {
                // Keyword not found in stock name
                if(!(stock.getName().toUpperCase().contains(tempStrings.get(i).toUpperCase()))) {
                    valid = false;
                    break;
                }
            }
            if(!valid) invalidStocks.add(stock);
        }

        for (Mutualfund mutual : mutualsTemp) {
            valid = true;
            for (int i = 0; i < tempStrings.size(); i++) {
                // Keyword not found in mutual name
                if(!(mutual.getName().toUpperCase().contains(tempStrings.get(i).toUpperCase()))) {
                    valid = false;
                    break;
                }
            }
            if(!valid) invalidMutuals.add(mutual);
        }

        stocksTemp.removeAll(invalidStocks);
        mutualsTemp.removeAll(invalidMutuals);
    }

    /**
     * filterPriceRange
     * ----------------
     * - This function will remove an investment from the given arraylists if
     *   the price of the investment is not in the range
     * @param: ArrayList<Stock> stocksTemp
     * @param: ArrayList<Mutualfund> mutualsTemp
     * @param: String priceRange: price range to match
     */
    private void filterPriceRange(ArrayList<Stock> stocksTemp, ArrayList<Mutualfund> mutualsTemp, String priceRange) {
        if(priceRange.matches("\\d+")) {
            for (Stock stock : this.stocks) {
                if(stock.getPrice() == Double.parseDouble(priceRange))
                    stocksTemp.add(stock);
            }
            for (Mutualfund mutual : this.mutuals) {
                if(mutual.getPrice() == Double.parseDouble(priceRange))
                    mutualsTemp.add(mutual);
            }
        } else if(priceRange.matches("\\d+\\-")) {
            priceRange = priceRange.replace("-", "");
            for (Stock stock : this.stocks) {
                if(stock.getPrice() >= Double.parseDouble(priceRange))
                    stocksTemp.add(stock);
            }
            for (Mutualfund mutual : this.mutuals) {
                if(mutual.getPrice() >= Double.parseDouble(priceRange))
                    mutualsTemp.add(mutual);
            }
        } else if(priceRange.matches("\\-\\d+")) {
            priceRange = priceRange.replace("-", "");
            for (Stock stock : this.stocks) {
                if(stock.getPrice() <= Double.parseDouble(priceRange))
                    stocksTemp.add(stock);
            }
            for (Mutualfund mutual : this.mutuals) {
                if(mutual.getPrice() <= Double.parseDouble(priceRange))
                    mutualsTemp.add(mutual);
            }
        } else if(priceRange.matches("\\d+\\-\\d+")) {
            double low =  Double.parseDouble(priceRange.split("-")[0]);
            double high =  Double.parseDouble(priceRange.split("-")[1]);
            for (Stock stock : this.stocks) {
                if(stock.getPrice() >= low && stock.getPrice() <= high)
                    stocksTemp.add(stock);
            }
            for (Mutualfund mutual : this.mutuals) {
                if(mutual.getPrice() >= low && mutual.getPrice() <= high)
                    mutualsTemp.add(mutual);
            }
        }
    }

    /**
     * showInvestments
     * ----------------
     * - Prints all items in the given lists
     * @param: ArrayList<Stock> stocksTemp
     * @param: ArrayList<Mutualfund> mutualsTemp
     */
    public void showInvestments(ArrayList<Stock> stocks, ArrayList<Mutualfund> mutuals) {
        for (Stock stock : stocks) {
            System.out.println("Type: Stock\nName: " + stock.getName() + "\nSymbol: " + stock.getSymbol() + "\nPrice: $" + stock.getPrice() + "\nQuantity: " + stock.getQty() + "\nBookvalue: $" + stock.getBookValue() + "\n------------------");
        }
        for (Mutualfund mutual : mutuals) {
            System.out.println("Type: Mutualfund\nName: " + mutual.getName() + "\nSymbol: " + mutual.getSymbol() + "\nPrice: $" + mutual.getPrice() + "\nQuantity: " + mutual.getQty() + "\nBookvalue: $" + mutual.getBookValue() + "\n------------------");
        }
    }

    /**
     * showAllInvestments
     * ----------------
     * - Prints all items in the arraylist instance variables
     */
    public void showAllInvestments() {
        for (Stock stock : stocks) {
            System.out.println("Type: Stock\nName: " + stock.getName() + "\nSymbol: " + stock.getSymbol() + "\nPrice: $" + stock.getPrice() + "\nQuantity: " + stock.getQty() + "\nBookvalue: $" + stock.getBookValue() + "\n------------------");
        }
        for (Mutualfund mutual : mutuals) {
            System.out.println("Type: Mutualfund\nName: " + mutual.getName() + "\nSymbol: " + mutual.getSymbol() + "\nPrice: $" + mutual.getPrice() + "\nQuantity: " + mutual.getQty() + "\nBookvalue: $" + mutual.getBookValue() + "\n------------------");
        }
    }

    /**
     * showSingleInvestment
     * ----------------
     * - Prints out a single investment if the symbol matches one
     * @param: String symbolL symbol to match
     */
    public void showSingleInvestment(String symbol) {
        Stock stock = getStock(symbol);
        Mutualfund mutual = getMutual(symbol);
        if(stock!= null) {
            System.out.println("Type: Stock\nName: " + stock.getName() + "\nSymbol: " + stock.getSymbol() + "\nPrice: $" + stock.getPrice() + "\nQuantity: " + stock.getQty() + "\nBookvalue: $" + stock.getBookValue());
        } else if(mutual != null) {
            System.out.println("Type: Mutualfund\nName: " + mutual.getName() + "\nSymbol: " + mutual.getSymbol() + "\nPrice: $" + mutual.getPrice() + "\nQuantity: " + mutual.getQty() + "\nBookvalue: $" + mutual.getBookValue());
        } else {
            System.out.println("Symbol not found\n");
        }
    }
}
