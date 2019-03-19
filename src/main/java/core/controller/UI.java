package core.controller;
import java.util.Scanner;

public class UI {
    static int xRed;
    static int yRed;
    static int xBlue;
    static int yBlue;
    static Scanner in;
    static String redPlayer, bluePlayer;

    public static void main(String[] args){

        in = new Scanner(System.in);

        getUsers();

        GameController controller = new GameController(redPlayer, bluePlayer);

        while(true) {
            getInputRedPlayer();
            while (!controller.makeMove(0, xRed, yRed, redPlayer))
                getInputRedPlayer();

            //System.out.println(controller.reportBoard(0));
            getInputBluePlayer();
            while (!controller.makeMove(0, xBlue, yBlue, bluePlayer))
                getInputBluePlayer();

            System.out.println();
            System.out.println(controller.reportBoard(0));
        }
    }

    public static void getUsers() {
        System.out.print("Enter the first user to play: ");
        redPlayer = in.nextLine();
        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();
    }

    public static void getInputRedPlayer() {
        System.out.print(redPlayer + "'s (red) x move: ");
        xRed = in.nextInt();
        System.out.print(redPlayer + "'s (red) y move: ");
        yRed = in.nextInt();
    }

    public static void getInputBluePlayer() {
        System.out.print(bluePlayer + "'s (blue) x move: ");
        xBlue = in.nextInt();
        System.out.print(bluePlayer + "'s (blue) y move: ");
        yBlue = in.nextInt();
    }
}
