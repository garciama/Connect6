package core.controller;
import java.util.InputMismatchException;
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

    /**
     * Gets the names of users that are entered
     */
    public static void getUsers() {
        System.out.print("Enter the first user to play: ");
        redPlayer = in.nextLine();
        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();
    }

    /**
     * Gets Red Player's x and y entered coordinates
     */
    public static void getInputRedPlayer() {
        //TODO: if an int isn't entered, and user doesnt enter an int again then program crashes, maybe have a do while
        // and take any input as a string and see if it can be casted to a valid int. Does this even matter cuz its a
        // temp UI

        // gets x coordinate
        try {
            System.out.println(redPlayer + "'s x move: ");
            xRed = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Please enter a number!");
            System.out.println(redPlayer + "'s x move: ");
            xRed = in.nextInt();
        }

        // get y coordinate
        try {
            System.out.println(redPlayer + "'s y move: ");
            yRed = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Please enter a number!");
            System.out.println(redPlayer + "'s y move: ");
            yRed = in.nextInt();
        }

    }

    /**
     * Gets Blue Player's x and y entered coordinates
     */
    public static void getInputBluePlayer() {


        // gets x coordinate
        try {
            System.out.println(bluePlayer + "'s x move: ");
            xBlue = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Please enter a number!");
            System.out.println(bluePlayer + "'s x move: ");
            xBlue = in.nextInt();
        }

        // get y coordinate
        try {
            System.out.println(bluePlayer + "'s y move: ");
            yBlue = in.nextInt();
        } catch (InputMismatchException e) {

                in.nextLine();
                System.out.println("Please enter a number!");
                System.out.println(bluePlayer + "'s y move: ");
                yBlue = in.nextInt();
        }


    }
}
