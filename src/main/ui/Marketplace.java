package ui;

import exceptions.IllegalAccountBalanceException;
import model.Account;
import model.AccountWorkRoom;
import model.GarageWorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import persistence.JsonReaderAccount;
import persistence.JsonReaderGarage;
import persistence.JsonWriterAccount;
import persistence.JsonWriterGarage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

// Console car marketplace, where car listings are shown and up for sale
// Inspired by CPSC210 TellerApp
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class Marketplace {

    private static final String JSON_MARKET = "./data/carMarket.json";
    private static final String JSON_FILTERED_MARKET = "./data/filteredCarMarket.json";
    private static final String JSON_USER_MARKET = "./data/userCarMarket.json";
    private static final String JSON_GARAGE = "./data/garage.json";
    private static final String JSON_ACCOUNT = "./data/account.json";

    //private Account userAccount;
    private Scanner input;

    private final DecimalFormat df = new DecimalFormat("#,###.##");
    private boolean isFiltered = false;
    private boolean listedCar = false;
    private boolean isDefaultMarket = true;

    private GarageWorkRoom marketplace;
    private GarageWorkRoom userMarketplace;
    private GarageWorkRoom userGarage;
    private GarageWorkRoom filteredMarketplace;
    private AccountWorkRoom userAccount;

    //private JsonWriter jsonWriterMarket; probably won't want to write to this file
    private JsonWriterGarage jsonWriterUserMarket;
    private JsonWriterGarage jsonWriterGarage;
    private JsonWriterAccount jsonWriterAccount;

    private JsonReaderGarage jsonReaderMarket;
    private JsonReaderGarage jsonReaderUserMarket;
    private JsonReaderGarage jsonReaderGarage;
    private JsonReaderAccount jsonReaderAccount;

    // EFFECTS: runs the marketplace application
    public Marketplace() {
        //runMarketplace();
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMarketplace() {
        boolean keepGoing = true;
        String command;

        System.out.println("Welcome to AutoMarket!");
        askLoadFiles();
        displayMenu();

        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                quit();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Quitting...");
    }

    // MODIFIES: this
    // EFFECTS: asks the user if they want to load from their files
    private void askLoadFiles() {
        System.out.println("Would you like to load your garage from file? (Y/N)");
        if (input.next().toLowerCase().equals("y")) {
            loadGarage();
        }
        System.out.println("Would you like to load your marketplace listings from file? (Y/N)");
        if (input.next().toLowerCase().equals("y")) {
            loadUserListings();
            isDefaultMarket = false;
            listedCar = true;
        } else {
            loadListings();
        }
        System.out.println("Would you like to load your account balance from file? (Y/N)");
        if (input.next().toLowerCase().equals("y")) {
            loadAccount();
        } else {
            userAccount.getAccount().set(0, new Account(0));
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the JSON reader, JSON writer, and marketplace
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

        //account = new Account(0);
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: loads the user's garage cars
    public void loadGarage() {
        try {
            userGarage = jsonReaderGarage.read();
            if (userGarage.getCars().isEmpty()) {
                System.out.println("No file loaded - your garage is empty. Visit the marketplace to buy cars!");
            } else {
                System.out.println("Garage successfully loaded.");
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_GARAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the user's account balance
    public void loadAccount() {
        try {
            userAccount = jsonReaderAccount.read();
            System.out.println("Account balance successfully loaded.");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_GARAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the user's marketplace car listings
    public void loadUserListings() {
        try {
            userMarketplace = jsonReaderUserMarket.read();
            System.out.println("Marketplace listings successfully loaded.");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_USER_MARKET);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the default marketplace car listings
    private void loadListings() {
        try {
            marketplace = jsonReaderMarket.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_MARKET);
        }
    }

    // MODIFIES: userGarage workroom
    // EFFECTS: saves the user's garage workroom to file
    public void saveGarage() {
        try {
            jsonWriterGarage.open();
            jsonWriterGarage.write(userGarage);
            jsonWriterGarage.close();
            System.out.println("Garage successfully saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_GARAGE);
        }
    }

    // MODIFIES: marketplace workroom
    // EFFECTS: saves the user's marketplace listings to file
    public void saveMarketplace() {
        try {
            jsonWriterUserMarket.open();
            jsonWriterUserMarket.write(userMarketplace);
            jsonWriterUserMarket.close();
            System.out.println("Marketplace successfully saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_USER_MARKET);
        }
    }

    // MODIFIES: userAccount workroom
    // EFFECTS: saves the user's account balance to file
    public void saveAccount() {
        try {
            jsonWriterAccount.open();
            jsonWriterAccount.write(userAccount);
            jsonWriterAccount.close();
            System.out.println("Account balance successfully saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_ACCOUNT);
        }
    }


    // MODIFIES: this
    // EFFECTS: processes user command for the main menu
    private void processCommand(String command) {
        if (command.equals("m")) {
            if (!isFiltered) {
                System.out.println("Now displaying the marketplace.\n");
                chooseMarketListing();
                processMarketCommandsUnfiltered(input.next());
            } else {
                System.out.println("Now displaying the filtered marketplace.\n");
                chooseMarketListing();
                processMarketCommandsFiltered(input.next());
            }
        } else if (command.equals("g")) {
            System.out.println("Now displaying your garage.\n");
            viewGarage();
        } else if (command.equals("a")) {
            System.out.println("Now displaying your account information.\n");
            displayAccountInfo();
            processAccountCommands(input.next());
        } else {
            System.out.println("Invalid input. Please select one of the options.");
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands for the marketplace menu, where the market is unfiltered
    private void processMarketCommandsUnfiltered(String command) {
        if (command.equals("b")) {
            System.out.println("Type the number of the car you would like to buy:");
            try {
                processBuyCar(input.nextInt() - 1);
            } catch (IndexOutOfBoundsException | InputMismatchException e) {
                System.out.println("Please choose from one of the cars listed, or enter an integer.");
            }
        } else if (command.equals("f")) {
            System.out.println("Filter by: \nYear\nSpeed\nHandling\nAcceleration\nBraking\nDrivetype\nPrice");
            filter(input.next().toLowerCase());
        } else if (command.equals("d")) {
            checkMarketStats();
        } else if (command.equals("s")) {
            createCarListing();
        } else {
            System.out.println("Invalid input. Please select one of the options.");
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands for the marketplace menu, where the market is filtered
    private void processMarketCommandsFiltered(String command) {
        if (command.equals("b")) {
            System.out.println("Type the number of the car you would like to buy:");
            try {
                processBuyCar(input.nextInt() - 1);
            } catch (IndexOutOfBoundsException | InputMismatchException e) {
                System.out.println("Please choose from one of the cars listed, or enter an integer.");
            }
        } else if (command.equals("r")) {
            isFiltered = false;
            filteredMarketplace = new GarageWorkRoom();
            System.out.println("Reset the marketplace.");
            displayMenu();
        } else if (command.equals("d")) {
            checkMarketStats();
        } else if (command.equals("s")) {
            createCarListing();
        } else {
            System.out.println("Invalid input. Please select one of the options.");
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands in the buy car menu, depending on if marketplace is filtered or not
    private void processBuyCar(int carNum) {
        Car carToBuy;
        if (!isFiltered && isDefaultMarket) {
            carToBuy = marketplace.getCars().get(carNum);
        } else if (!isFiltered && !isDefaultMarket) {
            carToBuy = userMarketplace.getCars().get(carNum);
        } else {
            carToBuy = filteredMarketplace.getCars().get(carNum);
        }
        buyCar(carToBuy);
    }

    // MODIFIES: this
    // EFFECTS: processes user commands in the garage menu
    private void processGarageCommands(String command) {
        if (command.equals("d")) {
            viewDetailedGarage();
        } else {
            System.out.println("Invalid input. Please select 'd' if you want to view car details"
                    + " or want to return to the main menu.");
            viewGarage();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user commands for the account menu
    private void processAccountCommands(String command) {
        if (command.equals("o")) {
            System.out.println("Set your account balance to any positive number:");
            try {
                userAccount.setBalance(input.nextDouble());
            } catch (IllegalAccountBalanceException e) {
                System.out.println("Cannot set balance to a negative number.");
                System.out.println("Your account balance is $" + formatAccountBalance(df));
                return;
            }
            System.out.println("Your account balance has been set to " + "$" + formatAccountBalance(df));
            displayMenu();
        } else {
            System.out.println("Invalid input. Please select one of the options.");
            displayMenu();
        }
    }

    // EFFECTS: displays menu of options to user, returns true if currently displayed
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> view the marketplace");
        System.out.println("\tg -> view your garage");
        System.out.println("\ta -> view your account information");
        System.out.println("\tquit -> exit the application");
    }

    // EFFECTS: choose which market list of cars to use, displays the cars, then brings up the marketplace menu
    private void chooseMarketListing() {
        if (isDefaultMarket) {
            displayCarsDefaultMarket();
        } else {
            displayCarsUserMarket();
        }
        System.out.println("Type 'b' to choose a car to buy");
        if (!isFiltered) {
            System.out.println("Type 'f' to filter the cars");
        } else {
            System.out.println("Type 'r' to reset the filter");
        }
        System.out.println("Type 'd' to view car specifications");
        System.out.println("Type 's' to list a car for sale on the marketplace");
    }

    // EFFECTS: displays the listed cars from the default market, depending on if filtered
    private void displayCarsDefaultMarket() {
        String carListings = "";
        String filteredCarListings = "";
        if (!isFiltered) {
            carListings = filteredCarListingsToString(marketplace, carListings);
            System.out.println(carListings);
        } else {
            filteredCarListings = filteredCarListingsToString(filteredMarketplace, filteredCarListings);
            System.out.println(filteredCarListings);
        }
    }

    // EFFECTS: displays the listed cars from the user market, depending on if filtered
    private void displayCarsUserMarket() {
        String userCarListings = "";
        String filteredCarListings = "";
        if (!isFiltered) {
            userCarListings = filteredCarListingsToString(userMarketplace, userCarListings);
            System.out.println(userCarListings);
        } else {
            filteredCarListings = filteredCarListingsToString(filteredMarketplace, filteredCarListings);
            System.out.println(filteredCarListings);
        }

    }

    // EFFECTS: returns the list of cars in the filtered marketplace as a string
    private String filteredCarListingsToString(GarageWorkRoom filteredMarketplace, String filteredCarListings) {
        for (int i = 0; i < filteredMarketplace.numCars(); i++) {
            filteredCarListings += (i + 1) + ". " + filteredMarketplace.getCars().get(i).getYear() + " "
                    + filteredMarketplace.getCars().get(i).getManufacturer()
                    + " " + filteredMarketplace.getCars().get(i).getModel() + " $"
                    + df.format(filteredMarketplace.getCars().get(i).getPrice()) + "\n";
        }
        return filteredCarListings;
    }

    // EFFECTS: checks which market to display detailed stats for
    private void checkMarketStats() {
        System.out.println("Now displaying detailed car information.");
        if (isDefaultMarket && !isFiltered) {
            viewDetailedStatsUnfilteredDefault();
        } else if (!isDefaultMarket && !isFiltered) {
            viewDetailedStatsUnfilteredUser();
        } else {
            viewDetailedStatsFiltered();
        }
        System.out.println("Enter any key to return to the main menu.");
    }

    // EFFECTS: displays all the car's detailed specifications for the default market
    private void viewDetailedStatsUnfilteredDefault() {
        String detailedCars = "";
        printDetailedStatsUnfiltered(marketplace.numCars(), detailedCars, marketplace);
    }

    // EFFECTS: displays all the car's detailed specifications for the user market
    private void viewDetailedStatsUnfilteredUser() {
        String detailedCarsUser = "";
        printDetailedStatsUnfiltered(userMarketplace.numCars(), detailedCarsUser, userMarketplace);
    }

    // EFFECTS: prints each car's detailed specifications according to the specific marketplace
    private void printDetailedStatsUnfiltered(int userMarketplace, String detailedCarsUser,
                                              GarageWorkRoom userMarketplace1) {
        for (int i = 0; i < userMarketplace; i++) {
            detailedCarsUser += "\n" + (i + 1) + ". " + userMarketplace1.getCars().get(i).getYear() + " "
                    + userMarketplace1.getCars().get(i).getManufacturer() + " "
                    + userMarketplace1.getCars().get(i).getModel() + " $"
                    + df.format(userMarketplace1.getCars().get(i).getPrice()) + "\n" + "Speed: "
                    + userMarketplace1.getCars().get(i).getSpeed() + "\n" + "Handling: "
                    + userMarketplace1.getCars().get(i).getHandling() + "\n" + "Acceleration: "
                    + userMarketplace1.getCars().get(i).getAcceleration() + "\n" + "Braking: "
                    + userMarketplace1.getCars().get(i).getBraking() + "\n" + "Drive type: "
                    + userMarketplace1.getCars().get(i).getDriveType() + "\n";
        }
        System.out.println(detailedCarsUser);
    }

    // EFFECTS: displays the filtered car's detailed specifications for the both the default and user market
    private void viewDetailedStatsFiltered() {
        String filteredDetailedCars = "";
        printDetailedStatsUnfiltered(filteredMarketplace.getCars().size(), filteredDetailedCars, filteredMarketplace);
    }

    // EFFECTS: displays the options an Account can operate on
    public void displayAccountInfo() {
        System.out.println("Your current account balance is: $" + formatAccountBalance(df));
        //System.out.println("Type 'i' to increase your account balance by $10,000");
        System.out.println("Type 'o' to set your account balance to any amount");
    }

    // EFFECTS: checks for correct input, then different methods handle the type of variable based on the input string
    public void filter(String filter) {
        while (!filter.equals("year") && !filter.equals("speed") && !filter.equals("handling")
                && !filter.equals("acceleration") && !filter.equals("braking") && !filter.equals("drivetype")
                && !filter.equals("price")) {
            System.out.println("Invalid filter. Please select from the options above.");
            input.nextLine();
            filter = input.next().toLowerCase();
        }
        if (filter.equals("year") || filter.equals("price")) {
            filterCarsInts(filter);
        }
        if (filter.equals("speed") || filter.equals("handling") || filter.equals("acceleration")
                || filter.equals("braking")) {
            filterCarsDoubles(filter);
        }
        if (filter.equals("drivetype")) {
            filterCarsDriveType(filter);
        }
    }

    // REQUIRES: user input must be an integer
    // MODIFIES: filteredCarListing
    // EFFECTS: filters the car market listings according to the selected filters with type int
    public void filterCarsInts(String filter) {
        int value;
        System.out.println("Select a less than value (integer) to compare against: ");
        value = input.nextInt();
        System.out.println("You selected: filter by " + filter + " < " + df.format(value));
        if (filter.equals("year")) {
            checkMarketYear(value);
        } else {
            checkMarketPrice(value);
        }
        isFiltered = true;
        displayMenu();
    }

    // EFFECTS: checks which market to base filtering on, for the year field
    private void checkMarketYear(int value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getYear() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getYear() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: checks which market to base filtering on, for the price field
    private void checkMarketPrice(int value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getPrice() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getPrice() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: filters the car market listings according to drive type
    private void filterCarsDriveType(String filter) {
        String value;
        System.out.println("Select from: AWD, FWD, or RWD");
        value = input.next();
        System.out.println("You selected: filter by " + value.toUpperCase() + " " + filter);
        checkFilterDriveType(value);
        isFiltered = true;
        displayMenu();
    }

    // REQUIRES: user input must be a double
    // MODIFIES: filteredCarListing
    // EFFECTS: filters the car market listings according to the selected filters with type double
    public void filterCarsDoubles(String filter) {
        //filteredCarListing = new ArrayList<>();
        double value;
        System.out.println("Select a less than value (double) to compare against: ");
        value = input.nextDouble();
        System.out.println("You selected: filter by " + filter + " < " + df.format(value));
        checkFilterDouble(filter, value);
        isFiltered = true;
        displayMenu();
    }

    // MODIFIES: filteredCarListing
    // EFFECTS: chooses which filters with type double to apply
    private void checkFilterDouble(String filter, double value) {
        if (filter.equals("speed")) {
            checkMarketFilterSpeed(value);
            //checkMarketFieldsDouble(, value);
        } else if (filter.equals("handling")) {
            checkMarketFilterHandling(value);
        } else if (filter.equals("acceleration")) {
            checkMarketFilterAcceleration(value);
        } else {
            checkMarketFilterBraking(value);
        }
    }

    // MODIFIES: filteredCarListing
    // EFFECTS: chooses which filters with type double to apply
    private void checkFilterDriveType(String value) {
        if (value.toUpperCase().equals("AWD")) {
            checkMarketFilterDriveType(value);
        } else if (value.toUpperCase().equals("FWD")) {
            checkMarketFilterDriveType(value);
        } else if (value.toUpperCase().equals("RWD")) {
            checkMarketFilterDriveType(value);
        } else {
            System.out.println("Please select one of the options above.");
        }
    }

    // EFFECTS: checks which market to base filtering on, for the DriveType field
    private void checkMarketFilterDriveType(String value) {
        DriveType driveType = DriveType.valueOf(value.toUpperCase());
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getDriveType() == driveType) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getDriveType() == driveType) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: checks which market to base filtering on, for the speed field
    private void checkMarketFilterSpeed(double value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getSpeed() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getSpeed() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: checks which market to base filtering on, for the handling field
    private void checkMarketFilterHandling(double value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getHandling() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getHandling() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: checks which market to base filtering on, for the acceleration field
    private void checkMarketFilterAcceleration(double value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getAcceleration() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getAcceleration() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: checks which market to base filtering on, for the braking field
    private void checkMarketFilterBraking(double value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (c.getBraking() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (c.getBraking() < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }

    // EFFECTS: checks which market to base filtering on, for any car field
    private void checkMarketFieldsDouble(double field, double value) {
        if (isDefaultMarket) {
            for (Car c : marketplace.getCars()) {
                if (field < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        } else {
            for (Car c : userMarketplace.getCars()) {
                if (field < value) {
                    filteredMarketplace.addCar(c);
                }
            }
        }
    }


    // MODIFIES: garage
    // EFFECTS: buys the car from the marketplace, adding it to the garage, and subtracting the car's
    // price from the account's.
    public void buyCar(Car c) {
        if (userAccount.boughtCar(c)) {
            userGarage.addCar(c);
            System.out.println("Purchase complete! Enjoy your new car!");
            displayMenu();
        } else {
            System.out.println("Insufficient funds. Please get more money before purchasing.");
            System.out.println("Your current account balance is: $" + formatAccountBalance(df));
            displayMenu();
        }
    }

    // MODIFIES: carListing
    // EFFECTS: creates a new car and lists it for sale on the marketplace
    public void createCarListing() {
        Car carToList = getCarListingInfo();
        marketplace.addCar(carToList);
        userMarketplace.addCar(carToList);
        System.out.println("Car successfully listed on the marketplace!");
        listedCar = true;
        displayMenu();
    }

    // EFFECTS: creates a new car with the given specifications
    public Car getCarListingInfo() {
        int year = carInfoInts("year");
        String manufacturer = carInfoStrings("manufacturer");
        String model = carInfoStrings("model");
        double speed = carInfoDoubles("speed");
        double handling = carInfoDoubles("handling");
        double acceleration = carInfoDoubles("acceleration");
        double braking = carInfoDoubles("braking");
        int price = carInfoInts("price");
        DriveType driveType = carInfoDriveType();
        String image = null;
        return new Car(manufacturer, model, year, speed, handling, acceleration, braking, driveType, price, image);
    }

    // EFFECTS: gets the user input for the car fields that are Ints
    public int carInfoInts(String intField) {
        System.out.println("Enter the " + intField + " of the car: ");
        while (true) {
            try {
                int value = input.nextInt();
                input.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer.");
                input.nextLine();
            }
        }
    }

    // EFFECTS: gets the user input for the car fields that are doubles
    public double carInfoDoubles(String doubleField) {
        System.out.println("Enter the " + doubleField + " of the car: ");
        while (true) {
            try {
                double value = input.nextDouble();
                input.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                input.nextLine();
            }
        }
    }

    // EFFECTS: gets the user input for the car fields that are Strings
    public String carInfoStrings(String stringField) {
        System.out.println("Enter the " + stringField + " of the car: ");
        return input.nextLine();
    }

    // EFFECTS: gets the user input for the drive type car field
    public DriveType carInfoDriveType() {
        System.out.println("Enter the drive type of the car (AWD, FWD, or RWD): ");
        while (true) {
            try {
                DriveType driveType = DriveType.valueOf(input.next().toUpperCase());
                input.nextLine();
                return driveType;
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter one of the drive types listed above.");
                input.nextLine();
            }
        }
    }

    // EFFECTS: displays the garage as a list of cars
    public void viewGarage() {
        System.out.println(userGarage.carsInGarage());
        System.out.println("Type 'd' to view car specifications");
        processGarageCommands(input.next());
    }

    // EFFECTS: displays the stats and specifications of each car in the garage
    private void viewDetailedGarage() {
        String detailedGarage = "";
        if (userGarage.getCars().isEmpty()) {
            System.out.println("Your garage is empty.");
        }
        for (int i = 0; i < userGarage.getCars().size(); i++) {
            detailedGarage += "\n" + (i + 1) + ". " + userGarage.getCars().get(i).getYear() + " "
                    + userGarage.getCars().get(i).getManufacturer() + " " + userGarage.getCars().get(i).getModel()
                    + "\n" + "Speed: " + userGarage.getCars().get(i).getSpeed() + "\n"
                + "Handling: " + userGarage.getCars().get(i).getHandling() + "\n"
                + "Acceleration: " + userGarage.getCars().get(i).getAcceleration() + "\n"
                + "Braking: " + userGarage.getCars().get(i).getBraking() + "\n"
                + "Drive type: " + userGarage.getCars().get(i).getDriveType() + "\n";
        }
        System.out.println(detailedGarage);
    }

    // EFFECTS: formats the account balance to return in a comprehensible format (i.e. 1,000,000)
    public String formatAccountBalance(DecimalFormat df) {
        return df.format(userAccount.getBalance());
    }

    // EFFECTS: reminds the user to save their garage and/or marketplace to file, then quits the program
    private void quit() {
        System.out.println("Would you like to save your garage to file? (Y/N)");
        if (input.next().toLowerCase().equals("y")) {
            saveGarage();
        }
        System.out.println("Would you like to save your marketplace listings to file? (Y/N)");
        if (input.next().toLowerCase().equals("y")) {
            if (listedCar) {
                saveMarketplace();
            } else {
                System.out.println("Unable to save to file - you did not list any cars for sale on the marketplace.");
            }
        }
        System.out.println("Would you like to save your account balance to file? (Y/N)");
        if (input.next().toLowerCase().equals("y")) {
            saveAccount();
        }
    }

    public GarageWorkRoom getUserGarage() {
        return userGarage;
    }

    public AccountWorkRoom getUserAccount() {
        return userAccount;
    }

}
