package tests;

import model.DJGame;
import model.PlayableCharacter;
import model.Player;
import model.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testGetStage() {
        Stage emptyStage = new Stage();
        assertEquals(emptyStage.getPlatforms(), game.getCurStage().getPlatforms());
    }

    @Test
    void testLoadStage() {
        Stage stage = new Stage();
        game.setSavedStage(stage);
        game.loadStage();

        assertEquals(stage, game.getCurStage());
    }
}