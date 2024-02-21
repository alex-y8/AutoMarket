package ui;

import exceptions.IllegalAccountBalanceException;
import model.Account;
import model.Garage;
import model.WorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

    private ArrayList<Car> carListing;
    private ArrayList<Car> filteredCarListing;
    private Garage garage;
    private Account account;
    private Scanner input;

    private final DecimalFormat df = new DecimalFormat("#,###.##");
    private boolean isFiltered = false;
    private boolean listedCar = false;
    private boolean isDefaultMarket = true;

    private WorkRoom marketplace;
    private WorkRoom userMarketplace;
    private WorkRoom userGarage;
    //private JsonWriter jsonWriterMarket; probably won't want to write to this file
    private JsonWriter jsonWriterUserMarket;
    private JsonWriter jsonWriterGarage;
    private JsonWriter jsonWriterFilteredMarket;

    private JsonReader jsonReaderMarket;
    private JsonReader jsonReaderUserMarket;
    private JsonReader jsonReaderGarage;
    private JsonReader jsonReaderFilteredMarket;

    // EFFECTS: runs the marketplace application
    public Marketplace() {
        runMarketplace();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMarketplace() {
        boolean keepGoing = true;
        String command;

        initialize();
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
    }

    // MODIFIES: this
    // EFFECTS: initializes the JSON reader, JSON writer, and marketplace
    private void initialize() {
        marketplace = new WorkRoom();
        userMarketplace = new WorkRoom();
        userGarage = new WorkRoom();

        jsonWriterUserMarket = new JsonWriter(JSON_USER_MARKET);
        jsonWriterGarage = new JsonWriter(JSON_GARAGE);
        jsonWriterFilteredMarket = new JsonWriter(JSON_FILTERED_MARKET);

        jsonReaderMarket = new JsonReader(JSON_MARKET);
        jsonReaderUserMarket = new JsonReader(JSON_USER_MARKET);
        jsonReaderGarage = new JsonReader(JSON_GARAGE);
        jsonReaderFilteredMarket = new JsonReader(JSON_FILTERED_MARKET);

        carListing = new ArrayList<>();
        account = new Account(0);
        garage = new Garage();
        //initializeCars1();
        //initializeCars2();
        input = new Scanner(System.in);
    }

//    // MODIFIES: carListing
//    // EFFECTS: initializes the cars being sold on the marketplace
//    private void initializeCars1() {
//        Car car1 = new Car("Audi", "R8", 2016, 8.2,
//                7.6, 9.0, 9.2, DriveType.AWD, 242000);
//        Car car2 = new Car("Nissan", "GT-R", 2017, 7.9,
//                7.2, 9.6, 7.6, DriveType.AWD, 132000);
//        Car car3 = new Car("BMW", "M5", 1988, 6.5,
//                5.3, 6.0, 5.3, DriveType.RWD, 54000);
//        Car car4 = new Car("Bugatti", "Veyron", 2011, 9.9,
//                8.0, 9.9, 8.4, DriveType.AWD, 2200000);
//        Car car5 = new Car("Ferrari", "LaFerrari", 2013, 9.5,
//                9.8, 8.2, 10, DriveType.RWD, 1500000);
//        Car car6 = new Car("Lamborghini", "Aventador", 2012, 8.7,
//                7.8, 9.8, 8.3, DriveType.AWD, 310000);
//        Car car7 = new Car("Mazda", "MX-5 Miata", 1994, 5.5,
//                4.9, 5.2, 4.3, DriveType.RWD, 25000);
//        carListing.add(car1);
//        carListing.add(car2);
//        carListing.add(car3);
//        carListing.add(car4);
//        carListing.add(car5);
//        carListing.add(car6);
//        carListing.add(car7);
//    }

//    // MODIFIES: carListing
//    // EFFECTS: initializes the cars being sold on the marketplace
//    private void initializeCars2() {
//        Car car8 = new Car("Porsche", "911 GT3 RS", 2019, 8.3,
//                9.7, 8.3, 10, DriveType.RWD, 255000);
//        Car car9 = new Car("Toyota", "Trueno AE86", 1985, 5.4,
//                4.7, 5.6, 4.5, DriveType.RWD, 22000);
//        Car car10 = new Car("Honda", "Civic Type R", 2018, 7.4,
//                6.7, 6.0, 6.8, DriveType.FWD, 59000);
//        Car car11 = new Car("Dodge", "Challenger", 2015, 8.1,
//                6.1, 5.9, 6.5, DriveType.RWD, 75000);
//        Car car12 = new Car("Chevrolet", "Stingray", 2020, 7.5,
//                7.6, 7.7, 7.7, DriveType.RWD, 87000);
//        carListing.add(car8);
//        carListing.add(car9);
//        carListing.add(car10);
//        carListing.add(car11);
//        carListing.add(car12);
//    }

    // MODIFIES: this
    // EFFECTS: loads the user's garage cars
    private void loadGarage() {
        if (userGarage.getCars().isEmpty()) {
            System.out.println("No file loaded - your garage is empty. Visit the marketplace to buy cars!");
        } else {
            try {
                userGarage = jsonReaderGarage.read();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_GARAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the user's marketplace car listings
    private void loadUserListings() {
        try {
            userMarketplace = jsonReaderUserMarket.read();
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
    private void saveGarage() {
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
    private void saveMarketplace() {
        try {
            jsonWriterUserMarket.open();
            jsonWriterUserMarket.write(userMarketplace);
            jsonWriterUserMarket.close();
            System.out.println("Marketplace successfully saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_USER_MARKET);
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
                viewCarListingDefaultMarket();
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
            System.out.println("Filter by: \nYear\nSpeed\nHandling\nAcceleration\nBraking\nDrivetype\nPrice\n");
            filter(input.next());
        } else if (command.equals("d")) {
            viewDetailedStatsUnfiltered();
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
            System.out.println("Reset the marketplace.");
            displayMenu();
        } else if (command.equals("d")) {
            viewDetailedStatsFiltered();
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
        if (!isFiltered) {
            carToBuy = carListing.get(carNum);
        } else {
            carToBuy = filteredCarListing.get(carNum);
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
        switch (command) {
            case "i":
                account.increaseBalance();
                System.out.println("Your account balance has been increased to " + "$" + formatAccountBalance(df));
                break;
            case "o":
                System.out.println("Set your account balance to any positive number:");
                try {
                    account.setBalance(input.nextDouble());
                } catch (IllegalAccountBalanceException e) {
                    System.out.println("Cannot set balance to a negative number.");
                    System.out.println("Your account balance is $" + formatAccountBalance(df));
                    break;
                }
                System.out.println("Your account balance has been set to " + "$" + formatAccountBalance(df));
                displayMenu();
                break;
            default:
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

    // EFFECTS: choose which market list of cars to use
    private void chooseMarketListing() {
        if (isDefaultMarket) {
            viewCarListingDefaultMarket();
        } else {
            viewCarListingUserMarket();
        }
    }

    // EFFECTS: displays the cars for sale on the default market, and brings up the marketplace menu
    public void viewCarListingDefaultMarket() {
        displayCarsDefaultMarket();
        System.out.println("Type 'b' to choose a car to buy");
        if (!isFiltered) {
            System.out.println("Type 'f' to filter the cars");
        } else {
            System.out.println("Type 'r' to reset the filter");
        }
        System.out.println("Type 'd' to view car specifications");
        System.out.println("Type 's' to list a car for sale on the marketplace");
    }

    // EFFECTS: displays the cars for sale on the user market, and brings up the marketplace menu
    public void viewCarListingUserMarket() {
        displayCarsUserMarket();
        System.out.println("Type 'b' to choose a car to buy");
        if (!isFiltered) {
            System.out.println("Type 'f' to filter the cars");
        } else {
            System.out.println("Type 'r' to reset the filter");
        }
        System.out.println("Type 'd' to view car specifications");
        System.out.println("Type 's' to list a car for sale on the marketplace");
    }

//    // EFFECTS: displays the cars for sale on the market
//    public void displayCars() {
//        String carListings = "";
//        String filteredCarListings = "";
//        if (!isFiltered) {
//            for (int i = 0; i < carListing.size(); i++) {
//               carListings += (i + 1) + ". " + carListing.get(i).getYear() + " " + carListing.get(i).getManufacturer()
//                        + " " + carListing.get(i).getModel() + " $" + df.format(carListing.get(i).getPrice()) + "\n";
//            }
//            System.out.println(carListings);
//        } else {
//            for (int i = 0; i < filteredCarListing.size(); i++) {
//                filteredCarListings += (i + 1) + ". " + filteredCarListing.get(i).getYear() + " "
//                        + filteredCarListing.get(i).getManufacturer()
//                        + " " + filteredCarListing.get(i).getModel() + " $"
//                        + df.format(filteredCarListing.get(i).getPrice()) + "\n";
//            }
//            System.out.println(filteredCarListings);
//        }
//    }

    // TODO: implement filtering once everything else works
    private void displayCarsDefaultMarket() {
        String carListings = "";
        for (int i = 0; i < marketplace.numCars(); i++) {
            carListings += (i + 1) + ". " + marketplace.getCars().get(i).getYear() + " "
                    + marketplace.getCars().get(i).getManufacturer() + " "
                    + marketplace.getCars().get(i).getModel() + " $"
                    + df.format(marketplace.getCars().get(i).getPrice()) + "\n";
        }
        System.out.println(carListings);
    }

    // TODO: implement filtering once everything else works
    private void displayCarsUserMarket() {
        String userCarListings = "";
        for (int i = 0; i < userMarketplace.numCars(); i++) {
            userCarListings += (i + 1) + ". " + userMarketplace.getCars().get(i).getYear() + " "
                    + userMarketplace.getCars().get(i).getManufacturer() + " "
                    + userMarketplace.getCars().get(i).getModel() + " $"
                    + df.format(userMarketplace.getCars().get(i).getPrice()) + "\n";
        }
        System.out.println(userCarListings);
    }

    // EFFECTS: displays all the car's detailed specifications
    private void viewDetailedStatsUnfiltered() {
        System.out.println("Now displaying detailed car information.");
        String detailedCars = "";
        for (int i = 0; i < carListing.size(); i++) {
            detailedCars += "\n" + (i + 1) + ". " + carListing.get(i).getYear() + " "
                    + carListing.get(i).getManufacturer() + " " + carListing.get(i).getModel() + " $"
                    + df.format(carListing.get(i).getPrice()) + "\n"
                    + "Speed: " + carListing.get(i).getSpeed() + "\n" + "Handling: " + carListing.get(i).getHandling()
                    + "\n" + "Acceleration: " + carListing.get(i).getAcceleration() + "\n" + "Braking: "
                    + carListing.get(i).getBraking() + "\n" + "Drive type: " + carListing.get(i).getDriveType() + "\n";
        }
        System.out.println(detailedCars);
        System.out.println("Enter any key to return to the main menu.");
    }

    // EFFECTS: displays the filtered car's detailed specifications
    private void viewDetailedStatsFiltered() {
        System.out.println("Now displaying detailed car information.");
        String filteredDetailedCars = "";
        for (int i = 0; i < filteredCarListing.size(); i++) {
            filteredDetailedCars += "\n" + (i + 1) + ". " + filteredCarListing.get(i).getYear() + " "
                    + filteredCarListing.get(i).getManufacturer() + " " + filteredCarListing.get(i).getModel() + " $"
                    + df.format(filteredCarListing.get(i).getPrice()) + "\n"
                    + "Speed: " + filteredCarListing.get(i).getSpeed() + "\n" + "Handling: "
                    + filteredCarListing.get(i).getHandling() + "\n" + "Acceleration: "
                    + filteredCarListing.get(i).getAcceleration() + "\n" + "Braking: "
                    + filteredCarListing.get(i).getBraking() + "\n" + "Drive type: "
                    + filteredCarListing.get(i).getDriveType() + "\n";
        }
        System.out.println(filteredDetailedCars);
        System.out.println("Enter any key to return to the main menu.");
    }

    // EFFECTS: displays the options an Account can operate on
    public void displayAccountInfo() {
        System.out.println("Your current account balance is: $" + formatAccountBalance(df));
        System.out.println("Type 'i' to increase your account balance by $10,000");
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
    }

    // MODIFIES: filteredCarListing
    // EFFECTS: filters the car market listings according to the selected filters with type int
    public void filterCarsInts(String filter) {
        filteredCarListing = new ArrayList<>();
        int value;
        System.out.println("Select a less than value to compare against: ");
        value = input.nextInt();
        System.out.println("You selected: filter by " + filter + " < " + df.format(value));
        if (filter.equals("year")) {
            for (Car c : carListing) {
                if (c.getYear() < value) {
                    filteredCarListing.add(c);
                }
            }
        } else {
            for (Car c : carListing) {
                if (c.getPrice() < value) {
                    filteredCarListing.add(c);
                }
            }
        }
        isFiltered = true;
        displayMenu();
    }

    // MODIFIES: filteredCarListing
    // EFFECTS: filters the car market listings according to the selected filters with type double
    public void filterCarsDoubles(String filter) {
        filteredCarListing = new ArrayList<>();
        double value;
        System.out.println("Select a less than value to compare against: ");
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
            for (Car c : carListing) {
                if (c.getSpeed() < value) {
                    filteredCarListing.add(c);
                }
            }
        } else if (filter.equals("handling")) {
            for (Car c : carListing) {
                if (c.getHandling() < value) {
                    filteredCarListing.add(c);
                }
            }
        } else if (filter.equals("acceleration")) {
            for (Car c : carListing) {
                if (c.getAcceleration() < value) {
                    filteredCarListing.add(c);
                }
            }
        } else {
            checkFilterBraking(value);
        }
    }

    // MODIFIES: filteredCarListing
    // EFFECTS: filters the carListing with filter braking
    private void checkFilterBraking(double value) {
        for (Car c : carListing) {
            if (c.getBraking() < value) {
                filteredCarListing.add(c);
            }
        }
    }

    // MODIFIES: garage
    // EFFECTS: buys the car from the marketplace, adding it to the garage, and subtracting the car's
    // price from the account's.
    public void buyCar(Car c) {
        if (account.boughtCar(c)) {
            garage.addCar(c);
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
        return new Car(manufacturer, model, year, speed, handling, acceleration, braking, driveType, price);
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
        System.out.println(garage.carsInGarage());
        System.out.println("Type 'd' to view car specifications");
        processGarageCommands(input.next());
    }

    // EFFECTS: displays the stats and specifications of each car in the garage
    private void viewDetailedGarage() {
        String detailedGarage = "";
        if (garage.getGarageSize() == 0) {
            System.out.println("Your garage is empty.");
        }
        for (int i = 0; i < garage.getGarageSize(); i++) {
            detailedGarage += "\n" + (i + 1) + ". " + garage.getGarage().get(i).getYear() + " "
                    + garage.getGarage().get(i).getManufacturer() + " " + garage.getGarage().get(i).getModel() + "\n"
                    + "Speed: " + garage.getGarage().get(i).getSpeed() + "\n"
                + "Handling: " + garage.getGarage().get(i).getHandling() + "\n"
                + "Acceleration: " + garage.getGarage().get(i).getAcceleration() + "\n"
                + "Braking: " + garage.getGarage().get(i).getBraking() + "\n"
                + "Drive type: " + garage.getGarage().get(i).getDriveType() + "\n";
        }
        System.out.println(detailedGarage);
    }

    // EFFECTS: formats the account balance to return in a comprehensible format (i.e. 1,000,000)
    public String formatAccountBalance(DecimalFormat df) {
        return df.format(account.getBalance());
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
    }

}
