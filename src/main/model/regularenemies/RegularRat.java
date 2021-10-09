package model.regularenemies;

/*
 * Represents the regular enemy, "Cat"
 */
public class RegularRat extends RegularEnemy {
    public static final String NAME = "Rat";
    public static final int MAX_HEALTH = 1;
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;

    public RegularRat() {
        super(NAME, MAX_HEALTH, WIDTH, HEIGHT);
    }
}