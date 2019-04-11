package Network.Resources;

import Network.ModelGateway;
import com.sun.org.apache.xpath.internal.operations.Mod;
import core.controller.GameController;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Think of a resource as a thing your game cares about.
//assignment 5
//create an arraylist of toDo objects > boolean done? String content of todo.
@Path("menu")
public class MenuResource {

    public MenuResource() {
        ModelGateway.getController().registerNewPlayer("walker");
        ModelGateway.getController().registerNewPlayer("sam");
        ModelGateway.getController().newPublicGame("walker", "sam");
    }

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

    @PUT
    @Path("createGame")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGame(String players) {
        JSONObject obj = new JSONObject(players);
        String redPlayer = obj.getString("red");
        String bluePlayer = obj.getString("blue");
        Response res;

        if(ModelGateway.getController().hasPlayerRegistered(redPlayer) && ModelGateway.getController().hasPlayerRegistered(bluePlayer)){
            ModelGateway.getController().newPublicGame(redPlayer, bluePlayer);
            String str = "game created";
            res = Response.ok(str).build();
        }
        else{
            String str1 = "players not found, try again or create players";
            res = Response.status(404).entity(str1).build();
        }
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
