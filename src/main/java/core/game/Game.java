package core.game;
import core.user.User;

public class Game {

    private boolean publicOrPrivate;
    private boolean isFinished;

    public void createGame(User redPlayer, User bluePlayer){
        Board b = new Board();
        User player1 = new User();
        User player2 = new User();

        player1.makesMove(5 ,5);
    }

}
