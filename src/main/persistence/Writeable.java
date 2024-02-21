package persistence;

import org.json.JSONObject;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
