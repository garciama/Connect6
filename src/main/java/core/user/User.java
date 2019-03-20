package core.user;
import core.Color;

public class User {

    private int wins;
    private int losses;
    private int ties;
    private Color color;
    private String name;

    public User(String name, Color color) {
        this.color = color;
        this.name = name;
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
    public String getName(){
        return this.name;
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

}
