package model;

import java.util.List;

/*
 * Represents an enemy
 */
public abstract class Enemy {
    protected int coordX;
    protected int coordY;
    protected int currentHealth;
    protected int width;
    protected int height;
    protected String name;

    public Enemy(String name, int health, int width, int height) {
        this.name = name;
        this.currentHealth = health;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // EFFECTS: Returns true if given player is touching this enemy
    public boolean isPlayerTouchingEnemy(Player player) {
        int playerLeftX = player.getCoordX() - player.getWidth() / 2;
        int playerRightX = player.getCoordX() + player.getWidth() / 2;
        int playerTopY = player.getCoordY() + player.getHeight() / 2;
        int playerBottomY = player.getCoordY() - player.getHeight() / 2;
        int enemyLeftX = coordX - width / 2;
        int enemyRightX = coordX + width / 2;
        int enemyTopY = coordY + height / 2;
        int enemyBottomY = coordY - height / 2;

        boolean isWithinWidth = (playerRightX >= enemyLeftX) && (playerLeftX <= enemyRightX);
        boolean isWithinHeight = (playerTopY >= enemyBottomY) && (playerBottomY <= enemyTopY);

        return (isWithinWidth && isWithinHeight);
    }

    // EFFECTS: Returns true if this enemy is touching a player projectile in the given list of projectiles
    public boolean checkCollisionWithAnyPlayerProjectile(List<Projectile> projectiles) {
        for (Projectile p : projectiles) {
            if (p.getType().equals("player")) {
                if (p.checkEnemyCollisionWithProjectile(this)) {
                    return true;
                }
            }
        }
        return false;
    }
}
