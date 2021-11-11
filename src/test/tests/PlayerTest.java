package tests;

import model.*;
import model.regularenemies.RegularCat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GameScreen;

import java.awt.event.KeyEvent;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;
    private Enemy enemy;
    private List<Enemy> enemyList;
    private Projectile projectile;
    private List<Projectile> projectileList;

    @BeforeEach
    void setUp() {
        player = new Player(Player.PlayableCharacter.DOGE);

        projectile = new Projectile(
                "enemy", 10, 6, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        projectileList = new ArrayList<>();
        projectileList.add(projectile);

        enemy = new RegularCat();
        enemyList = new ArrayList<>();
        enemyList.add(enemy);
    }

    @Test
    void testGetCurrentHealth() {
        player.setCurrentHealth(100);
        assertEquals(100, player.getCurrentHealth());
    }

    @Test
    void testGetDx() {
        player.setMaxDx(10);
        assertEquals(10, player.getMaxDx());
    }
    @Test
    void testGetDyForJump() {
        player.setDyForJump(100);
        assertEquals(100, player.getDyForJump());
    }

    @Test
    void testUpdatePositionAndVelocityWithinDyLimit() {
        int prevX = 100;
        int prevY = 300;
        double conversionFactor = (double) GameScreen.UPDATE_INTERVAL / 1000;
        player.setCoordX(prevX);
        player.setCoordY(prevY);
        player.setDirectionByKeyCodesHeldDown(Collections.singleton(KeyEvent.VK_D)); // set direction to 1

        int prevDy = (int) (Player.MAX_FALLING_DY / conversionFactor);
        player.setDy(prevDy);

        player.updatePositionAndVelocity();
        assertEquals(prevX + Math.round(player.getMaxDx() * conversionFactor), player.getCoordX());
        assertEquals((int) (prevY + prevDy * conversionFactor), player.getCoordY());
        assertEquals((int) (prevDy + Stage.GRAVITY_ACCELERATION * conversionFactor), player.getDy());

        testHandleHorizontalBoundaries();
    }

    private void testHandleHorizontalBoundaries() {
        player.setMaxDx(0);
        player.setDy(0);

        player.setCoordX(-1);
        player.updatePositionAndVelocity();
        assertEquals(Stage.WIDTH - 1, player.getCoordX());

        player.setCoordX(0);
        player.updatePositionAndVelocity();
        assertEquals(0, player.getCoordX());

        player.setCoordX(Stage.WIDTH + 1);
        player.updatePositionAndVelocity();
        assertEquals(1, player.getCoordX());

        player.setCoordX(Stage.WIDTH);
        player.updatePositionAndVelocity();
        assertEquals(Stage.WIDTH, player.getCoordX());
    }

    @Test
    void testUpdatePositionAndVelocityOutOfDyLimit() {
        int prevX = 100;
        int prevY = 300;
        double conversionFactor = (double) GameScreen.UPDATE_INTERVAL / 1000;
        player.setCoordX(prevX);
        player.setCoordY(prevY);
        player.setDirectionByKeyCodesHeldDown(Collections.singleton(KeyEvent.VK_D)); // set direction to 1

        int prevDy = (int) ((Player.MAX_FALLING_DY - 1) / conversionFactor);
        player.setDy(prevDy);

        player.updatePositionAndVelocity();
        assertEquals(prevX + Math.round(player.getMaxDx() * conversionFactor), player.getCoordX());
        assertEquals(prevY + Player.MAX_FALLING_DY, player.getCoordY());
        assertEquals((int) (prevDy + Stage.GRAVITY_ACCELERATION * conversionFactor), player.getDy());
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyProjectilePlayerType() {
        projectile = new Projectile(
                "player", 10, 6, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        projectileList = new ArrayList<>();
        projectileList.add(projectile);

        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2);

        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 + player.getWidth() / 2 + projectile.getWidth() / 2 + 1);
        player.setCoordY(Stage.HEIGHT / 2 + player.getHeight() / 2 + projectile.getHeight() / 2 + 1);

        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyProjectileRight() {
        // Top right of player
        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Middle right of player
        player.setCoordY(Stage.HEIGHT / 2);
        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Bottom right of player
        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyProjectileLeft() {
        // Top left of projectile
        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Middle left of projectile
        player.setCoordY(Stage.HEIGHT / 2);
        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Bottom left of projectile
        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyProjectileTop() {
        // Top left of projectile
        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Top middle of projectile
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Top right of projectile
        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + player.getHeight() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyProjectileBottom() {
        // Bottom left of projectile
        player.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Bottom middle of projectile
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        // Bottom right of projectile
        player.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemyProjectile(projectileList));

        player.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - player.getHeight() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemyProjectile(projectileList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyRight() {
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        // Top right of enemy
        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Middle right of enemy
        player.setCoordY(Stage.HEIGHT / 2);
        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Bottom right of enemy
        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyLeft() {
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        // Top left of enemy
        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Middle left of enemy
        player.setCoordY(Stage.HEIGHT / 2);
        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Bottom left of enemy
        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2);
        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyTop() {
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        // Top left of enemy
        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Top middle of enemy
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Top right of enemy
        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordY(Stage.HEIGHT / 2 + enemy.getHeight() / 2 + player.getHeight() / 2 + 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyBottom() {
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        // Bottom left of enemy
        player.setCoordX(Stage.WIDTH / 2 - enemy.getWidth() / 2 - player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Bottom middle of enemy
        player.setCoordX(Stage.WIDTH / 2);
        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));

        // Bottom right of enemy
        player.setCoordX(Stage.WIDTH / 2 + enemy.getWidth() / 2 + player.getWidth() / 2);
        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2);
        assertTrue(player.checkCollisionWithAnyEnemy(enemyList));

        player.setCoordY(Stage.HEIGHT / 2 - enemy.getHeight() / 2 - player.getHeight() / 2 - 1);
        assertFalse(player.checkCollisionWithAnyEnemy(enemyList));
    }

    @Test
    void testSetDirectionByKeyCodesHeldDown() {
        Set<Integer> keyCodes = new HashSet<>();

        keyCodes.add(KeyEvent.VK_A);
        player.setDirectionByKeyCodesHeldDown(keyCodes);
        assertEquals(-1, player.getDirection());
        keyCodes.remove(KeyEvent.VK_A);

        keyCodes.add(KeyEvent.VK_D);
        player.setDirectionByKeyCodesHeldDown(keyCodes);
        assertEquals(1, player.getDirection());
        keyCodes.remove(KeyEvent.VK_D);

        keyCodes.add(-1);
        player.setDirectionByKeyCodesHeldDown(keyCodes);
        assertEquals(0, player.getDirection());
    }

    @Test
    void testJump() {
        player.setDy(0);
        player.jump();
        assertEquals(player.getDyForJump(), player.getDy());
    }

    @Test
    void testGetImageFileLocation() {
        assertEquals("./images/doge.jpg", player.getImageFileLocation(player.getName()));
    }
}
