package Network.Resources;

import core.controller.GameController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("game")
public class MenuResource {

    GameController controller = new GameController();

    private void buildController() {
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
        buildController();
        String board;
        try {
            int id = Integer.parseInt(gameID);
            board = controller.reportBoard(id);
        } catch(Exception e) {
            throw new WebApplicationException(404);
        }
        return board;
    }

    @GET
    @Path("inProgress")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGamesInProgress() {
        buildController();
        String games = controller.seeInProgressGames();
        return games;
    }

    @GET
    @Path("completed")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFinishedGames() {
        buildController();
        return controller.seeFinishedGames();
    }

    @GET
    @Path("leaderboard")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLeaderboard() {
        buildController();
        return controller.getLeaderBoard();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "I received your POST";
    }
}

