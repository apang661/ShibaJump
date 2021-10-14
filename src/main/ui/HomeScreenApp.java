package ui;

import model.DJGame;
import model.EnemyList;

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
    boolean keepGoing;
    private Scanner input;

    public HomeScreenApp() {
        game = new DJGame();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        keepGoing = true;

        System.out.println("Welcome to Doge Jump!");
        System.out.println("Please enter your username:");
        game.setUsername(input.next());
        while (keepGoing) {
            System.out.println("\nHello " + game.getUsername() + "!");
            System.out.println("You have selected " + game.getPlayer().getName() + ".");

            selectOption();
        }
    }

    // EFFECTS: Allows user to select their next option
    public void selectOption() {
        System.out.println("Please enter one of these options:"
                + " Change username, View DogePoints,"
                + " Change character, Check or add encountered enemies, Enter game, Quit game");
        String nextLine = input.next();

        if (nextLine.equals("Change username")) {
            System.out.println("Enter your new username: ");
            game.setUsername(input.next());
        } else if (nextLine.equals("View DogePoints")) {
            System.out.println("You have " + game.getDogePoints() + " DogePoints!");
        } else if (nextLine.equals("Change character")) {
            changeCharacter();
        } else if (nextLine.equals("Check or add encountered enemies")) {
            checkAndAddEncounteredEnemies();
        } else if (nextLine.equals("Enter game")) {
            enterGame();
        } else if (nextLine.equals("Quit game")) {
            System.out.println("Thanks for playing!");
            keepGoing = false;
        } else {
            System.out.println("Not available.");
        }

    }

    // EFFECTS: Allows the user to select a character
    public void changeCharacter() {
        List<String> availableCharacters = new ArrayList<>();
        for (int i = 0; i < DJGame.CHARACTERS.size(); i++) {
            availableCharacters.add(DJGame.CHARACTERS.get(i).getName());
        }
        System.out.println("Enter the character name "
                + "(Character will not change unless entry is in the list below):");
        System.out.println("Available characters: " + placeCommaOrAndInBetweenStrings(availableCharacters, true));
        game.setCharacter(input.next());
    }

    // EFFECTS: Lists the enemies in the user's list of encountered enemies
    private void checkEncounteredEnemies() {
        EnemyList enemyList = game.getEncounteredEnemies();
        if (enemyList.size() == 0) {
            System.out.println("You have no enemies in your list.");
        } else {
            List<String> listOfEnemyName = new ArrayList<>();
            for (int i = 0; i < enemyList.size(); i++) {
                listOfEnemyName.add(enemyList.getEnemy(i).getName());
            }
            System.out.println("Your encountered enemies: " + placeCommaOrAndInBetweenStrings(listOfEnemyName, true));
        }
    }

    // EFFECTS: Allows user to add new enemies to their list of encountered enemies
    private void checkAndAddEncounteredEnemies() {
        checkEncounteredEnemies();

        System.out.println("Do you want to add an enemy to the list? (Y for yes, any other key to exit)");
        String wantToAdd = input.next();

        while (wantToAdd.equals("Y")) {
            System.out.println("Here is the list of regular enemies in the game: "
                    + placeCommaOrAndInBetweenStrings(game.getRegularEnemyNames(), true));
            System.out.println("Here is the list of boss enemies in the game: "
                    + placeCommaOrAndInBetweenStrings(game.getBossEnemyNames(), true));
            System.out.println("Which enemy do you want to add? "
                    + "(The entry will not be added unless it is found in the list above)");
            String addedEnemyName = input.next();
            game.addEncounteredEnemy(addedEnemyName);

            checkEncounteredEnemies();
            System.out.println("Do you want to add more enemies? (Y/N)");
            wantToAdd = input.next();
        }
    }

    // EFFECTS: Enter the game (only a message since game is incomplete)
    private void enterGame() {
        System.out.println("The game will be available in Phase 3!");
    }

    // REQUIRES: isFirstRecursion must be true, listOfString must be an ArrayList
    // MODIFIES: listOfString
    // EFFECTS: Places commas, spaces, and "and" between each string properly
    public String placeCommaOrAndInBetweenStrings(List<String> listOfString, boolean isFirstRecursion) {
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
                    + placeCommaOrAndInBetweenStrings(listOfString, false));
        }

        return result;
    }
}
