package core.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class gamePackageTest {

    Board b1;
    Square s1, s2;

    @Before
    public void init(){
        b1 = new Board();
        s1 = new Square(5,10);
        s2 = new Square(2,4);
    }

    @Test
    public void checkHorizontalTest(){
        //horizontal success
        b1.squaresOnBoard[2][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[3][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[4][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[5][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[6][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[7][2].changeColor(core.game.Color.Red);
        assertFalse(b1.checkHorizontal(4,1, b1.squaresOnBoard[2][2].getColor()));
        assertTrue(b1.checkHorizontal(2,2, b1.squaresOnBoard[2][2].getColor()));
        //only 5 in a row, should fail
        assertFalse(b1.checkHorizontal(3,2, b1.squaresOnBoard[3][2].getColor()));
        //would go off board, should fail
        b1.squaresOnBoard[16][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[17][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[18][2].changeColor(core.game.Color.Red);
        assertFalse(b1.checkHorizontal(16,2,b1.squaresOnBoard[16][2].getColor()));
    }

    @Test
    public void checkVerticalTest(){
        //vertical success
        b1.squaresOnBoard[1][1].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][2].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][3].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][4].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][5].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][6].changeColor(core.game.Color.Red);
        assertFalse(b1.checkVertical(6,4,b1.squaresOnBoard[1][1].getColor()));
        assertTrue(b1.checkVertical(1,1,b1.squaresOnBoard[1][1].getColor()));
        //only 4 in a row, should fail
        assertFalse(b1.checkVertical(1,3, b1.squaresOnBoard[1][3].getColor()));
        //would go off board, should fail
        b1.squaresOnBoard[1][15].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][16].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][17].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[1][18].changeColor(core.game.Color.Red);
        assertFalse(b1.checkVertical(1,15, b1.squaresOnBoard[1][15].getColor()));
    }

    @Test
    public void checkDiagonalLeftTest(){
        //diagonal left success
        b1.squaresOnBoard[8][7].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[7][8].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[6][9].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[5][10].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[4][11].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[3][12].changeColor(core.game.Color.Red);
        assertFalse(b1.checkDiagonalLeft(3,8, b1.squaresOnBoard[8][7].getColor()));
        assertTrue(b1.checkDiagonalLeft(8,7, b1.squaresOnBoard[8][7].getColor()));
    }

    @Test
    public void checkDiagonalRightTest(){
        //diagonal right success
        b1.squaresOnBoard[2][3].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[3][4].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[4][5].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[5][6].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[6][7].changeColor(core.game.Color.Red);
        b1.squaresOnBoard[7][8].changeColor(core.game.Color.Red);
        assertFalse(b1.checkDiagonalRight(2,10, b1.squaresOnBoard[2][3].getColor()));
        assertTrue(b1.checkDiagonalRight(2,3, b1.squaresOnBoard[2][3].getColor()));
    }

    @Test
    public void testIsWinning(){
        b1.squaresOnBoard[13][13].changeColor(core.game.Color.Red);
        assertFalse(b1.isWinning(13,13,b1.squaresOnBoard[13][13].getColor()));
        assertTrue(b1.isWinning(2,3,b1.squaresOnBoard[2][3].getColor()));
    }

    @Test
    public void changeColorTestSquare() {
        s1.changeColor(core.game.Color.Red);
        assertEquals(core.game.Color.Red, s1.getColor());
    }

    @Test
    public void toStringTestSquare() {
        assertEquals("( [10, 5] White )", s1.toString());
        assertEquals("( [4, 2] White )", s2.toString());
    }

    @Test
    public void getColorTestSquare() {
        assertEquals(Color.White, s2.getColor());
    }

    @Test
    public void testCheckIfSquareIsOpen() {
        assertEquals(true, b1.checkIfSquareIsOpen(0, 0));
        b1.squaresOnBoard[0][0].changeColor(Color.Red);
        assertEquals(false, b1.checkIfSquareIsOpen(0, 0));
    }

}