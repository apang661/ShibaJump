package model.regularenemies;

import model.Projectile;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents the regular enemy, "Cat"
 */
public class RegularRat extends RegularEnemy {
    public static final String NAME = "Rat";
    public static final int MAX_HEALTH = 1;
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    public static final int PROJECTILE_WIDTH = 2;
    public static final int PROJECTILE_HEIGHT = 10;
    public static final int PROJECTILE_DX = 0;
    public static final int PROJECTILE_DY = -20;
    public static final String IMAGE_FILE = "./data/images/regularRat.jpg";

    // EFFECTS: Creates a new RegularRat enemy
    public RegularRat() {
        super(NAME, MAX_HEALTH, WIDTH, HEIGHT, IMAGE_FILE);
    }

    @Override
    public List<Projectile> shoot() {
        List<Projectile> projectiles = new ArrayList<>();
        Projectile p0 = new Projectile("enemy", PROJECTILE_WIDTH, PROJECTILE_HEIGHT,
                super.coordX, super.coordY, PROJECTILE_DX, PROJECTILE_DY);
        projectiles.add(p0);

        return projectiles;
    }

//    @Override
//    public void move() {
//
//    }
}