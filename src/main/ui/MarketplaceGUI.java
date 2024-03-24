package ui;

import model.AccountWorkRoom;
import model.GarageWorkRoom;
import model.cars.Car;
import persistence.JsonReaderAccount;
import persistence.JsonReaderGarage;
import persistence.JsonWriterAccount;
import persistence.JsonWriterGarage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Graphical interface for the marketplace
public class MarketplaceGUI extends JFrame {

    private static final String JSON_MARKET = "./data/carMarket.json";
    private static final String JSON_ORIGINAL_MARKET = "./data/originalMarket.json";
    private static final String JSON_USER_MARKET = "./data/userCarMarket.json";
    private static final String JSON_GARAGE = "./data/garage.json";
    private static final String JSON_ACCOUNT = "./data/account.json";

    private static GarageWorkRoom marketplace;
    private static GarageWorkRoom originalMarketplace;
    private GarageWorkRoom userMarketplace;
    private GarageWorkRoom userGarage;
    private GarageWorkRoom filteredMarketplace;
    private AccountWorkRoom userAccount;

    private JsonWriterGarage jsonWriterMarket;
    private JsonWriterGarage jsonWriterUserMarket;
    private JsonWriterGarage jsonWriterGarage;
    private JsonWriterAccount jsonWriterAccount;

    private JsonReaderGarage jsonReaderOriginalMarket;
    private JsonReaderGarage jsonReaderMarket;
    private JsonReaderGarage jsonReaderUserMarket;
    private JsonReaderGarage jsonReaderGarage;
    private JsonReaderAccount jsonReaderAccount;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private static boolean isOriginalMarket;

    private JPanel mainMenu;

    // EFFECTS: constructs the marketplace gui
    public MarketplaceGUI() {
        initialize();
        initializeGraphics();
        createLoadMarketplaceWindow();
        createLoadGarageWindow();
        createLoadAccountWindow();
    }

    private void initialize() {
        marketplace = new GarageWorkRoom();
        originalMarketplace = new GarageWorkRoom();
        userMarketplace = new GarageWorkRoom();
        userGarage = new GarageWorkRoom();
        filteredMarketplace = new GarageWorkRoom();
        userAccount = new AccountWorkRoom();

        jsonWriterMarket = new JsonWriterGarage(JSON_MARKET);
        jsonWriterUserMarket = new JsonWriterGarage(JSON_USER_MARKET);
        jsonWriterGarage = new JsonWriterGarage(JSON_GARAGE);
        jsonWriterAccount = new JsonWriterAccount(JSON_ACCOUNT);

        jsonReaderOriginalMarket = new JsonReaderGarage(JSON_ORIGINAL_MARKET);
        jsonReaderMarket = new JsonReaderGarage(JSON_MARKET);
        jsonReaderUserMarket = new JsonReaderGarage(JSON_USER_MARKET);
        jsonReaderGarage = new JsonReaderGarage(JSON_GARAGE);
        jsonReaderAccount = new JsonReaderAccount(JSON_ACCOUNT);
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this Marketplace will operate, creates the menu buttons
    private void initializeGraphics() {
        initializeMainMenu();
        exitButton();
        //loadCars();
        loadGarage();
        loadAccount();

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        add(mainMenu);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu's borders and buttons
    private void initializeMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new BorderLayout());
        mainMenu.add(createMarketplaceButton());
        mainMenu.add(createGarageButton());
        mainMenu.add(createAccountButton());
        mainMenu.add(quitButton());
        mainMenu.setBounds(50, 100, WIDTH, HEIGHT);
        mainMenu.setLayout(new GridLayout(0, 1, 0, 10));
        mainMenu.setBorder(BorderFactory.createEmptyBorder(HEIGHT - 300, 20, 20, 20));
    }

    private void createLoadMarketplaceWindow() {
        String[] options = {"Yes", "No"};

        int loadData = JOptionPane.showOptionDialog(getContentPane(), "Load marketplace listings from file?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (loadData == 0) {
            //AbstractMenu.marketplace.loadUserListings();
            loadCars();
            isOriginalMarket = false;
        } else {
            loadOriginalMarket();
            isOriginalMarket = true;
        }

    }


