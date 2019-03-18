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
        GameController controller = new GameController(redPlayer, bluePlayer);
        //System.out.println( controller.reportBoard(0));
        while(true) {
            System.out.println(redPlayer + "'s x move: ");
            int x = in.nextInt();
            System.out.println(redPlayer + "'s y move: ");
            int y = in.nextInt();
            controller.makeMove(0, y, x, redPlayer);
            //System.out.println(controller.reportBoard(0));
            System.out.println(bluePlayer + "'s x move: ");
            int x1 = in.nextInt();
            System.out.println(bluePlayer + "'s y move: ");
            int y1 = in.nextInt();
            controller.makeMove(0, y1, x1, bluePlayer);
            System.out.println(controller.reportBoard(0));
        }
    }
}
