package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * This class represents a SJGame account
 */
public class Account implements Writable {
    String username;
    int shibaPoints;
    int nextStageNum;
    String selectedCharacter;
    EnemyList encounteredEnemies;

    // EFFECTS: Creates a new account with 0 ShibaPoints, Doge as the selected character, and no encountered enemies
    public Account() {
        username = "";
        shibaPoints = 0;
        nextStageNum = 1;
        selectedCharacter = "Doge";
        encounteredEnemies = new EnemyList();
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setShibaPoints(int shibaPoints) {
        this.shibaPoints = shibaPoints;
    }

    public int getShibaPoints() {
        return shibaPoints;
    }

    public int getNextStageNum() {
        return nextStageNum;
    }

    // EFFECTS: Sets next stage number to the given number (up to a maximum to 2)
    public void setNextStageNum(int nextStageNum) {
        this.nextStageNum = Math.min(nextStageNum, 2);
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
        jsonObject.put("shibaPoints", shibaPoints);
        jsonObject.put("selectedCharacter", selectedCharacter);
        jsonObject.put("nextStageNum", nextStageNum);
        jsonObject.put("encounteredEnemies", encounteredEnemies.toJson());

        return jsonObject;
    }
}