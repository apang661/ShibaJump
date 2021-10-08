package model.stages;

import model.Player;

/*
 * Represents a platform
 */
public class Platform {
    private int width;
    private int height;
    private int coordX;
    private int coordY;


    // REQUIRES: All parameters must be positive
    // EFFECTS: Creates new (width by height) platform at coordX, coordY
    public Platform(int width, int height, int coordX, int coordY) {
        this.width = width;
        this.height = height;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    // EFFECTS: Returns true if the player is falling (dy <= 0) on the platform
    //         (touching top surface to middle of platform)
    public boolean isPlayerFallingOnPlatform(Player player) {
        return true; // stub
    }
}
