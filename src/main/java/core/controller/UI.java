package core.controller;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    static int xRed;
    static int yRed;
    static int xBlue;
    static int yBlue;
    static int menuChoice;
    static Scanner in;
    static String redPlayer, bluePlayer;

    public static void main(String[] args){

        in = new Scanner(System.in);
        menu();

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
            System.out.print(redPlayer + "'s x move: ");
            xRed = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.print("Please enter a number!");
            System.out.print(redPlayer + "'s x move: ");
            xRed = in.nextInt();
        }

        // get y coordinate
        try {
            System.out.print(redPlayer + "'s y move: ");
            yRed = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.print("Please enter a number!");
            System.out.print(redPlayer + "'s y move: ");
            yRed = in.nextInt();
        }

    }

    /**
     * Gets Blue Player's x and y entered coordinates
     */
    public static void getInputBluePlayer() {


        // gets x coordinate
        try {
            System.out.print(bluePlayer + "'s x move: ");
            xBlue = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.print("Please enter a number!");
            System.out.print(bluePlayer + "'s x move: ");
            xBlue = in.nextInt();
        }

        // get y coordinate
        try {
            System.out.print(bluePlayer + "'s y move: ");
            yBlue = in.nextInt();
        } catch (InputMismatchException e) {

                in.nextLine();
                System.out.print("Please enter a number!");
                System.out.print(bluePlayer + "'s y move: ");
                yBlue = in.nextInt();
        }
    }

    private static void createUser() {

    }

    private static void makeNewGame() {
        getUsers();
        GameController controller = new GameController(redPlayer, bluePlayer);
        //System.out.println( controller.reportBoard(0));

        // loop that runs the actual playing of the game
        while(true) {
            getInputRedPlayer();
            while(!controller.makeMove(0, xRed, yRed, redPlayer))
                getInputRedPlayer();
            //System.out.println(controller.reportBoard(0));
            getInputBluePlayer();
            while(!controller.makeMove(0, xBlue, yBlue, bluePlayer))
                getInputBluePlayer();
            System.out.println();
            System.out.println(controller.reportBoard(0));
        }
    }

    private static void joinGame() {

    }

    private static void seeGamesInProgress() {

    }

    private static void seeCompletedGames() {

    }

    private static void seeLeaderboard() {

    }

    private static void menu() {
        System.out.println("Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n");
        menuChoice = in.nextInt();
        System.out.println();
        in.nextLine();
        switch (menuChoice) {
            case 1:
                createUser();
                break;
            case 2:
                makeNewGame();
                break;
            case 3:
                seeGamesInProgress();
                break;
            case 4:
                joinGame();
                break;
            case 5:
                seeCompletedGames();
                break;
            case 6:
                seeLeaderboard();
                break;
        }
    }
}
