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
        redPlayer = new User(redName);
        bluePlayer = new User(blueName);
        isFinished = false;
    }

    public String getBoard() {
        return board.displayBoard();
    }

    //TODO: make this return a bool to represent if the space is open so you can make a move or if its occupied
    public boolean makeMove(int x, int y, String playerName) {
        Move currentMove;
        if (board.checkIfSquareIsOpen(x, y)) {
            if (playerName.equals(this.redPlayer.getName())) {
                board.squaresOnBoard[x][y].changeColor(Color.Red);
                currentMove = new Move(x,y,redPlayer);
            }else {
                board.squaresOnBoard[x][y].changeColor(Color.Blue);
                currentMove = new Move(x,y,bluePlayer);
            }
            movesInGame.add(currentMove);
        }
        else {
            System.out.println("Illegal move - square already taken");
            return false;
        }

        if (board.isWinning()){
            if (redPlayer.getName().equals(playerName)) {
                System.out.println(playerName + " has won! Game over.");
                redPlayer.addWin();
                System.out.println(redPlayer.getName() + " now has " + redPlayer.getWins() + " wins!");
            }
            else if(bluePlayer.getName().equals(playerName)) {
                System.out.println(playerName + " has won! Game over.");
                bluePlayer.addWin();
                System.out.println(bluePlayer.getName() + " now has " + bluePlayer.getWins() + " wins!");
            }

            isFinished = true;

        }

        if (movesInGame.size() == (19*19)){
            System.out.println("Tie! Game over.");
            redPlayer.addTie();
            bluePlayer.addTie();

            System.out.println(redPlayer.getName() + " now has " + redPlayer.getTies() + " ties!");
            System.out.println(bluePlayer.getName() + " now has " + bluePlayer.getTies() + " ties!");

            isFinished = true;
        }

        return true;
    }

    public boolean getGameStatus(){
        return isFinished;
    }

    public String lastUserToMakeMove(){
        int last = movesInGame.size() - 1;
        Move lastMove = movesInGame.get(last);
        return lastMove.getOwner();
    }
}
