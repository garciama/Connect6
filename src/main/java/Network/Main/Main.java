package Network.Main;

import Network.ModelGateway;
import core.controller.GameController;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


public class Main {
    public static final int PORT = 6969;
    public static final String URI = "http://0.0.0.0/";

    public static final String[] RESOURCES_PACKAGES = {"Network.Resources"};
    public static GameController controller;

    public static void main(String [] args){
        startServer();
    }


    public static HttpServer startServer() {
        controller = new GameController();
        ModelGateway.setController(controller);
        URI baseUri = UriBuilder.fromUri(URI).port(PORT).build();
        final ResourceConfig config = new ResourceConfig().packages(RESOURCES_PACKAGES);
        return GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
    }



}
