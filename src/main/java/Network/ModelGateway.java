package Network;

import core.controller.GameController;

public class ModelGateway {

    private static GameController controller;

    public ModelGateway() {
        controller.registerNewPlayer("walker");
        controller.registerNewPlayer("same");
        controller.newPublicGame("walker", "sam");
    }

    public static void setController(GameController c){
        controller = c;
    }

    public static GameController getController(){
        return controller;
    }

}
