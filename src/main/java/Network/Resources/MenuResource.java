package Network.Resources;

import core.controller.GameController;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("menu")
public class MenuResource {

    GameController controller = new GameController();

//    private void buildController() {
//        controller.registerNewPlayer("walker");
//        controller.registerNewPlayer("sam");
//    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMenu() {
        return "Enter a number to select an option:\n1. Create a user\n2. Create a new game\n3. See games" +
                " in progress\n4. Join a game\n5. See list of completed games\n6. See leaderboard\n";

//        buildController();
//        int gameID = controller.newPublicGame("walker", "sam");
//        String board = controller.reportBoard(gameID);
//        return gameID + " " + board;
    }

    @GET
    @Path("menu/1")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCreateUser(){
        return "Enter your unique username (12 characters max, -1 to return to menu): ";

    }

    @POST
    @Path("")
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {


        //controller.registerNewPlayer();

        return "User successfully created! Returning to menu...";

    }
}
