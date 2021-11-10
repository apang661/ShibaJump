package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * This class represents a DJGame account
 */
public class Account implements Writable {
    String username;
    int dogePoints;
    int nextStageNum;
    String selectedCharacter;
    EnemyList encounteredEnemies;

    // EFFECTS: Creates a new account with 0 DogePoints, Doge as the selected character, and no encountered enemies
    public Account() {
        dogePoints = 0;
        selectedCharacter = "Doge";
        encounteredEnemies = new EnemyList();
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setDogePoints(int dogePoints) {
        this.dogePoints = dogePoints;
    }

    public int getDogePoints() {
        return dogePoints;
    }

    public int getNextStageNum() {
        return nextStageNum;
    }

    public void setNextStageNum(int nextStageNum) {
        this.nextStageNum = nextStageNum;
    }

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(String selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public EnemyList getEncounteredEnemies() {
        return encounteredEnemies;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given enemy to the list of encountered enemies in this account
    public void addEncounteredEnemy(Enemy enemy) {
        encounteredEnemies.addEnemy(enemy);
    }

    // MODIFIES: this
    // EFFECTS: If the given name is one of the regular or boss enemies in the game AND is not already in this list,
    //          add that enemy to this list; otherwise do nothing
    public void addEncounteredEnemy(String name) {
        encounteredEnemies.addEnemy(name);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("dogePoints", dogePoints);
        jsonObject.put("selectedCharacter", selectedCharacter);
        jsonObject.put("nextStageNum", nextStageNum);
        jsonObject.put("encounteredEnemies", encounteredEnemies.toJson());

        return jsonObject;
    }
}