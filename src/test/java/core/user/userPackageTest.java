package core.user;

import core.Color;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class userPackageTest {

    User u1;
    User u2;

    @Before
    public void initUser(){
        u1 = new User("test", Color.Red);
        u2 = new User("test1", Color.Blue);

        u1.addWin();
        u1.addLoss();
        u1.addTie();
    }
    @Test
    public void getWins() {
        assertEquals(0, u2.getWins());
        assertEquals(1, u1.getWins());
    }

    @Test
    public void getLosses() {
        assertEquals(0, u2.getLosses());
        assertEquals(1, u1.getLosses());
    }

    @Test
    public void getTies() {
        assertEquals(0, u2.getTies());
        assertEquals(1, u1.getTies());
    }

    @Test
    public void testDateAndTime() {
        Move move = new Move(0, 0, u1);
        Date date = new Date();
        assertEquals(move.getDate(), date);
    }

    @Test
    public void testSetGetUser() {
        Move move = new Move(0, 0, u2);
        assertEquals( u2, move.getPlayer());
        assertEquals(0, move.getX());
        assertEquals(0, move.getY());
    }

    @Test
    public void moveToString() {
        // TODO will have to change this when we change how we keep track of users
        Move move = new Move(5, 8, u1);
        assertEquals("5 8", move.toString());
    }
}