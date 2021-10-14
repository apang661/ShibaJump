package tests;

import model.DJGame;
import model.Player;
import model.stages.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player(DJGame.CHARACTERS.get(0));
    }

    @Test
    void testGetDyForJump() {
        assertEquals(DJGame.CHARACTERS.get(0).getDyForJump(), player.getDyForJump());
    }

    @Test
    void testUpdatePositionAndVelocity() {
        player = new Player(DJGame.CHARACTERS.get(0));
        int prevX = player.getCoordX();
        int prevY = player.getCoordY();
        int prevDy = player.getDy();
        player.updatePositionAndVelocity();

        assertEquals(prevX + player.getDx(), player.getCoordX());
        assertEquals(prevY + prevDy, player.getCoordY());
        assertEquals(prevDy + Stage.GRAVITY_ACCELERATION, player.getDy());
    }

}
