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


/*
 * Represents the game screen of Shiba Jump
 */
public class GameScreen extends JPanel {
    public static final int UPDATE_INTERVAL = 7; // 17 ms for ~60 fps; 7 ms for ~144 fps
    public static final int SCREEN_HEIGHT = GameWindow.SCREEN_HEIGHT;

    private SJGame game;
    private GameWindow gameWindow;
    private int currentTopBorderHeight; // the height of the player + half of the screen height
    private Toolkit toolkit;
    private Timer timer;
    private JPanel pausePanel;
    private JPanel topPanel;

    // EFFECTS: Creates a new game screen
    public GameScreen(SJGame game, GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.game = game;
        this.toolkit = Toolkit.getDefaultToolkit();

        setBackground(Color.GRAY);
        setLayout(new OverlayLayout(this));
        setPreferredSize(new Dimension(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT));
        setupPausePanel();
        setupTopPanel();
    }

    // MODIFIES: this
    // EFFECTS: Sets up the top panel consisting of a pause button at the top right
    private void setupTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);

        JButton pauseButton = HomeScreen.getDefaultButton(getActionPause(game), "Pause");
        topPanel.add(pauseButton);
        add(topPanel);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the pause panel consisting of a "resume" button and "save and quit" button
    private void setupPausePanel() {
        pausePanel = new JPanel();
        pausePanel.setBackground(new Color(200, 200, 200, 150));
        pausePanel.setVisible(false);

        JButton resumeButton = HomeScreen.getDefaultButton(getActionResume(), "Resume game");
        JButton saveQuitButton = HomeScreen.getDefaultButton(getActionSaveQuit(), "Save and quit game");

        pausePanel.add(resumeButton);
        pausePanel.add(saveQuitButton);

        add(pausePanel);
    }

    // EFFECTS: Returns the "save and quit" action
    private Action getActionSaveQuit() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausePanel.setVisible(false);
                topPanel.setVisible(true);
                timer.stop();
                gameWindow.switchDisplay(0);
            }
        };
    }

    // EFFECTS: Returns the "resume" action
    private Action getActionResume() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPlaying(true);
                topPanel.setVisible(true);
                pausePanel.setVisible(false);
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: Initializes the key bindings that are used during the game
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

    // EFFECTS: Returns the "move left" action
    private Action getActionLeft(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_A);
            }
        };
    }

    // EFFECTS: Returns the "stop moving left" action
    private Action getActionReleasedLeft(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyReleased(KeyEvent.VK_A);
            }
        };
    }

    // EFFECTS: Returns the "move right" action
    private Action getActionRight(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_D);
            }
        };
    }

    // EFFECTS: Returns the "stop moving right" action
    private Action getActionReleasedRight(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyReleased(KeyEvent.VK_D);
            }
        };
    }

    // EFFECTS: Returns the "shoot" action
    private Action getActionShoot(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_K);
            }
        };
    }

    // EFFECTS: Returns the "pause" action
    private Action getActionPause(SJGame game) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                game.keyPressed(KeyEvent.VK_P);
                if (!pausePanel.isVisible()) {
                    pausePanel.setVisible(true);
                    topPanel.setVisible(false);
                } else {
                    pausePanel.setVisible(false);
                    topPanel.setVisible(true);
                }
            }
        };
    }

    // EFFECTS: Adds and initializes a timer to update the game every UPDATE_INTERVAL
    private void addTimer() {
        timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.isPlaying()) {
                    game.update();
                    repaint();
                    checkGameOver();
                    checkWinGame();
                }
            }
        });
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: Ends the game if the game is over
    private void checkGameOver() {
        if (game.isGameOver()) {
            endGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: If the player reaches the top of the stage,
    //          advances account to the next stage, adds 100 ShibaPoints to the account, and ends the game
    private void checkWinGame() {
        if (game.getPlayer().getCoordY() > Stage.HEIGHT) {
            Account account = game.getAccount();
            account.setShibaPoints(account.getShibaPoints() + 100);
            account.setNextStageNum(account.getNextStageNum() + 1);
            endGame();
        }
    }

    // MODIFIES: this
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

    // MODIFIES: this
    // EFFECTS: Sets the game if the given game
    public void setGame(SJGame game) {
        this.game = game;
    }

    // MODIFIES: this
    // EFFECTS: Initializes the game
    public void startGame() {
        game.setPlaying(true);
        game.setGameOver(false);
        initializeKeyBindings(game);
        addTimer();
    }

    // MODIFIES: this
    // EFFECTS: Ends the game, switches the home screen, and prepares the next game
    private void endGame() {
        game.getPlayer().setCoordY(0);
        game.setGameOver(false);
        game.setPlaying(false);
        timer.stop();
        gameWindow.switchDisplay(0);
        resetKeys();
    }

    // MODIFIES: this
    // EFFECTS: Releases the movement keys pressed
    private void resetKeys() {
        game.keyReleased(KeyEvent.VK_A);
        game.keyReleased(KeyEvent.VK_D);
    }
}