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

        if (!controller.hasPlayerRegistered(redPlayer)) {
            System.out.println("Error: This player doesn't exist! Create a new user first.\n");
            menu();
        }

        System.out.print("Enter the next user to play: ");
        bluePlayer = in.nextLine();

        if (bluePlayer.equals("-1"))
            menu();

        if (!controller.hasPlayerRegistered(bluePlayer)) {
            System.out.println("Error: This player doesn't exist! Create a new user first.\n");
            menu();
        }
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
        System.out.print("Enter your unique username: ");
        String newPlayerName = in.nextLine();
        while(!controller.registerNewPlayer(newPlayerName)) {
            System.out.println("Error: Player already exists! Enter another name");
            System.out.print("Enter your unique username: ");
            newPlayerName = in.nextLine();

        }
        System.out.println("User successfully created! Returning to menu...");
        menu();

    }

    //TODO: have an actual way to keep track of users so you can actually join
    private static void makeNewGame() {
        System.out.println("Enter -1 at any point to go back to the main menu");
        getUsers();

        int gameId = controller.newGame(redPlayer, bluePlayer);


        if (!controller.hasPlayerRegistered(bluePlayer)) {
            System.out.println("Error: This player doesn't exist! Create a new user first.\n");
            menu();
        }

        System.out.println("Your gameId is " + gameId);
        playGame(gameId);
    }

    private static void playGame(int gameId) {

        takeFirstTurn(gameId);

        playGameStartingWithBlue(gameId);
    }

    private static void takeFirstTurn(int gameId) {

        printBoard(gameId);

        // First player gets 1 turn.
        getInputRedPlayer();

        while(!controller.makeMove(gameId, xRed, yRed, redPlayer))
            getInputRedPlayer();

        printBoard(gameId);
    }

    private static void bluePlayerTakeTurn(int gameId) {

        for (int i = 0; i < 2; i++){
            getInputBluePlayer();

            while(!controller.makeMove(gameId, xBlue, yBlue, bluePlayer))
                getInputBluePlayer();

            printBoard(gameId);
        }

    }

    private static void redPlayerTakeTurn(int gameId) {

        for (int i = 0; i < 2; i++) {
            getInputRedPlayer();

            while(!controller.makeMove(gameId, xRed, yRed, redPlayer))
                getInputRedPlayer();

            printBoard(gameId);
        }

    }

    private static void printBoard(int gameId) {
        System.out.println("\n" + controller.reportBoard(gameId));
    }

    private static void joinGame() {

        System.out.println("Enter the ID of a game to join");
        int gameID = in.nextInt();
        if (controller.checkForFinishedGame(gameID)){
            System.out.println("Game already finished. Returning to main menu.");
            menu();
        }
        printBoard(gameID);
        //check to see who made last move, then start game with other player
        String lastUserToMakeMove = controller.lastUserToMakeMove(gameID);
        if(lastUserToMakeMove.equals(redPlayer)){
            playGameStartingWithBlue(gameID);
        }
        else{
            playGameStartingWithRed(gameID);
        }

        System.out.println("Thank you for playing!\n");
        menu();
    }

    private static void playGameStartingWithBlue(int id){
        while(!controller.checkForFinishedGame(id)) {

            bluePlayerTakeTurn(id);

            //Break to cut the game right after the winning move is made.
            if (controller.checkForFinishedGame(id)) {
                printBoard(id);
                break;
            }

            redPlayerTakeTurn(id);
        }
        System.out.println("Thank you for playing!\n");
        menu();
    }

    private static void playGameStartingWithRed(int id){
        while(!controller.checkForFinishedGame(id)) {

            redPlayerTakeTurn(id);

            //Break to cut the game right after the winning move is made.
            if (controller.checkForFinishedGame(id)) {
                printBoard(id);
                break;
            }

            bluePlayerTakeTurn(id);
        }

        System.out.println("Thank you for playing!\n");
        menu();
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
