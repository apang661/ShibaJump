package model;

import model.logging.Event;
import model.logging.EventLog;
import org.json.JSONArray;
import persistence.ArrayWritable;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a list of enemies
 */
public class EnemyList implements ArrayWritable {
    List<Enemy> listOfEnemies;
    EventLog eventLog;

    public EnemyList() {
        this.listOfEnemies = new ArrayList<>();
        this.eventLog = EventLog.getInstance();
    }

    // MODIFIES: this
    // EFFECTS: Adds an enemy to the list
    public void addEnemy(Enemy e) {
        listOfEnemies.add(e);
        eventLog.logEvent(new Event("Added " + e.getName() + " from the enemy list."));
    }

    // MODIFIES: this
    // EFFECTS: If the given name is one of the regular or boss enemies in the game AND is not already in this list,
    //          add that enemy to this list; otherwise do nothing
    public void addEnemy(String name) {
        for (Enemy enemy : SJGame.REGULAR_ENEMIES) {
            if (enemy.getName().equals(name) && !(listOfEnemies.contains(enemy))) {
                addEnemy(enemy);
            }
        }
        for (Enemy enemy : SJGame.BOSS_ENEMIES) {
            if (enemy.getName().equals(name) && !(listOfEnemies.contains(enemy))) {
                addEnemy(enemy);
            }
        }
    }

    // REQUIRES: this.listOfEnemies must contain at least i+1 Enemy
    // EFFECTS: Returns the enemy at index i of the list
    public Enemy getEnemy(int i) {
        return listOfEnemies.get(i);
    }

    // EFFECTS: Removes the enemy from the list of enemies
    public void removeEnemy(Enemy e) {
        listOfEnemies.remove(e);
        eventLog.logEvent(new Event("Removed " + e.getName() + " to the enemy list."));
    }

    // EFFECTS: Returns true if list of enemies contains e
    public boolean containsEnemy(Enemy e) {
        return listOfEnemies.contains(e);
    }

    // EFFECTS: Returns true if list of enemies contains enemy with given name
    public boolean containsEnemy(String name) {
        for (Enemy e: listOfEnemies) {
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns the number of enemies in the list
    public int size() {
        return listOfEnemies.size();
    }

    @Override
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Enemy enemy: listOfEnemies) {
            jsonArray.put(enemy.getName());
        }
        return jsonArray;
    }

    public EventLog getEventLog() {
        return eventLog;
    }
}
