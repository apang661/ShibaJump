package persistence;

// This class references CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private JsonWriter writer;
    private JsonReader reader;

    @Test
    void testWriteNonExistentFile() {
        writer = new JsonWriter("./data/\0ImpossibleFile.json");
        try {
            writer.open();
            fail("IO Exception expected.");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriteFile0() {
        DJGame writtenGame = setupGameFile0();

        writer = new JsonWriter("./data/testWriteFile0.json");
        reader = new JsonReader("./data/testWriteFile0.json");

        try {
            writer.open();
            writer.write(writtenGame);
            writer.close();

            DJGame readGame = reader.loadToAccount();
            reader.loadToGame(readGame);
            Account readAccount = readGame.getAccount();
            Player readPlayer = readGame.getPlayer();
            Stage stage = readGame.getStage();
            List<Projectile> projectiles = readGame.getProjectiles();

            testWriteAccountFile0(readAccount);
            testWritePlayerFile0(readPlayer);
            testWriteStageFile0(stage);
            testWriteProjectilesFile0(projectiles);
        } catch (IOException e) {
            fail("Unexpected exception thrown.");
        }
    }

    private void testWriteProjectilesFile0(List<Projectile> projectiles) {
        Projectile projectile0 = projectiles.get(0);
        Projectile projectile1 = projectiles.get(1);

        assertEquals("player", projectile0.getType());
        assertEquals(2, projectile0.getWidth());
        assertEquals(3, projectile0.getHeight());
        assertEquals(4, projectile0.getCoordX());
        assertEquals(5, projectile0.getCoordY());
        assertEquals(6, projectile0.getDx());
        assertEquals(7, projectile0.getDy());

        assertEquals("enemy", projectile1.getType());
        assertEquals(3, projectile1.getWidth());
        assertEquals(4, projectile1.getHeight());
        assertEquals(5, projectile1.getCoordX());
        assertEquals(6, projectile1.getCoordY());
        assertEquals(7, projectile1.getDx());
        assertEquals(8, projectile1.getDy());
    }

    private void testWriteStageFile0(Stage stage) {
        Platform platform = stage.getPlatforms().get(1);

        assertEquals(1, stage.getStageNum());
        assertTrue(stage.isBossStage());

        assertEquals(2, stage.getPlatforms().size());
        assertTrue(stage.containsPlatformAtY(60));
        assertEquals(20, platform.getWidth());
        assertEquals(40, platform.getHeight());
        assertEquals(100, platform.getCoordX());
        assertEquals(120, platform.getCoordY());

        List<Enemy> regularEnemies = stage.getRegularEnemies();
        List<Enemy> bossEnemies = stage.getBossEnemies();
        testWriteStageEnemiesFile0(regularEnemies, bossEnemies);
    }

    private void testWriteStageEnemiesFile0(List<Enemy> regularEnemies, List<Enemy> bossEnemies) {
        Enemy regularEnemy0 = regularEnemies.get(0);
        Enemy regularEnemy1 = regularEnemies.get(1);
        Enemy bossEnemy0 = bossEnemies.get(0);

        assertEquals(2, regularEnemies.size());
        assertEquals("Cat", regularEnemy0.getName());
        assertEquals(10, regularEnemy0.getCoordX());
        assertEquals(35, regularEnemy0.getCoordY());
        assertEquals(3, regularEnemy0.getCurrentHealth());

        assertEquals("Rat", regularEnemy1.getName());
        assertEquals(11, regularEnemy1.getCoordX());
        assertEquals(36, regularEnemy1.getCoordY());
        assertEquals(4, regularEnemy1.getCurrentHealth());

        assertEquals(1, bossEnemies.size());
        assertEquals("Evil Cat", bossEnemy0.getName());
        assertEquals(40, bossEnemy0.getCoordX());
        assertEquals(15, bossEnemy0.getCoordY());
        assertEquals(2, bossEnemy0.getCurrentHealth());
    }

    private void testWritePlayerFile0(Player readPlayer) {
        assertEquals(3, readPlayer.getCurrentHealth());
        assertEquals(100, readPlayer.getCoordX());
        assertEquals(300, readPlayer.getCoordY());
        assertEquals(10, readPlayer.getMaxDx());
        assertEquals(-30, readPlayer.getDy());
    }

    private void testWriteAccountFile0(Account readAccount) {
        EnemyList enemyList = readAccount.getEncounteredEnemies();

        assertEquals("cathater", readAccount.getUsername());
        assertEquals(12345, readAccount.getDogePoints());
        assertEquals(1, readAccount.getNextStageNum());
        assertEquals(1, enemyList.size());
        assertTrue(enemyList.containsEnemy("Cat"));
    }

    private DJGame setupGameFile0() {
        DJGame game = new DJGame();
        game.setCharacter("Cheems");

        Account account = new Account();
        Player player = game.getPlayer();
        Stage stage = game.getStage();
        List<Projectile> projectiles = new ArrayList<>();

        game.setAccount(setupAccountGameFile0(account));
        game.setPlayer(setupPlayerGameFile0(player));
        game.setStage(setupStageGameFile0(stage));
        game.addProjectiles(setupProjectilesGameFile0(projectiles));
        return game;
    }

    private Account setupAccountGameFile0(Account account) {
        account.setUsername("cathater");
        account.setDogePoints(12345);
        account.setNextStageNum(1);
        account.addEncounteredEnemy("Cat");
        return account;
    }

    private Player setupPlayerGameFile0(Player player) {
        player.setCurrentHealth(3);
        player.setCoordX(100);
        player.setCoordY(300);
        player.setMaxDx(10);
        player.setDy(-30);
        return player;
    }

    private Stage setupStageGameFile0(Stage stage) {
        stage.setStageNum(1);
        stage.setBossStage(true);
        stage.addPlatform(10, 20, 50, 60);
        stage.addPlatform(20, 40, 100, 120);
        stage.addEnemy("Cat", 10, 35, 3);
        stage.addEnemy("Rat", 11, 36, 4);
        stage.addEnemy("Evil Cat", 40, 15, 2);
        return stage;
    }

    private List<Projectile> setupProjectilesGameFile0(List<Projectile> projectiles) {
        projectiles.add(new Projectile("player", 2, 3, 4, 5, 6, 7));
        projectiles.add(new Projectile("enemy", 3, 4, 5, 6, 7, 8));
        return projectiles;
    }



}
