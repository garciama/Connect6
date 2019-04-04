package Network.Resources;

import javax.ws.rs.GET;
        import javax.ws.rs.POST;
        import javax.ws.rs.Path;
        import javax.ws.rs.Produces;
        import javax.ws.rs.core.MediaType;

@Path("menu")
public class MenuResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return "Hello there from your server!";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "I received your POST";
    }
}

