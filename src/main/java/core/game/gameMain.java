package core.game;

import core.user.User;

public class gameMain {
    public static void main(String[] args) {
        Game g = new Game();

        User player1 = new User();
        User player2 = new User();
        g.createGame(player1, player2);
    }
}
