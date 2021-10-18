package model;

/*
 * Represents a playable character
 */
public enum PlayableCharacter {
    DOGE("Doge",5,  100, 100, 600, 1.3),
    WALTER("Walter",4,  80, 110, 800, 1.2),
    CHEEMS("Cheems", 4, 110, 70, 700, 1.1);

    private String name;
    private int maxHealth;
    private int width;
    private int height;
    private int dx;
    private int dyForJump;

    PlayableCharacter(String name, int health, int width, int height, int dx, double dyMultiplier) {
        this.maxHealth = health;
        this.name = name;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dyForJump = (int) Math.round(dyMultiplier * Stage.MIN_JUMP_DY);
    }

    public String getName() {
        return this.name;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDx() {
        return dx;
    }

    public int getDyForJump() {
        return dyForJump;
    }
}