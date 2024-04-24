package persistence;

import model.GarageWorkRoom;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriterGarage extends JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriterGarage(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(GarageWorkRoom wr) {
        JSONObject json = wr.toJson();
        saveToFile(json.toString(TAB));
    }
}
