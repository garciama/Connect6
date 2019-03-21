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
    static String redPlayer, bluePlayer; // TODO: change this when we keep track of users better
    static GameController controller;

    public static void main(String[] args) {

        in = new Scanner(System.in);
        controller = new GameController();
        menu();

    }


    /**
     * Gets the names of users that are entered
     */
    public static void getUsers() {
        System.out.print("Enter the first user to play: ");
        redPlayer = in.nextLine();

        if (redPlayer.equals("-1"))
            menu();

        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();

        if (bluePlayer.equals("-1"))
            menu();
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
            System.out.print(redPlayer + "'s (red) x move: ");
            xRed = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Please enter a number!");
            System.out.print(redPlayer + "'s (red) x move: ");
            xRed = in.nextInt();
        }

        if (xRed == -1)
            menu();


        // get y coordinate
        try {
            System.out.print(redPlayer + "'s (red) y move: ");
            yRed = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Please enter a number!");
            System.out.print(redPlayer + "'s (red) y move: ");
            yRed = in.nextInt();
        }

        if (yRed == -1)
            menu();

    }

    /**
     * Gets Blue Player's x and y entered coordinates
     */
    public static void getInputBluePlayer() {
        // gets x coordinate
        try {
            System.out.print(bluePlayer + "'s (blue) x move: ");
            xBlue = in.nextInt();
        } catch (InputMismatchException e) {
            in.nextLine();
            System.out.println("Please enter a number!");
            System.out.print(bluePlayer + "'s (blue) x move: ");
            xBlue = in.nextInt();
        }

        if (xBlue == -1)
            menu();


        // get y coordinate
        try {
            System.out.print(bluePlayer + "'s (blue) y move: ");
            yBlue = in.nextInt();
        } catch (InputMismatchException e) {

                in.nextLine();
                System.out.println("Please enter a number!");
                System.out.print(bluePlayer + "'s (blue) y move: ");
                yBlue = in.nextInt();
        }

        if (yBlue == -1)
            menu();

    }

    private static void createUser() {

    }

    //TODO: have an actual way to keep track of users so you can actually join
    private static void makeNewGame() {
        System.out.println("Enter -1 at any point to go back to the main menu");
        getUsers();
        int gameId = controller.newGame(redPlayer, bluePlayer);

        if (gameId == -1)
            menu();

        System.out.println("Your gameId is " + gameId);

        //first user gets one move, second user gets 2 moves, then enter game loop
        getInputRedPlayer();
        while(!controller.makeMove(gameId, xRed, yRed, redPlayer))
            getInputRedPlayer();
        getInputBluePlayer();
        while(!controller.makeMove(gameId, xBlue, yBlue, bluePlayer))
            getInputBluePlayer();
        getInputBluePlayer();
        while(!controller.makeMove(gameId, xBlue, yBlue, bluePlayer))
            getInputBluePlayer();
        System.out.println(controller.reportBoard(gameId));


        System.out.println();
        System.out.println(controller.reportBoard(gameId));

        // loop that runs the actual playing of the game
        while(!controller.checkForFinishedGame(gameId)) {
            getInputRedPlayer();
            while(!controller.makeMove(gameId, xRed, yRed, redPlayer))
                getInputRedPlayer();
            getInputRedPlayer();
            while(!controller.makeMove(gameId, xRed, yRed, redPlayer))
                getInputRedPlayer();


            System.out.println();
            System.out.println(controller.reportBoard(gameId));

            //Add a break in between to cut the game right after the winning move is made.
            if (controller.checkForFinishedGame(gameId)) {
                System.out.println();
                System.out.println(controller.reportBoard(gameId));
                break;
            }

            getInputBluePlayer();
            while(!controller.makeMove(gameId, xBlue, yBlue, bluePlayer))
                getInputBluePlayer();
            getInputBluePlayer();
            while(!controller.makeMove(gameId, xBlue, yBlue, bluePlayer))
                getInputBluePlayer();

            System.out.println();
            System.out.println(controller.reportBoard(gameId));
        }

        System.out.println("Thank you for playing!\n");
        menu();
    }

    //TODO: When you exit and join a game, need to keep track of who was the last move! and user names are messed up cuz the globals

    private static void joinGame() {

        System.out.println("Enter the ID of a game to join");
        int gameID = in.nextInt();

        if (controller.checkForFinishedGame(gameID)){
            System.out.println("Game already finished. Returning to main menu.");
            menu();
        }
        // loop that runs the actual playing of the game

        System.out.println(controller.reportBoard(gameID));

        while(true) {
            getInputRedPlayer();
            while(!controller.makeMove(gameID, xRed, yRed, redPlayer))
                getInputRedPlayer();
            //System.out.println(controller.reportBoard(0));
            getInputBluePlayer();
            while(!controller.makeMove(gameID, xBlue, yBlue, bluePlayer))
                getInputBluePlayer();
            System.out.println();
            System.out.println(controller.reportBoard(gameID));
        }


    }

    private static void seeGamesInProgress() {
        System.out.println("Games in progress:\nid red player blue player");
        System.out.println(controller.seeInProgressGames());
        menu();
    }

    private static void seeCompletedGames() {
        System.out.println("Games that have been completed:\nid red player blue player");
        System.out.println(controller.seeFinishedGames());
        menu();
    }

    private static void seeLeaderboard() {


    }

    private static void menu() {
        System.out.println("Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games" +
                " in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n");
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
            default:
                System.out.println("Exiting game...");
                System.exit(0);
        }
    }
}
