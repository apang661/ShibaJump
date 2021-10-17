package model;

import model.bossenemies.*;
import model.regularenemies.*;
import model.stages.Stage;

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

    private Player player;
    private String username;
    private int dogePoints;
    private Stage stage;
    private EnemyList encounteredEnemies;
    private List<Projectile> projectiles;

    // EFFECTS: Creates a new DJGame with the first character ("Doge")
    public DJGame() {
        this.player = new Player(PlayableCharacter.DOGE);
        this.stage = new Stage();
        this.dogePoints = 0;
        this.encounteredEnemies = new EnemyList();
        this.projectiles = new ArrayList<>();
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

    public Stage getStage() {
        return stage;
    }

    public EnemyList getEncounteredEnemies() {
        return encounteredEnemies;
    }

    // EFFECTS: Return the list of names of enemies in REGULAR_ENEMIES
    public List<String> getRegularEnemyNames() {
        List<String> listOfRegEnemies = new ArrayList<>();
        for (Enemy enemy: REGULAR_ENEMIES) {
            listOfRegEnemies.add(enemy.getName());
        }
        return listOfRegEnemies;
    }

    // EFFECTS: Return the list of names of enemies in BOSS_ENEMIES
    public List<String> getBossEnemyNames() {
        List<String> listOfBossEnemies = new ArrayList<>();
        for (Enemy enemy: BOSS_ENEMIES) {
            listOfBossEnemies.add(enemy.getName());
        }
        return listOfBossEnemies;
    }

    public void addEncounteredEnemy(Enemy enemy) {
        encounteredEnemies.addEnemy(enemy);
    }

    // MODIFIES: this
    // EFFECTS: If the given name is one of the regular or boss enemies in the game AND is not already in this list,
    //          add that enemy to this list; otherwise do nothing
    public void addEncounteredEnemy(String name) {
        encounteredEnemies.addEnemy(name);
    }
}
