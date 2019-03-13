package core.game;
import core.user.User;

public class Square {
    private int xCoord;
    private int yCoord;
    private User owner;
    private Color color;

    public Square(int y, int x){
        xCoord = x;
        yCoord = y;
        //Default color is white before anyone clicks the square
        color = Color.White;
    }

    public void changeColor(Color c){
        this.color = c;
    }

    public String toString(){
        return( "( [" + xCoord + ", " + yCoord + "] " + color + " )" );
    }

    public Color getColor(){
        return this.color;
    }

}

enum Color{
    White, Red, Blue
}