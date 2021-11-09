package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// This class references CPSC210/B02-SpaceInvadersBase
// Link: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase.git

public class GameScreen extends JPanel {
    public static final int SCREEN_WIDTH = Stage.WIDTH;
    public static final int SCREEN_HEIGHT = 600;

    private DJGame game;
    private int currentTopBorderHeight; // the height of the player + half of the screen height
    Toolkit toolkit;

    public GameScreen(DJGame game) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.gray);
        this.game = game;

        toolkit = Toolkit.getDefaultToolkit();
    }

    // EFFECTS: Sets the top of the screen to the player's height + half of the screen height
    //          or the top and bottom height limits, if the player is too high or low
    public void centerPlayerOnScreen() {
        int playerCoordY = game.getPlayer().getCoordY();
        currentTopBorderHeight = Math.max(
                Math.min(playerCoordY + SCREEN_HEIGHT / 2, Stage.HEIGHT),
                SCREEN_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);
    }

    // MODIFIES: g
    // EFFECTS: Renders the current game state onto g
    private void drawGame(Graphics g) {
        centerPlayerOnScreen();
        drawStage(g, game.getStage());
        drawPlayer(g, game.getPlayer());
        drawProjectiles(g, game.getProjectiles());
    }

    // MODIFIES: g
    // EFFECTS: Draws the player onto g
    private void drawPlayer(Graphics g, Player player) {
        Image playerImage;
        String imageFileLocation;
        String name = player.getName();

        // Find the image path of given character name
        imageFileLocation = player.getImageFileLocation(name);

        // Draw the image onto g if a path is found, otherwise draw a black rectangle of the same size
        if (!imageFileLocation.equals("")) {
            playerImage = toolkit.getImage(imageFileLocation);
            g.drawImage(playerImage,
                    player.getCoordX() - player.getWidth() / 2,
                    currentTopBorderHeight - (player.getCoordY() + player.getHeight() / 2),
                    player.getWidth(), player.getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(player.getCoordX() - player.getWidth() / 2,
                    currentTopBorderHeight - (player.getCoordY() + player.getHeight()),
                    player.getWidth(), player.getHeight());
        }
    }


    // MODIFIES: g
    // EFFECTS: Draws the stage onto g
    private void drawStage(Graphics g, Stage stage) {
        for (Platform p: stage.getPlatforms()) {
            if ((p.getCoordY() < currentTopBorderHeight + p.getHeight() / 2)
                    && (p.getCoordY() > currentTopBorderHeight - SCREEN_HEIGHT - p.getHeight() / 2)) {
                drawPlatform(g, p);
            }
        }
        for (Enemy e: stage.getRegularEnemies()) {
            if ((e.getCoordY() < currentTopBorderHeight + e.getHeight() / 2)
                    && (e.getCoordY() > currentTopBorderHeight - SCREEN_HEIGHT - e.getHeight() / 2)) {
                drawEnemy(g, e);
            }
        }
    }

    // MODIFIES: g
    // EFFECTS: Draws the platform onto g
    private void drawPlatform(Graphics g, Platform p) {
        g.setColor(Color.WHITE);
        g.fillRect(p.getCoordX() - p.getWidth() / 2,
                currentTopBorderHeight - (p.getCoordY() + p.getHeight()),
                p.getWidth(), p.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(p.getCoordX() - p.getWidth() / 2,
                currentTopBorderHeight - (p.getCoordY() + p.getHeight()),
                p.getWidth(), p.getHeight());
    }

    // MODIFIES: g
    // EFFECTS: Draws the enemy onto g
    private void drawEnemy(Graphics g, Enemy e) {
        Image enemyImage = toolkit.getImage(e.getImageFile());
        g.drawImage(enemyImage,
                e.getCoordX() - e.getWidth() / 2,
                currentTopBorderHeight - (e.getCoordY() + e.getHeight() / 2),
                e.getWidth(), e.getHeight(), this);
    }

    // MODIFIES: g
    // EFFECTS: Draws the projectiles onto g
    private void drawProjectiles(Graphics g, List<Projectile> projectiles) {
        for (Projectile p: projectiles) {
            if (p.getType().equals("player")) {
                g.setColor(Color.GREEN);
            } else if (p.getType().equals("enemy")) {
                g.setColor(Color.RED);
            }
            g.drawRect(p.getCoordX() - p.getWidth() / 2,
                    currentTopBorderHeight - (p.getCoordY() + p.getHeight()),
                    p.getWidth(), p.getHeight());
        }
    }
}