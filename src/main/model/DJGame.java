package model;

import model.stages.Stage;

import java.util.Arrays;
import java.util.List;

/*
 * Represents a game state of Doge Jump
 */
public class DJGame {
    public static final List<PlayableCharacter> CHARACTERS = Arrays.asList(
            new PlayableCharacter(5, "Doge", 300, 400),
            new PlayableCharacter(4, "Walter", 250, 350));

    private Player player;
    private String username;
    private int dogePoints;
    private Stage stage;
    private EnemyList encounteredEnemies;

    // EFFECTS: Creates a new DJGame with the first character ("Doge")
    public DJGame() {
        this.player = new Player(CHARACTERS.get(0));
        this.stage = new Stage();
        this.dogePoints = 0;
    }

    // REQUIRES: name is contained the names of CHARACTERS
    // MODIFIES: this
    // EFFECTS: Sets player to character with String name
    public void setCharacter(String name) {
        // stub
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getUsername() {
        return username;
    }

    public void setDogePoints(int dogePoints) {
        this.dogePoints = dogePoints;
    }

    public int getDogePoints() {
        return dogePoints;
    }

    public Player getPlayer() {
        return player;
    }
}
