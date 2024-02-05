package ui;

import model.cars.Car;

import java.util.ArrayList;

public class MarketPlace {

    private ArrayList<Car> carList;

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
    // EFFECTS: buys the car from the marketplace, adding it to the garage and subtracting the car's
    // price from the account's balance. If the account's balance is not sufficient, fail to buy car.
    public void buyCar(Car c) {

    }


}
