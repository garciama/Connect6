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

        if (!checkIfUsersExist(redName) || !checkIfUsersExist(blueName)) {
            System.err.println("This player doesn't exist! Create a new user first.");
            return -7;
        }

        Game g = new Game(gameIDCount, allUsers.get(redName), allUsers.get(blueName));
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

    public String leaderboardToString(){
        StringBuilder sb = new StringBuilder();
        Map<String, User> sortedUserByScore = sortMap();



//        for (String key : sortedUserByScore.keySet()){
//            System.out.println(sortedUserByScore.get(key).getName() + " " + sortedUserByScore.get(key).getScore()
//            + " " + sortedUserByScore.get(key).getWins() + " " + sortedUserByScore.get(key).getLosses() +
//                    " " + sortedUserByScore.get(key).getTies());
//        }

        return buildLeaderBoard(sortedUserByScore);
    }

    private String buildLeaderBoard(Map<String, User> boardStrings) {
        //Widths for the columns, easily change later if needed!
        int rowNameWidth = 12; //Make sure this >  than username min length!
        int rowScoreWidth = 11;
        int rowWinsWidth = 10;
        int rowLossesWidth = 10;
        int rowTiesWidth = 10;
        int total = rowNameWidth + rowScoreWidth + rowWinsWidth + rowLossesWidth + rowTiesWidth;

        StringBuilder s = new StringBuilder();

        for (int i = 0 ; i < total + 6; i++)
            s.append("-");

        s.append("\n|");
        leaderboardSpaceAppend("Name", rowNameWidth, s, false);
        s.append("Name");
        leaderboardSpaceAppend("Name", rowNameWidth, s, true);
        s.append("|");
        leaderboardSpaceAppend("Score", rowScoreWidth, s, false);
        s.append("Score");
        leaderboardSpaceAppend("Score", rowScoreWidth, s, true);
        s.append("|");
        leaderboardSpaceAppend("Wins", rowWinsWidth, s, false);
        s.append("Wins");
        leaderboardSpaceAppend("Wins", rowWinsWidth, s, true);
        s.append("|");
        leaderboardSpaceAppend("Ties", rowTiesWidth, s, false);
        s.append("Ties");
        leaderboardSpaceAppend("Ties", rowTiesWidth, s, true);
        s.append("|");
        leaderboardSpaceAppend("Losses", rowLossesWidth, s, false);
        s.append("Losses");
        leaderboardSpaceAppend("Losses", rowLossesWidth, s, true);
        s.append("|\n");


        //Add 6 for | in between
        for (int i = 0; i < total + 6; i++){
            s.append("-");
        }
        s.append("\n");


        //Now build the leaderboard string
        for (String key: boardStrings.keySet()){
            String rowName = boardStrings.get(key).getName();
            String rowScore = Integer.toString(boardStrings.get(key).getScore());
            String rowWins = Integer.toString(boardStrings.get(key).getWins());
            String rowLosses = Integer.toString(boardStrings.get(key).getLosses());
            String rowTies = Integer.toString(boardStrings.get(key).getTies());

            s.append("|");

            leaderboardSpaceAppend(rowName, rowNameWidth, s, false);
            s.append(rowName);
            leaderboardSpaceAppend(rowName, rowNameWidth, s, true);

            s.append("|");

            leaderboardSpaceAppend(rowScore, rowScoreWidth, s, false);
            s.append(rowScore);
            leaderboardSpaceAppend(rowScore, rowScoreWidth, s, true);

            s.append("|");

            leaderboardSpaceAppend(rowWins, rowWinsWidth, s, false);
            s.append(rowWins);
            leaderboardSpaceAppend(rowWins, rowWinsWidth, s, true);

            s.append("|");

            leaderboardSpaceAppend(rowLosses, rowLossesWidth, s, false);
            s.append(rowLosses);
            leaderboardSpaceAppend(rowLosses, rowLossesWidth, s, true);

            s.append("|");

            leaderboardSpaceAppend(rowTies, rowTiesWidth, s, false);
            s.append(rowTies);
            leaderboardSpaceAppend(rowTies, rowTiesWidth, s, true);

            s.append("|\n");

        }

        for (int i = 0 ; i < total + 6; i++)
            s.append("-");

        return s.toString();
    }

    private void leaderboardSpaceAppend(String col, int width, StringBuilder sb, boolean secondHalf){
        for (int i = 0; i < (width - col.length()) / 2; i++)
            sb.append(" ");

        //Append another space on second half for special cases
        if (secondHalf && ((col.length() % 2 == 1 && width % 2 == 0) ||
                (col.length() % 2 == 0 && width % 2 == 1)))
            sb.append(" ");

    }

    private Map<String, User> sortMap(){

        //Convert userScores to list
        List<Map.Entry<String, User>> list =
                new LinkedList<>(allUsers.entrySet());

        //Sort the list by the user score
        Collections.sort(list, new Comparator<Map.Entry<String, User>>() {
            public int compare(Map.Entry<String, User> o1, Map.Entry<String, User> o2) {
                return ((Integer)o1.getValue().getScore()).compareTo(o2.getValue().getScore());
            }
        });

        Collections.reverse(list);


        //Convert back to map
        Map<String, User> sortedUserMap = new LinkedHashMap<>();
        for (Map.Entry<String, User> entry : list) {
            sortedUserMap.put(entry.getKey(), entry.getValue());
        }





        return sortedUserMap;

    }

    public Map<String, User> getAllUsers(){
        return allUsers;
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
