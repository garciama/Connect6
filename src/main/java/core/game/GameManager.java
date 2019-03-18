package core.game;

import java.util.ArrayList;

public class GameManager {

    private int gameIDCount = 0;
    private ArrayList<Game> allGameList;

    public GameManager(){
        allGameList = new ArrayList<Game>();
    }

    public void createNew(String redName, String blueName){
       Game g = new Game(gameIDCount, redName, blueName);
       allGameList.add(g);
    }

    public String getGame(int id){
        for(int i = 0; i < allGameList.size(); i++)
            if(allGameList.get(i).gameID == id)
                return allGameList.get(i).getBoard();
        return "no games found";
    }

    public void moveInGame(int ID, int x, int y, String playerName){
        for(int i = 0; i < allGameList.size(); i++)
            if(allGameList.get(i).gameID == ID)
                allGameList.get(i).makeMove(x,y,playerName);
    }

}
