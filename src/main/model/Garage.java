package model;

import model.cars.Car;

import java.util.ArrayList;

// Represents the user's garage as a list of cars
public class Garage {

    private ArrayList<Car> garage;

    // Constructs an empty garage
    public Garage() {
        garage = new ArrayList<>();
    }

    // EFFECTS: returns the cars in the garage as a String in format "manufacturer model"
    // foreach car in garagelist, return the car name as a String
    public String carsInGarage() {
        String carList = "";
        for (Car c : garage) {
            carList = carList + c.getManufacturer() + " " + c.getModel() + "\n";
        }
        return carList;
    }


    // MODIFIES: this
    // EFFECTS: adds a car to the garage, regardless if the car is already in the garage
    public void addCar(Car c) {
        garage.add(c);
    }

    // MODIFIES: this
    // EFFECTS: remove car from the garage. If car isn't found, do nothing
    public void removeCar(Car c) {
        garage.remove(c);
    }

    // EFFECTS: return true if the given car is found in the garage, false if not
    public boolean containsCar(Car c) {
        return garage.contains(c);
    }

    // getters
    // EFFECTS: returns the number of cars in the garage
    public int getGarageSize() {
        return garage.size();
    }

    public ArrayList<Car> getGarage() {
        return garage;
    }

}
