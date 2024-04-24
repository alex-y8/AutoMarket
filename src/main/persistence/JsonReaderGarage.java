package persistence;

import model.GarageWorkRoom;
import model.cars.Car;
import model.cars.DriveType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReaderGarage extends JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderGarage(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GarageWorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private GarageWorkRoom parseWorkRoom(JSONObject jsonObject) {
        GarageWorkRoom wr = new GarageWorkRoom();
        addCars(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses cars from JSON object and adds them to workroom
    private void addCars(GarageWorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cars");
        for (Object json : jsonArray) {
            JSONObject nextCar = (JSONObject) json;
            addCar(wr, nextCar);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses car from JSON object and adds it to workroom
    private void addCar(GarageWorkRoom wr, JSONObject jsonObject) {
        String manufacturer = jsonObject.getString("manufacturer");
        String model = jsonObject.getString("model");
        int year = jsonObject.getInt("year");
        double speed = jsonObject.getDouble("speed");
        double handling = jsonObject.getDouble("handling");
        double acceleration = jsonObject.getDouble("acceleration");
        double braking = jsonObject.getDouble("braking");
        DriveType driveType = DriveType.valueOf(jsonObject.getString("driveType"));
        int price = jsonObject.getInt("price");
        String image = jsonObject.getString("image");
        Car car = new Car(manufacturer, model, year, speed, handling, acceleration, braking, driveType, price, image);
        wr.addCar(car);
    }

}
