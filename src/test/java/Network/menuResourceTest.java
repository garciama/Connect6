package Network;

import core.controller.GameController;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

//Maybe restart server for each test.
public class menuResourceTest {

    private static final String HOST_URI = "http://localhost:6969/";
    private static HttpServer server;
    private static Client client;

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
    public void testGetGameMenu() {
        String response = client.target(HOST_URI)
                .path("menu")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games" +
                " in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testInProgress() {
        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Nick");
        ModelGateway.getController().newPublicGame("Sam", "Nick");

        String response = client.target(HOST_URI)
                .path("menu/inProgress")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "1 Sam Nick\n";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testFinished() {
        ModelGateway.getController().registerNewPlayer("Michael");
        ModelGateway.getController().registerNewPlayer("San");
        ModelGateway.getController().newPublicGame("Michael", "San");
        ModelGateway.getController().makeMove(2, 1,1,"Michael");
        ModelGateway.getController().makeMove(2, 2,2,"Michael");
        ModelGateway.getController().makeMove(2, 3,3,"Michael");
        ModelGateway.getController().makeMove(2, 4,4,"Michael");
        ModelGateway.getController().makeMove(2, 5,5,"Michael");
        ModelGateway.getController().makeMove(2, 6,6,"Michael");


        String response = client.target(HOST_URI)
                .path("menu/completed")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        System.out.println(response);

        String expected = "2 Michael San\n";

        Assert.assertEquals(expected, response);
    }

    @Test
    public void testLeaderboard() {
        String response = client.target(HOST_URI)
                .path("menu/leaderboard")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "-----------------------------------------------------------\n" +
                "|    Name    |   Score   |   Wins   |  Losses  |   Ties   |\n" +
                "-----------------------------------------------------------\n" +
                "|  Michael   |     3     |    1     |    0     |    0     |\n" +
                "|    Sam     |     0     |    0     |    0     |    0     |\n" +
                "|  testing   |     0     |    0     |    0     |    0     |\n" +
                "|  newUser   |     0     |    0     |    0     |    0     |\n" +
                "|    Nick    |     0     |    0     |    0     |    0     |\n" +
                "|    San     |     0     |    0     |    1     |    0     |\n" +
                "-----------------------------------------------------------";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testCreateUser() {
        // The data to send with the PUT
        Entity data = Entity.entity("testing", MediaType.TEXT_PLAIN);

        String response = client.target(HOST_URI)
                .path("menu/createUser")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

        Assert.assertEquals("user created successfully", response);
        Assert.assertTrue(ModelGateway.getController().hasPlayerRegistered("newUser"));
    }

    @Test(expected = WebApplicationException.class)
    public void testCreateUser2(){
        Entity data = Entity.entity("newUser", MediaType.TEXT_PLAIN);

        String response = client.target(HOST_URI)
                .path("menu/createUser")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

        //Try to register a user with the same name
         response = client.target(HOST_URI)
                .path("menu/createUser")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);



    }


//    @Test
//    public void testCreateGame() {
//        controller.registerNewPlayer("walker");
//        controller.registerNewPlayer("sam");
//        // The data to send with the PUT
//        Entity data = Entity.entity("{\"red\":\"walker\",\"blue\":\"sam\"}", MediaType.APPLICATION_JSON);
//
//        String response = client.target(HOST_URI)
//                .path("game/createGame")
//                .request(MediaType.TEXT_PLAIN)
//                .put(data, String.class);
//
//        Assert.assertEquals("game created", response);
//
//        //System.out.println(ModelGateway.getController().seeInProgressGames());
//
//
//    }

}
