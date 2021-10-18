package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StageTest {
    public static final int NUM_OF_TEST_FOR_RANDOM = 1000;

    Stage stage;

    @BeforeEach
    void setUp() {
        stage = new Stage();
        stage.addWidePlatform(Stage.WIDTH / 2, Stage.HEIGHT / 2);
    }

    @Test
    void testGetPlatforms() {
        stage.addScreenWidePlatform(Stage.HEIGHT * 3 / 4);
        stage.addRegularPlatform(Stage.WIDTH / 2, Stage.HEIGHT / 4);
        stage.addNarrowPlatform(Stage.WIDTH / 2, Stage.HEIGHT / 2);

        List<Platform> listOfPlatforms = new ArrayList<>();
        listOfPlatforms.add(new Platform(
                Stage.WIDE_PLATFORM_WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT / 2));
        listOfPlatforms.add(new Platform(
                Stage.WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT * 3 / 4));
        listOfPlatforms.add(new Platform(
                Stage.REGULAR_PLATFORM_WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT / 4));
        listOfPlatforms.add(new Platform(
                Stage.NARROW_PLATFORM_WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT / 2));
        List<Platform> stagePlatforms = stage.getPlatforms();

        int i = 0;
        for (Platform platformA: listOfPlatforms) {
            assertEquals(platformA.getWidth(), stagePlatforms.get(i).getWidth());
            assertEquals(platformA.getHeight(), stagePlatforms.get(i).getHeight());
            assertEquals(platformA.getCoordX(), stagePlatforms.get(i).getCoordX());
            assertEquals(platformA.getCoordY(), stagePlatforms.get(i).getCoordY());
            i++;
        }
    }

    @Test
    void testIsBossStageTrue() {
        stage.setBossStage(true);
        assertTrue(stage.isBossStage());
    }

    @Test
    void testIsBossStageFalse() {
        stage.setBossStage(false);
        assertFalse(stage.isBossStage());
    }

    @Test
    void testGetStageNum() {
        stage.setStageNum(1);
        assertEquals(1, stage.getStageNum());
    }

    @Test
    void testIsPlayerFallingOnAnyPlatformFallingMiddle() {
        Player player = new Player(PlayableCharacter.DOGE);
        player.setDy(0);

        // Middle of platform
        player.setCoordX(Stage.WIDTH / 2);

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2 + 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2 - 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

    }

    @Test
    void testIsPlayerFallingOnAnyPlatformFallingLeft() {
        Player player = new Player(PlayableCharacter.DOGE);
        player.setDy(0);

        // On left edge of platform
        player.setCoordX(Stage.WIDTH / 2 - Stage.WIDE_PLATFORM_WIDTH / 2 - player.getWidth() / 2);

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2 + 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2 - 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        // Left of left edge by one pixel
        player.setCoordX(Stage.WIDTH / 2 - Stage.WIDE_PLATFORM_WIDTH / 2 - player.getWidth() / 2 - 1);
        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));
    }

    @Test
    void testIsPlayerFallingOnAnyPlatformFallingRight() {
        Player player = new Player(PlayableCharacter.DOGE);
        player.setDy(0);

        // On right edge of platform
        player.setCoordX(Stage.WIDTH / 2 + Stage.WIDE_PLATFORM_WIDTH / 2 + player.getWidth() / 2);

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2 + 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2 - 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        // Right of right edge by one pixel
        player.setCoordX(Stage.WIDTH / 2 + Stage.WIDE_PLATFORM_WIDTH / 2 + player.getWidth() / 2 + 1);
        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));
    }

    @Test
    void testIsPlayerFallingOnAnyPlatformNotFalling() {
        Player player = new Player(PlayableCharacter.DOGE);
        player.setDy(1);

        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + player.getHeight() / 2);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));
    }

    @Test
    void testSetStage0() {
        stage.setStageNum(0);
        stage.setStage();

        assertEquals(1, stage.getPlatforms().size());
        assertTrue(stage.containsPlatformAtY(0));
    }

    @Test
    void testContainsPlatformAtYEmpty() {
        stage.getPlatforms().clear();
        stage.containsPlatformAtY(1);
    }

    @Test
    void testContainsPlatformAtYNotEmpty() {
        stage.addRegularPlatform(0, 0);

        assertTrue(stage.containsPlatformAtY(0));
        assertTrue(stage.containsPlatformAtY(Stage.HEIGHT / 2));
        assertFalse(stage.containsPlatformAtY(Stage.HEIGHT / 4));
    }

    @Test
    void testSetStage1Reg() {
        stage.setStageNum(1);
        stage.setBossStage(false);
        stage.setStage();

        assertTrue(stage.getPlatforms().size() > Stage.HEIGHT / Stage.HEIGHT_BETWEEN_PLATFORMS);

        for (int i = 0; i < Stage.HEIGHT / Stage.HEIGHT_BETWEEN_PLATFORMS; i++) {
            int testHeight = i * Stage.HEIGHT_BETWEEN_PLATFORMS;
            assertTrue(stage.containsPlatformAtY(testHeight));
        }
    }

    @Test
    void testSetStage1Boss() {
        stage.setStageNum(1);
        stage.setBossStage(true);
        stage.setStage();

        assertEquals(1, stage.getPlatforms().size());
        assertTrue(stage.containsPlatformAtY(Stage.BOSS_STAGE_HEIGHT / 4));
    }

    @Test
    void testSetStage2() {
        stage.setStageNum(2);
        stage.setStage();

        assertEquals(0, stage.getPlatforms().size());
    }

    @Test
    void testAddEasyPlatformConfiguration() {
        stage.addEasyPlatformConfiguration(0);
        assertTrue(stage.containsPlatformAtY(0));
        stage.getPlatforms().clear();

        for (int i = 0; i < NUM_OF_TEST_FOR_RANDOM; i++) {
            stage.addEasyPlatformConfiguration(0);
        }

        assertTrue(stage.getPlatforms().size() > 14 * NUM_OF_TEST_FOR_RANDOM / 10);
        assertTrue(stage.getPlatforms().size() < 2 * NUM_OF_TEST_FOR_RANDOM);
    }

    @Test
    void testAddNormalPlatformConfiguration() {
        stage.addNormalPlatformConfiguration(0);
        assertTrue(stage.containsPlatformAtY(0));
        stage.getPlatforms().clear();

        for (int i = 0; i < NUM_OF_TEST_FOR_RANDOM; i++) {
            stage.addNormalPlatformConfiguration(0);
        }

        assertTrue(stage.getPlatforms().size() > NUM_OF_TEST_FOR_RANDOM);
        assertTrue(stage.getPlatforms().size() < 15 * NUM_OF_TEST_FOR_RANDOM / 10);
    }

    @Test
    void testAddHardPlatformConfiguration() {
        stage.addHardPlatformConfiguration(0);
        assertTrue(stage.containsPlatformAtY(0));
        stage.getPlatforms().clear();

        for (int i = 0; i < NUM_OF_TEST_FOR_RANDOM; i++) {
            stage.addHardPlatformConfiguration(0);
        }

        assertEquals(NUM_OF_TEST_FOR_RANDOM, stage.getPlatforms().size());
    }

    @Test
    void testAddPlatform() {
        stage.addPlatform(Stage.WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT / 2);
        List<Platform> platforms = stage.getPlatforms();


        assertEquals(2, platforms.size());
        assertEquals(Stage.WIDTH, stage.getPlatforms().get(1).getWidth());
        assertEquals(Stage.PLATFORM_THICKNESS, stage.getPlatforms().get(1).getHeight());
        assertEquals(Stage.WIDTH / 2, stage.getPlatforms().get(1).getCoordX());
        assertEquals(Stage.HEIGHT / 2, stage.getPlatforms().get(1).getCoordY());
    }

    @Test
    void testAddEnemyNoHealthParam() {
        stage.addEnemy(DJGame.REGULAR_ENEMIES.get(0).getName(), 0, 0);
        stage.addEnemy(DJGame.REGULAR_ENEMIES.get(1).getName(), 2, 2);
        assertEquals(2, stage.getRegularEnemies().size());

        List<Enemy> enemyList = stage.getRegularEnemies();
        assertEquals(DJGame.REGULAR_ENEMIES.get(0).getName(), enemyList.get(0).getName());
        assertEquals(0, enemyList.get(0).getCoordX());
        assertEquals(0, enemyList.get(0).getCoordY());
        assertEquals(DJGame.REGULAR_ENEMIES.get(1).getName(), enemyList.get(1).getName());
        assertEquals(2, enemyList.get(1).getCoordX());
        assertEquals(2, enemyList.get(1).getCoordY());
    }

    @Test
    void testAddEnemyWithHealthParam() {
        stage.addEnemy(DJGame.REGULAR_ENEMIES.get(0).getName(), 0, 0, 6);
        stage.addEnemy(DJGame.BOSS_ENEMIES.get(0).getName(), 2, 2, 10);
        assertEquals(1, stage.getRegularEnemies().size());
        assertEquals(1, stage.getBossEnemies().size());

        Enemy enemy0 = stage.getRegularEnemies().get(0);
        Enemy enemy1 = stage.getBossEnemies().get(0);
        assertEquals(DJGame.REGULAR_ENEMIES.get(0).getName(), enemy0.getName());
        assertEquals(0, enemy0.getCoordX());
        assertEquals(0, enemy0.getCoordY());
        assertEquals(6, enemy0.getCurrentHealth());

        assertEquals(DJGame.BOSS_ENEMIES.get(0).getName(), enemy1.getName());
        assertEquals(2, enemy1.getCoordX());
        assertEquals(2, enemy1.getCoordY());
        assertEquals(10, enemy1.getCurrentHealth());
    }

    @Test
    void testRemoveEnemy() {
        Enemy enemy0 = DJGame.REGULAR_ENEMIES.get(0);
        Enemy enemy1 = DJGame.BOSS_ENEMIES.get(0);

        stage.addEnemy(enemy0.getName(), 0, 0);
        stage.addEnemy(enemy1.getName(), 2, 2);
        assertEquals(1, stage.getRegularEnemies().size());
        assertEquals(1, stage.getBossEnemies().size());

        stage.removeEnemy(enemy0);
        assertEquals(0, stage.getRegularEnemies().size());
        assertEquals(1, stage.getBossEnemies().size());
        assertEquals(stage.getBossEnemies().get(0), enemy1);

        stage.removeEnemy(enemy1);
        assertEquals(0, stage.getBossEnemies().size());
    }

    @Test
    void testNameToEnemyNotFound() {
        assertEquals("Cat", stage.nameToEnemy("not found").getName());
    }
}