package core.game;
import core.Color;
import core.user.User;
import core.user.Move;
import java.util.ArrayList;

public class Game {

    public int gameID;
    private boolean isPublic; // TODO: have public or private be reflected in looking at list of games
    private boolean isFinished;
    private Board board;
    User redPlayer, bluePlayer;
    public ArrayList<Move> movesInGame;

    public Game(int id, User redName, User blueName, boolean isPublic){
        movesInGame = new ArrayList<>();
        this.gameID = id;
        board = new Board();
        redPlayer = redName;
        bluePlayer = blueName;
        isFinished = false;
        this.isPublic = isPublic;
    }


    public String getBoard() {
        return board.displayBoard();
    }

    /**
     * Method that is called when a user attempts to make a move from the
     * UI. The method checks if the move is valid, then checks if the board
     * is in a winning state after the move is placed.
     * @param x x coordinate on the board
     * @param y y coordinate on the board
     * @param playerName name of the user that is making the move.
     * @return True if the move was made, false if not ( due to violations).
     */
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
                bluePlayer.addLoss();
                System.out.println(redPlayer.getName() + " now has " + redPlayer.getWins() + " wins!");
            }
            else if(bluePlayer.getName().equals(playerName)) {
                System.out.println(playerName + " has won! Game over.");
                bluePlayer.addWin();
                redPlayer.addLoss();
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

    public boolean gameIsFinished(){
        return isFinished;
    }

    /**
     * Method that checks the board for the last move and returns the
     * user's name.
     * @return User's name who made the last move.
     */
    public String lastUserToMakeMove(){
        int last = movesInGame.size() - 1;
        Move lastMove = movesInGame.get(last);
        return lastMove.getOwner();
    }

    public String getRedPlayerName(){
        return redPlayer.getName();
    }

    public String getBluePlayerName(){
        return bluePlayer.getName();
    }

    public boolean isPublic(){
      return isPublic;
    }

}
