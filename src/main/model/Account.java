package model;

import java.util.ArrayList;
import java.util.List;

/*
 * This class represents a DJGame account
 */
public class Account {
    String username;
    int dogePoints;
    int nextStageNum;
    EnemyList encounteredEnemies;

    // EFFECTS: Creates a new account
    public Account() {
        dogePoints = 0;
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

    public EnemyList getEncounteredEnemies() {
        return encounteredEnemies;
    }

    // EFFECTS: Return the list of names of enemies in REGULAR_ENEMIES
    public List<String> getRegularEnemyNames() {
        List<String> listOfRegEnemies = new ArrayList<>();
        for (Enemy enemy : DJGame.REGULAR_ENEMIES) {
            listOfRegEnemies.add(enemy.getName());
        }
        return listOfRegEnemies;
    }

    // EFFECTS: Return the list of names of enemies in BOSS_ENEMIES
    public List<String> getBossEnemyNames() {
        List<String> listOfBossEnemies = new ArrayList<>();
        for (Enemy enemy : DJGame.BOSS_ENEMIES) {
            listOfBossEnemies.add(enemy.getName());
        }
        return listOfBossEnemies;
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
}