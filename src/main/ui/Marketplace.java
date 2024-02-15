package ui;

import model.Account;
import model.Garage;
import model.cars.Car;
import model.cars.DriveType;

import java.util.ArrayList;
import java.util.Scanner;

// Car marketplace, where car listings are shown and up for sale
// Credits to CPSC210 TellerApp for Scanner implementation
public class Marketplace {

    private ArrayList<Car> carList;
    private Garage garage;
    private Car car1;
    private Account account;
    private Scanner input;

    public Marketplace() {
        boolean keepGoing = true;
        String command = null;

        initialize();
        System.out.println("Welcome to AutoMarket!");
        displayMenu();
        //System.out.println("\nPress any key to view the car market, or type \"quit\" to quit the program.");

        while (keepGoing) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Thanks for shopping!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the marketplace
    private void initialize() {
        carList = new ArrayList<>();
        account = new Account(0);
        garage = new Garage();
        car1 = new Car("Audi", "R8", 2016, 8.2,
                7.6, 9.0, 9.2, DriveType.RWD, 242000);
        carList.add(car1);
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the main menu
    private void processCommand(String command) {
        switch (command) {
            case "m":
                System.out.println("Now displaying the marketplace");
                viewCarListing();
                processMarketCommands(command);
                break;
            case "g":
                System.out.println("Now displaying your garage");
                viewGarage();
                break;

            default:
                System.out.println("Invalid input. Please select one of the options.");
                displayMenu();
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tm -> view the marketplace");
        System.out.println("\tg -> view your garage");
        System.out.println("\tquit -> exit the application");
    }

    // EFFECTS: displays the cars for sale on the market
    public void viewCarListing() {
        String carListings = "";
        for (Car c : carList) {
            carListings = carListings + c.getManufacturer() + " " + c.getModel() + " $" + c.getPrice() + "\n";
        }
        System.out.println(carListings);
        System.out.println("Type 'b' to choose a car to buy");
        System.out.println("Type 'f' to filter the cars");
        System.out.println("Type 's' to view car specifications");
        System.out.println("Type 'back' to return to the previous menu");

    }

    // EFFECTS: processes user commands for the marketplace menu
    private void processMarketCommands(String command) {
        switch (command) {
            case "b":
                System.out.println("Please type the name of the car you would like to purchase");

                break;
            case "f":
                String filter = "";
                System.out.println("Select a filter: \nManufacturer\n Model\n Year\n"
                        + "Speed\n Handling\n Acceleration\n Braking\n Drivetype\n Price\n");
                filter = input.next();
                filterCars(carList, filter);
            case "s":
                //helper method foreach loop through all the marketcars, displaying full stats
            case "back":
                processCommand(command);
                break;
            default:
                System.out.println("Invalid input. Please select one of the options.");
                viewCarListing();
        }
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

    // EFFECTS: displays the stats and specifications of the selected car
    public void viewCarStats(Car c) {

    }

    // MODIFIES: garage
    // EFFECTS: buys the car from the marketplace, adding it to the garage, and subtracting the car's
    // price from the account's.
    public void buyCar(Car c) {
        if (account.boughtCar(c)) {
            garage.addCar(c);
            System.out.println("Purchase complete! Enjoy your new car!");
        } else {
            System.out.println("Insufficient funds. Please get more money before purchasing.");
        }

    }

    // EFFECTS: displays the garage as a list of cars
    public void viewGarage() {
        System.out.println(garage.carsInGarage());
    }


}
