package core.game;
import core.user.User;

import java.util.*;

public class GameManager{

    private int gameIDCount = 0;
    private Map<Integer, Game> allGameMap;
    private Map<String, User> allUsers;

    public GameManager() {
        allGameMap = new HashMap<>();
        allUsers = new HashMap<>();
    }

    public Game getInstanceOfGame(int gameID) {
        return allGameMap.get(gameID);
    }

    public int createNewGame(String redName, String blueName) {
        gameIDCount++;
        Game g = new Game(gameIDCount, redName, blueName);
        System.out.println("gameIDCount = " + gameIDCount);
        allGameMap.put(gameIDCount,g);

        return gameIDCount;
    }

    public boolean checkIfUsersExist(String playerName){
        if (allUsers.get(playerName) != null)
            return true;
        else
            return false;
    }

    public boolean createNewUser(String nameOfNewPlayer){
        if (nameOfNewPlayer.length() > 12) {
            System.out.println("Error: Name too long!");

            return false;
        }

        User newPlayer = new User(nameOfNewPlayer);
        if (allUsers.putIfAbsent(nameOfNewPlayer, newPlayer) == null)
            return true;

        System.out.println("Error: Player already exists! Enter another name");
        return false;

    }

    public String displayLeaderboard(){
        StringBuilder sb = new StringBuilder();
        Map<String, User> sortedUserByScore = prepMap();


        for (String key : sortedUserByScore.keySet()){
            System.out.println(sortedUserByScore.get(key).getName() + " " + sortedUserByScore.get(key).getScore());
        }



        return null;
    }

//    private String buildLeaderBoard(List<LeaderboardString> boardStrings, StringBuilder s){
//        String header = "|    Name    |   Score   |   Wins   |   Loss   |   Ties   |";
//        s.append(header + "\n");
//
//        for (int i = 0; i < header.length(); i++){
//            s.append("-");
//        }
//        s.append("\n");
//
//
//        for (int i = 0; i < boardStrings.size(); i++){
//            s.append(boardStrings.get(i).getName() + "\n");
//
//        }
//        return s.toString();
//    }

    private Map<String, User> prepMap(){

        for (String key : allUsers.keySet()){
            System.out.println(allUsers.get(key).getName() + " " + allUsers.get(key).getScore());
        }

        //Convert userScores to list
        List<Map.Entry<String, User>> list =
                new LinkedList<>(allUsers.entrySet());

        //Sort the list by the user score
        Collections.sort(list, new Comparator<Map.Entry<String, User>>() {
            public int compare(Map.Entry<String, User> o1, Map.Entry<String, User> o2) {
                return ((Integer)o1.getValue().getScore()).compareTo(o2.getValue().getScore());
            }
        });


        //Convert back to map
        Map<String, User> sortedUserMap = new LinkedHashMap<>();
        for (Map.Entry<String, User> entry : list) {
            sortedUserMap.put(entry.getKey(), entry.getValue());
        }





        return sortedUserMap;

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
