package Network.Resources;

import Network.ModelGateway;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("menu/game")
public class GameResource {
    @PUT
    @Path("createUser")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(String username) {
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

}
