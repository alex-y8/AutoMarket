package model.cars;

import org.json.JSONObject;
import persistence.Writeable;

import java.text.DecimalFormat;

// Represents a car, with stats and specifications and a price in dollars
public class Car implements Writeable {

    private String manufacturer;
    private String model;
    private int year;
    private double speed;
    private double handling;
    private double acceleration;
    private double braking;
    private DriveType driveType;
    private int price;
    private String image;

    private final DecimalFormat df = new DecimalFormat("#,###.##");

    // EFFECTS: constructs a new car with the given stats and specifications
    public Car(String manufacturer, String model, int year, double speed, double handling,
               double acceleration, double braking, DriveType driveType, int price, String image) {

        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.speed = speed;
        this.handling = handling;
        this.acceleration = acceleration;
        this.braking = braking;
        this.driveType = driveType;
        this.price = price;
        this.image = image;
    }

    // getters
    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHandling() {
        return handling;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getBraking() {
        return braking;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    // EFFECTS: creates a JSON object with the given fields
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("manufacturer", manufacturer);
        json.put("model", model);
        json.put("year", year);
        json.put("speed", speed);
        json.put("handling", handling);
        json.put("acceleration", acceleration);
        json.put("braking", braking);
        json.put("driveType", driveType);
        json.put("price", price);
        json.put("image", image);
        return json;
    }

    @Override
    public String toString() {
        return getYear() + " "
                + getManufacturer()
                + " " + getModel() + " $"
                + df.format(getPrice()) + "\n" + "Speed: "
                + getSpeed() + "\n" + "Handling: "
                + getHandling() + "\n" + "Acceleration: "
                + getAcceleration() + "\n" + "Braking: "
                + getBraking() + "\n" + "Drive type: "
                + getDriveType() + "\n";
    }
}
