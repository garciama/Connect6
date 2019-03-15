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
        return "";
    }

}
