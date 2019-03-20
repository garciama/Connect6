package core.game;
import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private int gameIDCount = 0;
    private Map<Integer, Game> allGameMap;

    public GameManager(){
        allGameMap = new HashMap<>();
    }

    public int createNew(String redName, String blueName) {
        Game g = new Game(gameIDCount, redName, blueName);

        while (allGameMap.putIfAbsent(gameIDCount, g) != null) {
            gameIDCount++;
        }
        return gameIDCount;
    }

    public String getBoard(int id){
        if(!allGameMap.containsKey(id))
            return "no games found";
        else
            return (allGameMap.get(id).getBoard());
    }

    public boolean moveInGame(int ID, int x, int y, String playerName){
        return allGameMap.get(ID).makeMove(x, y, playerName);
        }





}
