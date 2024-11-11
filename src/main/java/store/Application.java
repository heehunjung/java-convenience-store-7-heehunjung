package store;

import java.io.IOException;
import store.controller.FrontController;
import store.factory.ControllerFactory;

public class Application {
    public static void main(String[] args) {
        FrontController controller = ControllerFactory.createFrontController();

        try {
            controller.run();
        } catch (IOException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
