package ePortfolio;

/**
 * Investment Class
 */
public abstract class Investment {
    private String symbol;
    private String name;
    private int qty;
    private double price;
    private double bookValue;

    /**
     * @param symbol
     * @param name
     * @param qty
     * @param price
     * @param bookValue
     */
    public Investment(String symbol, String name, int qty, double price, double bookValue)
            throws InvalidNameException, InvalidSymbolException, InvalidPriceException, InvalidQuantityException {

        if (symbol.isEmpty() || symbol.isBlank())
            throw new InvalidSymbolException(
                    "\nInvalid entry:\nInvestment symbol must contain at least one character\n");

        if (name.isEmpty() || name.isBlank())
            throw new InvalidNameException("\nInvalid entry:\nInvestment name must contain at least one character\n");

        if (qty <= 0)
            throw new InvalidQuantityException("\nInvalid entry:\nQuantity can't be less than 0\n");

        if (price <= 0)
            throw new InvalidPriceException("\nInvalid entry:\nPrice can't be less than 0\n");

        this.symbol = symbol;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.bookValue = bookValue;
    }

    /**
     * Abstract method to calculate bookvalue for an investment
     * 
     * @param qty
     * @param price
     * @return double
     * @throws InvalidPriceException
     * @throws InvalidQuantityException
     */
    public abstract double calculateBookValue(int qty, double price)
            throws InvalidPriceException, InvalidQuantityException;

    /**
     * Abstract method to sell an investment
     * 
     * @param qty
     * @param price
     * @return int
     * @throws InvalidPriceException
     * @throws InvalidQuantityException
     */
    public abstract int sell(int qty, double price) throws InvalidPriceException, InvalidQuantityException;

    /**
     * Abstract method to update an investment
     * 
     * @param qty
     * @param price
     * @throws InvalidPriceException
     * @throws InvalidQuantityException
     */
    public abstract void update(int qty, double price) throws InvalidPriceException, InvalidQuantityException;

    /**
     * Abstract method to get the fee of the investment
     * 
     * @return
     */
    public abstract double getFee();

    /**
     * Gets the symbol attribute
     * 
     * @return String
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Sets the symbol attribute
     * 
     * @param symbol
     */
    public void setSymbol(String symbol) throws InvalidSymbolException {
        if (symbol.isEmpty() || symbol.isBlank())
            throw new InvalidSymbolException("\nInvalid entry:\nInvestment symbol must contain a character\n");
        this.symbol = symbol;
    }

    /**
     * Gets the name attribute
     * 
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name attribute
     * 
     * @param name
     */
    public void setName(String name) throws InvalidNameException {
        if (name.isEmpty() || name.isBlank())
            throw new InvalidNameException("\nInvalid entry:\nInvestment name must contain a character\n");
        this.name = name;
    }

    /**
     * Gets the quantity attribute
     * 
     * @return int
     */
    public int getQty() {
        return this.qty;
    }

    /**
     * Sets the quantity attribute
     * 
     * @param qty
     */
    public void setQty(int qty) throws InvalidQuantityException {
        if (qty <= 0)
            throw new InvalidQuantityException("\nInvalid entry:\nQuantity can't be less than 0yaya\n");
        this.qty = qty;
    }

    /**
     * Gets the price attribute
     * 
     * @return double
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Sets the price attribute
     * 
     * @param price
     */
    public void setPrice(double price) throws InvalidPriceException {
        if (price <= 0)
            throw new InvalidPriceException("\nInvalid entry:\nPrice can't be less than 0\n");
        this.price = price;
    }

    /**
     * Gets the bookvalue attribute
     * 
     * @return double
     */
    public double getBookValue() {
        return this.bookValue;
    }

    /**
     * Sets the bookvalue attribute
     * 
     * @param bookValue
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
    // ~~~~~~~ Custom Exceptions ~~~~~~~ //
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

    /**
     * Custom exception to handle investment symbols
     */
    public class InvalidSymbolException extends Exception {
        InvalidSymbolException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception to handle investment names
     */
    public class InvalidNameException extends Exception {
        InvalidNameException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception to handle investment prices
     */
    public class InvalidPriceException extends Exception {
        InvalidPriceException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception to handle investment quantity
     */
    public class InvalidQuantityException extends Exception {
        InvalidQuantityException(String message) {
            super(message);
        }
    }
}
