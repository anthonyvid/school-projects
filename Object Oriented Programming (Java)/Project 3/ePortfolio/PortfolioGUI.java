package ePortfolio;

import java.util.ArrayList;
import ePortfolio.Investment.InvalidNameException;
import ePortfolio.Investment.InvalidQuantityException;
import ePortfolio.Investment.InvalidSymbolException;
import ePortfolio.Investment.InvalidPriceException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;

/**
 * This class handles all the GUI stuff that happens
 */
public class PortfolioGUI extends JFrame {

    int listIndex = 0; // Keeps track of index of investment in investments list

    // ~~~~~~ WINDOW SIZE ~~~~~~ //
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // ~~~~~~ PANEL NAMES ~~~~~~ //
    final static String HOMEPANEL = "Home Panel";
    final static String BUYPANEL = "Buy Panel";
    final static String SELLPANEL = "Sell Panel";
    final static String UPDATEPANEL = "Update Panel";
    final static String GAINSPANEL = "Gains Panel";
    final static String SEARCHPANEL = "Search Panel";

    // ~~~~~~ FONTS ~~~~~~ //
    Font boldFont = new Font("Arial", Font.BOLD, 16);

    // ~~~~~~ PANELS ~~~~~~ //
    private JPanel masterPanel;
    private JPanel homePanel;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel updatePanel;
    private JPanel gainsPanel;
    private JPanel searchPanel;

    // ~~~~~~ BUTTONS ~~~~~~ //
    JButton resetBtn;
    JButton buyBtn;
    JButton sellBtn;
    JButton prevBtn;
    JButton nextBtn;
    JButton saveBtn;

    // ~~~~~~ COMBOBOXES ~~~~~~ //
    JComboBox<String> buyPanelComboBox;

    // ~~~~~~ TEXT FIELDS ~~~~~~ //
    JTextField buytlRow3TextField;
    JTextField buytlRow4TextField;
    JTextField buytlRow5TextField;
    JTextField buytlRow6TextField;
    JTextField selltlRow2TextField;
    JTextField selltlRow3TextField;
    JTextField selltlRow4TextField;
    JTextField updatetlRow2TextField;
    JTextField updatetlRow3TextField;
    JTextField updatetlRow4TextField;
    JTextField totalGainTextField;
    JTextField searchtlRow2TextField;
    JTextField searchtlRow3TextField;
    JTextField searchtlRow4TextField;
    JTextField searchtlRow5TextField;

    // ~~~~~~ TEXT AREAS ~~~~~~ //
    JTextArea msgTextArea;

