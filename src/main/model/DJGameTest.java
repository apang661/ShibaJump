package model;

import model.bossenemies.BossCat;
import model.regularenemies.RegularCat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DJGameTest {
    DJGame game;

    @BeforeEach
    void setUp() {
        game = new DJGame();
    }

    @Test
    void testSetCharacter() {
        PlayableCharacter char0 = DJGame.CHARACTERS.get(0);
        game.setCharacter(DJGame.CHARACTERS.get(0).getName());

        Player player = game.getPlayer();
        assertEquals(char0.getName(), player.getName());
        assertEquals(char0.getMaxHealth(), player.getCurrentHealth());
        assertEquals(char0.getWidth(), player.getWidth());
        assertEquals(char0.getHeight(), player.getHeight());
    }

    @Test
    void testSetUsername() {
        game.setUsername("test");
        assertEquals("test", game.getUsername());
    }

    @Test
    void testSetDogePoints() {
        game.setDogePoints(100);
        assertEquals(100, game.getDogePoints());
    }

    @Test
    void testAddEncounteredEnemy() {
        Enemy regCat = new RegularCat();
        Enemy bossCat = new BossCat();
        game.addEncounteredEnemy(regCat);
        game.addEncounteredEnemy(bossCat);

        assertEquals(2, game.getEncounteredEnemies().size());
        assertTrue(game.getEncounteredEnemies().containsEnemy(regCat));
        assertTrue(game.getEncounteredEnemies().containsEnemy(bossCat));
    }

    void testGetStage() {}
}