package model.stages;

import model.Player;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a stage (a list of platforms)
 */
public class Stage {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 6000;
    public static final int BOSS_STAGE_WIDTH = 800;
    public static final int BOSS_STAGE_HEIGHT = 600;
    public static final int PLATFORM_THICKNESS = 10;
    public static final double WIDE_PLATFORM_MULTIPLIER = 0.5;
    public static final int WIDE_PLATFORM_WIDTH = (int) Math.round(WIDTH * WIDE_PLATFORM_MULTIPLIER);
    public static final double REGULAR_PLATFORM_MULTIPLIER = 0.3;
    public static final int REGULAR_PLATFORM_WIDTH = (int) Math.round(WIDTH * REGULAR_PLATFORM_MULTIPLIER);
    public static final double NARROW_PLATFORM_MULTIPLIER = 0.15;
    public static final int NARROW_PLATFORM_WIDTH = (int) Math.round(WIDTH * NARROW_PLATFORM_MULTIPLIER);

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
        for (Platform platform: platforms) {
            if (platform.isPlayerFallingOnPlatform(player)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: stageNum must be less than or equal to 3
    // MODIFIES: this
    // EFFECTS: Sets the stage according to parameters
    public void setStage(int stageNum, boolean isBossStage) {
        if (stageNum == 1) {
            platforms.clear();
            if (isBossStage) {
                setStageToBossOne();
            } else {
                setStageToRegOne();
            }
        } else if (stageNum == 2) {
            //stub
        } else if (stageNum == 3) {
            //stub
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the stage to level one (regular)
    private void setStageToRegOne() {
    }

    // MODIFIES: this
    // EFFECTS: Sets the stage to level one (boss)
    private void setStageToBossOne() {
    }

    // REQUIRES: Platform must be within the stage (between 0 and HEIGHT)
    // MODIFIES: this
    // EFFECTS: Add a screen-wide platform at given y coordinate
    public void addScreenWidePlatform(int y) {
        Platform platform = new Platform(WIDTH, PLATFORM_THICKNESS,WIDTH / 2, y);
        platforms.add(platform);
    }

    // REQUIRES: Platform must be within the stage
    // MODIFIES: this
    // EFFECTS: Add a wide platform at the given x, y coordinates
    public void addWidePlatform(int x, int y) {
        Platform platform = new Platform(WIDE_PLATFORM_WIDTH, PLATFORM_THICKNESS, x, y);
        platforms.add(platform);
    }

    // REQUIRES: Platform must be within the stage
    // MODIFIES: this
    // EFFECTS: Add a regular-sized platform at the given x, y coordinates
    public void addRegularPlatform(int x, int y) {
        Platform platform = new Platform(REGULAR_PLATFORM_WIDTH, PLATFORM_THICKNESS, x, y);
        platforms.add(platform);
    }

    // REQUIRES: Platform must be within the stage
    // MODIFIES: this
    // EFFECTS: Add a regular-sized platform at the given x, y coordinates
    public void addNarrowPlatform(int x, int y) {
        Platform platform = new Platform(NARROW_PLATFORM_WIDTH, PLATFORM_THICKNESS, x, y);
        platforms.add(platform);
    }
}
