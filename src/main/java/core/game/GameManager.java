package core.game;
import core.Color;
import core.user.User;

import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private int gameIDCount = 0;
    private Map<Integer, Game> allGameMap;
    private Map<String, User> allUsers;

    public GameManager(){
        allGameMap = new HashMap<>();
        allUsers = new HashMap<>();
    }

    public int createNewGame(String redName, String blueName) {
        Game g = new Game(gameIDCount, redName, blueName);

        while (allGameMap.putIfAbsent(gameIDCount, g) != null)
            gameIDCount++;

        return gameIDCount;
    }

    public boolean checkIfUsersExist(String playerName){
        if (allUsers.get(playerName) != null)
            return true;
        else
            return false;
    }

    public boolean createNewUser(String nameOfNewPlayer){
        User newPlayer = new User(nameOfNewPlayer);
        if (allUsers.putIfAbsent(nameOfNewPlayer, newPlayer) == null)
            return true;

        return false;

    }

    public String getBoard(int id){
        if(!allGameMap.containsKey(id))
            return "no games found";
        else
            return (allGameMap.get(id).getBoard());
    }

    public boolean checkForGameOver(int gameID) {
        return allGameMap.get(gameID).getGameStatus();
    }

    /**
     * Loops through all of the games in allGameMap and if the game has not finished then that games information,
     * the ID and each player's name, is added to a string to be returned
     * @return string representation of every in progress game, including the gameID and each players name
     */
    public String getAllGamesInProgress() {
        StringBuilder str = new StringBuilder();
        for (Game g : allGameMap.values()) {
            if (g.getGameStatus() == false) {
                str.append(getGameInfo(g));
            }
        }
        return str.toString();
    }

    /**
     * Loops through all of the games in allGameMap and if the game has been completed then that games information,
     * the ID and each player's name, is added to a string to be returned
     * @return string representation of every finished game, including the gameID and each players name
     */
    public String getAllFinishedGames() {
        StringBuilder str = new StringBuilder();
        for (Game g : allGameMap.values()) {
            if (g.getGameStatus() == true) {
                str.append(getGameInfo(g));
            }
        }
        return str.toString();
    }

    /**
     * Helper method for getAllGamesInProgress and getAllFinishedGames that returns a string with a game's information
     * with the ID and player names
     * @param g the game to get the info from
     * @return string representation of the passed in game, including the gameID and each players name
     */
    private String getGameInfo(Game g) {
        int id = g.gameID;
        String redPlayer = g.redPlayer.getName();
        String bluePlayer = g.bluePlayer.getName();
        return String.format("%s %s %s\n", Integer.toString(id), redPlayer, bluePlayer);
    }

    public boolean moveInGame(int ID, int x, int y, String playerName){
        return allGameMap.get(ID).makeMove(x, y, playerName);
    }

    public String  getLastUserToMakeMove(int id){
        Game g = allGameMap.get(id);
        String lastUserToMakeMove = g.lastUserToMakeMove();
        return lastUserToMakeMove;
    }

}
