package core.game;

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

        /*squaresOnBoard[2][2].changeColor(Color.Red);
        squaresOnBoard[2][3].changeColor(Color.Red);
        squaresOnBoard[2][4].changeColor(Color.Red);
        squaresOnBoard[2][5].changeColor(Color.Red);
        squaresOnBoard[2][6].changeColor(Color.Red);
        squaresOnBoard[2][7].changeColor(Color.Red);*/

        /*squaresOnBoard[7][7].changeColor(Color.Blue);
        squaresOnBoard[6][6].changeColor(Color.Blue);
        squaresOnBoard[5][5].changeColor(Color.Blue);
        squaresOnBoard[4][4].changeColor(Color.Blue);
        squaresOnBoard[3][3].changeColor(Color.Blue);
        squaresOnBoard[2][2].changeColor(Color.Blue);*/

        //searchForColor();
    }

    //Maybe return something later?
   // private void lastMove(){
    //}

    private void searchForColor(){
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 19; j++){
                System.out.print(squaresOnBoard[i][j].toString());
                if (squaresOnBoard[i][j].getColor() != Color.White)
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

}
