package model;

import model.stages.Stage;

/*
 * Represents a player
 */
public class Player {

    private String name;
    private int currentHealth;
    private int width;
    private int height;
    private int coordX;
    private int coordY;
    private int dx;
    private int dy;
    private int dyForJump;

    // EFFECTS: Creates a new player with variables corresponding to the parameter character
    public Player(PlayableCharacter character) {
        this.name = character.getName();
        this.currentHealth = character.getMaxHealth();
        this.width = character.getWidth();
        this.height = character.getHeight();
        this.dx = character.getDx();
        this.dy = character.getDyForJump();
        this.dyForJump = character.getDyForJump();
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

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDyForJump() {
        return dyForJump;
    }

    // MODIFIES: this
    // EFFECTS: Updates the player's position and velocity based on dx, dy, Stage.GRAVITY_ACCELERATION
    public void updatePositionAndVelocity() {
        this.coordX += dx;
        this.coordY += dy;
        this.dy += Stage.GRAVITY_ACCELERATION;
    }
}
