package ePortfolio;

/**
 * Stock class, child class of Investment
 */
public class Stock extends Investment {
    final double COMMISSION = 9.99;

    /**
     * Constructer for Stock class that creates an instance of the class
     * 
     * @param symbol
     * @param name
     * @param qty
     * @param price
     * @param bookValue
     * @throws invalidNameException
     * @throws invalidSymbolException
     * @throws invalidPriceException
     * @throws invalidQuantityException
     */
    public Stock(String symbol, String name, int qty, double price, double bookValue)
            throws InvalidNameException, InvalidSymbolException, InvalidPriceException, InvalidQuantityException {
        super(symbol, name, qty, price, bookValue);
    }

    /**
     * Copy constructor
     * 
     * @param copyStock
     * @throws InvalidNameException
     * @throws InvalidSymbolException
     * @throws InvalidPriceException
     * @throws InvalidQuantityException
     */
    public Stock(Stock copyStock)
            throws InvalidNameException, InvalidSymbolException, InvalidPriceException, InvalidQuantityException {
        super(copyStock.getSymbol(), copyStock.getName(), copyStock.getQty(), copyStock.getPrice(),
                copyStock.getBookValue());
    }

    /**
     * Gets the fee for a stock
     */
    public double getFee() {
        return this.COMMISSION;
    }

    /**
     * The to string method
     * 
     * @returns: String
     */
    @Override
    public String toString() {
        return "\nInvestment Type: Stock\n" + "Name: " + super.getName() + "\n" + "Symbol: " + super.getSymbol() + "\n"
                + "Price: " + super.getPrice() + "\n" + "Quantity: " + super.getQty() + "\n" + "Bookvalue: "
                + super.getBookValue() + "\n";
    }

    /**
     * The equals method
     * 
     * @param other
     * @returns boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (getClass() != other.getClass()) {
            return false;
        } else {
            Stock otherStock = (Stock) other;
            return super.equals(otherStock) && getName().equals(otherStock.getName())
                    && getSymbol().equals(otherStock.getSymbol()) && getQty() == otherStock.getQty()
                    && getPrice() == otherStock.getPrice() && getBookValue() == otherStock.getBookValue();
        }
    }

    /**
     * Calculates the boovalue of a Mutualfund and sets it
     * 
     * @param price
     * @param quantity
     * @throws invalidPriceException
     * @throws invalidQuantityException
     */
    public double calculateBookValue(int qty, double price) throws InvalidPriceException, InvalidQuantityException {
        return super.getBookValue() + (qty * price) + COMMISSION;
    }

    /**
     * Algorithm to sell a stock
     * 
     * @param price
     * @param quantity
     * @throws invalidPriceException
     * @throws invalidQuantityException
     */
    public int sell(int qty, double price) throws InvalidPriceException, InvalidQuantityException {
        if (price <= 0)
            throw new InvalidPriceException("\nInvalid entry:\nPrice can't be less than 0\n");

        if (qty <= 0)
            throw new InvalidQuantityException("\nInvalid entry:\nQuantity can't be less than 0\n");

        if (super.getQty() < qty)
            return -1;

        if (super.getQty() - qty > 0) {
            super.setQty(super.getQty() - qty);
            super.setPrice(price);
        } else {
            // Qty is 0
            System.out.printf("You've sold all of this Stock for an amount of $%.2f\n", ((qty * price) - COMMISSION));
            return 0;
        }

        super.setBookValue(super.getBookValue() * ((double) (super.getQty()) / (double) (super.getQty() + qty)));
        System.out.printf("You've sold this Stock for an amount of $%.2f\n", ((qty * price) - COMMISSION));
        return 1;
    }

    /**
     * Algorithm to buy more of an existing stock
     * 
     * @param price
     * @param quantity
     * @throws invalidPriceException
     * @throws invalidQuantityException
     */
    public void update(int qty, double price) throws InvalidPriceException, InvalidQuantityException {
        if (price <= 0)
            throw new InvalidPriceException("\nInvalid entry:\nPrice can't be less than 0\n");

        if (qty <= 0)
            throw new InvalidQuantityException("\nInvalid entry:\nQuantity can't be less than 0\n");

        super.setQty(super.getQty() + qty);
        super.setPrice(price);
        super.setBookValue(calculateBookValue(qty, price));
    }
}