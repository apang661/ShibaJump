package model;

/*
 * Represents a playable character
 */
public enum PlayableCharacter {
    DOGE("Doge",5,  60, 60, 300, 1.3, "./images/doge.jpeg"),
    WALTER("Walter",4,  80, 110, 800, 1.2, "./images/walter.jpg"),
    CHEEMS("Cheems", 4, 110, 70, 700, 1.1, "./images/cheems.jpg");

    private String name;
    private int maxHealth;
    private int width;
    private int height;
    private int dx;
    private int dyForJump;
    private String imageFile;

    PlayableCharacter(String name, int health, int width, int height, int dx, double dyMultiplier, String imageFile) {
        this.maxHealth = health;
        this.name = name;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dyForJump = (int) Math.round(dyMultiplier * Stage.MIN_JUMP_DY);
        this.imageFile = imageFile;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDx() {
        return dx;
    }

    public int getDyForJump() {
        return dyForJump;
    }

    public String getImageFile() {
        return imageFile;
    }
}