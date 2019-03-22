package core.user;
import java.util.Date;

public class Move {

    private Date date;
    private int x;
    private int y;
    private User owner;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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
        String str = String.format("(%d, %d)", x, y);
        //String str = String.format("%d %d %s", x, y, player.name or whatever);
        return str;
    }

    public String getOwner(){ return owner.getName(); }

}
