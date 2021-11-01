package model;

/*
 * Represents a playable character
 */
public enum PlayableCharacter {
    DOGE("Doge",5,  60, 60, 400, 1.25, "./images/doge.jpg"),
    WALTER("Walter",4,  40, 70, 600, 1.2, "./images/walter.jpg"),
    CHEEMS("Cheems", 4, 40, 40, 500, 1.1, "./images/cheems.jpg");

    public static final int MIN_HEIGHT = 40;
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
        this.height = Math.max(MIN_HEIGHT, height);
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