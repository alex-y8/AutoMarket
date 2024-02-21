package persistence;

import model.WorkRoom;
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
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private WorkRoom parseWorkRoom(JSONObject jsonObject) {
        WorkRoom wr = new WorkRoom();
        addCars(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses cars from JSON object and adds them to workroom
    private void addCars(WorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cars");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addCar(wr, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses car from JSON object and adds it to workroom
    private void addCar(WorkRoom wr, JSONObject jsonObject) {
        String manufacturer = jsonObject.getString("manufacturer");
        String model = jsonObject.getString("model");
        int year = jsonObject.getInt("year");
        double speed = jsonObject.getDouble("speed");
        double handling = jsonObject.getDouble("handling");
        double acceleration = jsonObject.getDouble("acceleration");
        double braking = jsonObject.getDouble("braking");
        DriveType driveType = DriveType.valueOf(jsonObject.getString("driveType"));
        int price = jsonObject.getInt("price");
        Car car = new Car(manufacturer, model, year, speed, handling, acceleration, braking, driveType, price);
        wr.addCar(car);
    }

}
