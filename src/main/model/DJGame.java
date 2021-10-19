package model;

import model.bossenemies.BossCat;
import model.bossenemies.BossEnemy;
import model.regularenemies.RegularCat;
import model.regularenemies.RegularEnemy;
import model.regularenemies.RegularRat;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Represents a game state of Doge Jump
 */
public class DJGame implements Writable {
    // List of all the subclasses of RegularEnemy
    public static final List<RegularEnemy> REGULAR_ENEMIES = Arrays.asList(
            new RegularCat(),
            new RegularRat());
    // List of all the subclasses of BossEnemy
    public static final List<BossEnemy> BOSS_ENEMIES = Arrays.asList(
            new BossCat());

    private Account account;
    private Player player;
    private Stage stage;
    private List<Projectile> projectiles;

    // EFFECTS: Creates a new DJGame with the first character ("Doge")
    public DJGame() {
        this.account = new Account();
        this.player = new Player(PlayableCharacter.DOGE);
        this.stage = new Stage();
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given list of projectiles to the list of projectiles in this game
    public void addProjectiles(List<Projectile> projectiles) {
        this.projectiles.addAll(projectiles);
    }

    // MODIFIES: this
    // EFFECTS: Clears the list of projectiles in this game
    public void clearProjectiles() {
        this.projectiles = new ArrayList<>();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject =  new JSONObject();
        JSONArray projectilesJsonArray = new JSONArray();

        for (Projectile projectile: projectiles) {
            projectilesJsonArray.put(projectile.toJson());
        }

        jsonObject.put("account", account.toJson());
        jsonObject.put("player", player.toJson());
        jsonObject.put("stage", stage.toJson());
        jsonObject.put("projectiles", projectilesJsonArray);

        return jsonObject;
    }
}
