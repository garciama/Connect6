package Network.Resources;

import Network.ModelGateway;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("game")
public class GameResource {

    @PUT
    @Path("createGame")
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
    /*data will contain an x coordinate, y coordinate and name.
      Json eg. "{"x": "5", "y": "7", "name": "Sam"}"
      For some reason doesn't work with curl? Works fine with Postman
      Could maybe put username in URL??
     */
    public String makeMove(@PathParam("id") String idNumber, String data){
        int id = -1;

        try {
            id = Integer.parseInt(idNumber);
        } catch( NumberFormatException e ) {
            throw new WebApplicationException(404);
        }

        if (!ModelGateway.getController().checkIfGameExists(id))
            throw new WebApplicationException(404);
        

        JSONObject obj = new JSONObject(data);
        String xStr = obj.getString("x");
        String yStr = obj.getString("y");
        String userName = obj.getString("name");

        int x = -1;
        int y = -1;

        try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
        } catch (NumberFormatException e){
            throw new WebApplicationException(400);
        }

        if (x < 0 || x > 18 || y < 0 || y > 18)
            throw new WebApplicationException(400);

        if (!ModelGateway.getController().hasPlayerRegistered(userName))
            throw new WebApplicationException(400);


        if (!ModelGateway.getController().makeMove(id, x, y, userName))
            return "Invalid Move";

        //System.out.println(ModelGateway.getController().reportBoard(id));

        return "";
    }

    @GET
    @Path("getBoard{id}")
    public String getGameBoard(@PathParam("id") String idNumber){
        int id = -1;

        try {
            id = Integer.parseInt(idNumber);
        } catch( NumberFormatException e ) {
            throw new WebApplicationException(404);
        }

        if (!ModelGateway.getController().checkIfGameExists(id))
            throw new WebApplicationException(404);

        return ModelGateway.getController().reportBoard(id);
    }

}
