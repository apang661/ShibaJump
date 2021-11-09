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
    void testCheckCollisionWithAnyPlayerProjectilePlayerType() {
        projectileList.add(projectile);

        assertEquals(1, projectileList.size());
        enemy.setCoordX(projectile.getCoordX());
        enemy.setCoordY(projectile.getCoordY());

        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        assertEquals(0, projectileList.size());

        enemy.setCoordX(-500);
        enemy.setCoordY(-500);
        enemy.checkCollisionWithAnyPlayerProjectile(projectileList);
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileEnemyType() {
        projectile = new Projectile(
                "enemy", 10, 6, Stage.WIDTH / 2, Stage.HEIGHT / 2, 0, 0);
        projectileList.add(projectile);

        enemy.setCoordX(projectile.getCoordX());
        enemy.setCoordY(projectile.getCoordY());
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        assertEquals(1, projectileList.size());

        enemy.setCoordX(-300);
        enemy.setCoordY(-300);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        assertEquals(1, projectileList.size());
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileRight() {
        projectileList.add(projectile);

        // Top right of enemy
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Middle right of enemy
        enemy.setCoordY(Stage.HEIGHT / 2);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom right of enemy
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileLeft() {
        projectileList.add(projectile);

        // Top left of projectile
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Middle left of projectile
        enemy.setCoordY(Stage.HEIGHT / 2);
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom left of projectile
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileTop() {
        projectileList.add(projectile);

        // Top left of projectile
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Top middle of projectile
        projectileList.add(projectile);
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Top right of projectile
        projectileList.add(projectile);
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordY(Stage.HEIGHT / 2 + projectile.getHeight() / 2 + enemy.getHeight() / 2 + 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testCheckCollisionWithAnyPlayerProjectileBottom() {
        projectileList.add(projectile);

        // Bottom left of projectile
        enemy.setCoordX(Stage.WIDTH / 2 - projectile.getWidth() / 2 - enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom middle of projectile
        enemy.setCoordX(Stage.WIDTH / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));

        // Bottom right of projectile
        enemy.setCoordX(Stage.WIDTH / 2 + projectile.getWidth() / 2 + enemy.getWidth() / 2);
        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2);
        assertTrue(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
        projectileList.add(projectile);

        enemy.setCoordY(Stage.HEIGHT / 2 - projectile.getHeight() / 2 - enemy.getHeight() / 2 - 1);
        assertFalse(enemy.checkCollisionWithAnyPlayerProjectile(projectileList));
    }

    @Test
    void testGetImageFile() {
        assertEquals(RegularCat.IMAGE_FILE, enemy.getImageFile());
    }
}
