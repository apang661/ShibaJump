package ui;

import model.SJGame;

import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame {
    private GameScreen gs;
    private HomeScreen hs;

    public GameWindow(SJGame game) {
        super("Doge Jump");
        gs = new GameScreen(this, game);
        hs = new HomeScreen(this, game);

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
            case (0): {
                hs.setVisible(true);
                hs.setOpaque(true);
                gs.setVisible(false);
                gs.setOpaque(false);
            }
            case (1): {
                gs.setVisible(true);
                gs.setOpaque(true);
                hs.setVisible(false);
                hs.setOpaque(false);
            }
            default: {
                // do nothing
            }
        }
    }
}
