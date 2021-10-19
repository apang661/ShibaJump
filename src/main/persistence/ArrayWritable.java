package persistence;

import org.json.JSONArray;

public interface ArrayWritable {
    // EFFECTS: returns this as a JSON array
    JSONArray toJson();
}
