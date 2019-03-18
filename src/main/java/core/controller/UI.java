package core.controller;
import java.util.Scanner;

public class UI {
    static int x1;
    static int y1;
    static int x2;
    static int y2;
    static Scanner in;
    static String redPlayer, bluePlayer;

    public static void main(String[] args){


        in = new Scanner(System.in);

        System.out.print("Enter the first user to play: ");
        redPlayer = in.nextLine();
        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();

        GameController controller = new GameController(redPlayer, bluePlayer);
        //System.out.println( controller.reportBoard(0));

        while(true) {
            getInputRedPlayer();
            controller.makeMove(0, y1, x1, redPlayer);
            //System.out.println(controller.reportBoard(0));
            getInputBluePlayer();
            controller.makeMove(0, y2, x2, bluePlayer);
            System.out.println(controller.reportBoard(0));
        }
    }

    public static void getInputRedPlayer() {
        System.out.println(redPlayer + "'s x move: ");
        x1 = in.nextInt();
        System.out.println(redPlayer + "'s y move: ");
        y1 = in.nextInt();
    }

    public static void getInputBluePlayer() {
        System.out.println(bluePlayer + "'s x move: ");
        x2 = in.nextInt();
        System.out.println(bluePlayer + "'s y move: ");
        y2 = in.nextInt();
    }
}
