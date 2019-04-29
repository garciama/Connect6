package core.user;

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
        String str = String.format("(%d, %d)", this.x, this.y);
        return str;
    }

    public String getOwnerName() { return owner.getName(); }

    public User getOwner() { return owner; }

    public int getX() {
        return this.x;
    }

    public int getY() { return this.y; }

}
