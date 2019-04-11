package Network;

import Network.Main.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

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
                .path("game/menu")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games" +
                " in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testInProgress() {
        String response = client.target(HOST_URI)
                .path("game/inProgress")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "1 walker sam\n";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testFinished() {
        String response = client.target(HOST_URI)
                .path("game/completed")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testLeaderboard() {
        String response = client.target(HOST_URI)
                .path("game/leaderboard")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get(String.class); //Whatever response we get store in a string
        String menu = "1 walker sam\n";

        Assert.assertEquals(menu, response);
    }

    @Test
    public void testCreateUser() {
        // The data to send with the PUT
        Entity data = Entity.entity("newUser", MediaType.TEXT_PLAIN);

        String response = client.target(HOST_URI)
                .path("game/createUser")
                .request(MediaType.APPLICATION_JSON)
                .put(data, String.class);

        Assert.assertEquals("user created successfully", response);
    }

    @Test
    public void testCreateGame() {

    }

}
