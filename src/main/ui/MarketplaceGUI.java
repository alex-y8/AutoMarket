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
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

// Graphical interface for the marketplace
public class MarketplaceGUI extends JFrame {

    private static final String JSON_MARKET = "./data/carMarket.json";
    private static final String JSON_USER_MARKET = "./data/userCarMarket.json";
    private static final String JSON_GARAGE = "./data/garage.json";
    private static final String JSON_ACCOUNT = "./data/account.json";


    private GarageWorkRoom marketplace;
    private GarageWorkRoom userMarketplace;
    private GarageWorkRoom userGarage;
    private GarageWorkRoom filteredMarketplace;
    private AccountWorkRoom userAccount;

    private JsonWriterGarage jsonWriterUserMarket;
    private JsonWriterGarage jsonWriterGarage;
    private JsonWriterAccount jsonWriterAccount;

    private JsonReaderGarage jsonReaderMarket;
    private JsonReaderGarage jsonReaderUserMarket;
    private JsonReaderGarage jsonReaderGarage;
    private JsonReaderAccount jsonReaderAccount;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private JPanel mainMenu;
    private JPanel marketplaceMenu;
    private JPanel garageMenu;

    private JLabel carStats;

    private final DecimalFormat df = new DecimalFormat("#,###.##");

    // EFFECTS: constructs the marketplace gui
    public MarketplaceGUI() {
        initialize();
        initializeGraphics();

    }

    private void initialize() {
        marketplace = new GarageWorkRoom();
        userMarketplace = new GarageWorkRoom();
        userGarage = new GarageWorkRoom();
        filteredMarketplace = new GarageWorkRoom();
        userAccount = new AccountWorkRoom();

        jsonWriterUserMarket = new JsonWriterGarage(JSON_USER_MARKET);
        jsonWriterGarage = new JsonWriterGarage(JSON_GARAGE);
        jsonWriterAccount = new JsonWriterAccount(JSON_ACCOUNT);

        jsonReaderMarket = new JsonReaderGarage(JSON_MARKET);
        jsonReaderUserMarket = new JsonReaderGarage(JSON_USER_MARKET);
        jsonReaderGarage = new JsonReaderGarage(JSON_GARAGE);
        jsonReaderAccount = new JsonReaderAccount(JSON_ACCOUNT);
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this Marketplace will operate, creates the menu buttons
    private void initializeGraphics() {
        initializeMainMenu();
        //initializeMarketplaceMenu();
        loadCars();
        //initializeGarageMenu();
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

    // MODIFIES: this
    // EFFECTS: sets up the marketplace's borders and buttons
    private void initializeMarketplaceMenu() {
        CarListMenu carListMenu = new CarListMenu();

        marketplaceMenu = new JPanel();
        marketplaceMenu.setLayout(new BorderLayout());

//        marketplaceMenu.setLayout(new GridLayout(0, 1, 0, 10));
//        marketplaceMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


//        marketplaceMenu.setVisible(false);
        loadCars();

    }

    // MODIFIES: this
    // EFFECTS: sets up the garage menu's borders and buttons
    private void initializeGarageMenu() {
        garageMenu = new JPanel();
        garageMenu.setLayout(new BorderLayout());

        garageMenu.setBounds(0, 0, WIDTH - 20, HEIGHT - 20);
        garageMenu.setLayout(new GridLayout(0, 1, 0, 10));
        garageMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        garageMenu.add(scroll);

        garageMenu.setVisible(false);
        loadGarage();

    }

    // MODIFIES: this
    // EFFECTS:  creates the marketplace button for the menu
    private JButton createMarketplaceButton() {
        JButton marketplaceButton = new JButton("Marketplace");
        marketplaceButton.setFocusable(false);
        marketplaceButton.setFont(new Font("/", Font.PLAIN, 24));

        marketplaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bring up marketplace window with cars
                //displayMarketplaceMenu();
                loadCars();
                List<Car> carList = marketplace.getCars();
                new MarketplaceMenu(carList);
            }
        });

        return marketplaceButton;

    }

    // MODIFIES: this
    // EFFECTS:  creates the garage button for the menu
    private JButton createGarageButton() {
        JButton garageButton = new JButton("Garage");
        garageButton.setFocusable(false);
        garageButton.setFont(new Font("p", Font.PLAIN, 24));

        garageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bring up marketplace window with garage
                //displayGarage();
                List<Car> garageList = userGarage.getCars();
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
                //bring up marketplace window with account info
                new AccountMenu();
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
    // EFFECTS: loads the cars listed for sale onto the marketplace menu
    private void loadCars() {
        //convert json file to list
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
    // EFFECTS: displays the cars listed for sale on the marketplace
    private void displayMarketplaceMenu() {


        //add(carStats);
        marketplaceMenu.setVisible(true);
        mainMenu.setVisible(false);
        garageMenu.setVisible(false);
        displayMarketplaceCars();
        add(marketplaceMenu);
        JScrollPane scrollBar = new JScrollPane(marketplaceMenu);
        add(scrollBar, BorderLayout.CENTER);
    }

    private String[] createCarList() {
        String[] cars = new String[marketplace.numCars()];
        for (int i = 0; i < marketplace.numCars(); i++) {
            cars[i] = marketplace.getCars().get(i).toString();
        }
        return cars;

    }

    private Car[] createCarJList() {
        Car[] cars = new Car[marketplace.numCars()];
        for (int i = 0; i < marketplace.numCars(); i++) {
            cars[i] = marketplace.getCars().get(i);
        }
        return cars;
    }

    private void displayMarketplaceCars() {

        //JList<String> carsList = new JList<>(createCarList());

        CarListMenu carListMenu = new CarListMenu();
        JList<Car> carJList = new JList<>(createCarJList());
        carJList.setCellRenderer(carListMenu);

//        carsList.setVisibleRowCount(marketplace.numCars());
//        carsList.setFixedCellHeight(100);

//        marketplaceMenu.add(carsList);
        marketplaceMenu.add(carJList);
    }



    // MODIFIES: this
    // EFFECTS: loads the cars from the JSON file
    private void displayMarketplaceCars2() {

        String stats = "";
        for (int i = 0; i < marketplace.numCars(); i++) {
            JLabel statsLabel = new JLabel();
            stats = (i + 1) + ". " + marketplace.getCars().get(i).getYear() + " "
                    + marketplace.getCars().get(i).getManufacturer()
                    + " " + marketplace.getCars().get(i).getModel() + " $"
                    + df.format(marketplace.getCars().get(i).getPrice()) + "\n" + "Speed: "
                    + marketplace.getCars().get(i).getSpeed() + "\n" + "Handling: "
                    + marketplace.getCars().get(i).getHandling() + "\n" + "Acceleration: "
                    + marketplace.getCars().get(i).getAcceleration() + "\n" + "Braking: "
                    + marketplace.getCars().get(i).getBraking() + "\n" + "Drive type: "
                    + marketplace.getCars().get(i).getDriveType() + "\n";
            statsLabel.setText(stats);
            marketplaceMenu.add(statsLabel);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the cars in the user's garage
    private void displayGarage() {
        add(garageMenu);
        garageMenu.setVisible(true);
        marketplaceMenu.setVisible(false);
        mainMenu.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: prompt user to save data, then quits the program
    private void processQuit() {
        String[] options = {"Save and quit", "Quit without saving"};

        int saveData = JOptionPane.showOptionDialog(getContentPane(), "Data is unsaved", "",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (saveData == 1) {
            //save data
            System.exit(0);
        } else {
            System.exit(0);
        }
    }


}
