package Network.Resources;

import Network.ModelGateway;
import com.google.gson.Gson;
import core.user.User;
import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Path("menu")
public class MenuResource {

    @POST
    @Path("createUser")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(String username) {

        JSONObject obj = new JSONObject(username);
        String name = obj.getString("name");

        Response res;
        if (!ModelGateway.getController().registerNewPlayer(name)){
            throw new WebApplicationException(400);
        }
        String str = "User Created Successfully";
        res = Response.ok(str).build();
        return res;
    }

    //TODO: create a JSON object to return
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
    public String getLeaderboard() {

        LeaderBoardInfo leaderboard = new LeaderBoardInfo();

        Map<String, User> sortedUsers = ModelGateway.getController().getSortedByScore();

        for( String key : sortedUsers.keySet()){
            User row = sortedUsers.get(key);

            UserInfoRow newUser = new UserInfoRow(row.getName(), row.getScore(), row.getWins(),
                                    row.getLosses(), row.getTies());
            leaderboard.addUserInfo(newUser);
        }

        Gson gson = new Gson();
        return gson.toJson(leaderboard);
    }

    public class LeaderBoardInfo {
        private List<UserInfoRow> leaderboardRows = new ArrayList<>();

        public void addUserInfo(UserInfoRow newObject){
            leaderboardRows.add(newObject);
        }

        public List<UserInfoRow> getRows(){
            return leaderboardRows;
        }
    }

    public class UserInfoRow {
        String name;
        int score;
        int wins;
        int losses;
        int ties;

        UserInfoRow(String newName, int newScore, int newWins, int newLosses, int newTies){
            name = newName;
            score = newScore;
            wins = newWins;
            losses = newLosses;
            ties = newTies;
        }
    }

}
