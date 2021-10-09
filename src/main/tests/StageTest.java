package tests;

import model.DJGame;
import model.Player;
import model.stages.Platform;
import model.stages.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StageTest {
    Stage stage;

    @BeforeEach
    void setUp() {
        stage = new Stage();
        stage.addWidePlatform(Stage.WIDTH / 2, Stage.HEIGHT / 2);
    }

    @Test
    void testGetPlatforms() {
        stage.addScreenWidePlatform(Stage.HEIGHT * 3 / 4);
        stage.addWidePlatform(Stage.WIDTH / 2, Stage.HEIGHT / 4);

        List<Platform> listOfPlatforms = new ArrayList<>();
        listOfPlatforms.add(new Platform(
                Stage.WIDE_PLATFORM_WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT / 2));
        listOfPlatforms.add(new Platform(
                Stage.WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT * 3 / 4));
        listOfPlatforms.add(new Platform(
                Stage.WIDE_PLATFORM_WIDTH, Stage.PLATFORM_THICKNESS, Stage.WIDTH / 2, Stage.HEIGHT / 4));

        List<Platform> stagePlatforms = stage.getPlatforms();


        for (int i = 0; i < 3; i++) {
            assertTrue(listOfPlatforms.get(i).equals(stagePlatforms.get(i)));
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
    void testIsPlayerFallingOnAnyPlatformFallingMiddle() {
        Player player = new Player(DJGame.CHARACTERS.get(0));
        player.setDy(0);

        // Middle of platform
        player.setCoordX(Stage.WIDTH / 2);

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 - 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

    }

    @Test
    void testIsPlayerFallingOnAnyPlatformFallingLeft() {
        Player player = new Player(DJGame.CHARACTERS.get(0));
        player.setDy(0);

        // On left edge of platform
        player.setCoordX(Stage.WIDTH / 2 - Stage.WIDE_PLATFORM_WIDTH / 2);

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 - 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        // Left of left edge by one pixel
        player.setCoordX(Stage.WIDTH / 2 - Stage.WIDE_PLATFORM_WIDTH);
        player.setCoordY(Stage.HEIGHT / 2);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));
    }

    @Test
    void testIsPlayerFallingOnAnyPlatformFallingRight() {
        Player player = new Player(DJGame.CHARACTERS.get(0));
        player.setDy(0);

        // On right edge of platform
        player.setCoordX(Stage.WIDTH / 2 + Stage.WIDE_PLATFORM_WIDTH / 2);

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2 + 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2);
        assertTrue(stage.isPlayerFallingOnAnyPlatform(player));

        player.setCoordY(Stage.HEIGHT / 2 - 1);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));

        // Right of right edge by one pixel
        player.setCoordX(Stage.WIDTH / 2 + Stage.WIDE_PLATFORM_WIDTH);
        player.setCoordY(Stage.HEIGHT / 2);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));
    }

    @Test
    void testIsPlayerFallingOnAnyPlatformNotFalling() {
        Player player = new Player(DJGame.CHARACTERS.get(0));
        player.setDy(1);

        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 + Stage.PLATFORM_THICKNESS / 2);
        assertFalse(stage.isPlayerFallingOnAnyPlatform(player));
    }

}