package core.user;
import java.sql.Time;
import java.util.Date;

public class Move {

    private Date date;
    private User player;

    public Move() {
        date = new Date();
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
