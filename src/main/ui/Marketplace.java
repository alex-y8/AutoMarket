package ui;

import model.cars.Car;

import java.util.ArrayList;

// Car marketplace, where car listings are shown and up for sale
public class Marketplace {

    private ArrayList<Car> carList;

    public Marketplace() {

    }

    // EFFECTS: displays the cars for sale on the market
    public void viewCarListing() {

    }

    // MODIFIES: carList
    // EFFECTS: filters the car market listings according to the selected filter
    //
    // foreach car in carList, remove cars that don't satisfy predicate
    // if filter is not selected anymore, go to unfilter cars
    public void filterCars(ArrayList<Car> carList) {

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

    // MODIFIES: garage, account
    // EFFECTS: buys the car from the marketplace, adding it to the garage, subtracting the car's
    // price from the account's , and return true. If the account's balance is not sufficient, fail to buy car
    // and return false.
    // helper method: account.boughtCar
    public boolean buyCar(Car c) {
        return false;
    }

    // EFFECTS: displays the garage as a list of cars
    // helper: garage.carsInGarage
    public void viewGarage() {

    }


}
