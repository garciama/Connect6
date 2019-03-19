package core.controller;

import core.game.GameManager;

public class GameController {

    private GameManager mgr = new GameManager();

    public GameController(String redName, String blueName) {
        mgr.createNew(redName, blueName);
    }

    public String reportBoard(int gameID){
        return mgr.getBoard(gameID);
    }

    public void makeMove(int ID, int x, int y, String playerName){
        mgr.moveInGame(ID, x, y, playerName);
    }
}
