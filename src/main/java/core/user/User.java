package core.user;

public class User {

    private int wins;
    private int losses;
    private int ties;

    public User(int w, int l, int t){
        this.wins = w;
        this.losses = l;
        this.ties = t;
    }

    public int getWins(){
        return this.wins;
    }
    public int getLosses(){
        return this.losses;
    }
    public int getTies(){
        return this.ties;
    }
}
