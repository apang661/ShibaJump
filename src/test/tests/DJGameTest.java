package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DJGameTest {
    private DJGame game;

    @BeforeEach
    void setUp() {
        game = new DJGame();
    }

    @Test
    void testSetCharacter() {
        PlayableCharacter char0 = PlayableCharacter.values()[0];
        game.setCharacter(char0.getName());

        Player player = game.getPlayer();
        assertEquals(char0.getName(), player.getName());
        assertEquals(char0.getMaxHealth(), player.getCurrentHealth());
        assertEquals(char0.getWidth(), player.getWidth());
        assertEquals(char0.getHeight(), player.getHeight());
    }

    @Test
    void testGetStage() {
        Stage emptyStage = new Stage();
        assertEquals(emptyStage.getPlatforms(), game.getStage().getPlatforms());
    }

    @Test
    void testGetAccount() {
        Account account = new Account();
        account.setUsername("dogeplayer");

        game.setAccount(account);
        assertEquals(account, game.getAccount());
        assertEquals("dogeplayer", game.getAccount().getUsername());
    }

    @Test
    void testAddAndClearProjectiles() {
        assertEquals(0, game.getProjectiles().size());
        Projectile p0 = new Projectile("player", 10, 10, 0, 0, 0, 0);
        Projectile p1 = new Projectile("enemy", 10, 10, 0, 0, 0, 0);

        List<Projectile> addedList = new ArrayList<>();
        addedList.add(p0);
        addedList.add(p1);

        game.addProjectiles(addedList);
        List<Projectile> gameProjectiles = game.getProjectiles();
        assertEquals(p0, gameProjectiles.get(0));
        assertEquals(p1, gameProjectiles.get(1));

        game.clearProjectiles();
        assertEquals(0, game.getProjectiles().size());
    }
}