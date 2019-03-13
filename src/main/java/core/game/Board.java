package core.game;

import java.awt.*;

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

        /*
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                System.out.print(squaresOnBoard[j][i] + " ");
            }
            System.out.println();
        }*/

        /*
        squaresOnBoard[2][2].changeColor( new Color(7, 7, 7));
        squaresOnBoard[2][3].changeColor( new Color(7, 7, 7));
        squaresOnBoard[2][4].changeColor( new Color(7, 7, 7));
        squaresOnBoard[2][5].changeColor( new Color(7, 7, 7));
        squaresOnBoard[2][6].changeColor( new Color(7, 7, 7));
        squaresOnBoard[2][7].changeColor( new Color(7, 7, 7)); */


        squaresOnBoard[7][7].changeColor( new Color(7, 7, 7));
        squaresOnBoard[6][6].changeColor( new Color(7, 7, 7));
        squaresOnBoard[5][5].changeColor( new Color(7, 7, 7));
        squaresOnBoard[4][4].changeColor( new Color(7, 7, 7));
        squaresOnBoard[3][3].changeColor( new Color(7, 7, 7));
        squaresOnBoard[2][2].changeColor( new Color(7, 7, 7));

        searchForColor();
    }

    //Maybe return something later?
   // private void lastMove(){
    //}

    private void searchForColor(){
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                System.out.print(squaresOnBoard[i][j].toString());
                if (squaresOnBoard[i][j].getColor().getRed() != 0 &&
                        squaresOnBoard[i][j].getColor().getBlue() != 0 &&
                        squaresOnBoard[i][j].getColor().getGreen() != 0)
                    if (isWinning(i, j, squaresOnBoard[i][j].getColor())) {
                        System.out.println("Game over!");
                        System.exit(1);
                    }
            }
            System.out.println();
        }
    }

    public boolean isWinning(int x, int y, Color c){
        return (checkHorizontal(x, y, c) || checkVertical(x, y, c) || checkDiagonalRight(x, y, c) ||
                checkDiagonalLeft(x, y, c));
    }

    public boolean checkHorizontal(int x, int y, Color c){
        if (x > 13)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x + i][y].getColor().getRed() != c.getRed())
                return false;
        }
        return true;
    }

    public boolean checkVertical(int x, int y, Color c){
        if (y > 13)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x][y + i].getColor().getRed() != c.getRed())
                return false;
        }
        return true;
    }

    public boolean checkDiagonalRight(int x, int y, Color c){
        if (x > 13 || y > 13)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x + i][y + i].getColor().getRed() != c.getRed())
                return false;
        }
        return true;
    }

    public boolean checkDiagonalLeft(int x, int y, Color c){
        if (x < 5 || y < 5)
            return false;
        for (int i = 0; i < 6; i++){
            if(squaresOnBoard[x - i][y + i].getColor().getRed() != c.getRed())
                return false;
        }
        return true;
    }

}
