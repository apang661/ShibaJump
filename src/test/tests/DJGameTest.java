package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameWindow;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DJGameTest {
    private DJGame game;

    @BeforeEach
    void setUp() {
        game = new DJGame();
    }

    @Test
    void testSetCharacter() {
        Player testPlayer = new Player(Player.PlayableCharacter.DOGE);
        game.setCharacter(testPlayer.getName());

        Player player = game.getPlayer();
        assertEquals(testPlayer.getName(), player.getName());
        assertEquals(testPlayer.getCurrentHealth(), player.getCurrentHealth());
        assertEquals(testPlayer.getWidth(), player.getWidth());
        assertEquals(testPlayer.getHeight(), player.getHeight());
        assertEquals(testPlayer.getName(), game.getAccount().getSelectedCharacter());
    }

    @Test
    void testGetPlayer() {
        Player player = new Player(Player.PlayableCharacter.DOGE);
        game.setPlayer(player);

        assertEquals(player, game.getPlayer());
    }

    @Test
    void testGetStage() {
        Stage emptyStage = new Stage();
        game.setStage(emptyStage);

        assertEquals(emptyStage, game.getStage());
    }

    @Test
    void testGetAccount() {
        Account account = new Account();
        account.setUsername("dogeplayer");

        game.setAccount(account);
        assertEquals(account, game.getAccount());
        assertEquals("dogeplayer", game.getAccount().getUsername());
    }

    @Test
    void testIsPlaying() {
        game.setPlaying(true);
        assertTrue(game.isPlaying());

        game.setPlaying(false);
        assertFalse(game.isPlaying());
    }

    @Test
    void testIsGameOver() {
        game.setGameOver(true);
        assertTrue(game.isGameOver());

        game.setGameOver(false);
        assertFalse(game.isGameOver());
    }

    @Test
    void testAddAndClearProjectiles() {
        assertEquals(0, game.getProjectiles().size());
        Projectile p0 = new Projectile("player", 10, 10, 0, 0, 0, 0);
        Projectile p1 = new Projectile("enemy", 10, 10, 0, 0, 0, 0);

        List<Projectile> addedList = new ArrayList<>();
        addedList.add(p0);
        addedList.add(p1);

        game.addProjectiles(addedList);
        List<Projectile> gameProjectiles = game.getProjectiles();
        assertEquals(p0, gameProjectiles.get(0));
        assertEquals(p1, gameProjectiles.get(1));

        game.clearProjectiles();
        assertEquals(0, game.getProjectiles().size());
    }

    @Test
    void testKeyPressedAndReleasedA() {
        Set<Integer> keyCodesHeldDown = game.getKeyCodesHeldDown();

        assertTrue(keyCodesHeldDown.isEmpty());
        game.keyPressed(KeyEvent.VK_A);

        assertTrue(keyCodesHeldDown.contains(KeyEvent.VK_A));
        assertEquals(1, keyCodesHeldDown.size());

        game.keyReleased(KeyEvent.VK_A);
        assertFalse(keyCodesHeldDown.contains(KeyEvent.VK_A));
        assertEquals(0, keyCodesHeldDown.size());
    }

    @Test
    void testKeyPressedAndReleasedD() {
        Set<Integer> keyCodesHeldDown = game.getKeyCodesHeldDown();

        assertTrue(keyCodesHeldDown.isEmpty());
        game.keyPressed(KeyEvent.VK_D);

        assertTrue(keyCodesHeldDown.contains(KeyEvent.VK_D));
        assertEquals(1, keyCodesHeldDown.size());

        game.keyReleased(KeyEvent.VK_D);
        assertFalse(keyCodesHeldDown.contains(KeyEvent.VK_D));
        assertEquals(0, keyCodesHeldDown.size());
    }

    @Test
    void testKeyReleasedNotRegistered() {
        Set<Integer> keyCodesHeldDown = game.getKeyCodesHeldDown();

        assertTrue(keyCodesHeldDown.isEmpty());
        game.keyPressed(KeyEvent.VK_D);
        game.keyPressed(KeyEvent.VK_A);

        assertTrue(keyCodesHeldDown.contains(KeyEvent.VK_A));
        assertTrue(keyCodesHeldDown.contains(KeyEvent.VK_D));
        assertEquals(2, keyCodesHeldDown.size());

        game.keyReleased(0);
        assertTrue(keyCodesHeldDown.contains(KeyEvent.VK_D));
        assertTrue(keyCodesHeldDown.contains(KeyEvent.VK_A));
        assertEquals(2, keyCodesHeldDown.size());
    }

    @Test
    void testKeyPressedP() {
        assertFalse(game.isPlaying());

        game.keyPressed(KeyEvent.VK_P);
        assertTrue(game.isPlaying());

        game.keyPressed(KeyEvent.VK_P);
        assertFalse(game.isPlaying());
    }

    @Test
    void testKeyPressedKNotGameOverIsPlaying() {
        game.setPlaying(true);
        game.setGameOver(false);

        assertEquals(0, game.getProjectiles().size());
        game.keyPressed(KeyEvent.VK_K);
        assertEquals(1, game.getProjectiles().size());
    }

    @Test
    void testKeyPressedKGameOverIsPlaying() {
        game.setPlaying(true);
        game.setGameOver(true);

        assertEquals(0, game.getProjectiles().size());
        game.keyPressed(KeyEvent.VK_K);
        assertEquals(0, game.getProjectiles().size());
    }

    @Test
    void testKeyPressedKGameOverNotPlaying() {
        game.setPlaying(false);
        game.setGameOver(true);

        assertEquals(0, game.getProjectiles().size());
        game.keyPressed(KeyEvent.VK_K);
        assertEquals(0, game.getProjectiles().size());
    }

    @Test
    void testKeyPressedKNotGameOverNotPlaying() {
        game.setPlaying(false);
        game.setGameOver(false);

        assertEquals(0, game.getProjectiles().size());
        game.keyPressed(KeyEvent.VK_K);
        assertEquals(0, game.getProjectiles().size());
    }

    @Test
    void testKeyPressedNotRegistered() {
        game.setPlaying(true);
        game.setGameOver(true);

        game.keyPressed(0);
        assertEquals(0, game.getProjectiles().size());
        assertTrue(game.getKeyCodesHeldDown().isEmpty());

        game.setPlaying(false);
        game.setGameOver(true);

        game.keyPressed(0);
        assertEquals(0, game.getProjectiles().size());
        assertTrue(game.getKeyCodesHeldDown().isEmpty());

        game.setPlaying(true);
        game.setGameOver(false);

        game.keyPressed(0);
        assertEquals(0, game.getProjectiles().size());
        assertTrue(game.getKeyCodesHeldDown().isEmpty());

        game.setPlaying(false);
        game.setGameOver(false);

        game.keyPressed(0);
        assertEquals(0, game.getProjectiles().size());
        assertTrue(game.getKeyCodesHeldDown().isEmpty());
    }


    // Most of the functionality of update() is tested in by the tests of its method calls
    @Test
    void testUpdateIsPlayingAndNotGameOver() {
        game.setPlaying(true);
        game.setGameOver(false);

        testPlayerUpdate(true);
    }

    @Test
    void testUpdateIsPlayingAndIsGameOver() {
        game.setPlaying(true);
        game.setGameOver(true);

        testPlayerUpdate(false);
    }

    @Test
    void testUpdateNotPlaying() {
        game.setPlaying(false);

        testPlayerUpdate(false);
    }

    private void testPlayerUpdate(boolean bool) {
        Player player = game.getPlayer();
        game.keyPressed(KeyEvent.VK_D);
        int prevX = player.getCoordX();

        double conversionFactor = (double) GameWindow.UPDATE_INTERVAL / 1000;
        game.update();

        if (bool) {
            assertEquals(prevX + Math.round((double) player.getMaxDx() * conversionFactor),
                    player.getCoordX());
        } else {
            assertNotEquals(prevX + Math.round((double) player.getMaxDx() * conversionFactor),
                    player.getCoordX());
        }
    }

    @Test
    void testHandlePlayerCollisionsWithProjectile() {
        Player player = game.getPlayer();

        game.setGameOver(false);
        game.setPlaying(true);

        game.clearProjectiles();
        game.addProjectiles(Collections.singletonList(
                new Projectile("enemy", 10, 10, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0)));
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2);
        player.setCurrentHealth(2);

        game.handlePlayerCollisions();
        assertEquals(1, player.getCurrentHealth());
        assertFalse(game.isGameOver());

        game.handlePlayerCollisions();
        assertEquals(0, player.getCurrentHealth());
        assertTrue(game.isGameOver());
    }

    @Test
    void testHandlePlayerCollisionsWithRegularEnemy() {
        testHandlePlayerCollisionsWithEnemy("Cat");
    }

    @Test
    void testHandlePlayerCollisionsWithBossEnemy() {
        testHandlePlayerCollisionsWithEnemy("Evil Cat");
    }

    @Test
    void testHandlePlayerCollisionsWithNothing() {
        game.setGameOver(false);
        game.handlePlayerCollisions();
        game.getPlayer().setCurrentHealth(1);
        assertEquals(1, game.getPlayer().getCurrentHealth());
        assertFalse(game.isGameOver());
    }

    private void testHandlePlayerCollisionsWithEnemy(String s) {
        Player player = game.getPlayer();
        game.getStage().addEnemy(s, Stage.WIDTH / 2, Stage.HEIGHT / 2);

        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2);
        player.setCurrentHealth(2);

        game.handlePlayerCollisions();
        assertEquals(1, player.getCurrentHealth());
        assertFalse(game.isGameOver());

        game.handlePlayerCollisions();
        assertEquals(0, player.getCurrentHealth());
        assertTrue(game.isGameOver());
    }

    @Test
    void testHandleEnemyCollisions() {
        Stage stage = game.getStage();
        stage.addEnemy("Cat", Stage.WIDTH / 2, Stage.HEIGHT / 2, 2);

        game.addProjectiles(Collections.singletonList(
                new Projectile("player", 10, 10,
                        0, 0, 0, 0)));
        game.handleEnemyCollisions(stage.getRegularEnemies());
        assertEquals(2, stage.getRegularEnemies().get(0).getCurrentHealth());

        game.addProjectiles(Collections.singletonList(
                new Projectile("player", 10, 10,
                        Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0)));
        game.handleEnemyCollisions(stage.getRegularEnemies());
        assertEquals(1, stage.getRegularEnemies().get(0).getCurrentHealth());

        game.addProjectiles(Collections.singletonList(
                new Projectile("player", 10, 10,
                        Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0)));
        game.handleEnemyCollisions(stage.getRegularEnemies());
        assertTrue(stage.getRegularEnemies().isEmpty());
    }

    @Test
    void testUpdatePlayerFallingOnPlatform() {
        game.getStage().addPlatform(100, 10, Stage.WIDTH / 2, Stage.HEIGHT / 2);

        Player player = game.getPlayer();
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        player.setDy(-1);

        game.updatePlayer();

        double conversionFactor = (double) GameWindow.UPDATE_INTERVAL / 1000;
        assertEquals((int) (player.getDyForJump() + Stage.GRAVITY_ACCELERATION * conversionFactor),
                player.getDy());
    }

    @Test
    void testUpdatePlayerNotFallingOnPlatform() {
        game.getStage().addPlatform(100, 10, Stage.WIDTH / 2, Stage.HEIGHT / 2);

        Player player = game.getPlayer();
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        player.setDy(1);

        game.updatePlayer();

        double conversionFactor = (double) GameWindow.UPDATE_INTERVAL / 1000;
        assertEquals((int) (1 + Stage.GRAVITY_ACCELERATION * conversionFactor),
                player.getDy());
    }

    @Test
    void testUpdateProjectilesReachedMaxDistance() {
        Projectile p0 = new Projectile("player", 10, 10,
                Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        p0.setDistanceTravelled(Projectile.MAX_DISTANCE);
        game.addProjectiles(Collections.singletonList(p0));

        game.updateProjectiles();
        assertTrue(game.getProjectiles().isEmpty());
    }

    @Test
    void testUpdateProjectilesNotReachedMaxDistance() {
        Projectile p0 = new Projectile("player", 10, 10,
                Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        game.addProjectiles(Collections.singletonList(p0));

        game.updateProjectiles();
        assertEquals(1, game.getProjectiles().size());
    }

    @Test
    void testUpdateEnemy() {
        game.getStage().addEnemy("Cat", 0, 0);
        game.getStage().addEnemy("Evil Cat", 0, 0);

        game.updateEnemies();
    }
}