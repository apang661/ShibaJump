package model;

/*
 * Represents a playable character
 */
public class PlayableCharacter {
    private int maxHealth;
    private String name;
    private int width;
    private int height;

    public PlayableCharacter(int health, String name, int width, int height) {
        this.maxHealth = health;
        this.name = name;
        this.width = width;
        this.height = height;
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

}