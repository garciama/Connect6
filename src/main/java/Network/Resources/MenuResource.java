package Network.Resources;

import Network.ModelGateway;
import com.google.gson.Gson;
import core.user.User;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
        Response res;
        if (!ModelGateway.getController().registerNewPlayer(username)){
            throw new WebApplicationException(400);
        }
        String str = "user created successfully";
        res = Response.ok(str).build();
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
    public String getLeaderboard() {

        LeaderBoardInfo leaderboard = new LeaderBoardInfo();

        Map<String, User> allUsers = ModelGateway.getController().getUsers();

        for( String key : allUsers.keySet()){
            User row = allUsers.get(key);

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
