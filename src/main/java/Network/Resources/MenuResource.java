package Network.Resources;

import core.controller.GameController;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("menu")
public class MenuResource {

    GameController controller = new GameController();

    private void buildController() {
        controller.registerNewPlayer("walker");
        controller.registerNewPlayer("sam");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        buildController();
        int gameID = controller.newPublicGame("walker", "sam");
        String board = controller.reportBoard(gameID);
        return gameID + " " + board;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "I received your POST";
    }
}

