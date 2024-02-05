package model;

import model.cars.Car;

import java.util.ArrayList;
import java.util.List;

// Represents the user's garage as a list of cars
public class Garage {

    private List<Car> garage;

    // Constructs an empty garage
    public Garage() {
        garage = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a car to the garage
    public void addCar(Car c) {
        garage.add(c);
    }

}
