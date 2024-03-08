package model;

import model.cars.Car;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// Represents a workroom having a collection of cars
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class GarageWorkRoom implements Writeable {

    private List<Car> carList;

    // EFFECTS: constructs workroom with a name and empty list of cars
    public GarageWorkRoom() {
        carList = new ArrayList<>();
    }

    // EFFECTS: returns an unmodifiable list of cars in this workroom
    public List<Car> getCars() {
        return carList;
    }

    // MODIFIES: this
    // EFFECTS: adds car to this workroom
    public void addCar(Car car) {
        carList.add(car);
    }

    // EFFECTS: returns number of cars in this workroom
    public int numCars() {
        return carList.size();
    }

    // EFFECTS: returns the cars in the garage as a String in format "manufacturer model"
    public String carsInGarage() {
        String garageCars = "";
        if (carList.isEmpty()) {
            return "Your garage is empty. Visit the marketplace to buy cars!";
        }
        for (int i = 0; i < carList.size(); i++) {
            garageCars += (i + 1) + ". " + carList.get(i).getYear() + " " + carList.get(i).getManufacturer()
                    + " " + carList.get(i).getModel() + "\n";
        }
        return garageCars;
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
