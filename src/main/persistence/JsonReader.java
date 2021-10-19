package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// This class references CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

/*
 * Represents a reader that converts JSON data stored in the save file to DJGame data
 */
public class JsonReader {
    private String source;

    // EFFECTS: Creates a new reader that reads the given source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads the JSON file and returns a String of JSON text
    private String readFile() throws IOException {
        StringBuilder jsonText = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(jsonText::append);
        }
        return jsonText.toString();
    }

    // EFFECTS: Reads the JSON file at this reader's source and returns a new game state with the loaded account
    public DJGame loadToAccount() throws IOException {
        DJGame game = new DJGame();
        String jsonText = readFile();
        JSONObject jsonObject = new JSONObject(jsonText);
        game.setAccount(parseAccount(jsonObject));
        return game;
    }

    // EFFECTS: Reads the JSON file at this reader's source and returns the loaded game (except for Account)
    public DJGame loadToGame(DJGame game) throws IOException {
        String jsonText = readFile();
        JSONObject jsonObject = new JSONObject(jsonText);
        return parseGame(game, jsonObject);
    }

    // EFFECTS: Parses account from the given JSONObject and loads it into the given game
    private Account parseAccount(JSONObject jsonObject) {
        Account account = new Account();
        JSONObject accountJsonObject = jsonObject.getJSONObject("account");

        account.setUsername(accountJsonObject.getString("username"));
        account.setDogePoints(accountJsonObject.getInt("dogePoints"));
        account.setNextStageNum(accountJsonObject.getInt("nextStageNum"));
        parseEncounteredEnemies(account, accountJsonObject);
        return account;
    }

    // MODIFIES: account
    // EFFECTS: Parses encountered enemies from the given JSONObject and loads it into the given account
    private void parseEncounteredEnemies(Account account, JSONObject accountJsonObject) {
        JSONArray jsonArray = accountJsonObject.getJSONArray("encounteredEnemies");
        for (Object name: jsonArray) {
            account.addEncounteredEnemy((String) name);
        }
    }

    // MODIFIES: game
    // EFFECTS: Parses DJGame (except for the account) from the given JSONObject and loads it into the given game
    private DJGame parseGame(DJGame game, JSONObject jsonObject) {
        parsePlayer(game, jsonObject);
        parseStage(game, jsonObject);
        parseProjectiles(game, jsonObject);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: Parses player from the given JSONObject and loads it into the game
    private void parsePlayer(DJGame game, JSONObject jsonObject) {
        JSONObject playerJsonObject = jsonObject.getJSONObject("player");
        game.setCharacter(playerJsonObject.getString("name"));

        Player player = game.getPlayer();
        player.setCurrentHealth(playerJsonObject.getInt("currentHealth"));
        player.setCoordX(playerJsonObject.getInt("coordX"));
        player.setCoordY(playerJsonObject.getInt("coordY"));
        player.setDx(playerJsonObject.getInt("dx"));
        player.setDy(playerJsonObject.getInt("dy"));
    }

    // MODIFIES: game
    // EFFECTS: Parses stage from the given JSONObject and loads it into the game
    private void parseStage(DJGame game, JSONObject jsonObject) {
        Stage stage = game.getStage();
        JSONObject stageJsonObject = jsonObject.getJSONObject("stage");

        stage.setStageNum(stageJsonObject.getInt("stageNum"));
        stage.setBossStage(stageJsonObject.getBoolean("bossStage"));
        parseStagePlatforms(stage, stageJsonObject);
        parseStageRegularEnemies(stage, stageJsonObject);
        parseStageBossEnemies(stage, stageJsonObject);
    }

    // MODIFIES: stage
    // EFFECTS: Parses stage platforms from the given JSONObject and loads it into the game
    private void parseStagePlatforms(Stage stage, JSONObject stageJsonObject) {
        JSONArray platformsJsonArray = stageJsonObject.getJSONArray("platforms");
        for (Object platform: platformsJsonArray) {
            JSONObject platformJsonArray = (JSONObject) platform;
            stage.addPlatform(
                    platformJsonArray.getInt("width"),
                    platformJsonArray.getInt("height"),
                    platformJsonArray.getInt("coordX"),
                    platformJsonArray.getInt("coordY")
            );
        }
    }

    // MODIFIES: stage
    // EFFECTS: Parses regular stage enemies from the given JSONObject and loads it into the stage
    private void parseStageRegularEnemies(Stage stage, JSONObject stageJsonObject) {
        JSONArray enemiesJsonArray = stageJsonObject.getJSONArray("regularEnemies");
        for (Object enemy: enemiesJsonArray) {
            JSONObject enemyJsonObject = (JSONObject) enemy;
            stage.addEnemy(
                    enemyJsonObject.getString("name"),
                    enemyJsonObject.getInt("coordX"),
                    enemyJsonObject.getInt("coordY"),
                    enemyJsonObject.getInt("health")
            );
        }
    }

    // MODIFIES: stage
    // EFFECTS: Parses boss stage enemies from the given JSONObject and loads it into the stage
    private void parseStageBossEnemies(Stage stage, JSONObject stageJsonObject) {
        JSONArray enemiesJsonArray = stageJsonObject.getJSONArray("bossEnemies");
        for (Object enemy: enemiesJsonArray) {
            JSONObject enemyJsonObject = (JSONObject) enemy;
            stage.addEnemy(
                    enemyJsonObject.getString("name"),
                    enemyJsonObject.getInt("coordX"),
                    enemyJsonObject.getInt("coordY"),
                    enemyJsonObject.getInt("health")
            );
        }
    }

    // MODIFIES: game
    // EFFECTS: Parses projectiles from the given JSONObject and loads them into the game
    private void parseProjectiles(DJGame game, JSONObject jsonObject) {
        JSONArray projectilesJsonArray = jsonObject.getJSONArray("projectiles");
        List<Projectile> projectileList = new ArrayList<>();
        for (Object projectile: projectilesJsonArray) {
            JSONObject projectileJsonObject = (JSONObject) projectile;
            projectileList.add(new Projectile(
                    projectileJsonObject.getString("type"),
                    projectileJsonObject.getInt("width"),
                    projectileJsonObject.getInt("height"),
                    projectileJsonObject.getInt("coordX"),
                    projectileJsonObject.getInt("coordY"),
                    projectileJsonObject.getInt("dx"),
                    projectileJsonObject.getInt("dy")
            ));
        }
        game.addProjectiles(projectileList);
    }
}
