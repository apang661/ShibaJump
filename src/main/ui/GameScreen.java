package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

// This class references CPSC210/B02-SpaceInvadersBase
// Link: https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase.git

public class GameScreen extends JPanel {
    public static final int SCREEN_WIDTH = Stage.WIDTH;
    public static final int SCREEN_HEIGHT = 600;
    public static final int UPDATE_INTERVAL = 7; // 17 ms for ~60 fps; 7 ms for ~144 fps

    private SJGame game;
    private GameWindow gameWindow;
    private int currentTopBorderHeight; // the height of the player + half of the screen height
    Toolkit toolkit;

    public GameScreen(GameWindow gameWindow, SJGame game) {
        this.gameWindow = gameWindow;
        this.game = game;
        this.toolkit = Toolkit.getDefaultToolkit();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.GRAY);
        initializeKeyBindings(game);
        addTimer();
    }

    private void initializeKeyBindings(SJGame game) {
        InputMap windowInputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        windowInputMap.put(KeyStroke.getKeyStroke("A"), "left");
        actionMap.put("left", getActionLeft(game));
        windowInputMap.put(KeyStroke.getKeyStroke("released A"), "released left");
        actionMap.put("released left", getActionReleasedLeft(game));
        windowInputMap.put(KeyStroke.getKeyStroke("D"), "right");
        actionMap.put("right", getActionRight(game));
        windowInputMap.put(KeyStroke.getKeyStroke("released D"), "released right");
        actionMap.put("released right", getActionReleasedRight(game));
        windowInputMap.put(KeyStroke.getKeyStroke("K"), "shoot");
        actionMap.put("shoot", getActionShoot(game));
        windowInputMap.put(KeyStroke.getKeyStroke("P"), "pause");
        actionMap.put("pause", getActionPause(game));
    }


    private Action getActionLeft(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_A);
            }
        };
    }

    private Action getActionReleasedLeft(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyReleased(KeyEvent.VK_A);
            }
        };
    }

    private Action getActionRight(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_D);
            }
        };
    }

    private Action getActionReleasedRight(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyReleased(KeyEvent.VK_D);
            }
        };
    }

    private Action getActionShoot(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_K);
            }
        };
    }

    private Action getActionPause(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_P);
            }
        };
    }

    private void addTimer() {
        Timer t = new Timer(GameScreen.UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.isPlaying()) {
                    System.out.println("playing");
                    game.update();
                    repaint();
                    if (game.isGameOver()) {
                        System.out.println("You lose");
                        System.exit(0);
                    }
                }
            }
        });

        t.start();
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