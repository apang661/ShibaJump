package model;

import org.json.JSONObject;
import persistence.Writable;
import ui.GameWindow;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

/*
 * Represents the player of DogeGame
 */
public class Player implements Writable {

    private String name;
    private int currentHealth;
    private int width;
    private int height;
    private int coordX;
    private int coordY;
    private int maxDx;
    private int direction; // -1 is left, 0 is no direction, 1 is right
    private int dy;
    private int dyForJump;

    // EFFECTS: Creates a new player with variables corresponding to the parameter character
    public Player(PlayableCharacter character) {
        this.name = character.getName();
        this.currentHealth = character.getMaxHealth();
        this.width = character.getWidth();
        this.height = character.getHeight();
        this.maxDx = character.getDx();
        this.direction = 0;
        this.dy = character.getDyForJump();
        this.dyForJump = character.getDyForJump();
    }

    public String getName() {
        return name;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getWidth() {
        return width;
    }

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

    public int getMaxDx() {
        return maxDx;
    }

    public void setMaxDx(int maxDx) {
        this.maxDx = maxDx;
    }

    // MODIFIES: this
    // EFFECTS: Sets the direction based on the given key codes (A - left, D - right, other - no direction)
    public void setDirectionByKeyCodesHeldDown(Set<Integer> keyCodes) {
        if (keyCodes.contains(KeyEvent.VK_A)) {
            this.direction = -1;
        } else if (keyCodes.contains(KeyEvent.VK_D)) {
            this.direction = 1;
        } else {
            this.direction = 0;
        }
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
    // EFFECTS: Sets player's dy to its initial dy when jumping
    public void jump() {
        dy = dyForJump;
    }

    // MODIFIES: this
    // EFFECTS: Updates the player's position and velocity based on dx, dy, Stage.GRAVITY_ACCELERATION
    public void updatePositionAndVelocity() {
        double conversionFactor = GameWindow.UPDATE_INTERVAL / (Stage.TIME_BETWEEN_PLATFORMS * 1000);
        this.coordX += (maxDx * conversionFactor) * direction;
        this.coordY += (dy * GameWindow.UPDATE_INTERVAL / 1000);
        this.dy += Stage.GRAVITY_ACCELERATION * GameWindow.UPDATE_INTERVAL / 1000;

        checkBoundaries();
    }

    // MODIFIES: this
    // EFFECTS: Moves player to the other side of the stage if out of boundary
    private void checkBoundaries() {
        if (coordX < 0) {
            coordX = Stage.WIDTH - coordX;
        } else if (coordX > Stage.WIDTH) {
            coordX = coordX - Stage.WIDTH;
        }
    }

    //TODO: make projectile disappear on touch

    // MODIFIES: this
    // EFFECTS: Returns true if this player is touching any enemy projectile in the list of projectiles
    public boolean checkCollisionWithAnyEnemyProjectile(List<Projectile> projectiles) {
        for (Projectile p: projectiles) {
            if (p.checkPlayerCollisionWithProjectile(this)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Returns true if this player is touching any enemies in the list of enemies
    public boolean checkCollisionWithAnyEnemy(List<Enemy> enemies) {
        for (Enemy enemy: enemies) {
            if (enemy.isPlayerTouchingEnemy(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("currentHealth", currentHealth);
        jsonObject.put("coordX", coordX);
        jsonObject.put("coordY", coordY);
        jsonObject.put("dx", maxDx);
        jsonObject.put("dy", dy);

        return jsonObject;
    }

}
