package tests;

import model.Enemy;
import model.EnemyList;
import model.bossenemies.BossCat;
import model.regularenemies.RegularCat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyListTest {
    EnemyList enemyList;
    Enemy e0;
    Enemy e1;

    @BeforeEach
    void setUp() {
        enemyList = new EnemyList();
        e0 = new RegularCat();
        e1 = new BossCat();
    }

    @Test
    void testAddAndRemoveEnemy() {
        enemyList.addEnemy(e0);
        enemyList.addEnemy(e1);

        assertEquals(e0, enemyList.getEnemy(0));
        assertEquals(e1, enemyList.getEnemy(1));

        enemyList.removeEnemy(e0);
        assertEquals(e1, enemyList.getEnemy(0));
        assertEquals(1, enemyList.size());

        enemyList.removeEnemy(e1);
        assertEquals(0, enemyList.size());
    }

    @Test
    void testContainsEnemy() {
        enemyList.addEnemy(e0);

        assertTrue(enemyList.containsEnemy("Cat"));
        assertFalse(enemyList.containsEnemy("Unknown Enemy"));
    }
}
