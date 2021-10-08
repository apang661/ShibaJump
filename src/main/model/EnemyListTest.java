package model;

import model.bossenemies.BossCat;
import model.regularenemies.RegularCat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyListTest {
    EnemyList enemyList;

    @BeforeEach
    void setUp() {
        enemyList = new EnemyList();
    }

    @Test
    void testAddEnemy() {
        Enemy e0 = new RegularCat();
        Enemy e1 = new BossCat();
        enemyList.addEnemy(e0);
        enemyList.addEnemy(e1);

        assertEquals(e0, enemyList.getEnemy(0));
        assertEquals(e1, enemyList.getEnemy(1));
    }
}
