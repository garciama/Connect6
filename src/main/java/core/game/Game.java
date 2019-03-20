package core.game;
import core.Color;
import core.user.User;
import core.user.Move;
import java.util.ArrayList;

public class Game {

    public int gameID;
    private boolean publicOrPrivate;
    private boolean isFinished;
    private Board board;
    User redPlayer, bluePlayer;
    public ArrayList<Move> movesInGame;

    public Game(int id, String redName, String blueName){
        movesInGame = new ArrayList<>();
        this.gameID = id;
        board = new Board();
        redPlayer = new User(redName, Color.Red);
        bluePlayer = new User(blueName, Color.Blue);
        isFinished = false;
    }

    public String getBoard() {
        return board.displayBoard();
    }

    //TODO: make this return a bool to represent if the space is open so you can make a move or if its occupied
    public boolean makeMove(int x, int y, String playerName) {
        if (board.checkIfSquareIsOpen(x, y)) {
            if (playerName.equals(this.redPlayer.getName())) {
                board.squaresOnBoard[x][y].changeColor(Color.Red);
            }else
                board.squaresOnBoard[x][y].changeColor(Color.Blue);

            Move currentMove = new Move(x, y);
            movesInGame.add(currentMove);
        }
        else {
            System.out.println("Illegal move - square already taken");
            return false;
        }

        if(board.isWinning()){
            if (redPlayer.getName().equals(playerName)) {
                System.out.println(playerName + " has won! Game over.");
                redPlayer.addWin();
                isFinished = true;
                System.out.println(redPlayer.getName() + " now has " + redPlayer.getWins() + " wins!");
            }
            else if(bluePlayer.getName().equals(playerName)) {
                System.out.println(playerName + " has won! Game over.");
                bluePlayer.addWin();
                isFinished = true;
                System.out.println(bluePlayer.getName() + " now has " + bluePlayer.getWins() + " wins!");
            }

        }
        return true;
    }

    public boolean getGameStatus(){
        return isFinished;
    }
}
