package ui;

import model.Player;
import model.SJGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeScreen extends JPanel {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Color BACKGROUND_COLOR = new Color(177, 222, 242, 255);

    private SJGame game;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel namePanel;
    private JPanel characterPanel;
    private GameWindow gameWindow;


    public HomeScreen(SJGame game, GameWindow gameWindow) {
        this.game = game;
        this.gameWindow = gameWindow;
        this.contentPanel = new JPanel();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout(new GridBagLayout());

        setBackground(BACKGROUND_COLOR);
        setOpaque(true);
        setupTitlePanel();
        setupContentPanel();
        setVisible(true);
    }

    private void setupTitlePanel() {
        JPanel titlePanel = new JPanel();
        GridBagConstraints c = new GridBagConstraints();

        titlePanel.setBackground(BACKGROUND_COLOR);
        giveBorder(titlePanel);

        try {
            BufferedImage image = ImageIO.read(new File("./images/homeBackground.png"));
            JLabel imageContainer = new JLabel(new ImageIcon(image));
            titlePanel.add(imageContainer);
        } catch (IOException e) {
            // do nothing
        }

        c.weightx = 1;
        c.weighty = 0.3;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 20, 10, 20);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        add(titlePanel, c);

        c.gridwidth = 1;
    }

    private void setupSurroundingBorders(GridBagConstraints c) {
        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(BACKGROUND_COLOR);
        c.weightx = 0.2;
        c.weighty = 0.7;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        add(leftBorder, c);

        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(BACKGROUND_COLOR);
        c.weightx = 0.2;
        c.weighty = 0.7;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 1;
        add(rightBorder, c);
    }

    private void setupContentPanel() {
        contentPanel.setVisible(true);
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new OverlayLayout(contentPanel));

        setupButtonPanel();
        setupNamePanel();
        setupCharacterPanel();

        GridBagConstraints c = new GridBagConstraints();
        setupSurroundingBorders(c);
        c.weightx = 0.6;
        c.weighty = 0.7;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 20, 20, 20);
        c.gridx = 1;
        c.gridy = 1;

        giveBorder(contentPanel);

        add(contentPanel, c);
    }

    private void setupButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setOpaque(false);
        addButtons();

        contentPanel.add(buttonPanel);
    }

    private void addButtons() {
        JButton changeNameButton = new HomeButton(getActionChangeNameButton(), "Change Name");
        JButton changeCharacterButton = new HomeButton(getActionChangeCharacterButton(), "Change Character");
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

    private Action getActionChangeNameButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                namePanel.setVisible(true);
            }
        };
    }

    private Action getActionChangeCharacterButton() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                characterPanel.setVisible(true);
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


    private void setupNamePanel() {
        namePanel = new JPanel();
        namePanel.setBackground(BACKGROUND_COLOR);
        namePanel.setVisible(false);

        JTextField nameTextField = new JTextField("Enter name here:", 10);
        nameTextField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Document doc = nameTextField.getDocument();
                try {
                    String newName = doc.getText(0, doc.getEndPosition().getOffset());
                    game.getAccount().setUsername(newName);
                    System.out.println(newName);
                } catch (BadLocationException ex) {
                    System.out.println("Could not get name.");
                }
                namePanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        });

        namePanel.add(nameTextField);
        contentPanel.add(namePanel);
    }

    private void setupCharacterPanel() {
        characterPanel = new JPanel();
        characterPanel.setLayout(new GridLayout(0, 3));
        characterPanel.setBackground(BACKGROUND_COLOR);

        for (Player.PlayableCharacter pc: Player.PlayableCharacter.values()) {
            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.setCharacter(pc.getName());
                    characterPanel.setVisible(false);
                    buttonPanel.setVisible(true);
                }
            };
            JButton characterButton = new HomeButton(action, pc.getName());
            characterPanel.add(characterButton);
        }

        characterPanel.setVisible(false);
        contentPanel.add(characterPanel);
    }

    // for debugging
    private void giveBorder(JPanel titlePanel) {
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        //   titlePanel.setBorder(border);
    }
}
