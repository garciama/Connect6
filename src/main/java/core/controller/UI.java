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
        //System.out.println( controller.reportBoard(0));

        while(true) {
<<<<<<< HEAD
            System.out.print(redPlayer + "'s (red) x move: ");
            int x = in.nextInt();
            System.out.print(redPlayer + "'s (red) y move: ");
            int y = in.nextInt();
            controller.makeMove(0, x, y, redPlayer);
            //System.out.println(controller.reportBoard(0));
            System.out.print(bluePlayer + "'s (blue) x move: ");
            int x1 = in.nextInt();
            System.out.print(bluePlayer + "'s (blue) y move: ");
            int y1 = in.nextInt();
            controller.makeMove(0, x1, y1, bluePlayer);
            System.out.println();
=======
            getInputRedPlayer();
            controller.makeMove(0, yRed, xRed, redPlayer);
            //System.out.println(controller.reportBoard(0));
            getInputBluePlayer();
            controller.makeMove(0, yBlue, xBlue, bluePlayer);
>>>>>>> 0172b520826c17f609d2c7ff215ea049014eb047
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
        System.out.println(redPlayer + "'s x move: ");
        xRed = in.nextInt();
        System.out.println(redPlayer + "'s y move: ");
        yRed = in.nextInt();
    }

    public static void getInputBluePlayer() {
        System.out.println(bluePlayer + "'s x move: ");
        xBlue = in.nextInt();
        System.out.println(bluePlayer + "'s y move: ");
        yBlue = in.nextInt();
    }
}
