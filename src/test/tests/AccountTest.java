package tests;

import model.Account;
import model.DJGame;
import model.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void testSetUsername() {
        account.setUsername("test");
        assertEquals("test", account.getUsername());
    }

    @Test
    void testSetDogePoints() {
        account.setDogePoints(100);
        assertEquals(100, account.getDogePoints());
    }

    @Test
    void testAddEncounteredEnemyEnemyParam() {
        Enemy regCat = DJGame.REGULAR_ENEMIES.get(0);
        Enemy bossCat = DJGame.BOSS_ENEMIES.get(0);
        account.addEncounteredEnemy(regCat);
        account.addEncounteredEnemy(bossCat);

        assertEquals(2, account.getEncounteredEnemies().size());
        assertTrue(account.getEncounteredEnemies().containsEnemy(regCat));
        assertTrue(account.getEncounteredEnemies().containsEnemy(bossCat));
    }

    @Test
    void testAddEncounteredEnemyStringParamFound() {
        Enemy regCat = DJGame.REGULAR_ENEMIES.get(0);
        Enemy bossCat = DJGame.BOSS_ENEMIES.get(0);
        account.addEncounteredEnemy(regCat.getName());
        account.addEncounteredEnemy(bossCat.getName());

        assertEquals(2, account.getEncounteredEnemies().size());
        assertTrue(account.getEncounteredEnemies().containsEnemy(regCat));
        assertTrue(account.getEncounteredEnemies().containsEnemy(bossCat));

        account.addEncounteredEnemy(regCat.getName());
        account.addEncounteredEnemy(bossCat.getName());

        assertEquals(2, account.getEncounteredEnemies().size());
    }

    @Test
    void testAddEncounteredEnemyStringParamNotFound() {
        account.addEncounteredEnemy("Unknown Enemy 1313");
        account.addEncounteredEnemy("Unknown Enemy 1305");

        assertEquals(0, account.getEncounteredEnemies().size());
    }

    @Test
    void testGetRegularEnemyNames() {
        List<String> listOfRegularEnemies = new ArrayList<>();
        for (Enemy enemy: DJGame.REGULAR_ENEMIES) {
            listOfRegularEnemies.add(enemy.getName());
        }
        assertEquals(listOfRegularEnemies, account.getRegularEnemyNames());
        assertEquals(DJGame.REGULAR_ENEMIES.size(), account.getRegularEnemyNames().size());
    }

    @Test
    void testGetBossEnemyNames() {
        List<String> listOfBossEnemies = new ArrayList<>();
        for (Enemy enemy: DJGame.BOSS_ENEMIES) {
            listOfBossEnemies.add(enemy.getName());
        }

        List<String> accountListOfBossEnemies = account.getBossEnemyNames();
        assertEquals(listOfBossEnemies, accountListOfBossEnemies);
        assertEquals(DJGame.BOSS_ENEMIES.size(), accountListOfBossEnemies.size());
    }

    @Test
    void testGetNextStageNum() {
        account.setNextStageNum(1);
        assertEquals(1, account.getNextStageNum());
    }
}
