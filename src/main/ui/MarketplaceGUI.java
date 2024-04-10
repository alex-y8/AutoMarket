package ui;

import model.AccountWorkRoom;
import model.EventLog;
import model.GarageWorkRoom;
import model.cars.Car;
import persistence.JsonReaderAccount;
import persistence.JsonReaderGarage;
import persistence.JsonWriterAccount;
import persistence.JsonWriterGarage;

import javax.swing.*;
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
    private GarageWorkRoom userGarage;
    private AccountWorkRoom userAccount;

    private JsonWriterGarage jsonWriterOriginalMarket;
    private JsonWriterGarage jsonWriterMarket;

    private JsonReaderGarage jsonReaderOriginalMarket;
    private JsonReaderGarage jsonReaderMarket;
    private JsonReaderGarage jsonReaderGarage;
    private JsonReaderAccount jsonReaderAccount;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private static boolean isOriginalMarket;

    private JPanel mainMenu;

    private GridBagConstraints gbc;

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
        userGarage = new GarageWorkRoom();
        userAccount = new AccountWorkRoom();

        jsonWriterOriginalMarket = new JsonWriterGarage(JSON_ORIGINAL_MARKET);
        jsonWriterMarket = new JsonWriterGarage(JSON_MARKET);

        jsonReaderOriginalMarket = new JsonReaderGarage(JSON_ORIGINAL_MARKET);
        jsonReaderMarket = new JsonReaderGarage(JSON_MARKET);
        jsonReaderGarage = new JsonReaderGarage(JSON_GARAGE);
        jsonReaderAccount = new JsonReaderAccount(JSON_ACCOUNT);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this Marketplace will operate, creates the menu buttons
    private void initializeGraphics() {
        initializeMainMenu(gbc);
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
        setIconImage(new ImageIcon("src/images/logo.png").getImage());

        add(mainMenu);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: sets up the main menu's borders and buttons
    private void initializeMainMenu(GridBagConstraints gbc) {
        mainMenu = new JPanel();
        mainMenu.setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 10, 5, 10);
        addGraphic(gbc);

        gbc.gridx = 0;
        //gbc.gridwidth = 3;
        //gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
//        panel.add(imageButton, gbc);

        gbc.gridy++;

        mainMenu.add(createMarketplaceButton(), gbc);
        gbc.gridy++;
        mainMenu.add(createGarageButton(), gbc);
        gbc.gridy++;
        mainMenu.add(createAccountButton(), gbc);
        gbc.gridy++;
        mainMenu.add(quitButton(), gbc);
        //mainMenu.setLayout(new GridBagLayout());
        //mainMenu.setLayout(new GridLayout(0, 1, 0, 10));
        //mainMenu.setBorder(BorderFactory.createEmptyBorder(HEIGHT - 300, 20, 20, 20));
    }

    // EFFECTS: adds the application logo to the main menu
    private void addGraphic(GridBagConstraints gbc) {
        ImageIcon imageIcon = new ImageIcon("src/images/mainmenu_graphic.png");
        JLabel imageLabel = new JLabel();

        Image scaled = imageIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(scaled);

        imageLabel.setIcon(scaledImage);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(WIDTH - 100, (HEIGHT / 2) + 75));

        mainMenu.add(imageLabel, gbc);

    }

    // MODIFIES: this
    // EFFECTS: creates a pop-up window that prompts the user if they want to load their marketplace
    private void createLoadMarketplaceWindow() {
        String[] options = {"Yes", "No"};

        int loadData = JOptionPane.showOptionDialog(getContentPane(), "Load marketplace listings from file?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (loadData == 0) {
            //AbstractMenu.marketplace.loadUserListings();
            loadOriginalMarket();
            loadCars();
            isOriginalMarket = false;
        } else {
            loadCars();
            loadOriginalMarket();
            isOriginalMarket = true;
        }

    }

    // MODIFIES: this
    // EFFECTS: creates a pop-up window that prompts the user if they want to load their garage
    private void createLoadGarageWindow() {
        String[] options = {"Yes", "No"};

        int loadData = JOptionPane.showOptionDialog(getContentPane(), "Load garage from file?",
                "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (loadData == 0) {
            AbstractMenu.marketplace.loadGarage();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a pop-up window that prompts the user if they want to load their account information
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
            // EFFECTS:
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

    // MODIFIES: this
    // EFFECTS: loads the market if the user chooses not to load their market
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
        LogPrinter lp = new LogPrinter();
        String[] options = {"Save and quit", "Quit without saving"};

        int saveData = JOptionPane.showOptionDialog(getContentPane(), "Data is unsaved", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (saveData == 0) {
            if (SellCarMenu.getHasListedCar()) {
                if (isOriginalMarket) {
                    saveOriginalMarketplace();
                    //saveMarketplace(); //choose no load market, save and market saves 0 cars
                } else {
                    saveMarketplace();
                }
                //loadCars();
                System.out.println(marketplace.getCars().size());
                System.out.println(AbstractMenu.marketplace.getMarketplaceCars().numCars());

            }
            //AbstractMenu.marketplace.saveMarketplace();
            AbstractMenu.marketplace.saveGarage();
            AbstractMenu.marketplace.saveAccount();
        }
        lp.printLog(EventLog.getInstance());
        System.exit(0);
    }

    // MODIFIES: originalMarket.json
    // EFFECTS: saves the original marketplace to file
    private void saveOriginalMarketplace() {
        try {
            jsonWriterOriginalMarket.open();
            jsonWriterOriginalMarket.write(originalMarketplace);
            jsonWriterOriginalMarket.close();
            System.out.println("original market saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_ORIGINAL_MARKET);
        }
    }

    // MODIFIES: carMarket.json
    // EFFECTS: saves the marketplace to file
    public void saveMarketplace() {
//        try {
//            marketplace = jsonReaderMarket.read();
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_MARKET);
//        }
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
