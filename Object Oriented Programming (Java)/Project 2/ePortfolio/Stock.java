package ePortfolio;

/**
 * Child class of Investment
 */
public class Stock extends Investment {
    final double COMMISSION = 9.99;

    /**
     * Initialize stock values
     */
    Stock(String symbol, String name, int qty, double price, double bookValue) {
        super(symbol, name, qty, price, bookValue);
    }
}