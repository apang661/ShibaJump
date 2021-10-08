package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a list of enemies
 */
public class EnemyList {
    List<Enemy> listOfEnemies;

    public EnemyList() {
        this.listOfEnemies = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds an enemy to the list
    public void addEnemy(Enemy e) {
        listOfEnemies.add(e);
    }

    // REQUIRES: this.listOfEnemies must contain at least i+1 Enemy
    // EFFECTS: Returns the enemy at index i of the list
    public Enemy getEnemy(int i) {
        return listOfEnemies.get(i);
    }
}