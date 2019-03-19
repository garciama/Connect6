package core.controller;

import core.game.GameManager;

public class GameController {

    private GameManager gameManager = new GameManager();

    public GameController(String redName, String blueName) {
        gameManager.createNew(redName, blueName);
    }

    public String reportBoard(int gameID){
        return gameManager.getBoard(gameID);
    }

    public boolean makeMove(int ID, int x, int y, String playerName) {
        return gameManager.moveInGame(ID, x, y, playerName);
    }
}
