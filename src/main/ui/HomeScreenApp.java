package ui;

import model.Account;
import model.DJGame;
import model.EnemyList;
import model.PlayableCharacter;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// This class references CPSC210/TellerApp
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

/*
 * Represents the console-based home screen of Doge Jump
 */
public class HomeScreenApp {
    DJGame game;
    Account account;
    boolean keepGoing;
    private Scanner input;

    public HomeScreenApp() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        System.out.println("Welcome to Doge Jump!");
        loadGame();

        account = game.getAccount();
        keepGoing = true;

        while (keepGoing) {
            System.out.println("\nHello " + account.getUsername() + "!");
            System.out.println("You have selected " + game.getPlayer().getName() + ".");

            selectOption();
        }

        saveGame();

    }

    // EFFECTS: Saves the game data as a JSON file
    private void saveGame() {
        JsonWriter writer = new JsonWriter("./data/saveFile0.json");
        try {
            writer.open();
            writer.write(game);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Reads and loads JSON data from save file to game
    private void loadGame() {
        JsonReader reader = new JsonReader("./data/saveFile0.json");
        try {
            game = reader.loadToAccount();
            reader.loadToGame(game);
        } catch (IOException e) {
            System.out.println("Save file not found.");
            game = new DJGame();
            System.out.println("Please enter your username:");
            game.getAccount().setUsername(input.next());
        }
    }

    // EFFECTS: Allows user to select their next option
    public void selectOption() {
        System.out.println("Please enter one of these options:"
                + " Change username, View DogePoints,"
                + " Change character, Check or add encountered enemies, Enter game, Save and quit game");
        String nextLine = input.next();

        if (nextLine.equals("Change username")) {
            System.out.println("Enter your new username: ");
            account.setUsername(input.next());
        } else if (nextLine.equals("View DogePoints")) {
            System.out.println("You have " + account.getDogePoints() + " DogePoints!");
        } else if (nextLine.equals("Change character")) {
            changeCharacter();
        } else if (nextLine.equals("Check or add encountered enemies")) {
            checkAndAddEncounteredEnemies();
        } else if (nextLine.equals("Enter game")) {
            enterGame();
        } else if (nextLine.equals("Save and quit game")) {
            System.out.println("Thanks for playing!");
            keepGoing = false;
        } else {
            System.out.println("Not available.");
        }

    }

    // EFFECTS: Allows the user to select a character
    public void changeCharacter() {
        List<String> availableCharacters = new ArrayList<>();
        for (PlayableCharacter p: PlayableCharacter.values()) {
            availableCharacters.add(p.getName());
        }
        System.out.println("Enter the character name "
                + "(Character will not change unless entry is in the list below):");
        System.out.println("Available characters: " + placeCommaOrAndInBetweenStrings(availableCharacters));
        game.setCharacter(input.next());
    }

    // EFFECTS: Lists the enemies in the user's list of encountered enemies
    private void checkEncounteredEnemies() {
        EnemyList enemyList = account.getEncounteredEnemies();
        if (enemyList.size() == 0) {
            System.out.println("You have no enemies in your list.");
        } else {
            List<String> listOfEnemyName = new ArrayList<>();
            for (int i = 0; i < enemyList.size(); i++) {
                listOfEnemyName.add(enemyList.getEnemy(i).getName());
            }
            System.out.println("Your encountered enemies: " + placeCommaOrAndInBetweenStrings(listOfEnemyName));
        }
    }

    // EFFECTS: Allows user to add new enemies to their list of encountered enemies
    private void checkAndAddEncounteredEnemies() {
        checkEncounteredEnemies();

        System.out.println("Do you want to add an enemy to the list? (Y for yes, any other key to exit)");
        String wantToAdd = input.next();

        while (wantToAdd.equals("Y")) {
            System.out.println("Here is the list of regular enemies in the game: "
                    + placeCommaOrAndInBetweenStrings(account.getRegularEnemyNames()));
            System.out.println("Here is the list of boss enemies in the game: "
                    + placeCommaOrAndInBetweenStrings(account.getBossEnemyNames()));
            System.out.println("Which enemy do you want to add? "
                    + "(The entry will not be added unless it is found in the list above)");
            String addedEnemyName = input.next();
            account.addEncounteredEnemy(addedEnemyName);

            checkEncounteredEnemies();
            System.out.println("Do you want to add more enemies? (Y for yes, any other key to exit)");
            wantToAdd = input.next();
        }
    }

    // EFFECTS: Enter the game (only a message since game is incomplete)
    private void enterGame() {
        System.out.println("The game will be available in Phase 3!");
    }


    // EFFECTS: Places commas, spaces, and "and" between each string properly
    public String placeCommaOrAndInBetweenStrings(List<String> listOfString) {
        return placeCommaOrAndInBetweenStringsHelper(listOfString, true);
    }

    // REQUIRES: isFirstRecursion must be true, listOfString must be an ArrayList
    // EFFECTS: Places commas, spaces, and "and" between each string properly (with isFirstRecursion parameter)
    public String placeCommaOrAndInBetweenStringsHelper(List<String> listOfString, boolean isFirstRecursion) {
        String result = "";
        int listSize = listOfString.size();
        if (listSize == 1) {
            result = listOfString.get(0);
        } else if (listSize == 2) {
            if (isFirstRecursion) {
                result = result.concat(listOfString.get(0) + " and " + listOfString.get(1));
            } else {
                result = result.concat(listOfString.get(0) + ", and " + listOfString.get(1));
            }
        } else if (listSize > 2) {
            result = result.concat(listOfString.remove(0) + ", "
                    + placeCommaOrAndInBetweenStringsHelper(listOfString, false));
        }

        return result;
    }
}
