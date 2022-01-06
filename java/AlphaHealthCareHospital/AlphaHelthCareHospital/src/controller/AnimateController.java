package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnimateController implements Initializable {
    public static String temp;
    static String nameOne;
    public AnchorPane rootPane;

    public static void setValue(String location, String name) {

        temp = location;
        nameOne = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SplashScreen().start();

    }

    class SplashScreen extends Thread {
        public void run() {
            try {
                Thread.sleep(2500);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(temp));
                        Parent root1 = null;

                        try {
                            root1 = fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (temp.equals("../view/adminDashBoardForm.fxml")) {
                            AdminDashBoardFormController controller = fxmlLoader.getController();
                            controller.txtAdminName.setText(nameOne);
                            controller.txtName.setText(nameOne);

                        }
                        if (temp.equals("../view/docDashBoardForm.fxml")) {
                            DocDashBoardFormController controller = fxmlLoader.getController();
                            controller.txtDocName.setText(nameOne);
                            controller.txtName.setText(nameOne);

                        }
                        if (temp.equals("../view/cashierDashBoardForm.fxml")) {
                            CashierDashBoardFormController controller = fxmlLoader.getController();
                            controller.txtRecepName.setText(nameOne);
                            controller.txtName.setText(nameOne);

                        }
                        Stage stage = new Stage();
                        Scene scene = new Scene(root1);
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        scene.setFill(Color.TRANSPARENT);
                        CashierDashBoardFormController.setStage(stage);
                        stage.show();

                        rootPane.getScene().getWindow().hide();

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
