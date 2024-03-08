package persistence;

import model.AccountWorkRoom;
import model.GarageWorkRoom;
import org.json.JSONObject;

public class JsonWriterAccount extends JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriterAccount(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(AccountWorkRoom wr) {
        JSONObject json = wr.toJson();
        saveToFile(json.toString(TAB));
    }
}
