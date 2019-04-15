package Network.Resources;

import Network.ModelGateway;
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
    @Path("joinGame/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response joinGame(@PathParam("id") String idNumber){
        Response res;
        int id = -1;

        String response;

        try {
            id = Integer.parseInt(idNumber);
        } catch( NumberFormatException e ) {
            response = "invalid game id value.";
            res = Response.status(404).entity(response).build();
            return res;
        }

        if( !ModelGateway.getController().checkIfGameExists(id)) {
            response = "Game " + id + " does not exist.";
            res = Response.status(404).entity(response).build();
            return res;
        }

        response = "Game " + id + " joined!";
        res = Response.ok(response).build();
        return res;
    }

    @PUT
    @Path("makeMove/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    /*data will contain an x coordinate, y coordinate and name.
      Json eg. '{"x": "5", "y": "7", "name": "Sam"}'
     */
    public Response makeMove(@PathParam("id") String idNumber, String data){
        int id = -1;
        Response res;
        String response;

        try {
            id = Integer.parseInt(idNumber);
        } catch( NumberFormatException e ) {
            //If a non integer value is entered.
            response = "invalid game id value.";
            res = Response.status(404).entity(response).build();
            return res;
        }
        if (!ModelGateway.getController().checkIfGameExists(id)) {
            //If game id doesn't exist
            response = "no games found with id " + id;
            res = Response.status(404).entity(response).build();
            return res;
        }

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
            //If invalid number format entered in Json
            response = "invalid x or y value";
            res = Response.status(404).entity(response).build();
            return res;
        }

        if (x < 0 || x > 18 || y < 0 || y > 18) {
            response = "out of bounds value";
            res = Response.status(400).entity(response).build();
            return res;
        }

        if (!ModelGateway.getController().hasPlayerRegistered(userName)){
            response = "player " + userName + " not found";
            res = Response.status(404).entity(response).build();
            return res;
        }

        if (!ModelGateway.getController().makeMove(id, x, y, userName)) {
            response = "invalid move(square taken)";
            res = Response.status(400).entity(response).build();
            return res;
        }

        response = "move made";
        res = Response.ok(response).build();
        return res;
    }

    @GET
    @Path("getBoard/{id}")
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
