package core.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User u1;

    @Before
    public void initUser(){
        u1 = new User(1,2,3);
    }
    @Test
    public void getWins() {
        assertEquals(1, u1.getWins());
    }

    @Test
    public void getLosses() {
        assertEquals(2, u1.getLosses());
    }

    @Test
    public void getTies() {
        assertEquals(3, u1.getTies());
    }
}