package model.bossenemies;

/*
 * Represents the boss enemy, "Evil Cat"
 */
public class BossCat extends BossEnemy {
    public static final String NAME = "Evil Cat";
    public static final int MAX_HEALTH = 1;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 40;

    public BossCat() {
        super(NAME, MAX_HEALTH, WIDTH, HEIGHT);
    }
}
