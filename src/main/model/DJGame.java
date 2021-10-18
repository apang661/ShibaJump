package model;

import model.bossenemies.*;
import model.regularenemies.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Represents a game state of Doge Jump
 */
public class DJGame {
    // List of all the subclasses of RegularEnemy
    public static final List<RegularEnemy> REGULAR_ENEMIES = Arrays.asList(
            new RegularCat(),
            new RegularRat());
    // List of all the subclasses of BossEnemy
    public static final List<BossEnemy> BOSS_ENEMIES = Arrays.asList(
            new BossCat());

    private Account account;
    private Player player;
    private Stage curStage;
    private Stage savedStage;
    private List<Projectile> projectiles;

    // EFFECTS: Creates a new DJGame with the first character ("Doge")
    public DJGame() {
        this.account = new Account();
        this.player = new Player(PlayableCharacter.DOGE);
        this.curStage = new Stage();
        this.projectiles = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // MODIFIES: this
    // EFFECTS: Sets player to character with String name if found in CHARACTERS; otherwise do nothing
    public void setCharacter(String name) {
        for (PlayableCharacter character: PlayableCharacter.values()) {
            if (character.getName().equals(name)) {
                player = new Player(character);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Stage getCurStage() {
        return curStage;
    }

    public void setSavedStage(Stage savedStage) {
        this.savedStage = savedStage;
    }

    // MODIFIES: this
    // EFFECTS: Loads the saved stage to the current stage
    public void loadStage() {
        curStage = savedStage;
    }
}
