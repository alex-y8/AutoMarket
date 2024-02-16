package ui;

import exceptions.IllegalAccountBalanceException;
import model.Account;
import model.Garage;
import model.cars.Car;
import model.cars.DriveType;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.text.DecimalFormat;

// Car marketplace, where car listings are shown and up for sale
// Inspired by CPSC210 TellerApp Scanner implementation
public class Marketplace {

    private ArrayList<Car> carListing;
    private Garage garage;
    private Account account;
    private Scanner input;

    DecimalFormat df = new DecimalFormat("#,###.##");

    public Marketplace() {
        boolean keepGoing = true;
        String command;

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
        initializeCars1();
        initializeCars2();
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: initializes the cars being sold on the marketplace
    private void initializeCars1() {
        Car car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.AWD, 242000);
        Car car2 = new Car("Nissan", "GT-R", 2017, 7.9,
                7.2, 9.6, 7.6, DriveType.AWD, 132000);
        Car car3 = new Car("BMW", "M5", 1988, 6.5,
                5.3, 6.0, 5.3, DriveType.RWD, 54000);
        Car car4 = new Car("Bugatti", "Veyron", 2011, 9.9,
                8.0, 9.9, 8.4, DriveType.AWD, 2200000);
        Car car5 = new Car("Ferrari", "LaFerrari", 2013, 9.5,
                9.8,8.2,10, DriveType.RWD, 1500000);
        Car car6 = new Car("Lamborghini", "Aventador", 2012, 8.7,
                7.8,9.8,8.3,DriveType.AWD, 310000);
        Car car7 = new Car("Mazda", "MX-5 Miata", 1994, 5.5,
                4.9, 5.2, 4.3, DriveType.RWD, 25000);
        carListing.add(car1);
        carListing.add(car2);
        carListing.add(car3);
        carListing.add(car4);
        carListing.add(car5);
        carListing.add(car6);
        carListing.add(car7);
    }

    // MODIFIES: this
    // EFFECTS: initializes the cars being sold on the marketplace
    private void initializeCars2() {
        Car car8 = new Car("Porsche", "911 GT3 RS", 2019, 8.3,
                9.7,8.3,10, DriveType.RWD, 255000);
        Car car9 = new Car("Toyota", "Trueno AE86", 1985, 5.4,
                4.7,5.6,4.5, DriveType.RWD, 22000);
        Car car10 = new Car("Honda", "Civic Type R", 2018, 7.4,
                6.7, 6.0, 6.8, DriveType.FWD, 59000);
        Car car11 = new Car("Dodge", "Challenger", 2015, 8.1,
                6.1, 5.9, 6.5, DriveType.RWD, 75000);
        Car car12 = new Car("Chevrolet", "Stingray", 2020, 7.5,
                7.6, 7.7, 7.7, DriveType.RWD, 87000);
        carListing.add(car8);
        carListing.add(car9);
        carListing.add(car10);
        carListing.add(car11);
        carListing.add(car12);
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
                System.out.println("Filter by: \nYear\nSpeed\nHandling\nAcceleration\nBraking\nDrivetype\nPrice\n");
                filter(carListing, input.next());
                break;
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
        System.out.println("\nSelect from:");
        System.out.println("\tm -> view the marketplace");
        System.out.println("\tg -> view your garage");
        System.out.println("\ta -> view your account information");
        System.out.println("\tquit -> exit the application");
    }

    // EFFECTS: displays the cars for sale on the market
    public void viewCarListing() {
        String carListings = "";
        for (int i = 0; i < carListing.size(); i++) {
            carListings += (i + 1) + ". " + carListing.get(i).getYear() + " " + carListing.get(i).getManufacturer()
                    + " " + carListing.get(i).getModel() + " $" + df.format(carListing.get(i).getPrice()) + "\n";
        }
        System.out.println(carListings);
        System.out.println("Type 'b' to choose a car to buy");
        System.out.println("Type 'f' to filter the cars");
        System.out.println("Type 'd' to view car specifications");
        System.out.println("Type 's' to list a car for sale on the marketplace");
        //System.out.println("Type 'back' to return to the previous menu");
    }

    // EFFECTS: displays all the car's detailed specifications
    private void viewDetailedStats() {
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

    // EFFECTS: displays the options an Account can operate on
    public void displayAccountInfo() {
        System.out.println("Your current account balance is: $" + formatAccountBalance(df));
        System.out.println("Type 'i' to increase your account balance by $10,000");
        System.out.println("Type 'o' to set your account balance to any amount");
    }

    // EFFECTS: checks for correct input, then different methods handle the type of variable based on the input string
    public void filter(ArrayList<Car> carList, String filter) {
        while (!filter.equals("year") && !filter.equals("speed") && !filter.equals("handling")
                && !filter.equals("acceleration") && !filter.equals("braking") && !filter.equals("drivetype")
                && !filter.equals("price")) {
            System.out.println("Invalid filter. Please select from the options above.");
            input.nextLine();
            filter = input.next().toLowerCase();
        }
        if (filter.equals("year") || filter.equals("price")) {
            filterCarsInts(carList, filter);
        }
        if (filter.equals("speed") || filter.equals("handling") || filter.equals("acceleration")
                || filter.equals("braking")) {
            filterCarsDoubles(carList, filter);
        }
    }

    // MODIFIES: carList
    // EFFECTS: filters the car market listings according to the selected filters with type int
    //
    // foreach car in carList, remove cars that don't satisfy predicate
    // if filter is not selected anymore, go to unfilter cars
    public void filterCarsInts(ArrayList<Car> carList, String filter) {
        carList = new ArrayList<>();
        int value = 0;
        System.out.println("Select a comparison operator (> or <):");
        String operator = input.next();
        while (!operator.equals(">") && !operator.equals("<")) {
            System.out.println("Invalid operator. Please select from the options above.");
            input.nextLine();
            operator = input.next();
        }
        System.out.println("Select a value to compare: ");
        value = input.nextInt();
        System.out.println("You selected: Filter by " + filter + " " + operator + " " + value);
        if (filter.equals("year")) {
            value = getCarListingInfo().getYear();
        } else {
            value = getCarListingInfo().getPrice();
        }
        if (operator.equals(">")) {

        }
//        for (Car c : carListing) {
//            if (c.getYear() operator )
//            carList.
//        }
    }

    // MODIFIES: carList
    // EFFECTS: filters the car market listings according to the selected filters with type double
    //
    // foreach car in carList, remove cars that don't satisfy predicate
    // if filter is not selected anymore, go to unfilter cars
    public void filterCarsDoubles(ArrayList<Car> carList, String filter) {
        double value = 0;
        System.out.println("Select a comparison operator (> or <):");
        String operator = input.next();
        while (!operator.equals(">") && !operator.equals("<")) {
            System.out.println("Invalid operator. Please select from the options above.");
            input.nextLine();
            operator = input.next();
        }
        System.out.println("Select a value to filter with: ");
        value = input.nextDouble();

        System.out.println("How would you like to filter? (i.e. " + filter + " > 5)");

        for (Car c : carList) {

        }
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
