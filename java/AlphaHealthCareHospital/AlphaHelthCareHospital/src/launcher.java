import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class launcher extends Preloader {

    private Stage proloaderStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.proloaderStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/openInitializeWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            proloaderStage.hide();
        }
    }
}
