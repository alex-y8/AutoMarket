package model;

import model.cars.Car;

import java.util.ArrayList;
import java.util.List;

// Represents a workroom having a collection of cars
public class WorkRoom {

    private List<Car> carList;

    // EFFECTS: constructs workroom with a name and empty list of cars
    public WorkRoom() {
        carList = new ArrayList<>();
    }

    // EFFECTS: returns an unmodifiable list of thingies in this workroom
    public List<Car> getCars() {
        return carList;
    }

    // MODIFIES: this
    // EFFECTS: adds car to this workroom
    public void addCar(Car car) {
        carList.add(car);
    }

    // EFFECTS: returns number of thingies in this workroom
    public int numCars() {
        return carList.size();
    }
}
