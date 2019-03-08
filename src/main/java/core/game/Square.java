package core.game;
import core.user.User;
import java.awt.*;

public class Square {
    private int xCoord;
    private int yCoord;
    private User owner;
    private Color color;

    public Square(int x, int y){
        xCoord = x;
        yCoord = y;
        //Default color is black before anyone clicks the square
        color = new Color(0, 0, 0);
        /*Blue (r:25 g:0  b:155)
        Red (r: 153 g: 0 b: 56)*/
    }

    public void changeColor(Color c){
        this.color = c;
    }

    public String toString(){
        return( "( [" + xCoord + ", " + yCoord + "]" + color + " )" );
    }

    public Color getColor(){
        return this.color;
    }

}
