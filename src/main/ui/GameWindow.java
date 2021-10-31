package ui;

import model.DJGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameWindow extends JFrame {
    public static final int UPDATE_INTERVAL = 7; // 17 ms for ~60 fps; 7 ms for ~144 fps

    DJGame game;
    GameScreen gs;

    public GameWindow(DJGame game) {
        super("Doge Jump");
        this.game = game;
        gs = new GameScreen(game);

        add(gs);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            game.keyReleased(e.getKeyCode());
        }
    }

    private void addTimer() {
        Timer t = new Timer(UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.update();
                gs.repaint();
            }
        });

        t.start();
    }

    // MODIFIES: this
    // EFFECTS: Sets the location of the frame to the center of desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }
}
