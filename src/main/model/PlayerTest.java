package model;

import model.stages.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    Player player;

    @Test
    void testMove() {
        player = new Player(DJGame.CHARACTERS.get(0));
        int prevX = player.getCoordX();
        int prevY = player.getCoordY();
        player.move();

        assertEquals(prevX + Player.DX, player.getCoordX());
        assertEquals(prevY + player.getDy(), player.getCoordX());
    }

}
