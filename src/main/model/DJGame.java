package model;

import model.bossenemies.BossCat;
import model.bossenemies.BossEnemy;
import model.regularenemies.RegularCat;
import model.regularenemies.RegularEnemy;
import model.regularenemies.RegularRat;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.event.KeyEvent;
import java.util.*;

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
    private Set<Integer> keyCodesHeldDown;
    private boolean isPlaying;
    private boolean isGameOver;

    // EFFECTS: Creates a new DJGame with the first character ("Doge")
    public DJGame() {
        this.account = new Account();
        this.player = new Player(PlayableCharacter.DOGE);
        this.stage = new Stage();
        this.projectiles = new ArrayList<>();
        this.keyCodesHeldDown = new HashSet<>();
        this.isPlaying = false;
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
                account.setSelectedCharacter(name);
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

    public void setPlaying(boolean playing) {
        isPlaying = playing;
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

    // MODIFIES: this
    // EFFECTS: Adds key code to the list of keys pressed if it is the A or D key
    public void keyPressed(Integer keyCode) {
        if ((keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_D)) {
            keyCodesHeldDown.add(keyCode);
        } else if (keyCode == KeyEvent.VK_P) {
            isPlaying = !isPlaying;
        } else if (keyCode == KeyEvent.VK_K && isPlaying && !isGameOver) {
            shoot();
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the key code from the list of key pressed if it is the A or D key
    public void keyReleased(Integer keyCode) {
        if ((keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_D)) {
            keyCodesHeldDown.remove(keyCode);
        }
    }

    // MODIFIES: this
    // EFFECTS: Shoots a player-type projectile upwards
    public void shoot() {
        List<Projectile> projectiles = new ArrayList<>();
        projectiles.add(new Projectile("player", 10, 10,
                player.getCoordX(), player.getCoordY(), 0, 600));
        addProjectiles(projectiles);
    }

    // MODIFIES: this
    // EFFECTS: Updates the game to its next game state if it is not paused
    public void update() {
        if (isPlaying && !isGameOver) {
            checkCollisions();

            updatePlayer();
            updateProjectiles();
            updateEnemies();
        }
    }

    // MODIFIES: enemies
    // EFFECTS: Checks and
    private void checkCollisions() {
        player.checkCollisionWithAnyEnemyProjectile(projectiles);
        if (player.checkCollisionWithAnyEnemy(stage.getRegularEnemies())
                || player.checkCollisionWithAnyEnemy(stage.getBossEnemies())) {
            player.setCurrentHealth(player.getCurrentHealth() - 1);
            isGameOver = true;
        }


        checkEnemyCollisions(stage.getRegularEnemies());
        checkEnemyCollisions(stage.getBossEnemies());
    }


    // MODIFIES: enemies
    // EFFECTS: Updates the given list of enemies based on its collisions with player projectiles
    private void checkEnemyCollisions(List<Enemy> enemies) {
        List<Enemy> deadEnemies = new ArrayList<>();
        for (Enemy e: enemies) {
            if (e.checkCollisionWithAnyPlayerProjectile(projectiles)) {
                e.setCurrentHealth(e.getCurrentHealth() - 1);
                if (e.getCurrentHealth() <= 0) {
                    deadEnemies.add(e);
                }
            }
        }
        enemies.removeAll(deadEnemies);
    }

    // MODIFIES: this
    // EFFECTS: Updates the player to its next player state
    private void updatePlayer() {
        player.setDirectionByKeyCodesHeldDown(keyCodesHeldDown);
        if (stage.isPlayerFallingOnAnyPlatform(player)) {
            player.jump();
        }

        player.updatePositionAndVelocity();
    }

    // MODIFIES: this
    // EFFECTS: Updates this game's projectiles to their next state
    private void updateProjectiles() {
        List<Projectile> deadProjectiles = new ArrayList<>();
        for (Projectile p: projectiles) {
            p.updatePositionAndVelocity();
            if (p.getDistanceTravelled() > Projectile.MAX_DISTANCE) {
                deadProjectiles.add(p);
                System.out.println("removed");
            }
        }
        projectiles.removeAll(deadProjectiles);
    }


    // MODIFIES: this
    // EFFECTS: Updates the enemies in this game's stage to their next state
    private void updateEnemies() {
    }

}
