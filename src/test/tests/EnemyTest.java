package tests;

import model.Projectile;
import model.regularenemies.RegularCat;
import model.regularenemies.RegularEnemy;
import model.regularenemies.RegularRat;
import model.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    RegularEnemy enemy;
    Projectile projectile;
    List<Projectile> projectileList;

    @BeforeEach
    void setUp() {
        enemy = new RegularCat();
        projectile = new Projectile(
                "player", 10, 6, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        projectileList = new ArrayList<>();
        projectileList.add(projectile);
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

    @Test
    void testRegularCatShoot() {
        enemy = new RegularCat();
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        List<Projectile> projectiles = enemy.shoot();
        Projectile p = projectiles.get(0);

        assertEquals(1, projectiles.size());
        assertEquals(RegularCat.PROJECTILE_WIDTH, p.getWidth());
        assertEquals(RegularCat.PROJECTILE_HEIGHT, p.getHeight());
        assertEquals(Stage.WIDTH / 2, p.getCoordX());
        assertEquals(Stage.HEIGHT / 2, p.getCoordY());
        assertEquals(RegularCat.PROJECTILE_DX, p.getDx());
        assertEquals(RegularCat.PROJECTILE_DY, p.getDy());
    }

    @Test
    void testRegularRatShoot() {
        enemy = new RegularRat();
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        List<Projectile> projectiles = enemy.shoot();
        Projectile p = projectiles.get(0);

        assertEquals(1, projectiles.size());
        assertEquals(RegularRat.PROJECTILE_WIDTH, p.getWidth());
        assertEquals(RegularRat.PROJECTILE_HEIGHT, p.getHeight());
        assertEquals(Stage.WIDTH / 2, p.getCoordX());
        assertEquals(Stage.HEIGHT / 2, p.getCoordY());
        assertEquals(RegularRat.PROJECTILE_DX, p.getDx());
        assertEquals(RegularRat.PROJECTILE_DY, p.getDy());
    }

    @Test
    void testCheckPlayerCollisionWithAnyEnemyProjectilePlayerType() {
        projectile = new Projectile(
                "enemy", 10, 6, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        projectileList = new ArrayList<>();
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2);

        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileRight() {
        // Top right of enemy
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Middle right of enemy
        enemy.setCoordY(Stage.HEIGHT / 2);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom right of enemy
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileLeft() {
        // Top left of projectile
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Middle left of projectile
        enemy.setCoordY(Stage.HEIGHT / 2);
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom left of projectile
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileTop() {
        // Top left of projectile
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Top middle of projectile
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Top right of projectile
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileBottom() {
        // Bottom left of projectile
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom middle of projectile
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom right of projectile
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }
}
