package model.bossenemies;

import model.Enemy;

/*
 * Represents a boss-type enemy
 */
public abstract class BossEnemy extends Enemy {
    public BossEnemy(String name, int health, int width, int height) {
        super(name, health, width, height);
    }
}
