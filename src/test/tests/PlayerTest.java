package tests;

import model.*;
import model.regularenemies.RegularCat;
import model.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    private Player player;
    private Enemy enemy;
    private List<Enemy> enemyList;
    private Projectile projectile;
    private List<Projectile> projectileList;

    @BeforeEach
    void setUp() {
        player = new Player(PlayableCharacter.DOGE);

        projectile = new Projectile(
                "enemy", 10, 6, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        projectileList = new ArrayList<>();
        projectileList.add(projectile);

        enemy = new RegularCat();
        enemyList = new ArrayList<>();
        enemyList.add(enemy);
    }

    @Test
    void testGetDyForJump() {
        assertEquals(PlayableCharacter.DOGE.getDyForJump(), player.getDyForJump());
    }

    @Test
    void testUpdatePositionAndVelocity() {
        player = new Player(PlayableCharacter.DOGE);
        int prevX = player.getCoordX();
        int prevY = player.getCoordY();
        int prevDy = player.getDy();
        player.updatePositionAndVelocity();

        assertEquals(prevX + player.getDx(), player.getCoordX());
        assertEquals(prevY + prevDy, player.getCoordY());
        assertEquals(prevDy + Stage.GRAVITY_ACCELERATION, player.getDy());
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

}
