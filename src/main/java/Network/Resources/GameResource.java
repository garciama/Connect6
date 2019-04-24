package Network.Resources;

import Network.ModelGateway;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Mod;
import core.Color;
import core.user.Move;
import java.util.ArrayList;
import java.util.List;
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
           int id = ModelGateway.getController().newPublicGame(redPlayer, bluePlayer);
            String str = Integer.toString(id);
            res = Response.ok(str).build();
        }
        else{
            String str1 = "players not found, try again or create players";
            res = Response.status(404).entity(str1).build();
        }
        return res;
    }

    @POST
    @Path("joinGame/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response joinGame(@PathParam("id") String idNumber, String data){
        Response res;
        int id = -1;

        String response;

        JSONObject obj = new JSONObject(data);
        String userName = obj.getString("name");

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

        if (ModelGateway.getController().checkForFinishedGame(id)){
            response = "Game " + id + " is already finished.";
            res = Response.status(400).entity(response).build();
            return res;
        }
        //Now we now the game exists and is in progress.

        //If the username passed in isn't the username of the red player in the game or the blue player,
        //throw a 403 forbidden.
        if (!(ModelGateway.getController().getUserNameRed(id).equalsIgnoreCase(userName) ||
            ModelGateway.getController().getUserNameBlue(id).equalsIgnoreCase(userName))){
            response = "You aren't part of this game.";
            res = Response.status(403).entity(response).build();
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
    /* data will contain an x coordinate, y coordinate and name.
      Json eg '{"x": "5", "y": "7", "name": "Sam"}' */
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
        int x = obj.getInt("x");
        int y = obj.getInt("y");
        String userName = obj.getString("name");


        if (! (ModelGateway.getController().getUserNameRed(id).equalsIgnoreCase(userName) ||
            ModelGateway.getController().getUserNameBlue(id).equalsIgnoreCase(userName))){
            response = "You aren't part of this game";
            res = Response.status(403).entity(response).build();
            return res;
        }


        if (!ModelGateway.getController().userCurrentTurn(id).equalsIgnoreCase(userName)){
            response = "it is not your turn";
            res = Response.status(403).entity(response).build();
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

        List<Move> moves = ModelGateway.getController().getMovesInGame(id);
        BoardInfo newBoardInfo = new BoardInfo();


        for(int i = 0; i < moves.size(); i++){
            int x = moves.get(i).getX();
            int y = moves.get(i).getY();
            Color c;

            if ( ModelGateway.getController().getUserNameRed(id).equalsIgnoreCase(moves.get(i).getOwnerName())) {
                c = Color.Red;
            } else
                c = Color.Blue;

            SquareInfo newSquareInfo = new SquareInfo(x, y, c);
            newBoardInfo.addSquareInfo(newSquareInfo);
        }

        Gson gson = new Gson();
        return gson.toJson(newBoardInfo);
    }

    public class SquareInfo {
        private int x;
        private int y;
        private Color color;

        SquareInfo(int newX, int newY, Color newColor){
            x = newX;
            y = newY;
            color = newColor;
        }
        public int getX() { return x; }
        public int getY() { return y; }
        public Color getColor() {return color; }
    }

    public class BoardInfo {
        private List<SquareInfo> Board = new ArrayList<>();

        public void addSquareInfo(SquareInfo newObject){
            Board.add(newObject);
        }
        public List<SquareInfo> getBoardSquares(){ return Board; }

    }

}
