package Network.Resources;

import core.controller.UI;

import javax.ws.rs.GET;
        import javax.ws.rs.POST;
        import javax.ws.rs.Path;
        import javax.ws.rs.Produces;
        import javax.ws.rs.core.MediaType;

@Path("menu")
public class MenuResource {
    UI ui = new UI();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "aaa";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "I received your POST";
    }
}

