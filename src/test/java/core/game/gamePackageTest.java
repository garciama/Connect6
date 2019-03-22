package core.game;
import core.Color;
import core.controller.GameController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class gamePackageTest {

    Board b1;
    Square s1, s2;
    GameManager gm;

    @Before
    public void init(){
        b1 = new Board();
        s1 = new Square(5,10);
        s2 = new Square(2,4);
        gm = new GameManager();
    }

    @Test
    public void checkHorizontalTest(){
        //horizontal success
        b1.squaresOnBoard[2][2].changeColor(Color.Red);
        b1.squaresOnBoard[3][2].changeColor(Color.Red);
        b1.squaresOnBoard[4][2].changeColor(Color.Red);
        b1.squaresOnBoard[5][2].changeColor(Color.Red);
        b1.squaresOnBoard[6][2].changeColor(Color.Red);
        b1.squaresOnBoard[7][2].changeColor(Color.Red);
        assertFalse(b1.checkHorizontal(4,1, b1.squaresOnBoard[2][2].getColor()));
        assertTrue(b1.checkHorizontal(2,2, b1.squaresOnBoard[2][2].getColor()));
        //only 5 in a row, should fail
        assertFalse(b1.checkHorizontal(3,2, b1.squaresOnBoard[3][2].getColor()));
        //would go off board, should fail
        b1.squaresOnBoard[16][2].changeColor(Color.Red);
        b1.squaresOnBoard[17][2].changeColor(Color.Red);
        b1.squaresOnBoard[18][2].changeColor(Color.Red);
        assertFalse(b1.checkHorizontal(16,2,b1.squaresOnBoard[16][2].getColor()));
    }

    @Test
    public void checkVerticalTest(){
        //vertical success
        b1.squaresOnBoard[1][1].changeColor(Color.Red);
        b1.squaresOnBoard[1][2].changeColor(Color.Red);
        b1.squaresOnBoard[1][3].changeColor(Color.Red);
        b1.squaresOnBoard[1][4].changeColor(Color.Red);
        b1.squaresOnBoard[1][5].changeColor(Color.Red);
        b1.squaresOnBoard[1][6].changeColor(Color.Red);
        assertFalse(b1.checkVertical(6,4,b1.squaresOnBoard[1][1].getColor()));
        assertTrue(b1.checkVertical(1,1,b1.squaresOnBoard[1][1].getColor()));
        //only 4 in a row, should fail
        assertFalse(b1.checkVertical(1,3, b1.squaresOnBoard[1][3].getColor()));
        //would go off board, should fail
        b1.squaresOnBoard[1][15].changeColor(Color.Red);
        b1.squaresOnBoard[1][16].changeColor(Color.Red);
        b1.squaresOnBoard[1][17].changeColor(Color.Red);
        b1.squaresOnBoard[1][18].changeColor(Color.Red);
        assertFalse(b1.checkVertical(1,15, b1.squaresOnBoard[1][15].getColor()));
    }

    @Test
    public void checkDiagonalLeftTest(){
        //diagonal left success
        b1.squaresOnBoard[8][7].changeColor(Color.Red);
        b1.squaresOnBoard[7][8].changeColor(Color.Red);
        b1.squaresOnBoard[6][9].changeColor(Color.Red);
        b1.squaresOnBoard[5][10].changeColor(Color.Red);
        b1.squaresOnBoard[4][11].changeColor(Color.Red);
        b1.squaresOnBoard[3][12].changeColor(Color.Red);
        assertFalse(b1.checkDiagonalLeft(3,8, b1.squaresOnBoard[8][7].getColor()));
        assertTrue(b1.checkDiagonalLeft(8,7, b1.squaresOnBoard[8][7].getColor()));
    }

    @Test
    public void checkDiagonalRightTest(){
        //diagonal right success
        b1.squaresOnBoard[2][3].changeColor(Color.Red);
        b1.squaresOnBoard[3][4].changeColor(Color.Red);
        b1.squaresOnBoard[4][5].changeColor(Color.Red);
        b1.squaresOnBoard[5][6].changeColor(Color.Red);
        b1.squaresOnBoard[6][7].changeColor(Color.Red);
        b1.squaresOnBoard[7][8].changeColor(Color.Red);
        assertFalse(b1.checkDiagonalRight(2,10, b1.squaresOnBoard[2][3].getColor()));
        assertTrue(b1.checkDiagonalRight(2,3, b1.squaresOnBoard[2][3].getColor()));
    }

    @Test
    public void testIsWinning(){
        b1.squaresOnBoard[13][13].changeColor(Color.Red);
        //assertFalse(b1.isWinning(13,13,b1.squaresOnBoard[13][13].getColor()));
        //assertTrue(b1.isWinning(2,3,b1.squaresOnBoard[2][3].getColor()));
        assertFalse(b1.isWinning());
        //assertTrue(b1.isWinning());
    }

    @Test
    public void changeColorTestSquare() {
        s1.changeColor(Color.Red);
        assertEquals(Color.Red, s1.getColor());
    }

    @Test
    public void toStringTestSquare() {
        assertEquals("|   ", s1.toString());
        assertEquals("|   ", s2.toString());
    }

    @Test
    public void getColorTestSquare() {
        assertEquals(Color.Black, s2.getColor());
    }

    @Test
    public void testCheckIfSquareIsOpen() {
        assertEquals(true, b1.checkIfSquareIsOpen(0, 0));
        b1.squaresOnBoard[0][0].changeColor(Color.Red);
        assertEquals(false, b1.checkIfSquareIsOpen(0, 0));
    }


    //TODO: figure out how to test a tie.

    @Test
    public void testTie(){
        GameController c = new GameController();
        String redPlayer = "sam", bluePlayer = "nick";
        int id = c.newGame(redPlayer, bluePlayer);

        for (int row = 0; row < 19; row++){

            if (row%2 == 0) {
                for (int col = 0; col < 18; col += 4) {
                    c.makeMove(id, col, row, redPlayer);
                    c.makeMove(id, col + 1, row, redPlayer);

                }

                for (int col = 2; col < 18; col += 4) {
                    c.makeMove(id, col, row, bluePlayer);
                    c.makeMove(id, col + 1, row, bluePlayer);

                }
            }else{
                for (int col = 0; col < 18; col += 4) {
                    c.makeMove(id, col, row, bluePlayer);
                    c.makeMove(id, col + 1, row, bluePlayer);

                }

                for (int col = 2; col < 18; col += 4) {
                    c.makeMove(id, col, row, redPlayer);
                    c.makeMove(id, col + 1, row, redPlayer);
                }
            }
        }

        for (int row = 0; row < 19; row++){
            if (row%2 == 0)
                c.makeMove(id, 18, row, redPlayer);
            else
                c.makeMove(id, 18, row, bluePlayer);


        }

        /*We can see the board is full and nobody has won,
        so the game gives each player a tie and closes the game. */
        System.out.println(c.reportBoard(id));

    }

    @Test
    public void gameControllerGameCreationAndProgressReport() {
        // make a game and make sure its in progress and not finished
        gm.createNewGame("red", "blue");
        assertEquals("1 red blue\n", gm.getAllGamesInProgress());
        assertEquals("", gm.getAllFinishedGames());

        // make a second game and make it be finished
        gm.createNewGame("red2", "blue2");
        for (int i = 0; i < 6; i++)
            gm.moveInGame(2, 0, i, "red2");
        // check that the second game has finished and that the game in progress is still there and that there is a
        // new finished game
        assertEquals(true, gm.getInstanceOfGame(2).getGameStatus());

        assertEquals("1 red blue\n", gm.getAllGamesInProgress());

        assertEquals("2 red2 blue2\n", gm.getAllFinishedGames());

    }

}