    /**
     * This is the class constructor that sets up some initial values
     */
    public PortfolioGUI() {
        super();
        setSize(WIDTH, HEIGHT);
        setTitle("My ePortfolio");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // If user exits window, save investments to file
        addWindowListener((WindowListener) new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                App.writeInvestmentsToFile(App.filename, Portfolio.investments);
                System.exit(0);
            }
        });

        masterPanel = new JPanel();
        masterPanel.setLayout(new CardLayout());

        // Make and add menu bar to window
        setJMenuBar(makeMenuBar());

        // Add items to master panel
        masterPanel.add(homePanel(), HOMEPANEL);
        masterPanel.add(buyPanel(), BUYPANEL);
        masterPanel.add(sellPanel(), SELLPANEL);
        masterPanel.add(updatePanel(), UPDATEPANEL);
        masterPanel.add(gainsPanel(), GAINSPANEL);
        masterPanel.add(searchPanel(), SEARCHPANEL);

        // Add master panel to window
        add(masterPanel);
        setContentPane(masterPanel);
    }

    /**
     * Sets up initial window that user sees when application starts up
     * 
     * @return JPanel
     */
    public JPanel homePanel() {
        homePanel = new JPanel();

        // Create text panel
        JPanel welcomeText = new JPanel();
        welcomeText.setLayout(new GridLayout(1, 2));

        // Create label to hold welcome message
        JLabel msg = new JLabel(
                "<html>Welcome to ePortfolio.<br/><br/><br/>Choose a command from the “Commands” menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.</html>");
        welcomeText.add(msg);
        welcomeText.setBorder(new EmptyBorder(90, 100, 100, 100));
        homePanel.setLayout(new GridLayout(1, 1));
        homePanel.add(welcomeText);

        return homePanel;
    }

    /**
     * Sets up buy window that user sees when buy menu option is selected
     * 
     * @return JPanel
     */
    public JPanel buyPanel() {
        buyPanel = new JPanel(new GridLayout(2, 1));

        // ~~~~~~~~~~~~~~~~~~~~~~~~ TOP PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        // --- TOP LEFT PANEL --- //
        JPanel topLeft = new JPanel(new GridLayout(6, 1, 0, 10));

        // Title
        JPanel tlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tlTitle = new JLabel("Buying an investment");
        tlTitle.setFont(boldFont);
        tlRow1.add(tlTitle);

        // Type of investment
        String[] types = { "Stock", "Mutualfund" };
        JPanel tlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow2Text = new JLabel("Type");
        buyPanelComboBox = new JComboBox<>(types);
        tlRow2.add(tlRow2Text);
        tlRow2.add(buyPanelComboBox);

        // Symbol
        JPanel tlRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow3Text = new JLabel("Symbol");
        buytlRow3TextField = new JTextField(8);
        tlRow3.add(tlRow3Text);
        tlRow3.add(buytlRow3TextField);

        // Name
        JPanel tlRow4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow4Text = new JLabel("Name");
        buytlRow4TextField = new JTextField(16);
        tlRow4.add(tlRow4Text);
        tlRow4.add(buytlRow4TextField);

        // Quantity
        JPanel tlRow5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow5Text = new JLabel("Quantity");
        buytlRow5TextField = new JTextField(8);
        tlRow5.add(tlRow5Text);
        tlRow5.add(buytlRow5TextField);

        // Price
        JPanel tlRow6 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow6Text = new JLabel("Price");
        buytlRow6TextField = new JTextField(8);
        tlRow6.add(tlRow6Text);
        tlRow6.add(buytlRow6TextField);

        // Add rows to top left panel
        topLeft.add(tlRow1);
        topLeft.add(tlRow2);
        topLeft.add(tlRow3);
        topLeft.add(tlRow4);
        topLeft.add(tlRow5);
        topLeft.add(tlRow6);

        // --- TOP RIGHT PANEL --- //
        JPanel topRight = new JPanel(new GridBagLayout());
        topRight.setBorder(new EmptyBorder(0, 150, 0, 0));

        JPanel btnsWrap = new JPanel();
        btnsWrap.setLayout(new GridLayout(2, 1, 50, 50));
        resetBtn = new JButton("Reset");
        buyBtn = new JButton("Buy");
        resetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetBtn.addActionListener(new BuyPanelResetBtnAction());
        buyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyBtn.addActionListener(new BuyPanelBuyBtnAction());
        btnsWrap.add(resetBtn);
        btnsWrap.add(buyBtn);
        topRight.add(btnsWrap);

        // Add top left and right panels to top panel
        topPanel.add(topLeft);
        topPanel.add(topRight);

        // ~~~~~~~~~~~~~~~~~~~~~~~~ BOTTOM PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.anchor = GridBagConstraints.NORTHWEST;
        gcon.weightx = 1;
        gcon.weighty = 1;

        JPanel bottomPanel = new JPanel(gbl);
        JPanel bottomTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomBottom = new JPanel();
        bottomBottom.setLayout(new BoxLayout(bottomBottom, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Messages");
        textLabel.setFont(boldFont);
        bottomTop.add(textLabel);

        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(msgTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottomBottom.add(scroll, BorderLayout.CENTER);

        gcon.gridy = 0;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomTop, gcon);

        gcon.gridy = 1;
        gcon.ipady = 180;
        gcon.insets = new Insets(0, 25, 0, 25);
        gcon.gridheight = GridBagConstraints.REMAINDER;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomBottom, gcon);

        // Add top and bottom to whole panel
        buyPanel.add(topPanel);
        buyPanel.add(bottomPanel);

        return buyPanel;
    }

    /**
     * Sets up sell window that user sees when sell menu option is selected
     * 
     * @return JPanel
     */
    public JPanel sellPanel() {
        sellPanel = new JPanel(new GridLayout(2, 1));

        // ~~~~~~~~~~~~~~~~~~~~~~~~ TOP PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        // --- TOP LEFT PANEL --- //
        JPanel topLeft = new JPanel(new GridLayout(6, 1, 0, 10));

        // Title
        JPanel tlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tlTitle = new JLabel("Selling an investment");
        tlTitle.setFont(boldFont);
        tlRow1.add(tlTitle);

        // Symbol
        JPanel tlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow2Text = new JLabel("Symbol");
        selltlRow2TextField = new JTextField(8);
        tlRow2.add(tlRow2Text);
        tlRow2.add(selltlRow2TextField);

        // Quantity
        JPanel tlRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow3Text = new JLabel("Quantity");
        selltlRow3TextField = new JTextField(8);
        tlRow3.add(tlRow3Text);
        tlRow3.add(selltlRow3TextField);

        // Price
        JPanel tlRow4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow4Text = new JLabel("Price");
        selltlRow4TextField = new JTextField(8);
        tlRow4.add(tlRow4Text);
        tlRow4.add(selltlRow4TextField);

        // Add rows to top left panel
        topLeft.add(tlRow1);
        topLeft.add(tlRow2);
        topLeft.add(tlRow3);
        topLeft.add(tlRow4);

        // --- TOP RIGHT PANEL --- //
        JPanel topRight = new JPanel(new GridBagLayout());
        topRight.setBorder(new EmptyBorder(0, 150, 0, 0));

        JPanel btnsWrap = new JPanel();
        btnsWrap.setLayout(new GridLayout(2, 1, 50, 50));
        resetBtn = new JButton("Reset");
        sellBtn = new JButton("Sell");
        resetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetBtn.addActionListener(new SellPanelResetBtnAction());
        sellBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        sellBtn.addActionListener(new SellPanelSellBtnAction());
        btnsWrap.add(resetBtn);
        btnsWrap.add(sellBtn);
        topRight.add(btnsWrap);

        // Add top left and right panels to top panel
        topPanel.add(topLeft);
        topPanel.add(topRight);

        // ~~~~~~~~~~~~~~~~~~~~~~~~ BOTTOM PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.anchor = GridBagConstraints.NORTHWEST;
        gcon.weightx = 1;
        gcon.weighty = 1;

        JPanel bottomPanel = new JPanel(gbl);
        JPanel bottomTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomBottom = new JPanel();
        bottomBottom.setLayout(new BoxLayout(bottomBottom, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Messages");
        textLabel.setFont(boldFont);
        bottomTop.add(textLabel);

        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(msgTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottomBottom.add(scroll, BorderLayout.CENTER);

        gcon.gridy = 0;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomTop, gcon);

        gcon.gridy = 1;
        gcon.ipady = 180;
        gcon.insets = new Insets(0, 25, 0, 25);
        gcon.gridheight = GridBagConstraints.REMAINDER;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomBottom, gcon);

        // Add top and bottom to whole panel
        sellPanel.add(topPanel);
        sellPanel.add(bottomPanel);

        return sellPanel;
    }

    /**
     * Sets up update window that user sees when update menu option is selected
     * 
     * @return JPanel
     */
    public JPanel updatePanel() {
        updatePanel = new JPanel(new GridLayout(2, 1));

        // ~~~~~~~~~~~~~~~~~~~~~~~~ TOP PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        // --- TOP LEFT PANEL --- //
        JPanel topLeft = new JPanel(new GridLayout(6, 1, 0, 10));

        // Title
        JPanel tlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tlTitle = new JLabel("Updating investments");
        tlTitle.setFont(boldFont);
        tlRow1.add(tlTitle);

        // Symbol
        JPanel tlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow2Text = new JLabel("Symbol");
        updatetlRow2TextField = new JTextField(8);
        updatetlRow2TextField.setEditable(false);
        tlRow2.add(tlRow2Text);
        tlRow2.add(updatetlRow2TextField);

        // Name
        JPanel tlRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow3Text = new JLabel("Name");
        updatetlRow3TextField = new JTextField(16);
        updatetlRow3TextField.setEditable(false);
        tlRow3.add(tlRow3Text);
        tlRow3.add(updatetlRow3TextField);

        // Show first investment
        if (Portfolio.investments.size() > 0) {
            updatetlRow2TextField.setText(Portfolio.investments.get(listIndex).getSymbol());
            updatetlRow3TextField.setText(Portfolio.investments.get(listIndex).getName());
        }

        // Price
        JPanel tlRow4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow4Text = new JLabel("Price");
        updatetlRow4TextField = new JTextField(8);
        tlRow4.add(tlRow4Text);
        tlRow4.add(updatetlRow4TextField);

        // Add rows to top left panel
        topLeft.add(tlRow1);
        topLeft.add(tlRow2);
        topLeft.add(tlRow3);
        topLeft.add(tlRow4);

        // --- TOP RIGHT PANEL --- //
        JPanel topRight = new JPanel(new GridBagLayout());
        topRight.setBorder(new EmptyBorder(0, 150, 0, 0));

        JPanel btnsWrap = new JPanel();
        btnsWrap.setLayout(new GridLayout(3, 1, 50, 50));
        prevBtn = new JButton("Prev");
        nextBtn = new JButton("Next");
        saveBtn = new JButton("Save");
        prevBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        prevBtn.addActionListener(new UpdatePanelPrevBtnAction());
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextBtn.addActionListener(new UpdatePanelNextBtnAction());
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(new UpdatePanelSaveBtnAction());
        btnsWrap.add(prevBtn);
        btnsWrap.add(nextBtn);
        btnsWrap.add(saveBtn);
        topRight.add(btnsWrap);

        // Add top left and right panels to top panel
        topPanel.add(topLeft);
        topPanel.add(topRight);

        // ~~~~~~~~~~~~~~~~~~~~~~~~ BOTTOM PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.anchor = GridBagConstraints.NORTHWEST;
        gcon.weightx = 1;
        gcon.weighty = 1;

        JPanel bottomPanel = new JPanel(gbl);
        JPanel bottomTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomBottom = new JPanel();
        bottomBottom.setLayout(new BoxLayout(bottomBottom, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Messages");
        textLabel.setFont(boldFont);
        bottomTop.add(textLabel);

        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(msgTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottomBottom.add(scroll, BorderLayout.CENTER);

        gcon.gridy = 0;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomTop, gcon);

        gcon.gridy = 1;
        gcon.ipady = 180;
        gcon.insets = new Insets(0, 25, 0, 25);
        gcon.gridheight = GridBagConstraints.REMAINDER;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomBottom, gcon);

        // Add top and bottom to whole panel
        updatePanel.add(topPanel);
        updatePanel.add(bottomPanel);

        return updatePanel;
    }

    /**
     * Sets up gains window that user sees when gains menu option is selected
     * 
     * @return JPanel
     */
    public JPanel gainsPanel() {
        gainsPanel = new JPanel(new GridLayout(2, 1));

        // ~~~~~~~~~~~~~~~~~~~~~~~~ TOP PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        // --- TOP TOP PANEL --- //
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JPanel topTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tlTitle = new JLabel("Getting total gain");
        tlTitle.setFont(boldFont);
        topTop.add(tlTitle);

        // --- TOP BOTTOM PANEL --- //
        JPanel topBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        JLabel tlRowText = new JLabel("Total gain");
        totalGainTextField = new JTextField(10);
        totalGainTextField.setEditable(false);
        topBottom.add(tlRowText);
        topBottom.add(totalGainTextField);

        topPanel.add(topTop);
        topPanel.add(topBottom);

        // ~~~~~~~~~~~~~~~~~~~~~~~~ BOTTOM PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.anchor = GridBagConstraints.NORTHWEST;
        gcon.weightx = 1;
        gcon.weighty = 1;

        JPanel bottomPanel = new JPanel(gbl);
        JPanel bottomTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomBottom = new JPanel();
        bottomBottom.setLayout(new BoxLayout(bottomBottom, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Individual gains");
        textLabel.setFont(boldFont);
        bottomTop.add(textLabel);

        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(msgTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottomBottom.add(scroll, BorderLayout.CENTER);

        gcon.gridy = 0;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomTop, gcon);

        gcon.gridy = 1;
        gcon.ipady = 180;
        gcon.insets = new Insets(0, 25, 0, 25);
        gcon.gridheight = GridBagConstraints.REMAINDER;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomBottom, gcon);

        // Add top and bottom to whole panel
        gainsPanel.add(topPanel);
        gainsPanel.add(bottomPanel);

        return gainsPanel;
    }

    public JPanel searchPanel() {
        searchPanel = new JPanel(new GridLayout(2, 1));

        // ~~~~~~~~~~~~~~~~~~~~~~~~ TOP PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        // --- TOP LEFT PANEL --- //
        JPanel topLeft = new JPanel(new GridLayout(6, 1, 0, 10));

        // Title
        JPanel tlRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel tlTitle = new JLabel("Searching investments");
        tlTitle.setFont(boldFont);
        tlRow1.add(tlTitle);

        // Symbol
        JPanel tlRow2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow2Text = new JLabel("Symbol");
        searchtlRow2TextField = new JTextField(8);
        tlRow2.add(tlRow2Text);
        tlRow2.add(searchtlRow2TextField);

        // Name Keywords
        JPanel tlRow3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow3Text = new JLabel("Name Keywords");
        searchtlRow3TextField = new JTextField(16);
        tlRow3.add(tlRow3Text);
        tlRow3.add(searchtlRow3TextField);

        // Quantity
        JPanel tlRow4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow4Text = new JLabel("Low price");
        searchtlRow4TextField = new JTextField(8);
        tlRow4.add(tlRow4Text);
        tlRow4.add(searchtlRow4TextField);

        // Price
        JPanel tlRow5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JLabel tlRow5Text = new JLabel("High price");
        searchtlRow5TextField = new JTextField(8);
        tlRow5.add(tlRow5Text);
        tlRow5.add(searchtlRow5TextField);

        // Add rows to top left panel
        topLeft.add(tlRow1);
        topLeft.add(tlRow2);
        topLeft.add(tlRow3);
        topLeft.add(tlRow4);
        topLeft.add(tlRow5);

        // --- TOP RIGHT PANEL --- //
        JPanel topRight = new JPanel(new GridBagLayout());
        topRight.setBorder(new EmptyBorder(0, 150, 0, 0));

        JPanel btnsWrap = new JPanel();
        btnsWrap.setLayout(new GridLayout(2, 1, 50, 50));
        resetBtn = new JButton("Reset");
        buyBtn = new JButton("Search");
        resetBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetBtn.addActionListener(new SearchPanelResetBtnAction());
        buyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyBtn.addActionListener(new SearchPanelSearchBtnAction());
        btnsWrap.add(resetBtn);
        btnsWrap.add(buyBtn);
        topRight.add(btnsWrap);

        // Add top left and right panels to top panel
        topPanel.add(topLeft);
        topPanel.add(topRight);

        // ~~~~~~~~~~~~~~~~~~~~~~~~ BOTTOM PANEL ~~~~~~~~~~~~~~~~~~~~~~~~ //
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.anchor = GridBagConstraints.NORTHWEST;
        gcon.weightx = 1;
        gcon.weighty = 1;

        JPanel bottomPanel = new JPanel(gbl);
        JPanel bottomTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomBottom = new JPanel();
        bottomBottom.setLayout(new BoxLayout(bottomBottom, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Messages");
        textLabel.setFont(boldFont);
        bottomTop.add(textLabel);

        msgTextArea = new JTextArea();
        msgTextArea.setEditable(false);
        msgTextArea.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(msgTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottomBottom.add(scroll, BorderLayout.CENTER);

        gcon.gridy = 0;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomTop, gcon);

        gcon.gridy = 1;
        gcon.ipady = 180;
        gcon.insets = new Insets(0, 25, 0, 25);
        gcon.gridheight = GridBagConstraints.REMAINDER;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomBottom, gcon);

        // Add top and bottom to whole panel
        searchPanel.add(topPanel);
        searchPanel.add(bottomPanel);

        return searchPanel;
    }

    /**
     * Sets up the menu bar for the window
     * 
     * @return JMenuBar
     */
    public JMenuBar makeMenuBar() {
        // Create MenuBar
        JMenuBar menubar = new JMenuBar();

        // Create Menu
        JMenu menu = new JMenu("Commands");

        // Create menu items
        JMenuItem buy = new JMenuItem("Buy");
        JMenuItem sell = new JMenuItem("Sell");
        JMenuItem update = new JMenuItem("Update");
        JMenuItem getGain = new JMenuItem("GetGain");
        JMenuItem search = new JMenuItem("Search");
        JMenuItem quit = new JMenuItem("Quit");

        // Add action listeners to menu items
        buy.addActionListener(new BuyPanelAction());
        sell.addActionListener(new SellPanelAction());
        update.addActionListener(new UpdatePanelAction());
        getGain.addActionListener(new GetGainPanelAction());
        search.addActionListener(new SearchPanelAction());
        quit.addActionListener(new QuitPanelAction());

        // Add menu items to menu
        menu.add(buy);
        menu.add(sell);
        menu.add(update);
        menu.add(getGain);
        menu.add(search);
        menu.add(quit);

        menubar.add(menu);

        return menubar;
    }

    /**
     * Resets the state of the msgTextArea
     * 
     * @param panel
     */
    public void resetMsgTextArea(JPanel panel) {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gcon = new GridBagConstraints();
        gcon.anchor = GridBagConstraints.NORTHWEST;
        gcon.weightx = 1;
        gcon.weighty = 1;

        JPanel bottomPanel = new JPanel(gbl);
        JPanel bottomTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomBottom = new JPanel();
        bottomBottom.setLayout(new BoxLayout(bottomBottom, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Messages");
        textLabel.setFont(boldFont);
        bottomTop.add(textLabel);

        JScrollPane scroll = new JScrollPane(msgTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        bottomBottom.add(scroll, BorderLayout.CENTER);

        gcon.gridy = 0;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomTop, gcon);

        gcon.gridy = 1;
        gcon.ipady = 180;
        gcon.insets = new Insets(0, 25, 0, 25);
        gcon.gridheight = GridBagConstraints.REMAINDER;
        gcon.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(bottomBottom, gcon);

        panel.remove(1);
        panel.add(bottomPanel, 1);
    }

    // ~~~~~~~~~~~~~~~ Action Listeners for Menu Items ~~~~~~~~~~~~~~~ //
    /**
     * Action listener class for when buy menu option is pressed
     */
    private class BuyPanelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (masterPanel.getLayout());
            cl.show(masterPanel, BUYPANEL);
        }
    }

    /**
     * Action listener class for when sell menu option is pressed
     */
    private class SellPanelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (masterPanel.getLayout());
            cl.show(masterPanel, SELLPANEL);
        }
    }

    /**
     * Action listener class for when update menu option is pressed
     */
    private class UpdatePanelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (masterPanel.getLayout());
            cl.show(masterPanel, UPDATEPANEL);
        }
    }

    /**
     * Action listener class for when get gain menu option is pressed
     */
    private class GetGainPanelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (masterPanel.getLayout());
            cl.show(masterPanel, GAINSPANEL);

            msgTextArea.setText("");
            resetMsgTextArea(gainsPanel);
            repaint();
            revalidate();

            totalGainTextField.setText("$" + App.roundOffTo2DecPlaces(Portfolio.getGain()));

            for (Investment investment : Portfolio.investments) {
                String gain = Double.toString(
                        investment.getQty() * investment.getPrice() - investment.getFee() - investment.getBookValue());
                msgTextArea.append(investment.toString() + "\nGain for " + investment.getName() + ": $" + gain
                        + "\n---------------------------");
            }

            resetMsgTextArea(gainsPanel);
            repaint();
            revalidate();
        }
    }

    /**
     * Action listener class for when search menu option is pressed
     */
    private class SearchPanelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (masterPanel.getLayout());
            cl.show(masterPanel, SEARCHPANEL);
        }
    }

    /**
     * Action listener class for when quit menu option is pressed
     */
    private class QuitPanelAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            App.writeInvestmentsToFile(App.filename, Portfolio.investments);
            System.exit(0);
        }
    }

    // ~~~~~~~~~~~~~~~ Action Listeners for Buy Panel ~~~~~~~~~~~~~~~ //
    /**
     * Action listener class for when reset button on buy panel is pressed
     */
    private class BuyPanelResetBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            resetMsgTextArea(buyPanel);
            buytlRow3TextField.setText("");
            buytlRow4TextField.setText("");
            buytlRow5TextField.setText("");
            buytlRow6TextField.setText("");
            repaint();
            revalidate();
        }
    }

    /**
     * Action listener class for when buy button on buy panel is pressed
     */
    private class BuyPanelBuyBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            String symbol, name, tempQuantity, tempPrice, type;
            int quantity;
            double price;

            // Validate Input
            try {
                type = String.valueOf(buyPanelComboBox.getSelectedItem());
                symbol = buytlRow3TextField.getText();
                System.out.println(symbol);
                name = buytlRow4TextField.getText();
                tempQuantity = buytlRow5TextField.getText();
                tempPrice = buytlRow6TextField.getText();

                if (symbol.isBlank() || name.isBlank() || tempQuantity.isBlank() || tempPrice.isBlank())
                    throw new Exception("Please fill out all fields\n");

                quantity = Integer.parseInt(tempQuantity);
                price = Double.parseDouble(tempPrice);

                if (type.equals("Stock")) {
                    // User entered in Mutualfund but selected Stock option
                    if (Portfolio.getInvestment(symbol) instanceof Mutualfund)
                        throw new Exception(
                                "This is a Mutualfund that you own, please select \"Mutualfund\" to buy more Mutualfunds.\n");

                    // Check if they already own the stock
                    if (Portfolio.getInvestment(symbol) instanceof Stock) {
                        msgTextArea.append("You own this Stock!\n");
                        Portfolio.getInvestment(symbol).update(quantity, price);
                    } else {
                        msgTextArea.append("Successfully purchased new Stock!\n");
                        Portfolio.investments.add(new Stock(symbol, name, quantity, price, quantity * price + 9.99));
                        Portfolio.addNames2HashMap(name, symbol);
                    }
                } else if (type.equals("Mutualfund")) {
                    System.out.println("buying mutualfund");
                    // User entered in Mutualfund but selected Stock option
                    if (Portfolio.getInvestment(symbol) instanceof Stock)
                        throw new Exception(
                                "This is a Stock that you own, please select \"Stock\" to buy more Stocks.\n");

                    // Check if they already own the stock
                    if (Portfolio.getInvestment(symbol) instanceof Mutualfund) {
                        msgTextArea.append("You own this Mutualfund!\n");
                        Portfolio.getInvestment(symbol).update(quantity, price);
                    } else {
                        msgTextArea.append("Successfully purchased new Mutualfund!\n");
                        Portfolio.investments.add(new Mutualfund(symbol, name, quantity, price, quantity * price));
                        Portfolio.addNames2HashMap(name, symbol);
                    }
                } else {
                    System.out.println("Internal Error");
                    System.exit(0);
                }
                msgTextArea.append(Portfolio.getInvestment(symbol).toString());
            } catch (InvalidSymbolException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (InvalidNameException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (InvalidQuantityException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (InvalidPriceException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (NumberFormatException ex) {
                msgTextArea.append("Invalid entry:\nPrice or Quantity is not a number\n");
            } catch (Exception ex) {
                msgTextArea.append("Invalid entry:\n" + ex.getMessage());
            }
            resetMsgTextArea(buyPanel);
            repaint();
            revalidate();
        }
    }

    // ~~~~~~~~~~~~~~~ Action Listeners for Sell Panel ~~~~~~~~~~~~~~~ //
    /**
     * Action listener class for when reset button on sell panel is pressed
     */
    private class SellPanelResetBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            resetMsgTextArea(sellPanel);
            selltlRow2TextField.setText("");
            selltlRow3TextField.setText("");
            selltlRow4TextField.setText("");
            repaint();
            revalidate();
        }
    }

    /**
     * Action listener class for when sell button on sell panel is pressed
     */
    private class SellPanelSellBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            String symbol, tempQuantity, tempPrice;
            int quantity;
            double price;

            try {
                symbol = selltlRow2TextField.getText();
                tempQuantity = selltlRow3TextField.getText();
                tempPrice = selltlRow4TextField.getText();

                if (symbol.isBlank() || tempQuantity.isBlank() || tempPrice.isBlank())
                    throw new Exception("Please fill out all fields\n");

                if (Portfolio.getInvestment(symbol) == null)
                    throw new Exception("Investment not found, try agian\n");

                quantity = Integer.parseInt(tempQuantity);
                price = Double.parseDouble(tempPrice);

                int result = Portfolio.getInvestment(symbol).sell(quantity, price);
                if (result == 0) {
                    msgTextArea.append("You've sold all of this investment for an amount of $"
                            + App.roundOffTo2DecPlaces((quantity * price) - Portfolio.getInvestment(symbol).getFee()));
                    Portfolio.removeNamesFromHashMap(Portfolio.getInvestment(symbol));
                    Portfolio.investments.remove(Portfolio.getInvestment(symbol));
                } else if (result == -1) {
                    throw new Exception("Quantity entered exceeps quantity of investment\n");
                } else {
                    msgTextArea.append("You've sold this investment for an amount of $"
                            + App.roundOffTo2DecPlaces((quantity * price) - Portfolio.getInvestment(symbol).getFee()));
                }
            } catch (InvalidSymbolException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (InvalidQuantityException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (InvalidPriceException ex) {
                msgTextArea.append(ex.getMessage());
            } catch (NumberFormatException ex) {
                msgTextArea.append("Invalid entry:\nPrice or Quantity is not a number\n");
            } catch (Exception ex) {
                msgTextArea.append("Invalid entry:\n" + ex.getMessage());
            }
            resetMsgTextArea(sellPanel);
            repaint();
            revalidate();
        }
    }

    // ~~~~~~~~~~~~~~~ Action Listeners for Update Panel ~~~~~~~~~~~~~~~ //
    /**
     * Action listener class for when previous button on update panel is pressed
     */
    private class UpdatePanelPrevBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            repaint();
            revalidate();
            try {
                listIndex--;
                nextBtn.setEnabled(true);
                updatetlRow2TextField.setText(Portfolio.investments.get(listIndex).getSymbol());
                updatetlRow3TextField.setText(Portfolio.investments.get(listIndex).getName());
            } catch (IndexOutOfBoundsException ex) {
                msgTextArea.setText("Warning: Reached first investment in your portfolio\n");
                prevBtn.setEnabled(false);
                listIndex = 0;
                resetMsgTextArea(updatePanel);
                repaint();
                revalidate();
            }
        }
    }

    /**
     * Action listener class for when next button on update panel is pressed
     */
    private class UpdatePanelNextBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            repaint();
            revalidate();
            try {
                listIndex++;
                prevBtn.setEnabled(true);
                updatetlRow2TextField.setText(Portfolio.investments.get(listIndex).getSymbol());
                updatetlRow3TextField.setText(Portfolio.investments.get(listIndex).getName());
            } catch (IndexOutOfBoundsException ex) {
                msgTextArea.setText("Warning: Reached last investment in your portfolio\n");
                nextBtn.setEnabled(false);
                listIndex--;
                ;
                resetMsgTextArea(updatePanel);
                repaint();
                revalidate();
            }
        }
    }

    /**
     * Action listener class for when save button on update panel is pressed
     */
    private class UpdatePanelSaveBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // If no investments in list
                if (Portfolio.investments.size() < 1)
                    throw new Exception("Warning: No available investments to update\n");

                String tempPrice = updatetlRow4TextField.getText();
                double price;

                if (tempPrice.isBlank())
                    throw new Exception("Warning: Please fill out price field\n");

                if (Portfolio.getInvestment(updatetlRow2TextField.getText()) == null)
                    throw new Exception("Warning: Investment not found, please reload system\n");

                price = Double.parseDouble(tempPrice);

                Portfolio.getInvestment(updatetlRow2TextField.getText()).setPrice(price);
                msgTextArea.setText("Successfully updated investment:\n"
                        + Portfolio.getInvestment(updatetlRow2TextField.getText()).toString());

            } catch (InvalidPriceException ex) {
                msgTextArea.setText(ex.getMessage());
            } catch (NumberFormatException ex) {
                msgTextArea.setText("Invalid entry:\nPrice is not a number\n");
            } catch (Exception ex) {
                msgTextArea.setText(ex.getMessage());
            }
            resetMsgTextArea(updatePanel);
            repaint();
            revalidate();
        }
    }

    // ~~~~~~~~~~~~~~~ Action Listeners for Search Panel ~~~~~~~~~~~~~~~ //
    /**
     * Action listener class for when reset button on search panel is pressed
     */
    private class SearchPanelResetBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            searchtlRow2TextField.setText("");
            searchtlRow3TextField.setText("");
            searchtlRow4TextField.setText("");
            searchtlRow5TextField.setText("");
            resetMsgTextArea(searchPanel);
            repaint();
            revalidate();
        }
    }

    /**
     * Action listener class for when search button on search panel is pressed
     */
    private class SearchPanelSearchBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            msgTextArea.setText("");
            resetMsgTextArea(searchPanel);
            repaint();
            revalidate();

            String fromPrice, toPrice, keywords, symbol, priceRange;
            ArrayList<Investment> results = new ArrayList<>();

            try {
                symbol = searchtlRow2TextField.getText();
                keywords = searchtlRow3TextField.getText();
                fromPrice = searchtlRow4TextField.getText();
                toPrice = searchtlRow5TextField.getText();

                if (fromPrice.isBlank() && toPrice.isBlank()) {
                    System.out.println("ya");
                    priceRange = "";
                } else if (fromPrice.equals("0") && !toPrice.isBlank()) {
                    priceRange = "-" + toPrice;
                } else if (!fromPrice.isBlank() && toPrice.equals("-")) {
                    priceRange = fromPrice + "-";
                } else if (fromPrice.isBlank() && !toPrice.isBlank()) {
                    priceRange = toPrice;
                } else if (toPrice.isBlank() && !fromPrice.isBlank()) {
                    priceRange = fromPrice;
                } else if (fromPrice.equals(toPrice)) {
                    priceRange = fromPrice;
                } else {
                    priceRange = fromPrice + "-" + toPrice;
                }

                results = Portfolio.searchInvestments(symbol, keywords, priceRange);

                if (results.size() == 0)
                    throw new Exception("No investments found");

                for (Investment investment : results) {
                    msgTextArea.append(investment.toString() + "--------------------");
                }

            } catch (Exception ex) {
                msgTextArea.setText("Error: " + ex.getMessage());
            }
            resetMsgTextArea(searchPanel);
            repaint();
            revalidate();
        }
    }
}