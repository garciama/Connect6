package core.user;
import java.util.Date;

public class Move {

    private Date date;
    private User player;
    private int x;
    private int y;

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
        this.player = player;
    }

    public Date getDate() {
        return date;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

}
