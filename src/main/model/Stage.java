package model;

import model.bossenemies.BossCat;
import model.bossenemies.BossEnemy;
import model.regularenemies.RegularCat;
import model.regularenemies.RegularEnemy;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a stage (a list of platforms)
 */
public class Stage implements Writable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 6000;
    public static final int HEIGHT_BETWEEN_PLATFORMS = 200;
    public static final double TIME_BETWEEN_PLATFORMS = 0.5; // in milliseconds
    public static final int BOSS_STAGE_WIDTH = 800;
    public static final int BOSS_STAGE_HEIGHT = 600;
    public static final int PLATFORM_THICKNESS = 20;
    public static final double WIDE_PLATFORM_MULTIPLIER = 0.5;
    public static final double REGULAR_PLATFORM_MULTIPLIER = 0.3;
    public static final double NARROW_PLATFORM_MULTIPLIER = 0.15;
    public static final int WIDE_PLATFORM_WIDTH = (int) Math.round(WIDTH * WIDE_PLATFORM_MULTIPLIER);
    public static final int REGULAR_PLATFORM_WIDTH = (int) Math.round(WIDTH * REGULAR_PLATFORM_MULTIPLIER);
    public static final int NARROW_PLATFORM_WIDTH = (int) Math.round(WIDTH * NARROW_PLATFORM_MULTIPLIER);
    public static final double GRAVITY_ACCELERATION =
            -1 * 2 * HEIGHT_BETWEEN_PLATFORMS / Math.pow(TIME_BETWEEN_PLATFORMS, 2);
    public static final double MIN_JUMP_DY
            = Math.sqrt(-2 * Stage.GRAVITY_ACCELERATION * Stage.HEIGHT_BETWEEN_PLATFORMS);

    int stageNum;
    boolean bossStage;
    private List<Platform> platforms;
    private List<Enemy> regularEnemies;
    private List<Enemy> bossEnemies;


    // EFFECTS: Create a new Stage with no platforms and not in bossStage
    public Stage() {
        platforms = new ArrayList<>();
        regularEnemies = new ArrayList<>();
        bossEnemies = new ArrayList<>();
        bossStage = false;
        stageNum = 0;

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

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

    public List<Enemy> getRegularEnemies() {
        return regularEnemies;
    }

    public List<Enemy> getBossEnemies() {
        return bossEnemies;
    }

    // EFFECTS: Returns true if this stage contains a platform at given y
    public boolean containsPlatformAtY(int y) {
        boolean containsPlatform = false;
        int i = 0;
        int numOfPlatforms = platforms.size();
        while (!containsPlatform && (i < numOfPlatforms)) {
            if (platforms.get(i).getCoordY() == y) {
                containsPlatform = true;
            }
            i++;
        }
        return containsPlatform;
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

    // REQUIRES: stageNum must be between 0 and 1
    // MODIFIES: this
    // EFFECTS: Sets the stage according to the current stage number and stage type (reg or boss)
    public void setStage() {
        platforms.clear();
        if (stageNum == 0) {
            addScreenWidePlatform(PLATFORM_THICKNESS / 2 + 1);
        } else if (stageNum == 1) {
            if (bossStage) {
                setStageToBossOne();
            } else {
                setStageToRegOne();
            }
        }
    }

    // REQUIRES: platforms must be empty
    // MODIFIES: this
    // EFFECTS: Sets the stage to level one (regular)
    private void setStageToRegOne() {
        for (int i = 0; i < HEIGHT / HEIGHT_BETWEEN_PLATFORMS; i++) {
            int indexForQuarterOfHeight = HEIGHT / HEIGHT_BETWEEN_PLATFORMS / 4;
            int heightOfPlatformAtI = i * HEIGHT_BETWEEN_PLATFORMS;
            if (i == 0 || i == indexForQuarterOfHeight) {
                addScreenWidePlatform(i * HEIGHT_BETWEEN_PLATFORMS);
            } else {
                double rand = Math.random();
                if (rand < .4) {
                    addEasyPlatformConfiguration(heightOfPlatformAtI);
                } else if (rand < .8) {
                    addNormalPlatformConfiguration(heightOfPlatformAtI);
                } else {
                    addHardPlatformConfiguration(heightOfPlatformAtI);
                }
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: Sets the stage to level one (boss)
    private void setStageToBossOne() {
        Platform platform = new Platform(BOSS_STAGE_WIDTH, PLATFORM_THICKNESS,WIDTH / 2, BOSS_STAGE_HEIGHT / 4);
        platforms.add(platform);
        BossEnemy boss = new BossCat();
        bossEnemies.add(boss);
    }

    // MODIFIES: this
    // EFFECTS: Adds an easy-level platform configuration to the stage
    public void addEasyPlatformConfiguration(int y) {
        double rand = Math.random();
        if (rand < .25) {
            addWidePlatform(WIDTH / 2, y);
        } else if (rand < .5) {
            addRegularPlatform(REGULAR_PLATFORM_WIDTH / 2, y);
            addRegularPlatform(WIDTH - REGULAR_PLATFORM_WIDTH / 2, y);
        } else if (rand < .75) {
            addWidePlatform(WIDE_PLATFORM_WIDTH / 2, y);
            addNarrowPlatform(WIDTH - NARROW_PLATFORM_WIDTH / 2, y);
        } else {
            addNarrowPlatform(NARROW_PLATFORM_WIDTH / 2, y);
            addWidePlatform(WIDTH - WIDE_PLATFORM_WIDTH / 2, y);
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a normal-level platform configuration to the stage
    public void addNormalPlatformConfiguration(int y) {
        double rand = Math.random();
        if (rand < .25) {
            addRegularPlatform(WIDTH / 2, y);
        } else if (rand < .5) {
            addRegularPlatform(WIDTH / 4, y);
        } else if (rand < .75) {
            addRegularPlatform(3 * WIDTH / 4, y);
        } else {
            addNarrowPlatform(NARROW_PLATFORM_WIDTH / 2, y);
            addWidePlatform(WIDTH - WIDE_PLATFORM_WIDTH / 2, y);
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a hard-level platform configuration to the stage
    public void addHardPlatformConfiguration(int y) {
        double rand = Math.random();
        if (rand < .5) {
            addNarrowPlatform(WIDTH / 4, y);
        } else {
            addNarrowPlatform(3 * WIDTH / 4, y);
        }
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
    // EFFECTS: Add a narrow platform at the given x, y coordinates
    public void addNarrowPlatform(int x, int y) {
        Platform platform = new Platform(NARROW_PLATFORM_WIDTH, PLATFORM_THICKNESS, x, y);
        platforms.add(platform);
    }

    // REQUIRES: Platform must be within the stage
    // MODIFIES: this
    // EFFECTS: Adds a platform at the given width, height, and x, y coordinates
    public void addPlatform(int width, int height, int x, int y) {
        Platform platform = new Platform(width, height, x, y);
        platforms.add(platform);
    }

    // MODIFIES: this
    // EFFECTS: Add the given regular enemy to this stage's regular enemies with coordinates x, y
    public void addEnemy(String name, int x, int y) {
        Enemy enemy = nameToEnemy(name);
        setupAndPlaceEnemyInCorrectList(x, y, enemy);
    }


    // MODIFIES: this
    // EFFECTS: Add the given regular enemy to this stage's regular enemies with coordinates x, y at the given health
    public void addEnemy(String name, int x, int y, int health) {
        Enemy enemy = nameToEnemy(name);
        enemy.setCurrentHealth(health);
        setupAndPlaceEnemyInCorrectList(x, y, enemy);
    }

    private void setupAndPlaceEnemyInCorrectList(int x, int y, Enemy enemy) {
        enemy.setCoordX(x);
        enemy.setCoordY(y);
        if (enemy instanceof RegularEnemy) {
            regularEnemies.add(enemy);
        } else {
            bossEnemies.add(enemy);
        }
    }


    // REQUIRES: Given name must be a name of an enemy in either DJGame.REGULAR_ENEMIES or DJGame.BOSS_ENEMIES
    // EFFECTS: Returns the enemy object with the given name
    public static Enemy nameToEnemy(String name) {
        for (Enemy e: DJGame.REGULAR_ENEMIES) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        for (Enemy e: DJGame.BOSS_ENEMIES) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return new RegularCat();
    }

    // REQUIRES: Given enemy is in the list of enemies in this stage
    // MODIFIES: this
    // EFFECTS: Removes the given enemy from the list of enemies in this stage
    public void removeEnemy(Enemy enemy) {
        if (enemy instanceof RegularEnemy) {
            regularEnemies.remove(enemy);
        } else {
            bossEnemies.remove(enemy);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray platformsJsonArray = new JSONArray();
        JSONArray regularEnemiesJsonArray = new JSONArray();
        JSONArray bossEnemiesJsonArray = new JSONArray();

        for (Platform platform: platforms) {
            platformsJsonArray.put(platform.toJson());
        }

        for (Enemy enemy: regularEnemies) {
            regularEnemiesJsonArray.put(enemy.toJson());
        }

        for (Enemy enemy: bossEnemies) {
            bossEnemiesJsonArray.put(enemy.toJson());
        }

        jsonObject.put("stageNum", stageNum);
        jsonObject.put("bossStage", bossStage);
        jsonObject.put("platforms", platformsJsonArray);
        jsonObject.put("regularEnemies", regularEnemiesJsonArray);
        jsonObject.put("bossEnemies", bossEnemiesJsonArray);

        return jsonObject;
    }
}
