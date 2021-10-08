package model;

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
}
