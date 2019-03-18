package core.game;
import core.Color;

public class Board {

    private boolean winningState;
    protected Square[][] squaresOnBoard;

    public Board(){
        squaresOnBoard = new Square[19][19];
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                Square individualSquare = new Square(i, j);
                squaresOnBoard[i][j] = individualSquare;
            }
        }

       /* squaresOnBoard[2][2].changeColor(Color.Red);
        squaresOnBoard[2][3].changeColor(Color.Red);
        squaresOnBoard[2][4].changeColor(Color.Red);
        squaresOnBoard[2][5].changeColor(Color.Red);
        squaresOnBoard[2][6].changeColor(Color.Red);
        squaresOnBoard[2][7].changeColor(Color.Red);*/


        //squaresOnBoard[7][7].changeColor(Color.Blue);
        //squaresOnBoard[6][6].changeColor(Color.Blue);
        //squaresOnBoard[5][5].changeColor(Color.Blue);
        //squaresOnBoard[4][4].changeColor(Color.Blue);
        //squaresOnBoard[3][3].changeColor(Color.Blue);
        //squaresOnBoard[2][2].changeColor(Color.Blue);
    }

    //Maybe return something later?
   // private void lastMove(){
    //}

    public String displayBoard(){
        StringBuilder sb = new StringBuilder("  | ");
        for(int i = 0; i< squaresOnBoard.length; i++){
            if(i == 0)
                sb.append(i );
            else if(i < 10)
                sb.append(" | " + i);
            else
                sb.append(" |" + i);
        }
        sb.append(" |\n ");
        printDashedRow(sb);
        for(int i = 0; i < squaresOnBoard.length; i++){
            sb.append(i);
            if( i < 10)
                sb.append(" ");
            for(int j = 0; j < squaresOnBoard[0].length; j++){
                sb.append(squaresOnBoard[i][j].toString());
            }
            sb.append("|\n ");
            printDashedRow(sb);
        }
        return sb.toString();
    }

    private void printDashedRow(StringBuilder b){
        for (int k = 0; k < (squaresOnBoard.length*3)+1; k++){
            if (k % 3 == 1) {
                b.append("|");
            }
            b.append("-");
        }
        b.append("|\n");
    }

    public boolean searchForColor(){
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                if (squaresOnBoard[i][j].getColor() != Color.Black)
                    if (isWinning(i, j, squaresOnBoard[i][j].getColor())) {
                        return true;
                    }
            }
        }
        return false;
    }

    public boolean isWinning(int x, int y, Color c){
        return (checkHorizontal(x, y, c) || checkVertical(x, y, c) || checkDiagonalRight(x, y, c) ||
                checkDiagonalLeft(x, y, c));
    }

    public boolean checkHorizontal(int x, int y, Color c){
        if (x > 13)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x + i][y].getColor() != c)
                return false;
        }
        return true;
    }

    public boolean checkVertical(int x, int y, Color c){
        if (y > 13)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x][y + i].getColor() != c)
                return false;
        }
        return true;
    }

    public boolean checkDiagonalRight(int x, int y, Color c){
        if (x > 13 || y > 13)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x + i][y + i].getColor() != c)
                return false;
        }
        return true;
    }

    public boolean checkDiagonalLeft(int x, int y, Color c){
        if (x < 5 || y < 5)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x - i][y + i].getColor() != c)
                return false;
        }
        return true;
    }

    /**
     * Checks if a square is occupied already or not
     * @param x x coordinate on board
     * @param y y coordinate on board
     * @return true if the square isn't occupied, false if the square is already occupied
     */
    public boolean checkIfSquareIsOpen(int x, int y) {
        if (squaresOnBoard[x][y].getColor() != Color.Black) {
            return false;
        } else {
            return true;
        }
    }

}
