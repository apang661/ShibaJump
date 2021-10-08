package model.stages;

import model.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a stage (a list of platforms)
 */
public class Stage {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int BOSS_STAGE_WIDTH = 100;
    public static final int BOSS_STAGE_HEIGHT = 100;
    public static final int PLATFORM_THICKNESS = 10;
    public static final double WIDE_PLATFORM_MULTIPLIER = 0.6;

    private List<Platform> platforms;
    boolean bossStage;

    // EFFECTS: Create a new Stage with no platforms and not in bossStage
    public Stage() {
        platforms = new ArrayList<>();
        bossStage = false;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setBossStage(boolean bossStage) {
        this.bossStage = bossStage;
    }

    public boolean isBossStage() {
        return bossStage;
    }

    // EFFECTS: Return true if the given player is falling (dy <= 0) on any platform
    //          (touching top surface to middle of platform) in the stage
    public boolean isPlayerFallingOnAnyPlatform(Player player) {
        return true;
    }

    // REQUIRES: Platform must be within the stage (between 0 and HEIGHT)
    // MODIFIES: this
    // EFFECTS: Add a screen-wide platform at given y coordinate
    public void addScreenWidePlatform(int y) {

    }

    // REQUIRES: Platform must be within the stage
    // MODIFIES: this
    // EFFECTS: Add a wide platform at the given x, y coordinates
    public void addWidePlatform(int x, int y) {

    }


}
