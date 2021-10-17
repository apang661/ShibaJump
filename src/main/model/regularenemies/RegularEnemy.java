package model.regularenemies;

import model.Enemy;
import model.Projectile;

import java.util.List;

/*
 * Represents a regular-type enemy
 */
public abstract class RegularEnemy extends Enemy {
    public RegularEnemy(String name, int health, int width, int height) {
        super(name, health, width, height);
    }

    // EFFECTS: Creates a projectile
    public abstract List<Projectile> shoot();
}