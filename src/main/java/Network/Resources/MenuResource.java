package Network.Resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.controller.GameController;


import javax.inject.Singleton;
import com.google.gson.Gson;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("game")
public class MenuResource {

    GameController controller;

    public MenuResource() {
        controller = new GameController();
        controller.registerNewPlayer("walker");
        controller.registerNewPlayer("sam");
        controller.newPublicGame("walker", "sam");
    }

    @GET
    @Path("menu")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMenu() {
        return "Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games" +
                " in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n";
    }

    @GET
    @Path("board/{game_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBoard(@PathParam("game_id") String gameID) {
        int id = Integer.parseInt(gameID);
        String board = controller.reportBoard(id);
        return board;
    }

    @GET
    @Path("inProgress")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGamesInProgress() {
        String games = controller.seeInProgressGames();
        return games;
    }

    @PUT
    @Path("createUser")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(String username) {
        System.out.println(username);
        controller.registerNewPlayer(username);
        String str = "user created successfully";
        Response res = Response.ok(str).build();
        return res;
    }

    @GET
    @Path("completed")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFinishedGames() {
        return controller.seeFinishedGames();
    }

    @GET
    @Path("leaderboard")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLeaderboard() {
        return controller.getLeaderBoard();
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
        if(controller.hasPlayerRegistered(redPlayer) && controller.hasPlayerRegistered(bluePlayer)){
            controller.newPublicGame(redPlayer, bluePlayer);
            String str = "game created";
            res = Response.ok(str).build();
        }
        else{
            String str1 = "players not found, try again or create players";
            res = Response.status(404).entity(str1).build();
        }
        return res;
    }
}
