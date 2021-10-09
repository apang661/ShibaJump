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

    // EFFECTS: Returns true if stageB has the same values as this stage
    public boolean equals(Platform platformB) {
        return (this.width == platformB.width)
                && (this.height == platformB.height)
                && (this.coordX == platformB.coordX)
                && (this.coordY == platformB.coordY);
    }

    // EFFECTS: Returns true if the player is falling (dy <= 0) on the platform
    //         (touching top surface to middle of platform)
    public boolean isPlayerFallingOnPlatform(Player player) {
        if (player.getDy() <= 0) {
            int playerLeftX = player.getCoordX() - player.getWidth() / 2;
            int playerRightX = player.getCoordX() + player.getWidth() / 2;
            int playerBottomY = player.getCoordY() - player.getHeight() / 2;
            int platformLeftX = coordX - width / 2;
            int platformRightX = coordX + width / 2;

            boolean withinWidth = (playerRightX >= platformLeftX) && (playerLeftX <= platformRightX);
            boolean withinTopSurfaceToMiddle =
                    playerBottomY >= coordY && playerBottomY <= (coordY + height / 2);
            return (withinWidth && withinTopSurfaceToMiddle);
        }
        return false;
    }
}
