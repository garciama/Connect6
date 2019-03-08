package core.game;

public class Board {

    private boolean winningState;
    private int x;

    public Board(int d){
        this.x = d;
    }

    public int getX(){
        return this.x;
    }

}
