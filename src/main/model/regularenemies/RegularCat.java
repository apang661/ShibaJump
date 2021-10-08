package model.regularenemies;

/*
 * Represents the regular enemy, "Cat"
 */
public class RegularCat extends RegularEnemy {
    public static final String NAME = "Cat";
    public static final int MAX_HEALTH = 1;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 40;

    public RegularCat() {
        super(NAME, MAX_HEALTH, WIDTH, HEIGHT);
    }
}
