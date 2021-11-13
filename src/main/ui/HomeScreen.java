package ui;

import model.Enemy;
import model.EnemyList;
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
import java.util.List;

public class HomeScreen extends JPanel {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Color BACKGROUND_COLOR = new Color(177, 222, 242, 255);

    private SJGame game;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel namePanel;
    private JPanel characterPanel;
    private JPanel enemiesPanel;
    private JPanel enemiesMainPanel;
    private int selectedEnemyIndex;
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
        setupEnemiesPanel();

        GridBagConstraints c = new GridBagConstraints();
        setupSurroundingBorders(c);
        c.weightx = 0.6;
        c.weighty = 0.7;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 20, 20, 20);
        c.gridx = 1;
        c.gridy = 1;

        contentPanel.add(buttonPanel);
        contentPanel.add(characterPanel);
        contentPanel.add(namePanel);
        contentPanel.add(enemiesPanel);


        giveBorder(contentPanel);
        add(contentPanel, c);
    }

    private void setupButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setOpaque(false);
        addButtons();
    }

    private void addButtons() {
        JButton changeNameButton = getDefaultButton(getActionChangeNameButton(), "Change Name");
        JButton changeCharacterButton = getDefaultButton(getActionChangeCharacterButton(), "Change Character");
        JButton newGameButton = getDefaultButton(getActionNewGameButton(), "Enter Game");
        JButton continueGameButton = getDefaultButton(getActionContinueGameButton(), "Continue Game");
        JButton viewEnemiesButton = getDefaultButton(getActionViewEnemiesButton(),"View Enemies");
        JButton quitGameButton = getDefaultButton(getActionQuitGameButton(),"Quit Game");

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
                buttonPanel.setVisible(false);
                enemiesPanel.setVisible(true);
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

    // MODIFIES: this
    // EFFECTS: Sets up the "Change Name" panel
    private void setupNamePanel() {
        namePanel = new JPanel();
        namePanel.setOpaque(false);
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
    }

    // MODIFIES: this
    // EFFECTS: Sets up the "Change Character" panel
    private void setupCharacterPanel() {
        characterPanel = new JPanel();
        characterPanel.setLayout(new GridLayout(0, 3));
        characterPanel.setOpaque(false);

        for (Player.PlayableCharacter pc: Player.PlayableCharacter.values()) {
            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.setCharacter(pc.getName());
                    characterPanel.setVisible(false);
                    buttonPanel.setVisible(true);
                }
            };
            JButton characterButton = getDefaultButton(action, pc.getName());
            characterPanel.add(characterButton);
        }

        characterPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the "View Enemies" panel
    private void setupEnemiesPanel() {
        enemiesPanel = new JPanel(new BorderLayout());
        enemiesPanel.setBackground(new Color(190, 230, 255));
        enemiesPanel.setVisible(false);
        selectedEnemyIndex = 0;

        JPanel enemyTopPanel = getEnemyTopPanel();
        JPanel enemyBottomPanel = getEnemyBottomPanel();
        JPanel enemyLeftPanel = getVCentredButtonPanel(getActionEnemyLeft(), "<");
        JPanel enemyRightPanel = getVCentredButtonPanel(getActionEnemyRight(), ">");

        enemiesPanel.add(enemyTopPanel, BorderLayout.PAGE_START);
        enemiesPanel.add(enemyBottomPanel, BorderLayout.PAGE_END);
        enemiesPanel.add(enemyLeftPanel, BorderLayout.LINE_START);
        enemiesPanel.add(enemyRightPanel, BorderLayout.LINE_END);

        setupAndUpdateEnemyPanel();
        enemiesPanel.add(enemiesMainPanel, BorderLayout.CENTER);
        giveBorder(enemiesMainPanel);
    }

    private JPanel getEnemyTopPanel() {
        JPanel enemyTopPanel = new JPanel();
        enemyTopPanel.setOpaque(false);
        enemyTopPanel.setLayout(new BoxLayout(enemyTopPanel, BoxLayout.LINE_AXIS));
        JButton addEnemyButton = getDefaultButton(getActionAddEnemy(), "+");
        JButton removeEnemyButton = getDefaultButton(getActionRemoveEnemy(), "-");

        enemyTopPanel.add(addEnemyButton);
        enemyTopPanel.add(removeEnemyButton);
        enemyTopPanel.add(Box.createHorizontalGlue());
        return enemyTopPanel;
    }


    private Action getActionAddEnemy() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Here is the list of regular enemies in the game: "
                        + placeCommaOrAndInBetweenStrings(SJGame.getRegularEnemyNames())
                        + "\nHere is the list of boss enemies in the game: "
                        + placeCommaOrAndInBetweenStrings(SJGame.getBossEnemyNames())
                        + "\nEnter the added enemy name here:";
                String enteredName = JOptionPane.showInputDialog(enemiesMainPanel, message, "");
                EnemyList enemyList = game.getAccount().getEncounteredEnemies();
                enemyList.addEnemy(enteredName);
                selectedEnemyIndex = enemyList.size() - 1;
                setupAndUpdateEnemyPanel();
            }
        };
    }

    private Action getActionRemoveEnemy() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EnemyList enemyList = game.getAccount().getEncounteredEnemies();
                if (enemyList.size() != 0) {
                    enemyList.removeEnemy(enemyList.getEnemy(selectedEnemyIndex));
                    if (enemyList.size() == 0) {
                        selectedEnemyIndex = 0;
                    } else if (selectedEnemyIndex > enemyList.size() - 1) {
                        selectedEnemyIndex = enemyList.size() - 1;
                    }
                }
                setupAndUpdateEnemyPanel();
            }
        };
    }

    private JPanel getEnemyBottomPanel() {
        JPanel enemyBottomPanel = new JPanel();
        enemyBottomPanel.setOpaque(false);
        enemyBottomPanel.setLayout(new BoxLayout(enemyBottomPanel, BoxLayout.LINE_AXIS));
        JButton quitEnemyButton = getDefaultButton(getQuitEnemiesAction(), "Back");

        enemyBottomPanel.add(quitEnemyButton);
        enemyBottomPanel.add(Box.createHorizontalGlue());
        return enemyBottomPanel;
    }

    private Action getQuitEnemiesAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemiesPanel.setVisible(false);
                buttonPanel.setVisible(true);
            }
        };
    }

    // EFFECTS: Creates a JPanel with a vertically centred JButton with the given action and label
    //          (used for enemy panel)
    private JPanel getVCentredButtonPanel(Action action, String label) {
        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.PAGE_AXIS));
        leftButtonPanel.setOpaque(false);
        JButton leftButton = new JButton(action);
        leftButton.setText(label);
        leftButtonPanel.add(Box.createVerticalGlue());
        leftButtonPanel.add(leftButton);
        leftButtonPanel.add(Box.createVerticalGlue());
        return leftButtonPanel;
    }

    private Action getActionEnemyLeft() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int listSize = game.getAccount().getEncounteredEnemies().size();
                if (listSize == 0)  {
                    selectedEnemyIndex = 0;
                } else if (selectedEnemyIndex != 0) {
                    selectedEnemyIndex -= 1;
                }
                setupAndUpdateEnemyPanel();
            }
        };
    }

    private Action getActionEnemyRight() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int listSize = game.getAccount().getEncounteredEnemies().size();
                if (listSize == 0) {
                    selectedEnemyIndex = 0;
                } else if (selectedEnemyIndex != listSize - 1) {
                    selectedEnemyIndex += 1;
                }
                setupAndUpdateEnemyPanel();
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: Changes the enemy panel based on the selectedEnemyIndex
    private void setupAndUpdateEnemyPanel() {
        if (enemiesMainPanel != null) {
            enemiesPanel.remove(enemiesMainPanel);
        }
        enemiesMainPanel = new JPanel();
        enemiesMainPanel.setOpaque(false);
        enemiesMainPanel.setLayout(new BoxLayout(enemiesMainPanel, BoxLayout.PAGE_AXIS));

        EnemyList enemyList = game.getAccount().getEncounteredEnemies();
        if (enemyList.size() == 0) {
            JLabel indexTextLabel = new JLabel("No Enemies");
            indexTextLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
            enemiesMainPanel.add(Box.createVerticalGlue());
            enemiesMainPanel.add(indexTextLabel);
            enemiesMainPanel.add(Box.createVerticalGlue());
        } else {
            createEnemyPanel(enemyList);
        }

        enemiesPanel.add(enemiesMainPanel);
        enemiesPanel.revalidate();
    }

    private void createEnemyPanel(EnemyList enemyList) {
        Enemy enemy = enemyList.getEnemy(selectedEnemyIndex);
        Image image = getEnemyImage(enemy);

        JLabel enemyNameLabel = new JLabel(enemy.getName());
        JLabel enemyImageLabel = new JLabel(new ImageIcon(image));
        JLabel indexTextLabel = new JLabel((selectedEnemyIndex + 1) + "/" + enemyList.size());
        enemyNameLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
        enemyImageLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
        indexTextLabel.setAlignmentX(Box.CENTER_ALIGNMENT);

        enemiesMainPanel.add(Box.createVerticalGlue());
        enemiesMainPanel.add(enemyNameLabel);
        enemiesMainPanel.add(enemyImageLabel);
        enemiesMainPanel.add(indexTextLabel);
        enemiesMainPanel.add(Box.createVerticalGlue());
    }

    // EFFECTS: Returns the image of the given enemy and restricts the size to a maximum of 150 x 150 pixels
    //          If the image cannot be found, returns a 150 x 150 black square
    private Image getEnemyImage(Enemy enemy) {
        Image image;
        try {
            BufferedImage originalImage = ImageIO.read(new File(enemy.getImageFile()));
            int height = originalImage.getHeight();
            int width = originalImage.getWidth();

            if (height > 150 && height > width) {
                image = originalImage.getScaledInstance(-1, 150, Image.SCALE_SMOOTH);
            } else if (width > 150) {
                image = originalImage.getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            } else {
                image = originalImage;
            }
        } catch (IOException e) {
            image = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
        }
        return image;
    }

    // for debugging
    private void giveBorder(JPanel titlePanel) {
        Border border = BorderFactory.createLineBorder(Color.BLACK);
      //  titlePanel.setBorder(border);
    }

    private JButton getDefaultButton(Action action, String label) {
        JButton button = new JButton(action);
        button.setText(label);
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Courier", Font.PLAIN, 20));
        button.setMargin(new Insets(7, 30, 7, 30));
        button.setVisible(true);
        return button;
    }

    // EFFECTS: Places commas, spaces, and "and" between each string properly
    public String placeCommaOrAndInBetweenStrings(java.util.List<String> listOfString) {
        return placeCommaOrAndInBetweenStringsHelper(listOfString, true);
    }

    // EFFECTS: Places commas, spaces, and "and" between each string properly (with isFirstRecursion parameter)
    private String placeCommaOrAndInBetweenStringsHelper(List<String> listOfString, boolean isFirstRecursion) {
        String result = "";
        int listSize = listOfString.size();
        if (listSize == 1) {
            result = listOfString.get(0);
        } else if (listSize == 2) {
            if (isFirstRecursion) {
                result = result.concat(listOfString.get(0) + " and " + listOfString.get(1));
            } else {
                result = result.concat(listOfString.get(0) + ", and " + listOfString.get(1));
            }
        } else if (listSize > 2) {
            result = result.concat(listOfString.remove(0) + ", "
                    + placeCommaOrAndInBetweenStringsHelper(listOfString, false));
        }
        return result;
    }
}