    private void createLoadGarageWindow() {
        String[] options = {"Yes", "No"};

        int loadData = JOptionPane.showOptionDialog(getContentPane(), "Load garage from file?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (loadData == 0) {
            AbstractMenu.marketplace.loadGarage();
        }
    }

    private void createLoadAccountWindow() {
        String[] options = {"Yes", "No"};

        int loadData = JOptionPane.showOptionDialog(getContentPane(), "Load account balance from file?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (loadData == 0) {
            AbstractMenu.marketplace.loadAccount();
        }
    }

    // MODIFIES: this
    // EFFECTS:  creates the marketplace button for the menu
    private JButton createMarketplaceButton() {
        JButton marketplaceButton = new JButton("Marketplace");
        marketplaceButton.setFocusable(false);
        marketplaceButton.setFont(new Font("", Font.PLAIN, 24));

        marketplaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isOriginalMarket) {
                    List<Car> carList = originalMarketplace.getCars();
                    new MarketplaceMenu(carList);
                } else {
                    List<Car> carList = marketplace.getCars();
                    new MarketplaceMenu(carList);
                }
            }
        });

        return marketplaceButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the garage button for the menu
    private JButton createGarageButton() {
        JButton garageButton = new JButton("Garage");
        garageButton.setFocusable(false);
        garageButton.setFont(new Font("", Font.PLAIN, 24));

        garageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Car> garageList = AbstractMenu.marketplace.getUserGarage().getCars();
                new GarageMenu(garageList);
            }
        });

        return garageButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the account button for the menu
    private JButton createAccountButton() {
        JButton accountButton = new JButton("Account");
        accountButton.setFocusable(false);
        accountButton.setFont(new Font("p", Font.PLAIN, 24));

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //garage list just for placeholder value
                List<Car> garageList = userGarage.getCars();
                new AccountMenu(garageList);
            }
        });

        return accountButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the quit button for the menu
    private JButton quitButton() {
        JButton accountButton = new JButton("Quit");
        accountButton.setFocusable(false);
        accountButton.setFont(new Font("p", Font.PLAIN, 24));

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processQuit();
            }
        });

        return accountButton;

    }

    private void loadOriginalMarket() {
        try {
            originalMarketplace = jsonReaderOriginalMarket.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_ORIGINAL_MARKET);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the cars listed for sale onto the marketplace menu
    private void loadCars() {
        try {
            marketplace = jsonReaderMarket.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_MARKET);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the cars in the garage onto the garage menu
    private void loadGarage() {
        try {
            userGarage = jsonReaderGarage.read();
            if (userGarage.getCars().isEmpty()) {
                System.out.println("No file loaded - your garage is empty. Visit the marketplace to buy cars!");
            } else {
                System.out.println("Garage successfully loaded.");
            }
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_GARAGE);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads the user's account information
    private void loadAccount() {
        try {
            userAccount = jsonReaderAccount.read();
            System.out.println("Account balance successfully loaded.");
            System.out.println(userAccount.getBalance());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_GARAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: listener for when the user clicks the "x" button in the top-right corner
    private void exitButton() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                processQuit();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: prompt user to save data, then quits the program
    private void processQuit() {
        String[] options = {"Save and quit", "Quit without saving"};

        int saveData = JOptionPane.showOptionDialog(getContentPane(), "Data is unsaved", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (saveData == 0) {
            if (!isOriginalMarket || SellCarMenu.getHasListedCar()) {
                loadCars();

                saveMarketplace();
            }
            //AbstractMenu.marketplace.saveMarketplace();
            AbstractMenu.marketplace.saveGarage();
            AbstractMenu.marketplace.saveAccount();
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    public void saveMarketplace() {
        try {
            jsonWriterMarket.open();
            jsonWriterMarket.write(marketplace);
            jsonWriterMarket.close();
            System.out.println("Marketplace successfully saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_USER_MARKET);
        }
    }

    public static GarageWorkRoom getMarketplace() {
        return marketplace;
    }

    public static GarageWorkRoom getOriginalMarket() {
        return originalMarketplace;
    }

    public static boolean getIsOriginalMarket() {
        return isOriginalMarket;
    }

}
