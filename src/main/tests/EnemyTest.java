package tests;

import model.Enemy;
import model.regularenemies.RegularCat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    Enemy enemy;

    @BeforeEach
    void setUp() {
        enemy = new RegularCat();
    }

    @Test
    void testGetName() {
        assertEquals(RegularCat.NAME, enemy.getName());
    }

    @Test
    void testSetCoordX() {
        enemy.setCoordX(100);
        assertEquals(100, enemy.getCoordX());
    }

    @Test
    void testSetCoordY() {
        enemy.setCoordY(100);
        assertEquals(100, enemy.getCoordY());
    }
}
