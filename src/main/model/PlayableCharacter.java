package model;

import model.stages.Stage;

/*
 * Represents a playable character
 */
public class PlayableCharacter {
    public static final double MIN_JUMP_DY
            = 1.1 * Math.sqrt(-2 * Stage.GRAVITY_ACCELERATION * Stage.HEIGHT_BETWEEN_PLATFORMS);

    private int maxHealth;
    private String name;
    private int width;
    private int height;
    private int dx;
    private int dyForJump;

    public PlayableCharacter(int health, String name, int width, int height, int dx, double dyMultiplier) {
        this.maxHealth = health;
        this.name = name;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dyForJump = (int) Math.round(dyMultiplier * MIN_JUMP_DY);
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public String getName() {
        return this.name;
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