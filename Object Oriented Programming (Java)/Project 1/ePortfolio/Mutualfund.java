package ePortfolio;

public class Mutualfund {
    
    private String symbol;
    private String name;
    private int qty;
    private double price;
    private double bookValue;
    final double REDEMPTION_FEE = 45;

    /**
     * Initialize Mutualfund values
     */
    Mutualfund(String symbol, String name, int qty, double price, double bookValue) {
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
        return symbol;
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
     * update
     * ----------------
     * - updates the quantity and price of a mutualfund
     * @param: int qty: new qty to update investment with
     * @param: double price: new price to update investment with
     */
    public void update(int qty, double price) {
        this.qty += qty;
        this.price = price;
        this.bookValue = this.bookValue + (qty * price);
    }

    /**
     * sell
     * ----------------
     * - Removes the qty from the investment, and updated the price and bookvalue of investment
     * @param: int qty: quantity to sell
     * @param: double price: price to sell at, old price will be updated with this price also
     */
    public int sell(int qty, double price) {
        if(this.qty < qty) return -1;
        this.qty -= qty;
        this.price = price;

        if(this.qty > 0) {
            this.bookValue = this.bookValue * ((double)(this.qty)/(double)(this.qty+qty));
            System.out.printf("You've sold this mutualfund for an amount of $%.2f\n", ((qty * price) - REDEMPTION_FEE));
            return 1;
        } else {
            //qty is 0, remove from portfolio
            System.out.printf("You've sold all of this mutualfund for an amount of $%.2f\n", ((qty * price) - REDEMPTION_FEE));
            return 0;
        }
    }
}
