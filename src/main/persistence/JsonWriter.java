package persistence;

import model.SJGame;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


// This class references CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

/*
 * Represents a writer that converts game data to JSON data and stores it into a file
 */
public class JsonWriter {
    public static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Creates a writer that writes to the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes the given DJGame to the destination file
    public void write(SJGame game) {
        JSONObject jsonObject = game.toJson();
        String jsonText = jsonObject.toString(TAB);
        writer.write(jsonText);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}
