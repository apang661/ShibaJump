package model.regularenemies;

import model.Enemy;

/*
 * Represents a regular-type enemy
 */
public abstract class RegularEnemy extends Enemy {
    public RegularEnemy(String name, int health, int width, int height) {
        super(name, health, width, height);
    }
}