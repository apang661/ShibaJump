package ui;

import model.SJGame;

public class Main {
    public static void main(String[] args) {
        SJGame game = new SJGame();
        new GameWindow(game);
    }
}
