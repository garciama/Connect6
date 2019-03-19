package core.controller;
import java.util.Scanner;

public class UI {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String redPlayer, bluePlayer;
        System.out.print("Enter the first user to play: ");
        redPlayer = in.nextLine();
        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();
        while(redPlayer.equals(bluePlayer)){
            System.out.print("can't use same user names, enter a user: ");
            bluePlayer = in.nextLine();
        }
        GameController controller = new GameController(redPlayer, bluePlayer);
        System.out.println( controller.reportBoard(0));
        while(true) {
            System.out.print(redPlayer + "'s x move: ");
            int x = in.nextInt();
            System.out.print(redPlayer + "'s y move: ");
            int y = in.nextInt();
            controller.makeMove(0,x,y, redPlayer);
            //System.out.println(controller.reportBoard(0));
            System.out.print(bluePlayer + "'s x move: ");
            int x1 = in.nextInt();
            System.out.print(bluePlayer + "'s y move: ");
            int y1 = in.nextInt();
            controller.makeMove(0, x1, y1, bluePlayer);
            System.out.println(controller.reportBoard(0));
        }
    }
}
