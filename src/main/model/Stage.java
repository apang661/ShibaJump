package model;

import model.bossenemies.BossCat;
import model.bossenemies.BossEnemy;
import model.exceptions.UnknownEnemyException;
import model.regularenemies.RegularCat;
import model.regularenemies.RegularEnemy;
import model.regularenemies.RegularRat;
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
    public static final int HEIGHT_BETWEEN_PLATFORMS = 150;
    public static final double TIME_BETWEEN_PLATFORMS = 0.4; // in seconds
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
            = Math.sqrt(-2 * Stage.GRAVITY_ACCELERATION * Stage.HEIGHT_BETWEEN_PLATFORMS)
            + Player.PlayableCharacter.MIN_HEIGHT;

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
                setStageToRegN();
            }
        } else { // stage 2
            setStageToRegN();
        }
    }

    // REQUIRES: platforms must be empty
    // MODIFIES: this
    // EFFECTS: Sets the stage to the regular stage of stageNum
    private void setStageToRegN() {
        double easyPlatformProbability = getEasyPlatformProbability();
        double normalPlatformProbability = getNormalPlatformProbability();

        for (int i = 0; i < HEIGHT / HEIGHT_BETWEEN_PLATFORMS; i++) {
            int indexForQuarterOfHeight = HEIGHT / HEIGHT_BETWEEN_PLATFORMS / 4;
            int heightOfPlatformAtI = i * HEIGHT_BETWEEN_PLATFORMS;
            if (i == 0) {
                addScreenWidePlatform(0);
            } else if (i % indexForQuarterOfHeight == 0 && stageNum == 1) {
                addScreenWidePlatform(i * HEIGHT_BETWEEN_PLATFORMS);
            } else {
                addRandomPlatformForStageNum(easyPlatformProbability,
                        normalPlatformProbability, i, heightOfPlatformAtI);
            }
        }
    }

    private void addRandomPlatformForStageNum(double easyPlatformProbability, double normalPlatformProbability,
                                              int i, int heightOfPlatformAtI) {
        double stageRand = Math.random();
        double enemyRand = Math.random();
        List<Integer> enemyLocations;

        if (stageRand < easyPlatformProbability) {
            enemyLocations = addEasyPlatformConfiguration(heightOfPlatformAtI);
        } else if (stageRand < easyPlatformProbability + normalPlatformProbability) {
            enemyLocations = addNormalPlatformConfiguration(heightOfPlatformAtI);
        } else {
            enemyLocations = addHardPlatformConfiguration(heightOfPlatformAtI);
        }
        if (i > 2) {
            addEnemyStageReg(enemyLocations, heightOfPlatformAtI, enemyRand);
        }
    }


    private double getEasyPlatformProbability() {
        if (stageNum == 1) {
            return 0.4;
        } else {
            return 0.2;
        }
    }

    private double getNormalPlatformProbability() {
        if (stageNum == 1) {
            return 0.4;
        } else {
            return 0.5;
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

    // REQUIRES: Given rand value is between 0 and 1, this.isBossStage must be false
    // MODIFIES: this
    // EFFECTS: If rand < .4 (50% chance) and enemyLocations is not empty,
    //              - randomly choose one of the given enemy locations
    //              - place a randomly chosen enemy at the location chosen above
    private void addEnemyStageReg(List<Integer> enemyLocations, int heightOfPlatformAtI, double rand) {
        if (!enemyLocations.isEmpty() && (rand < .5)) {
            double locationRand = Math.random(); // chooses location of enemy
            double enemyRand = Math.random(); // chooses which enemy to add

            int numOfLocations = enemyLocations.size();
            int coordX = 0;

            for (int i = 0; i < numOfLocations; i++) {
                if (locationRand < (i + 1) / (double) numOfLocations && locationRand >= i / (double) numOfLocations) {
                    coordX = enemyLocations.get(i);
                }
            }
            if (stageNum == 1) {
                spawnEnemyStage1Reg(heightOfPlatformAtI, enemyRand, coordX);
            } else { // stage 2
                spawnEnemyStage2Reg(heightOfPlatformAtI, enemyRand, coordX);
            }
        }
    }


    private void spawnEnemyStage1Reg(int heightOfPlatformAtI, double enemyRand, int coordX) {
        if (enemyRand < .5) {
            addEnemy("Rat", coordX, heightOfPlatformAtI + HEIGHT_BETWEEN_PLATFORMS / 2);
        } else {
            addEnemy("Cat", coordX, heightOfPlatformAtI + HEIGHT_BETWEEN_PLATFORMS / 2);
        }
    }

    private void spawnEnemyStage2Reg(int heightOfPlatformAtI, double enemyRand, int coordX) {
        if (enemyRand < .7) {
            addEnemy("Rat", coordX, heightOfPlatformAtI + HEIGHT_BETWEEN_PLATFORMS / 2);  // *** test this
        } else {
            addEnemy("Cat", coordX, heightOfPlatformAtI + HEIGHT_BETWEEN_PLATFORMS / 2);
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds an easy-level platform configuration to the stage and returns a list of possible enemy locations
    public List<Integer> addEasyPlatformConfiguration(int y) {
        double rand = Math.random();
        List<Integer> enemyLocations = new ArrayList<>();

        if (rand < .25) {
            addWidePlatform(WIDTH / 2, y);
        } else if (rand < .5) {
            addRegularPlatform(REGULAR_PLATFORM_WIDTH / 2, y);
            addRegularPlatform(WIDTH - REGULAR_PLATFORM_WIDTH / 2, y);
            enemyLocations.add(REGULAR_PLATFORM_WIDTH / 2);
            enemyLocations.add(WIDTH - REGULAR_PLATFORM_WIDTH / 2);
        } else if (rand < .75) {
            addWidePlatform(WIDE_PLATFORM_WIDTH / 2, y);
            addNarrowPlatform(WIDTH - NARROW_PLATFORM_WIDTH / 2, y);
            enemyLocations.add(WIDE_PLATFORM_WIDTH / 2);
            enemyLocations.add(WIDTH - NARROW_PLATFORM_WIDTH / 2);
        } else {
            addNarrowPlatform(NARROW_PLATFORM_WIDTH / 2, y);
            addWidePlatform(WIDTH - WIDE_PLATFORM_WIDTH / 2, y);
            enemyLocations.add(NARROW_PLATFORM_WIDTH / 2);
            enemyLocations.add(WIDTH - WIDE_PLATFORM_WIDTH / 2);
        }

        return enemyLocations;
    }

    // MODIFIES: this
    // EFFECTS: Adds a normal-level platform configuration to the stage and returns a list of possible enemy locations
    public List<Integer> addNormalPlatformConfiguration(int y) {
        double rand = Math.random();
        List<Integer> enemyLocations = new ArrayList<>();

        if (rand < .25) {
            addRegularPlatform(WIDTH / 2, y);
            enemyLocations.add(WIDTH / 2);
        } else if (rand < .5) {
            addRegularPlatform(WIDTH / 4, y);
            enemyLocations.add(WIDTH / 4);
        } else if (rand < .75) {
            addRegularPlatform(3 * WIDTH / 4, y);
            enemyLocations.add(3 * WIDTH / 4);
        } else {
            addNarrowPlatform(NARROW_PLATFORM_WIDTH / 2, y);
            addWidePlatform(WIDTH - WIDE_PLATFORM_WIDTH / 2, y);
            enemyLocations.add(NARROW_PLATFORM_WIDTH / 2);
            enemyLocations.add(WIDTH - WIDE_PLATFORM_WIDTH / 2);

        }
        return enemyLocations;
    }

    // MODIFIES: this
    // EFFECTS: Adds a hard-level platform configuration to the stage and returns a list of possible enemy locations
    public List<Integer> addHardPlatformConfiguration(int y) {
        double rand = Math.random();
        List<Integer> enemyLocations = new ArrayList<>();

        if (rand < .5) {
            addNarrowPlatform(WIDTH / 4, y);
            enemyLocations.add(WIDTH / 4);
        } else {
            addNarrowPlatform(3 * WIDTH / 4, y);
            enemyLocations.add(3 * WIDTH / 4);
        }
        return enemyLocations;
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
    //          If the given name is not a name of an enemy in the game, do nothing
    public void addEnemy(String name, int x, int y) {
        try {
            Enemy enemy = nameToEnemy(name);
            setupAndPlaceEnemyInCorrectList(x, y, enemy);
        } catch (UnknownEnemyException e) {
            // will not add a new enemy
        }
    }

    // MODIFIES: this
    // EFFECTS: Add enemy of the given name to this stage's regular enemies with coordinates x, y at the given health
    //          If the given name is not a name of an enemy in the game, do nothing
    public void addEnemy(String name, int x, int y, int health) {
        try {
            Enemy enemy = nameToEnemy(name);
            enemy.setCurrentHealth(health);
            setupAndPlaceEnemyInCorrectList(x, y, enemy);
        } catch (UnknownEnemyException e) {
            // will not add a new enemy
        }
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


    // EFFECTS: Returns the enemy object with the given name
    //          throws UnknownEnemyException if the given name is not a name of a valid enemy
    public static Enemy nameToEnemy(String name) throws UnknownEnemyException {
        switch (name) {
            case ("Cat"): {
                return new RegularCat();
            }
            case ("Rat"): {
                return new RegularRat();
            }
            case ("Evil Cat"): {
                return new BossCat();
            }
            default: {
                throw new UnknownEnemyException();
            }
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
