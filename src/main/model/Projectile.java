package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents a projectile
 */
public class Projectile implements Writable {

    private String type; // either "player" if created by the player or "enemy" if created by the enemy
    private int width;
    private int height;
    private int coordX;
    private int coordY;
    private int dx;
    private int dy;

    // EFFECTS: Creates a new projectile with the given parameters
    public Projectile(String type, int width, int height, int coordX, int coordY, int dx, int dy) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.coordX = coordX;
        this.coordY = coordY;
        this.dx = dx;
        this.dy = dy;
    }

    public String getType() {
        return type;
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

    public int getCoordY() {
        return coordY;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    // EFFECTS: Checks if the projectile touches a rectangle with the given coordinates and size
    private boolean checkCollision(int coordX, int coordY, int width, int height) {
        int rectRightX = coordX + width / 2;
        int rectLeftX = coordX - width / 2;
        int rectTopY = coordY + height / 2;
        int rectBottomY = coordY - height / 2;
        int projRightX = this.coordX + this.width / 2;
        int projLeftX = this.coordX - this.width / 2;
        int projTopY = this.coordY + this.height / 2;
        int projBottomY = this.coordY - this.height / 2;

        boolean withinWidth = (rectRightX >= projLeftX) && (rectLeftX <= projRightX);
        boolean withinHeight = (rectTopY >= projBottomY) && (rectBottomY <= projTopY);

        return withinWidth && withinHeight;
    }

    // EFFECTS: Returns true if the projectile type is "enemy" AND is touching the given player
    public boolean checkPlayerCollisionWithProjectile(Player p) {
        return type.equals("enemy")
                && checkCollision(p.getCoordX(), p.getCoordY(), p.getWidth(), p.getHeight());
    }

    // EFFECTS: Returns true if the projectile is touching the given enemy
    public boolean checkEnemyCollisionWithProjectile(Enemy e) {
        return type.equals("player")
                && checkCollision(e.getCoordX(), e.getCoordY(), e.getWidth(), e.getHeight());
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("width", width);
        jsonObject.put("height", height);
        jsonObject.put("coordX", coordX);
        jsonObject.put("coordY", coordY);
        jsonObject.put("dx", dx);
        jsonObject.put("dy", dy);

        return jsonObject;
    }
}
