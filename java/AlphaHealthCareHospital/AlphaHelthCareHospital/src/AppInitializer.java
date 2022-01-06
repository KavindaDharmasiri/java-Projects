import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    public static Stage primaryStage = null;
    public static Scene primaryScene = null;
    double x, y = 0;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(AppInitializer.class, launcher.class, args);
    }

    @Override
    public void init() throws Exception {
        OpenInitializeWindowController init = new OpenInitializeWindowController();
        init.checkFunctions();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppInitializer.primaryStage = primaryStage;

    }
}

