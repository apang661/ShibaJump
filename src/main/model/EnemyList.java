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

    // MODIFIES: this
    // EFFECTS: If the given name is one of the regular or boss enemies in the game AND is not already in this list,
    //          add that enemy to this list; otherwise do nothing
    public void addEnemy(String name) {
        for (Enemy enemy : DJGame.REGULAR_ENEMIES) {
            if (enemy.getName().equals(name) && !(listOfEnemies.contains(enemy))) {
                addEnemy(enemy);
            }
        }
        for (Enemy enemy : DJGame.BOSS_ENEMIES) {
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

    // EFFECTS: Returns true if list of enemies contains e
    public boolean containsEnemy(Enemy e) {
        return listOfEnemies.contains(e);
    }

    // EFFECTS: Returns true if list of enemies contains enemy with given name
    public boolean containsEnemy(String name) {
        Enemy enemy = Stage.nameToEnemy(name);
        return listOfEnemies.contains(enemy);
    }

    // EFFECTS: Returns the number of enemies in the list
    public int size() {
        return listOfEnemies.size();
    }
}
