package core.controller;
import java.sql.SQLOutput;
import java.util.Scanner;

public class UI {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String redPlayer, bluePlayer;
        System.out.print("Enter the first user to play: ");
        redPlayer = in.nextLine();
        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();
        GameController controller = new GameController(redPlayer, bluePlayer);
        System.out.println( controller.reportBoard(0));
    }
}
