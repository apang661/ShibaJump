package ui;

import model.SJGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeScreen extends JPanel {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Color BACKGROUND_COLOR = new Color(165, 223, 245);

    private SJGame game;
    private JPanel bottomPanel;
    private JPanel buttonPanel;
    private Toolkit toolkit;
    private GameWindow gameWindow;

    public HomeScreen(GameWindow gameWindow, SJGame game) {
        this.game = game;
        this.toolkit = Toolkit.getDefaultToolkit();
        this.bottomPanel = new JPanel();
        this.gameWindow = gameWindow;

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        setupBottomPanel();
        setVisible(true);
    }

    private void setupBottomPanel() {
        bottomPanel.setVisible(true);
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new OverlayLayout(bottomPanel));
        setupButtonPanel();

        GridBagConstraints c = new GridBagConstraints();
        setupSurroundingBorders(c);
        c.weightx = 0.8;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;

        add(bottomPanel, c);
    }

    private void setupSurroundingBorders(GridBagConstraints c) {
        JPanel topPanel = new JPanel();
        try {
            BufferedImage image = ImageIO.read(new File("./images/homeBackground.png"));
            JLabel imageContainer = new JLabel(new ImageIcon(image));
            topPanel.add(imageContainer);
        } catch (IOException e) {
            // do nothing
        }

        c.weightx = 1;
        c.weighty = 0.3;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        add(topPanel, c);

        JPanel leftBorder = new JPanel();
        JPanel rightBorder = new JPanel();
        JPanel bottomBorder = new JPanel();
//
//        add(leftBorder, BorderLayout.LINE_START);
//        add(rightBorder, BorderLayout.LINE_END);
//        add(bottomBorder, BorderLayout.PAGE_END);
    }

    private void setupButtonPanel() {
        buttonPanel = new JPanel();
        bottomPanel.add(buttonPanel);
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setOpaque(false);
        addButtons();
    }

    private void addButtons() {
        JButton changeNameButton = new HomeButton(getActionNameButton(), "Change Name");
        JButton changeCharacterButton = new HomeButton(getActionCharacterButton(), "Change Character");
        JButton newGameButton = new HomeButton(getActionNewGameButton(), "Enter Game");
        JButton continueGameButton = new HomeButton(getActionContinueGameButton(), "Continue Game");
        JButton viewEnemiesButton = new HomeButton(getActionViewEnemiesButton(),"View Enemies");
        JButton quitGameButton = new HomeButton(getActionQuitGameButton(),"Quit Game");

        buttonPanel.add(changeNameButton);
        buttonPanel.add(changeCharacterButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueGameButton);
        buttonPanel.add(viewEnemiesButton);
        buttonPanel.add(quitGameButton);
    }

    private Action getActionNameButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
            }
        };
    }

    private Action getActionCharacterButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    private Action getActionNewGameButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.createNewStage();
                game.setPlaying(true);
                gameWindow.switchDisplay(1);
            }
        };
    }

    private Action getActionContinueGameButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    private Action getActionViewEnemiesButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    private Action getActionQuitGameButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
     //   paintBackground(g);
    }

    private void paintBackground(Graphics g) {
        Image backgroundImage = toolkit.getImage("./images/homeBackground.png");
        g.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
    }
}
