package ePortfolio;

/**
 * Investment Class
 */
public class Investment {
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
    Investment(String symbol, String name, int qty, double price, double bookValue) {
        this.symbol = symbol;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.bookValue = bookValue;
    }
	
    /** 
     * @return String
     */
    public String getSymbol() {
		return this.symbol;
	}
	
    /** 
     * @param symbol
     */
    public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
    /** 
     * @return String
     */
    public String getName() {
		return this.name;
	}
	
    /** 
     * @param name
     */
    public void setName(String name) {
		this.name = name;
	}
	
    /** 
     * @return int
     */
    public int getQty() {
		return this.qty;
	}
	
    /** 
     * @param qty
     */
    public void setQty(int qty) {
		this.qty = qty;
	}
	
    /** 
     * @return double
     */
    public double getPrice() {
		return this.price;
	}
	
    /** 
     * @param price
     */
    public void setPrice(double price) {
		this.price = price;
	}
	
    /** 
     * @return double
     */
    public double getBookValue() {
		return this.bookValue;
	}
	
    /** 
     * @param bookValue
     */
    public void setBookValue(double bookValue) {
		this.bookValue = bookValue;
	}      
    
    /** 
     * @return String
     */
    public String toString() {
        return getName() + "\n" + getSymbol() + "\n" + getPrice() + "\n" + getQty() + "\n" + getBookValue();
    }
    
    /** 
     * @return Boolean
     */
    public boolean isEquals(String symbol) {
        if(this.symbol.equals(symbol.toLowerCase())) return true;
        return false;
    }

    /**
     * update
     * ----------------
     * - updates the quantity and price of an investment
     * @param: int qty: new qty to update investment with
     * @param: double price: new price to update investment with
     */
    public void update(int qty, double price, double FEE) {
        setQty(getQty() + qty);
        setPrice(price);
        setBookValue(getBookValue() + (qty * price) + FEE);
    }

    /**
     * sell
     * ----------------
     * - Removes the qty from the investment, and updated the price and bookvalue of investment
     * @param: int qty: quantity to sell
     * @param: double price: price to sell at, old price will be updated with this price also
     */
    public int sell(int qty, double price, double FEE) {
        if(getQty() < qty) return -1;
        setQty(getQty() - qty);
        setPrice(price);

        if(getQty() > 0) {
            setBookValue(getBookValue() * ((double)(getQty())/(double)(getQty()+qty)));
            System.out.printf("You've sold this stock for an amount of $%.2f\n", ((qty * price) - FEE));
            return 1;
        } else {
            //qty is 0
            System.out.printf("You've sold all of this stock for an amount of $%.2f\n", ((qty * price) - FEE));
            return 0;
        }
    }
}
