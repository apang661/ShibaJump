package tests;

import model.Projectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameScreen;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectileTest {
    Projectile projectile;

    @BeforeEach
    void setup() {
        projectile = new Projectile("player", 0, 0, 0, 0, 10, 100);
    }
    @Test
    void testUpdatePositionAndVelocity() {
        double conversionFactor = (double) GameScreen.UPDATE_INTERVAL / 1000;
        int prevX = projectile.getCoordX();
        int prevY = projectile.getCoordY();
        int prevDistanceTravelled = projectile.getDistanceTravelled();

        projectile.updatePositionAndVelocity();

        assertEquals((int) (prevX + conversionFactor * projectile.getDx()), projectile.getCoordX());
        assertEquals((int) (prevY + conversionFactor * projectile.getDy()), projectile.getCoordY());
        assertEquals((int) (prevDistanceTravelled + conversionFactor * projectile.getDy()), projectile.getDistanceTravelled());
    }

}
