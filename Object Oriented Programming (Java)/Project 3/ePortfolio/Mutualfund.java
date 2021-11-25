package ePortfolio;

/**
 * Mutualfund class, child class of Investment
 */
public class Mutualfund extends Investment {
    final double REDEMPTION_FEE = 45;

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
    Mutualfund(String symbol, String name, int qty, double price, double bookValue)
            throws InvalidNameException, InvalidSymbolException, InvalidPriceException, InvalidQuantityException {
        super(symbol, name, qty, price, bookValue);
    }

    /**
     * Copy constructor
     * 
     * @param copyMutualfund
     * @throws InvalidNameException
     * @throws InvalidSymbolException
     * @throws InvalidPriceException
     * @throws InvalidQuantityException
     */
    public Mutualfund(Mutualfund copyMutualfund)
            throws InvalidNameException, InvalidSymbolException, InvalidPriceException, InvalidQuantityException {
        super(copyMutualfund.getSymbol(), copyMutualfund.getName(), copyMutualfund.getQty(), copyMutualfund.getPrice(),
                copyMutualfund.getBookValue());
    }

    /**
     * Gets the fee for a mutualfund
     */
    public double getFee() {
        return this.REDEMPTION_FEE;
    }

    /**
     * The to string method
     * 
     * @returns: String
     */
    @Override
    public String toString() {
        return "\nInvestment Type: Mutualfund\n" + "Name: " + super.getName() + "\n" + "Symbol: " + super.getSymbol()
                + "\n" + "Price: " + super.getPrice() + "\n" + "Quantity: " + super.getQty() + "\n" + "Bookvalue: "
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
            Mutualfund otherMutualfund = (Mutualfund) other;
            return super.equals(otherMutualfund) && getName().equals(otherMutualfund.getName())
                    && getSymbol().equals(otherMutualfund.getSymbol()) && getQty() == otherMutualfund.getQty()
                    && getPrice() == otherMutualfund.getPrice() && getBookValue() == otherMutualfund.getBookValue();
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
        return super.getBookValue() + (qty * price);
    }

    /**
     * Algorithm to sell a mutualfund
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
            System.out.printf("You've sold all of this Mutualfund for an amount of $%.2f\n",
                    ((qty * price) - REDEMPTION_FEE));
            return 0;
        }

        super.setBookValue(super.getBookValue() * ((double) (super.getQty()) / (double) (super.getQty() + qty)));
        System.out.printf("You've sold this Mutualfund for an amount of $%.2f\n", ((qty * price) - REDEMPTION_FEE));
        return 1;
    }

    /**
     * Algorithm to buy more of an existing mutualfund
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
