package core.game;
import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private int gameIDCount = 0;
    private Map<Integer, Game> allGameMap;

    public GameManager(){
        allGameMap = new HashMap<>();
    }

    public void createNew(String redName, String blueName) {
        Game g = new Game(gameIDCount, redName, blueName);

        while (allGameMap.putIfAbsent(gameIDCount, g) != null) {
            gameIDCount++;
        }
    }

    public String getBoard(int id){
        if(!allGameMap.containsKey(id))
            return "no games found";
        else
            return (allGameMap.get(id).getBoard());

        }

    public boolean moveInGame(int ID, int x, int y, String playerName){
        if(allGameMap.get(ID).makeMove(x, y, playerName))
            return true;
        else
            return false;
    }



}
