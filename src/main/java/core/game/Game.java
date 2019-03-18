package core.game;
import core.Color;
import core.user.User;

public class Game {

    public int gameID;
    private boolean publicOrPrivate;
    private boolean isFinished;
    private Board board;
    User red, blue;

    public Game(int id, String redName, String blueName){
     this.gameID = id;
     board = new Board();
     red = new User(redName, Color.Red);
     blue = new User(blueName, Color.Blue);
    }

    public String getBoard() {
        return board.displayBoard();
    }

    public void makeMove(int x, int y, String playerName) {
        if (board.checkIfSquareIsOpen(x, y)) {
            if (playerName.equals(this.red.getName()))
                board.squaresOnBoard[x][y].changeColor(Color.Red);
            else
                board.squaresOnBoard[x][y].changeColor(Color.Blue);
        }
        else {
            System.out.println("Illegal move - square already taken");
            //may have to let player try again instead?
        }
        if(board.searchForColor()){
            System.out.println(playerName + " has won! Game over.");
            System.exit(1);
        }
    }
}
