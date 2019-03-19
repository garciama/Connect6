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

<<<<<<< HEAD
    //TODO: make this return a bool to represent if the space is open so you can make a move or if its occupied
=======
>>>>>>> 3b0d70fdcfec5c648c35c5308ffd806e2687ba10
    public boolean makeMove(int x, int y, String playerName) {
        if (board.checkIfSquareIsOpen(x, y)) {
            if (playerName.equals(this.red.getName()))
                board.squaresOnBoard[x][y].changeColor(Color.Red);
            else
                board.squaresOnBoard[x][y].changeColor(Color.Blue);
        }
        else {
            System.out.println("Illegal move - square already taken");
            return false;
        }

        if(board.searchForColor()){
            System.out.println(playerName + " has won! Game over.");
            System.exit(1);
        }
<<<<<<< HEAD

=======
>>>>>>> 3b0d70fdcfec5c648c35c5308ffd806e2687ba10
        return true;
    }
}
