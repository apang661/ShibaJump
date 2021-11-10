package model.regularenemies;

import model.Projectile;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents the regular enemy, "Cat"
 */
public class RegularCat extends RegularEnemy {
    public static final String NAME = "Cat";
    public static final int MAX_HEALTH = 1;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int PROJECTILE_WIDTH = 10;
    public static final int PROJECTILE_HEIGHT = 10;
    public static final int PROJECTILE_DX = 0;
    public static final int PROJECTILE_DY = -10;
    public static final String IMAGE_FILE = "./images/regularCat.jpg";

    // EFFECTS: Creates a new RegularCat enemy
    public RegularCat() {
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

    @Override
    public void move() {

    }
}
