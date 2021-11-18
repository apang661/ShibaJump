package ui;

import model.SJGame;

import javax.swing.*;
import java.awt.*;

/*
 * Represents the Shiba Jump game window
 */

public class GameWindow extends JFrame {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    private GameScreen gs;
    private HomeScreen hs;

    // EFFECTS: Starts up a Shiba Jump game window
    public GameWindow(SJGame game) {
        super("Doge Jump");
        gs = new GameScreen(game, this);
        hs = new HomeScreen(game, this);

        setLayout(new OverlayLayout(getContentPane()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        add(gs);
        gs.setVisible(false);
        add(hs);
        hs.setVisible(true);
        pack();
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Sets the location of the frame to the center of desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: Switches the display based on the given int:
    //            - 0 = Home Screen
    //            - 1 = Game Screen
    //            - other = do nothing
    public void switchDisplay(int i) {
        switch (i) {
            case 0:
                gs.setVisible(false);
                hs.setVisible(true);
                hs.updateInfoLabel();
                break;
            case 1:
                gs.setVisible(true);
                hs.setVisible(false);
                break;
            default:
                // do nothing
        }
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Sets the game stored in the game screen and home screen to the given game
    public void setGame(SJGame game) {
        gs.setGame(game);
        hs.setGame(game);
    }

    // MODIFIES: this
    // EFFECTS: Starts the game in the game screen
    public void startGame() {
        gs.startGame();
    }
}
