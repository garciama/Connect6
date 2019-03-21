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
    }

    public int createNew(String redName, String blueName) {
        Game g = new Game(gameIDCount, redName, blueName);

        allUsers = new HashMap<>();

        User player1 = new User(redName, Color.Red);
        User player2 = new User(blueName, Color.Blue);

        if (allUsers.get(redName) == null) {
            System.out.println("Error: This player doesn't exist! Create a new user first.\n");
            return -1;
        }

        if (allUsers.get(blueName) == null) {
            System.out.println("Error: This player doesn't exist! Create a new user first.\n");
            return -1;
        }



        while (allGameMap.putIfAbsent(gameIDCount, g) != null) {
            gameIDCount++;
        }
        return gameIDCount;
    }

    public boolean checkIfUsersExist(String playerName){
        return (allUsers.get(playerName) != null);
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

    public boolean moveInGame(int ID, int x, int y, String playerName){
        return allGameMap.get(ID).makeMove(x, y, playerName);
        }


}
