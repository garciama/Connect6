package Network;

import core.controller.GameController;
import core.user.Move;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class gameResourceTest {

    private static final String HOST_URI = "http://localhost:6969/";
    private static HttpServer server;
    private static Client client;
    private static GameController controller;


    // This starts the server and creates the client object once before all tests in this class
    @BeforeClass  //run before the class is even created.
    public static void startServer() {
        server = Network.Main.Main.startServer();
        client = ClientBuilder.newClient();
    }

    // This shuts the server down and closes the client after all tests in this class are complete
    @AfterClass
    public static void stopServer() {
        if( client != null ) client.close();
        if( server != null ) server.shutdown();
    }


    @Test
    public void testCreateGame() {
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().registerNewPlayer("Sam");
        // The data to send with the PUT
        Entity data = Entity.entity("{\"red\":\"Walker\",\"blue\":\"Sam\"}", MediaType.APPLICATION_JSON);

        String before = ModelGateway.getController().seeInProgressGames();
        Assert.assertEquals("", before);

        String response = client.target(HOST_URI)
                .path("game/createGame")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

        Assert.assertEquals("game created", response);

        String after = ModelGateway.getController().seeInProgressGames();
        Assert.assertEquals("1 Walker Sam\n", after);

        //Test to make sure the gameid counter is working
        ModelGateway.getController().registerNewPlayer("Nick");

        data = Entity.entity("{\"red\":\"Walker\",\"blue\":\"Nick\"}", MediaType.APPLICATION_JSON);

        client.target(HOST_URI)
                .path("game/createGame")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

        after = ModelGateway.getController().seeInProgressGames();
        Assert.assertEquals("1 Walker Sam\n2 Walker Nick\n", after);

        }


    @Test(expected = WebApplicationException.class)
    public void createGameTest2(){
        controller = new GameController();
        ModelGateway.setController(controller);

        Entity data = Entity.entity("{\"red\":\"Walker\",\"blue\":\"Sam\"}", MediaType.APPLICATION_JSON);

        //throws 404 because there are no users created
        client.target(HOST_URI)
                .path("game/createGame")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

    }

    @Test
    public void joinGameTest(){
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Michael");
        ModelGateway.getController().newPublicGame("Sam", "Michael");

        String response = client.target(HOST_URI)
                .path("game/joinGame/1")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

        Assert.assertEquals("Game 1 joined!", response);

        ModelGateway.getController().registerNewPlayer("Nick");
        ModelGateway.getController().registerNewPlayer("San");
        ModelGateway.getController().newPublicGame("Nick", "San");

         response = client.target(HOST_URI)
                 .path("game/joinGame/2")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

        Assert.assertEquals("Game 2 joined!", response);
    }

    @Test(expected = WebApplicationException.class)
    public void joinGameTest2(){
        controller = new GameController();
        ModelGateway.setController(controller);

        //Throws 404 Exception because there is no game of id asdf
        client.target(HOST_URI)
                .path("game/joinGame/asdf")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

    }

    @Test(expected = WebApplicationException.class)
    public void joinGameTest3(){
        controller = new GameController();
        ModelGateway.setController(controller);

        //Throws 404 Exception because there is no game of id 55
        client.target(HOST_URI)
                .path("game/joinGame/55")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

    }

    @Test
    public void makeMoveTest(){
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().newPublicGame("Sam", "Walker");

        List<Move> moves;

        //no moves when the game first starts
        moves = ModelGateway.getController().getMovesInGame(1);
        Assert.assertEquals(0, moves.size());

        Entity data = Entity.entity("{\"x\":\"5\",\"y\":\"12\",\"name\":\"Sam\"}", MediaType.APPLICATION_JSON);

        String response = client.target(HOST_URI)
                .path("game/makeMove/1")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);
        Assert.assertEquals("move made", response);

        //now we have a move in game 1
        moves = ModelGateway.getController().getMovesInGame(1);
        Assert.assertEquals(1, moves.size());
        Assert.assertEquals(5, moves.get(0).getX());
        Assert.assertEquals(12, moves.get(0).getY());
        Assert.assertEquals("Sam", moves.get(0).getOwner());
    }

    @Test(expected = WebApplicationException.class)
    public void makeMoveTest2(){
        controller = new GameController();
        ModelGateway.setController(controller);

        Entity data = Entity.entity("{\"x\":\"5\",\"y\":\"12\",\"name\":\"Sam\"}", MediaType.APPLICATION_JSON);

        //Throws 404 because there is no game of id 1 to make a move in.
        client.target(HOST_URI)
                .path("game/makeMove/1")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

    }

    @Test(expected = WebApplicationException.class)
    public void makeMoveTest3(){
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().newPublicGame("Sam", "Walker");

        Entity data = Entity.entity("{\"x\":\"5\",\"y\":\"12\",\"name\":\"test\"}", MediaType.APPLICATION_JSON);

        //throws 404 because there is no user named test
        client.target(HOST_URI)
                .path("game/makeMove/1")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);
    }

    @Test(expected = WebApplicationException.class)
    public void makeMoveTest4(){
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().newPublicGame("Sam", "Walker");

        Entity data = Entity.entity("{\"x\":\"asdf\",\"y\":\"12\",\"name\":\"Sam\"}", MediaType.APPLICATION_JSON);

        //throws 404 because x is an invalid value
        client.target(HOST_URI)
                .path("game/makeMove/1")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);
    }

    @Test(expected = WebApplicationException.class)
    public void makeMoveTest5(){
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().newPublicGame("Sam", "Walker");

        Entity data = Entity.entity("{\"x\":\"5\",\"y\":\"12\",\"name\":\"Sam\"}", MediaType.APPLICATION_JSON);

        client.target(HOST_URI)
                .path("game/makeMove/1")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

         data = Entity.entity("{\"x\":\"5\",\"y\":\"12\",\"name\":\"Walker\"}", MediaType.APPLICATION_JSON);

        //throws 400 because square is already taken
        client.target(HOST_URI)
                .path("game/makeMove/1")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);
    }

    @Test
    public void getBoardTest(){
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().newPublicGame("Sam", "Walker");

        String response = client.target(HOST_URI)
                .path("game/getBoard/1")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

        //Honestly, the best way to test this is to just print it and look at it.
        System.out.println(response);

        Assert.assertEquals(ModelGateway.getController().reportBoard(1), response);

        //make a move to change the board state
        ModelGateway.getController().makeMove(1, 5, 5, "Sam");

         response = client.target(HOST_URI)
                .path("game/getBoard/1")
                .request(MediaType.TEXT_PLAIN)
                .get(String.class);

         //We can see that using our get request it displays the board with the new move made on it.
        System.out.println(response);
        Assert.assertEquals(ModelGateway.getController().reportBoard(1), response);


    }





}
