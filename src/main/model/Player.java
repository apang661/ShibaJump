package model;

import java.util.List;

/*
 * Represents a player
 */
public class Player {
    public static final int DX = 5;

    private String name;
    private int currentHealth;
    private int width;
    private int height;
    private int coordX;
    private int coordY;
    private int dy;

    // EFFECTS: Creates a new player with variables corresponding to the parameter character
    public Player(PlayableCharacter character) {
        this.name = character.getName();
        this.currentHealth = character.getMaxHealth();
        this.width = character.getWidth();
        this.height = character.getHeight();
    }

    // EFFECTS: Returns the name of the player
    public String getName() {
        return name;
    }

    // EFFECTS: Returns the current health of the player
    public int getCurrentHealth() {
        return currentHealth;
    }

    // EFFECTS: Returns the width of the player
    public int getWidth() {
        return width;
    }

    // EFFECTS: Returns the height of the player
    public int getHeight() {
        return height;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: Move the player based on its dy and DX
    public void move() {
        this.coordX += DX;
        this.coordY += dy;
    }
}
