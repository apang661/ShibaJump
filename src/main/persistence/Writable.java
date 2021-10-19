package persistence;

import org.json.JSONObject;

// This interface references CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
