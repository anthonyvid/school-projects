# ePortfolio CIS2430

### General Problem

Creating a program to track and manage an investor's portfolio for stocks and mutualfunds.

### Assumptions and Limitations

It is a assumed that the user will have the knowledge of the current stock or mutualfund price that they are adding into their portfolio. This results in the limitaion of having to constantly update the prices in the portfolio everyday to get accurate results.

-   The program will terminate if a command line argument is not given.

### Building Program

The user will need to run the program from an IDE.

The user can compile from the console from inside the root directory:

`javac javac ePortfolio/App.java`

To run after compilation:

`java ePortfolio.App <filename>.txt`

### Test Cases

#### **BUY**

##### **Buying a stock with valid info**

```
INPUT: AAPL
INPUT: apple
INPUT: 50
INPUT: 158.03

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 50
Price: 158.03
Bookvalue: 7891.51
```

##### **Buying a stock with invalid info**

```
INPUT: AAPL
INPUT: apple
INPUT: -50
OUTPUT: Invalid info, please try again
```

##### **Buying a stock with mutualfund symbol**

```
INPUT: AAPL
OUTPUT: This is a mutualfund you are trying to buy not a stock, please try again
```

##### **Adding more shares of a stock that doesnt exist in your portfolio**

```
INPUT: AAPL
OUTPUT: Stock doesnt exist, please try again
```

#### **SELL**

##### **Selling a stock with valid info**

```
INPUT: AAPL
INPUT: apple
INPUT: 25
INPUT: 100

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100
```

##### **Selling a stock with invalid info**

```
INPUT: AAPL
INPUT: apple
INPUT: -50
OUTPUT: Invalid info, please try again
```

#### **UPDATE**

##### **Updating prices of investments**

```
INPUT: 100
VALID INPUT

INPUT: -100
INVALID INPUT
```

#### **SEARCH**

##### **Searching with invalid symbol**

```
INPUT: BRKR
OUTPUT: Cant find a stock or mutualfund with that symbol, try again.
```

##### **Searching with valid symbol**

```
INPUT: AAPL
INPUT: Apple
INPUT: 100-250

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100
```

##### **Searching with valid price range**

```
INPUT: AAPL
INPUT: Apple
INPUT: 100-250

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100
```

##### **Searching with invalid price range**

```
INPUT: AAPL
INPUT: Apple
INPUT: one hundred

OUTPUT:
Invalid options try again
```

##### **Searching with keyword that is not found**

```
INPUT:
INPUT: L
INPUT:

OUTPUT:

```

-   No investment found so nothing prints out

##### **Searching with keyword that is at the start of list**

```
INPUT:
INPUT: Apple
INPUT:

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100

```

##### **Searching with keyword that is at the end of list**

```
INPUT:
INPUT: Apple
INPUT:

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100

```

##### **Searching with keyword that is in the middle of list**

```
INPUT:
INPUT: Apple
INPUT:

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100
```

##### **Searching with multiple keywords**

```
INPUT:
INPUT: Apple Computers
INPUT:

OUTPUT OF STOCK:
Name: Apple
Symbol: AAPL
Quantity: 25
Price: 100

```

##### **Running with no command line argument**

```
java ePortfolio.App

OUTPUT:
No filename given, program terminating...

```

### Improvements

An improvement that can be made in the future, is making the prices of the investments automatically update on their own. A GUI can also be made as an improvement.
