package core.user;
import core.game.Board;

import java.awt.*;

public class User {

    private int wins;
    private int losses;
    private int ties;
    private Color color;

    public User(){
        wins = 0;
        losses = 0;
        ties = 0;
    }

    public int getWins(){
        return this.wins;
    }
    public int getLosses(){
        return this.losses;
    }
    public int getTies(){
        return this.ties;
    }

    public void setWins( int updatedWins ){
        wins = updatedWins;
    }

    public void setLosses( int updatedLosses ){
        losses = updatedLosses;
    }

    public void setTies( int updatedTies ){
        ties = updatedTies;
    }

    public void makesMove(int x, int y){
        // TODO need to update the player part of this call when we know how we are keeping track of users
        Move move = new Move(x, y, this);

    }
}
