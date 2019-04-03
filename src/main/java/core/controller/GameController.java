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

    public int newPublicGame(String redPlayer, String bluePlayer) {
        return gameManager.createNewGame(redPlayer, bluePlayer, true);
    }

    public int newPrivateGame(String redPlayer, String bluePlayer) {
        return gameManager.createNewGame(redPlayer, bluePlayer, false);
    }

    public boolean hasPlayerRegistered(String playerName) {
        return gameManager.checkIfUsersExist(playerName);
    }

    public boolean registerNewPlayer(String nameOfNewUser) {
        return gameManager.createNewUser(nameOfNewUser);
    }

    public String getLeaderBoard() {
        return gameManager.leaderboardToString();
    }

<<<<<<< HEAD
    public boolean playerHasPutDownPiece(int id, String name){
        return gameManager.hasPutDownPiece(id, name);
    }

    public String getUserNameRed(int id){ return gameManager.playerNameInGameRed(id); }
=======
    public String getUserNameRed(int id) { return gameManager.playerNameInGameRed(id); }
>>>>>>> 039b6f0a427b13db8aa3fcd8db872f9e662d28b6

    public String getUserNameBlue(int id) { return gameManager.playerNameInGameBlue(id); }

<<<<<<< HEAD
    public String userCurrentTurn( int id){ return gameManager.getUserCurrentTurn(id); }
=======
    public String lastUserToMakeMove( int id) { return gameManager.getLastUserToMakeMove(id); }
>>>>>>> 039b6f0a427b13db8aa3fcd8db872f9e662d28b6

}
