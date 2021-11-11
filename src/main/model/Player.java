package model;

import org.json.JSONObject;
import persistence.Writable;
import ui.GameScreen;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

/*
 * Represents the player of DogeGame
 */
public class Player implements Writable {
    public static final int MAX_FALLING_DY =
            -1 * Stage.PLATFORM_THICKNESS / 2; // max dy for player to not fall through platform

    public enum PlayableCharacter {
        DOGE("Doge",5,  60, 60, 400, 1.25, "./images/doge.jpg"),
        WALTER("Walter",4,  40, 70, 600, 1.2, "./images/walter.jpg"),
        CHEEMS("Cheems", 4, 40, 40, 600, 1.1, "./images/cheems.jpg");

        public static final int MIN_HEIGHT = 40;
        private final String name;
        private final int maxHealth;
        private final int width;
        private final int height;
        private final int dx;
        private final int dyForJump;
        private final String imageFile;

        PlayableCharacter(String name, int health, int width, int height, int dx, double dyMultiplier, String imgFile) {
            this.maxHealth = health;
            this.name = name;
            this.width = width;
            this.height = Math.max(MIN_HEIGHT, height);
            this.dx = dx;
            this.dyForJump = (int) Math.round(dyMultiplier * Stage.MIN_JUMP_DY);
            this.imageFile = imgFile;
        }

        public String getName() {
            return this.name;
        }
    }

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
        this.name = character.name;
        this.currentHealth = character.maxHealth;
        this.width = character.width;
        this.height = character.height;
        this.maxDx = character.dx;
        this.direction = 0;
        this.dy = character.dyForJump;
        this.dyForJump = character.dyForJump;
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

    public int getDirection() {
        return direction;
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

    public void setDyForJump(int dyForJump) {
        this.dyForJump = dyForJump;
    }

    // EFFECTS: Returns the image file directory for the character of the given name
    public String getImageFileLocation(String name) {
        String imageFileLocation = "";
        for (PlayableCharacter pc: PlayableCharacter.values()) {
            if (name.equals(pc.getName())) {
                imageFileLocation = pc.imageFile;
            }
        }
        return imageFileLocation;
    }

    // MODIFIES: this
    // EFFECTS: Sets player's dy to its initial dy when jumping
    public void jump() {
        dy = dyForJump;
    }

    // MODIFIES: this
    // EFFECTS: Updates the player's position and velocity based on dx, dy, Stage.GRAVITY_ACCELERATION
    public void updatePositionAndVelocity() {
        double conversionFactor = (double) GameScreen.UPDATE_INTERVAL / 1000;
        this.coordX +=
                Math.round((maxDx * conversionFactor) * direction); // *** rounded to keep left and right movement equal
        this.coordY += Math.max(MAX_FALLING_DY, (dy * conversionFactor));
        this.dy += Stage.GRAVITY_ACCELERATION * conversionFactor;

        handleHorizontalBoundaries();
    }

    // MODIFIES: this
    // EFFECTS: Moves player to the other side of the stage if out of boundary
    private void handleHorizontalBoundaries() {
        if (coordX < 0) {
            coordX = Stage.WIDTH + coordX;
        } else if (coordX > Stage.WIDTH) {
            coordX = coordX - Stage.WIDTH;
        }
    }

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
