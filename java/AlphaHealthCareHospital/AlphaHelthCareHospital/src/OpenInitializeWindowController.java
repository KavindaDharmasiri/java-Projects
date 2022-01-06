import controller.loginFormController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OpenInitializeWindowController {


    public static Label lblLoadingg;
    public Label lblLoading;

    public void initialize() {
        lblLoadingg = lblLoading;
    }

    public String checkFunctions() {
        final String[] message = {""};
        Thread t1 = new Thread(() -> {
            message[0] = "First Function";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            message[0] = "Second Function";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            message[0] = "Open Stage";
            Platform.runLater(() -> lblLoadingg.setText(message[0]));

            Platform.runLater(new Runnable() {
                double x, y = 0;

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("view/loginForm.fxml"));

                        root.setOnMousePressed(event -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged(event -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        scene.setFill(Color.TRANSPARENT);
                        loginFormController.setPrimary(stage);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.show();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        });


        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return message[0];
    }

}
