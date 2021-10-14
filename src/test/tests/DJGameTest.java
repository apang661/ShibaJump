package tests;

import model.DJGame;
import model.Enemy;
import model.PlayableCharacter;
import model.Player;
import model.stages.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DJGameTest {
    DJGame game;

    @BeforeEach
    void setUp() {
        game = new DJGame();
    }

    @Test
    void testSetCharacter() {
        PlayableCharacter char0 = DJGame.CHARACTERS.get(1);
        game.setCharacter(DJGame.CHARACTERS.get(1).getName());

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
    void testAddEncounteredEnemyEnemyParam() {
        Enemy regCat = DJGame.REGULAR_ENEMIES.get(0);
        Enemy bossCat = DJGame.BOSS_ENEMIES.get(0);
        game.addEncounteredEnemy(regCat);
        game.addEncounteredEnemy(bossCat);

        assertEquals(2, game.getEncounteredEnemies().size());
        assertTrue(game.getEncounteredEnemies().containsEnemy(regCat));
        assertTrue(game.getEncounteredEnemies().containsEnemy(bossCat));
    }

    @Test
    void testAddEncounteredEnemyStringParamFound() {
        Enemy regCat = DJGame.REGULAR_ENEMIES.get(0);
        Enemy bossCat = DJGame.BOSS_ENEMIES.get(0);
        game.addEncounteredEnemy(regCat.getName());
        game.addEncounteredEnemy(bossCat.getName());

        assertEquals(2, game.getEncounteredEnemies().size());
        assertTrue(game.getEncounteredEnemies().containsEnemy(regCat));
        assertTrue(game.getEncounteredEnemies().containsEnemy(bossCat));

        game.addEncounteredEnemy(regCat.getName());
        game.addEncounteredEnemy(bossCat.getName());

        assertEquals(2, game.getEncounteredEnemies().size());
    }

    @Test
    void testAddEncounteredEnemyStringParamNotFound() {
        game.addEncounteredEnemy("Unknown Enemy 1313");
        game.addEncounteredEnemy("Unknown Enemy 1305");

        assertEquals(0, game.getEncounteredEnemies().size());
    }

    @Test
    void testGetRegularEnemyNames() {
        List<String> listOfRegularEnemies = new ArrayList<>();
        for (Enemy enemy: DJGame.REGULAR_ENEMIES) {
            listOfRegularEnemies.add(enemy.getName());
        }
        assertEquals(listOfRegularEnemies, game.getRegularEnemyNames());
        assertEquals(DJGame.REGULAR_ENEMIES.size(), game.getRegularEnemyNames().size());
    }

    @Test
    void testGetBossEnemyNames() {
        List<String> listOfBossEnemies = new ArrayList<>();
        for (Enemy enemy: DJGame.BOSS_ENEMIES) {
            listOfBossEnemies.add(enemy.getName());
        }
        assertEquals(listOfBossEnemies, game.getBossEnemyNames());
        assertEquals(DJGame.BOSS_ENEMIES.size(), game.getBossEnemyNames().size());
    }

    @Test
    void testGetStage() {
        Stage emptyStage = new Stage();
        assertEquals(emptyStage.getPlatforms(), game.getStage().getPlatforms());
    }
}