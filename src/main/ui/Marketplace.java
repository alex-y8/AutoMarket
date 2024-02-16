package ui;

import exceptions.IllegalAccountBalanceException;
import model.Account;
import model.Garage;
import model.cars.Car;
import model.cars.DriveType;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

// Car marketplace, where car listings are shown and up for sale
// Inspired by CPSC210 TellerApp Scanner implementation
public class Marketplace {

    private ArrayList<Car> carListing;
    private Garage garage;
    private Car car1;
    private Car car2;
    private Account account;
    private Scanner input;

    DecimalFormat df = new DecimalFormat("#,###.##");

    private boolean mainMenuDisplayed = true;
    private boolean viewCarListingDisplayed;
    private boolean viewAccountDisplayed;

    public Marketplace() {
        boolean keepGoing = true;
        String command = null;

        initialize();
        System.out.println("Welcome to AutoMarket!");
        System.out.println("Enter any key to get started.");
        displayMenu();


        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Quitting...");
    }

    // MODIFIES: this
    // EFFECTS: initializes the marketplace
    private void initialize() {
        carListing = new ArrayList<>();
        account = new Account(0);
        garage = new Garage();
        car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.AWD, 242000);
        car2 = new Car("Nissan", "GT-R", 2017, 7.9,
                7.2, 9.6, 7.6, DriveType.AWD, 132000);
        carListing.add(car1);
        carListing.add(car2);
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the main menu
    private void processCommand(String command) {
        switch (command) {
            case "m":
                System.out.println("Now displaying the marketplace.");
                viewCarListing();
                processMarketCommands(input.next());
                break;
            case "g":
                System.out.println("Now displaying your garage.");
                viewGarage();
                break;
            case "a":
                System.out.println("Now displaying your account information.");
                displayAccountInfo();
                processAccountCommands(input.next());
                break;
            default:
                System.out.println("Invalid input. Please select one of the options.");
                displayMenu();
        }
    }


    // EFFECTS: processes user commands for the marketplace menu
    private void processMarketCommands(String command) {
        switch (command) {
            case "b":
                System.out.println("Type the number of the car you would like to buy:");
                try {
                    processBuyCar(input.nextInt() - 1);
                } catch (Exception e) {
                    System.out.println("Please choose from one of the cars listed, or enter an integer.");
                }
                break;
            case "f":
                System.out.println("Select a filter: \nManufacturer\nModel\nYear\n"
                        + "Speed\nHandling\nAcceleration\nBraking\nDrive type\nPrice\n");
                filterCars(carListing, input.next());
            case "d":
                viewDetailedStats();
                break;
            case "s":
                createCarListing();
                break;
            default:
                System.out.println("Invalid input. Please select one of the options.");
                displayMenu();
        }
    }




    // EFFECTS: processes user commands in the buy car menu
    private void processBuyCar(int carNum) {
        Car carToBuy = carListing.get(carNum);
        buyCar(carToBuy);
    }

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
        viewAccountDisplayed = false;
        viewCarListingDisplayed = false;
        System.out.println("\nSelect from:");
        System.out.println("\tm -> view the marketplace");
        System.out.println("\tg -> view your garage");
        System.out.println("\ta -> view your account information");
        System.out.println("\tquit -> exit the application");
        mainMenuDisplayed = true;
    }

    // EFFECTS: displays the cars for sale on the market
    public void viewCarListing() {
        viewAccountDisplayed = false;
        mainMenuDisplayed = false;
        String carListings = "";
        for (int i = 0; i < carListing.size(); i++) {
            carListings += (i + 1) + ". " + carListing.get(i).getYear() + " " + carListing.get(i).getManufacturer()
                    + " " + carListing.get(i).getModel() + " $" + carListing.get(i).getPrice() + "\n";
        }
        System.out.println(carListings);
        System.out.println("Type 'b' to choose a car to buy");
        System.out.println("Type 'f' to filter the cars");
        System.out.println("Type 'd' to view car specifications");
        System.out.println("Type 's' to list a car for sale on the marketplace");
        //System.out.println("Type 'back' to return to the previous menu");
        viewCarListingDisplayed = true;
    }

    public boolean isViewCarListingDisplayed() {
        return viewCarListingDisplayed;
    }

    public boolean isViewAccountDisplayed() {
        return viewAccountDisplayed;
    }

    public boolean isMainMenuDisplayed() {
        return mainMenuDisplayed;
    }

    // EFFECTS: displays all the car's detailed specifications
    private void viewDetailedStats() {
        String detailedCars = "";
        for (int i = 0; i < carListing.size(); i++) {
            detailedCars += (i + 1) + ". " + carListing.get(i).getYear() + " " + carListing.get(i).getManufacturer()
                    + " " + carListing.get(i).getModel() + " $" + carListing.get(i).getPrice() + "\n"
                + "Speed: " + carListing.get(i).getSpeed() + "\n" + "Handling: " + carListing.get(i).getHandling()
                    + "\n" + "Acceleration: " + carListing.get(i).getAcceleration() + "\n" + "Braking: "
                    + carListing.get(i).getBraking() + "\n" + "Drive type: " + carListing.get(i).getDriveType() + "\n"
                + "\n";
        }
        System.out.println(detailedCars);
    }

    // EFFECTS: displays the options an Account can operate on
    public void displayAccountInfo() {
        viewCarListingDisplayed = false;
        mainMenuDisplayed = false;
        System.out.println("Your current account balance is: $" + formatAccountBalance(df));
        System.out.println("Type 'i' to increase your account balance by $10,000");
        System.out.println("Type 'o' to set your account balance to any amount");
        viewAccountDisplayed = true;
    }

    // MODIFIES: carList
    // EFFECTS: filters the car market listings according to the selected filter
    //
    // foreach car in carList, remove cars that don't satisfy predicate
    // if filter is not selected anymore, go to unfilter cars
    public void filterCars(ArrayList<Car> carList, String filter) {

    }

    // MODIFIES: carList
    // EFFECTS: unfilters the car market listings, all original cars will be back in the list
    //
    // assign the original carList back to the current list
    // this ensures that all the original cars would be back on the market
    public void unfilterCars(ArrayList<Car> carList) {

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
        carListing.add(carToList);
        System.out.println("Car successfully listed on the marketplace!");
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
        while (true) {
            try {
                String value = input.next();
                input.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a string.");
                input.nextLine();
            }
        }
    }

    // EFFECTS: gets the user input for the car fields that are DriveTypes
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
    }

    // EFFECTS: formats the account balance to return in a comprehensible format (i.e. 1,000,000)
    public String formatAccountBalance(DecimalFormat df) {
        return df.format(account.getBalance());
    }

}
