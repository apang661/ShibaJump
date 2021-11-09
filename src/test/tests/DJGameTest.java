package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

    @Test
    void testCheckCollisions() {

    }

    @Test
    void testCheckEnemyCollisions() {

    }

    @Test
    void testUpdatePlayer() {

    }

    @Test
    void testUpdateProjectiles() {

    }

    @Test
    void testUpdateEnemy() {

    }
}