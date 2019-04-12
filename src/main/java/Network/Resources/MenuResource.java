package Network.Resources;

import Network.ModelGateway;
import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("menu")
public class MenuResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMenu() {
        return "Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games" +
                " in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n";
    }

    @PUT
    @Path("createUser")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(String username) {
        System.out.println(username);

        if (!ModelGateway.getController().registerNewPlayer(username)){
            throw new WebApplicationException(400);
        }
        String str = "user created successfully";
        Response res = Response.ok(str).build();
        return res;
    }


    @GET
    @Path("inProgress")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGamesInProgress() {

        String games = ModelGateway.getController().seeInProgressGames();
        return games;
    }


    @GET
    @Path("completed")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFinishedGames() {
        return ModelGateway.getController().seeFinishedGames();
    }

    @GET
    @Path("leaderboard")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLeaderboard() {
        return ModelGateway.getController().getLeaderBoard();
    }



}
