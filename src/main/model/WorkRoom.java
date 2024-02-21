package model;

import model.cars.Car;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// Represents a workroom having a collection of cars
public class WorkRoom implements Writeable {

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

    // EFFECTS: creates a JSON object with the given fields
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cars", carsToJson());
        return json;
    }

    // EFFECTS: returns cars in this workroom as a JSON array
    private JSONArray carsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Car c : carList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
