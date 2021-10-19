package tests;

import model.*;
import model.bossenemies.BossCat;
import model.regularenemies.RegularCat;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This class references CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest {
    JsonReader reader;

    @Test
    void testLoadToAccountNonExistentFile() {
        reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.loadToAccount();
            fail("IOException expected.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testLoadToAccountTestFileNew() {
        reader = new JsonReader("./data/testReadFileNew.json");
        try {
            DJGame game = reader.loadToAccount();
            Account account = game.getAccount();
            assertEquals("", account.getUsername());
            assertEquals(0, account.getDogePoints());
            assertEquals("Doge", account.getSelectedCharacter());
            assertEquals(0, account.getNextStageNum());
            testParseEncounteredEnemiesTestFileNew(account.getEncounteredEnemies());
        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    private void testParseEncounteredEnemiesTestFileNew(EnemyList encounteredEnemies) {
        assertEquals(0, encounteredEnemies.size());
    }

    @Test
    void testLoadToAccountTestFile0() {
        reader = new JsonReader("./data/testReadFile0.json");
        try {
            DJGame game = reader.loadToAccount();
            Account account = game.getAccount();
            assertEquals("dogelover", account.getUsername());
            assertEquals(10000, account.getDogePoints());
            assertEquals("Walter", account.getSelectedCharacter());
            assertEquals(2, account.getNextStageNum());
            testParseEncounteredEnemiesTestFile0(account.getEncounteredEnemies());
        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    private void testParseEncounteredEnemiesTestFile0(EnemyList encounteredEnemies) {
        assertEquals(3, encounteredEnemies.size());
        assertTrue(encounteredEnemies.containsEnemy("Cat"));
        assertTrue(encounteredEnemies.containsEnemy("Evil Cat"));
        assertTrue(encounteredEnemies.containsEnemy("Rat"));
    }

    @Test
    void testLoadToGameNonExistentFile() {
        reader = new JsonReader("./data/noSuchFile.json");
        try {
            DJGame game = new DJGame();
            reader.loadToGame(game);
            fail("IOException expected.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testLoadToGameTestFileNew() {
        reader = new JsonReader("./data/testReadFileNew.json");
        try {
            DJGame game = new DJGame();
            reader.loadToGame(game);

            Player player = game.getPlayer();
            Stage stage = game.getStage();
            List<Projectile> projectiles = game.getProjectiles();

            testParsePlayerTestFileNew(player);
            testParseStageTestFileNew(stage);
            testParseProjectilesTestNew(projectiles);

        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    private void testParsePlayerTestFileNew(Player player) {
        PlayableCharacter pc = PlayableCharacter.DOGE;
        assertEquals(pc.getName(), player.getName());
        assertEquals(pc.getMaxHealth(), player.getCurrentHealth());
        assertEquals(pc.getWidth(), player.getWidth());
        assertEquals(pc.getHeight(), player.getHeight());
        assertEquals(pc.getDx(), player.getDx());
        assertEquals(0, player.getDy());
    }

    private void testParseStageTestFileNew(Stage stage) {
        assertEquals(0, stage.getStageNum());
        assertFalse(stage.isBossStage());
        assertEquals(0, stage.getRegularEnemies().size());
        assertEquals(0, stage.getBossEnemies().size());
        assertEquals(0, stage.getPlatforms().size());
    }

    private void testParseProjectilesTestNew(List<Projectile> projectiles) {
        assertEquals(0, projectiles.size());
    }

    @Test
    void testLoadToGameTestFile0() {
        reader = new JsonReader("./data/testReadFile0.json");
        try {
            DJGame game = new DJGame();
            reader.loadToGame(game);

            Player player = game.getPlayer();
            Stage stage = game.getStage();
            List<Projectile> projectiles = game.getProjectiles();

            testParsePlayerTestFile0(player);
            testParseStageTestFile0(stage);
            testParseProjectilesTestFile0(projectiles);

        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    private void testParsePlayerTestFile0(Player player) {
        assertEquals("Walter", player.getName());
        assertEquals(5, player.getCurrentHealth());
        assertEquals(PlayableCharacter.WALTER.getWidth(), player.getWidth());
        assertEquals(PlayableCharacter.WALTER.getHeight(), player.getHeight());
        assertEquals(20, player.getDx());
        assertEquals(2, player.getDy());
    }

    private void testParseStageTestFile0(Stage stage) {
        assertEquals(2, stage.getStageNum());
        assertFalse(stage.isBossStage());

        testParseRegularEnemiesTestFile0(stage);
        testParseBossEnemiesTestFile0(stage);
        testParsePlatformsTestFile0(stage);
    }

    private void testParseRegularEnemiesTestFile0(Stage stage) {
        assertEquals(3, stage.getRegularEnemies().size());
        Enemy enemy0 = stage.getRegularEnemies().get(0);
        assertEquals("Cat", enemy0.getName());
        assertEquals(RegularCat.WIDTH, enemy0.getWidth());
        assertEquals(RegularCat.HEIGHT, enemy0.getHeight());
        assertEquals(3, enemy0.getCurrentHealth());
        assertEquals(40, enemy0.getCoordX());
        assertEquals(50, enemy0.getCoordY());
    }

    private void testParseBossEnemiesTestFile0(Stage stage) {
        assertEquals(1, stage.getBossEnemies().size());
        Enemy enemy1 = stage.getBossEnemies().get(0);
        assertEquals("Evil Cat", enemy1.getName());
        assertEquals(BossCat.WIDTH, enemy1.getWidth());
        assertEquals(BossCat.HEIGHT, enemy1.getHeight());
        assertEquals(30, enemy1.getCurrentHealth());
        assertEquals(500, enemy1.getCoordX());
        assertEquals(300, enemy1.getCoordY());
    }

    private void testParsePlatformsTestFile0(Stage stage) {
        assertEquals(2, stage.getPlatforms().size());
        Platform platform = stage.getPlatforms().get(0);
        assertEquals(100, platform.getWidth());
        assertEquals(35, platform.getHeight());
        assertEquals(10, platform.getCoordX());
        assertEquals(90, platform.getCoordY());
    }

    private void testParseProjectilesTestFile0(List<Projectile> projectiles) {
        assertEquals(2, projectiles.size());

        Projectile projectile0 = projectiles.get(0);
        Projectile projectile1 = projectiles.get(1);

        assertEquals("player", projectile0.getType());
        assertEquals(10, projectile0.getWidth());
        assertEquals(50, projectile0.getHeight());
        assertEquals(90, projectile0.getCoordX());
        assertEquals(80, projectile0.getCoordY());
        assertEquals(0, projectile0.getDx());
        assertEquals(5, projectile0.getDy());

        assertEquals("enemy", projectile1.getType());
        assertEquals(10, projectile1.getWidth());
        assertEquals(40, projectile1.getHeight());
        assertEquals(100, projectile1.getCoordX());
        assertEquals(180, projectile1.getCoordY());
        assertEquals(1, projectile1.getDx());
        assertEquals(-4, projectile1.getDy());
    }
}
