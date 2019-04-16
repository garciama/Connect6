package core.user;
import core.game.Square;

import java.util.Date;

public class Move {

    private Date date;
    private int x,y;
    private User owner;

    public Move(int x, int y, User player) {
        date = new Date();
        this.x = x;
        this.y = y;
        this.owner = player;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        // TODO: update this when we figure out how we are keeping track of users
        String str = String.format("(%d, %d)", this.x, this.y);
        //String str = String.format("%d %d %s", x, y, player.name or whatever);
        return str;
    }

    public String getOwnerName() { return owner.getName(); }

    public User getOwner() { return owner; }

    public int getX() {
        return this.x;
    }

    public int getY() { return this.y; }



}
