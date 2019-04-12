package Network.Resources;

import Network.ModelGateway;
<<<<<<< HEAD
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.google.gson.Gson;
=======
>>>>>>> 23c2c026d2830c3e6c494cf4bfd3d1269acacc33
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

<<<<<<< HEAD

@Path("game")
public class GameResource {

=======
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
>>>>>>> 23c2c026d2830c3e6c494cf4bfd3d1269acacc33

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
<<<<<<< HEAD

        return res;
    }


    @GET
    @Path("joinGame{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String joinGame(@PathParam("id") String idNumber){
        int id = -1;

        try {
            id = Integer.parseInt(idNumber);
        } catch( NumberFormatException e ) {
            throw new WebApplicationException(404);
        }

        if( !ModelGateway.getController().checkIfGameExists(id))
            throw new WebApplicationException(404);

        return "Game Joined!";

    }

    @PUT
    @Path("makeMove{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeMove(@PathParam("id") String idNumber, String xAndY){

        return "";
    }

=======
        return res;
    }

>>>>>>> 23c2c026d2830c3e6c494cf4bfd3d1269acacc33
}
