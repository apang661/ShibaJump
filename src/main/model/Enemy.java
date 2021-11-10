package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

/*
 * Represents an enemy
 */
public abstract class Enemy implements Writable {
    protected String name;
    protected int coordX;
    protected int coordY;
    protected int currentHealth;
    protected int width;
    protected int height;
    protected String imageFile;

    public Enemy(String name, int health, int width, int height, String imageFile) {
        this.name = name;
        this.currentHealth = health;
        this.width = width;
        this.height = height;
        this.imageFile = imageFile;
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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getImageFile() {
        return imageFile;
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

    // EFFECTS: If this enemy is touching a player projectile in the given list of projectiles,
    //          return true and remove the projectile
    public boolean checkCollisionWithAnyPlayerProjectile(List<Projectile> projectiles) {
        for (Projectile p : projectiles) {
            if (p.checkEnemyCollisionWithProjectile(this)) {
                projectiles.remove(p);
                return true;
            }
        }
        return false;
    }

    public abstract void move();

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("health", getCurrentHealth());
        jsonObject.put("coordX", getCoordX());
        jsonObject.put("coordY", getCoordY());

        return jsonObject;
    }
}
