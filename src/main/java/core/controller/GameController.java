package core.controller;

import core.game.GameManager;

public class GameController {

    private GameManager gameManager = new GameManager();

    public GameController() { }

    public String reportBoard(int gameID) {
        return gameManager.getBoard(gameID);
    }

    public boolean makeMove(int ID, int x, int y, String playerName) {
        return gameManager.moveInGame(ID, x, y, playerName);

    }

    public boolean checkForFinishedGame(int gameID) {
        return gameManager.checkForGameOver(gameID);
    }

    public String seeInProgressGames() {
        return gameManager.getAllGamesInProgress();
    }

    public String seeFinishedGames() {
        return gameManager.getAllFinishedGames();
    }

    public int newGame(String redPlayer, String bluePlayer) {
        return gameManager.createNewGame(redPlayer, bluePlayer);
    }

    public boolean hasPlayerRegistered(String playerName){
        return gameManager.checkIfUsersExist(playerName);
    }

    public boolean registerNewPlayer(String nameOfNewUser){
        return gameManager.createNewUser(nameOfNewUser);
    }
}
