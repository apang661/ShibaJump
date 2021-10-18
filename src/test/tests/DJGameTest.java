package tests;

import model.*;
import model.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DJGameTest {
    private DJGame game;

    @BeforeEach
    void setUp() {
        game = new DJGame();
    }

    @Test
    void testSetCharacter() {
        PlayableCharacter char0 = PlayableCharacter.values()[0];
        game.setCharacter(char0.getName());

        Player player = game.getPlayer();
        assertEquals(char0.getName(), player.getName());
        assertEquals(char0.getMaxHealth(), player.getCurrentHealth());
        assertEquals(char0.getWidth(), player.getWidth());
        assertEquals(char0.getHeight(), player.getHeight());
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