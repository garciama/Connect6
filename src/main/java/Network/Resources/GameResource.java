package Network.Resources;

import Network.ModelGateway;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("menu/game")
public class GameResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void getGame() {

    }

}
