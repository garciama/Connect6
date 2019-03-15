package core.game;
import core.Color;
import core.user.User;

public class Game {

    public int gameID;
    private boolean publicOrPrivate;
    private boolean isFinished;
    private Board board;

    public Game(int id, String redName, String blueName){
     this.gameID = id;
     board = new Board();
     User red = new User(redName, Color.Red);
     User blue = new User(blueName, Color.Blue);
    }

    public String getBoard() {
        return board.displayBoard();
    }
}
