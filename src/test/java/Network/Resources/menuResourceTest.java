package Network.Resources;

import Network.ModelGateway;
import com.google.gson.Gson;
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
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Sam");
        ModelGateway.getController().registerNewPlayer("Nick");
        ModelGateway.getController().newPublicGame("Sam", "Nick");

        String response = client.target(HOST_URI)
                .path("menu/inProgress")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "1 Sam Nick\n";

        Assert.assertEquals(menu, response);

        ModelGateway.getController().registerNewPlayer("Michael");
        ModelGateway.getController().newPublicGame("Michael", "Sam");

         response = client.target(HOST_URI)
                .path("menu/inProgress")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
         menu = "1 Sam Nick\n2 Michael Sam\n";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testFinished() {
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Michael");
        ModelGateway.getController().registerNewPlayer("San");
        ModelGateway.getController().newPublicGame("Michael", "San");
        ModelGateway.getController().makeMove(1, 1,1,"Michael");
        ModelGateway.getController().makeMove(1, 2,2,"Michael");
        ModelGateway.getController().makeMove(1, 3,3,"Michael");
        ModelGateway.getController().makeMove(1, 4,4,"Michael");
        ModelGateway.getController().makeMove(1, 5,5,"Michael");
        ModelGateway.getController().makeMove(1, 6,6,"Michael");


        String response = client.target(HOST_URI)
                .path("menu/completed")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string

        String expected = "1 Michael San\n";

        Assert.assertEquals(expected, response);
    }

    @Test
    public void testLeaderboard() {
        controller = new GameController();
        ModelGateway.setController(controller);

        ModelGateway.getController().registerNewPlayer("Michael");
        ModelGateway.getController().registerNewPlayer("Walker");
        ModelGateway.getController().registerNewPlayer("Nick");
        ModelGateway.getController().registerNewPlayer("Sam");

        String response = client.target(HOST_URI)
                .path("menu/leaderboard")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string

        Gson gson = new Gson();
        MenuResource.LeaderBoardInfo rows = gson.fromJson(response, MenuResource.LeaderBoardInfo.class);

        Assert.assertEquals("Nick", rows.getRows().get(0).name);
        Assert.assertEquals(0, rows.getRows().get(0).score);
        Assert.assertEquals(0, rows.getRows().get(0).wins);
        Assert.assertEquals(0, rows.getRows().get(0).losses);

        //Now after someone wins a game, check the json response again.
        ModelGateway.getController().getUsers().get("Walker").addWin();
        ModelGateway.getController().getUsers().get("Walker").addWin();

        ModelGateway.getController().getUsers().get("Michael").addWin();

        ModelGateway.getController().getUsers().get("Sam").addWin();
        ModelGateway.getController().getUsers().get("Sam").addWin();
        ModelGateway.getController().getUsers().get("Sam").addWin();


        response = client.target(HOST_URI)
                .path("menu/leaderboard")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string

        System.out.println(response);

        rows = gson.fromJson(response, MenuResource.LeaderBoardInfo.class);

        //And we can also see that the json object is in order from lowest to highest score
        Assert.assertEquals("Sam", rows.getRows().get(3).name);
        Assert.assertEquals(9, rows.getRows().get(3).score);
        Assert.assertEquals(3, rows.getRows().get(3).wins);
        Assert.assertEquals(0, rows.getRows().get(3).losses);

        Assert.assertEquals("Walker", rows.getRows().get(2).name);
        Assert.assertEquals(6, rows.getRows().get(2).score);
        Assert.assertEquals(2, rows.getRows().get(2).wins);
        Assert.assertEquals(0, rows.getRows().get(2).losses);

        Assert.assertEquals("Michael", rows.getRows().get(1).name);
        Assert.assertEquals(3, rows.getRows().get(1).score);
        Assert.assertEquals(1, rows.getRows().get(1).wins);
        Assert.assertEquals(0, rows.getRows().get(1).losses);
    }

    @Test
    public void testCreateUser() {
        controller = new GameController();
        ModelGateway.setController(controller);

        // The data to send with the PUT
        Entity data = Entity.entity("{\"name\":\"Testing\"}", MediaType.APPLICATION_JSON);

        String response = client.target(HOST_URI)
                .path("menu/createUser")
                .request(MediaType.TEXT_PLAIN)
                .post(data, String.class);

        Assert.assertEquals("User Created Successfully", response);
        Assert.assertTrue(ModelGateway.getController().hasPlayerRegistered("Testing"));
    }

    @Test(expected = WebApplicationException.class)
    public void testCreateUser2(){
        controller = new GameController();
        ModelGateway.setController(controller);

        Entity data = Entity.entity("newUser", MediaType.TEXT_PLAIN);

            client.target(HOST_URI)
                .path("menu/createUser")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);

        //Try to register a user with the same name
            client.target(HOST_URI)
                .path("menu/createUser")
                .request(MediaType.TEXT_PLAIN)
                .put(data, String.class);



    }



}
