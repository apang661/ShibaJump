package tests;

import model.Account;
import model.SJGame;
import model.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void testGetUsername() {
        account.setUsername("test");
        assertEquals("test", account.getUsername());
    }

    @Test
    void testGetShibaPoints() {
        account.setShibaPoints(100);
        assertEquals(100, account.getShibaPoints());
    }

    @Test
    void testGetSelectedCharacter() {
        account.setSelectedCharacter("Walter");
        assertEquals("Walter", account.getSelectedCharacter());
    }

    @Test
    void testAddEncounteredEnemyEnemyParam() {
        Enemy regCat = SJGame.REGULAR_ENEMIES.get(0);
        Enemy bossCat = SJGame.BOSS_ENEMIES.get(0);
        account.addEncounteredEnemy(regCat);
        account.addEncounteredEnemy(bossCat);

        assertEquals(2, account.getEncounteredEnemies().size());
        assertTrue(account.getEncounteredEnemies().containsEnemy(regCat));
        assertTrue(account.getEncounteredEnemies().containsEnemy(bossCat));
    }

    @Test
    void testAddEncounteredEnemyStringParamFound() {
        Enemy regCat = SJGame.REGULAR_ENEMIES.get(0);
        Enemy bossCat = SJGame.BOSS_ENEMIES.get(0);
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
    void testGetNextStageNum() {
        account.setNextStageNum(1);
        assertEquals(1, account.getNextStageNum());
    }
}
