package ePortfolio;

/**
 * Child class of Investment
 */
public class Mutualfund extends Investment{
    final double REDEMPTION_FEE = 45;

    /**
     * Initialize Mutualfund values
     */
    Mutualfund(String symbol, String name, int qty, double price, double bookValue) {
        super(symbol, name, qty, price, bookValue);
    }
}
