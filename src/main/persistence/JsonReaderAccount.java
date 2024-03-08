package persistence;

import model.Account;
import model.AccountWorkRoom;
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

public class JsonReaderAccount extends JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderAccount(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountWorkRoom read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private AccountWorkRoom parseWorkRoom(JSONObject jsonObject) {
        AccountWorkRoom wr = new AccountWorkRoom();
        addAccounts(wr, jsonObject);
        return wr;
    }

    // MODIFIES: wr
    // EFFECTS: parses account from JSON object and adds it to workroom
    private void addAccounts(AccountWorkRoom wr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("account");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(wr, nextAccount);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses car from JSON object and adds it to workroom
    private void addAccount(AccountWorkRoom wr, JSONObject jsonObject) {
        double balance = jsonObject.getDouble("balance");
        Account account = new Account(balance);
        wr.addAccount(account);
    }

}
