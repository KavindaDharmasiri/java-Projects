package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CashierDashBoardFormController implements Initializable {
    private static Stage stage1;
    private final String tempTwo;
    public AnchorPane slider;
    public Label txtDate;
    public Label txtTime;
    public Label Menu;
    public Label MenuClose;
    public ImageView Exit;
    public AnchorPane resepDashBoard;
    public AnchorPane receptionAnchorpne;
    public Label txtRecepName;
    public Label txtName;
    public Circle circle;
    int temp;

    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        tempTwo = formatter.format(date);
    }

    public static void setStage(Stage stage) {
        stage1 = stage;
    }

    public void Timenow() {

        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                final String timeNow = sdf.format(new Date());
                Platform.runLater(() -> {
                    txtTime.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    public void min(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("/sample/Images/download.jpeg");
        circle.setFill(new ImagePattern(img));
        txtDate.setText(tempTwo.substring(0, 10));
        Timenow();

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        resepDashBoard.setOnMouseDragged(event -> event.setDragDetect(true));

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/cashierDashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) resepDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();

        loginFormController.prime.show();

        notification();
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Reception Log Out Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(4));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void cashierHome(ActionEvent actionEvent) throws IOException {
        CashierHomeController.name = txtRecepName.getText();
        URL resource = getClass().getResource("../view/cashierHome.fxml");
        Parent load = FXMLLoader.load(resource);
        receptionAnchorpne.getChildren().clear();
        receptionAnchorpne.getChildren().add(load);


    }

    public void patientForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/patientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        receptionAnchorpne.getChildren().clear();
        receptionAnchorpne.getChildren().add(load);
    }

    public void bookingForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/apoinmentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        receptionAnchorpne.getChildren().clear();
        receptionAnchorpne.getChildren().add(load);
    }

    public void apoinmentDetails(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/apoinmentDetailForm.fxml");
        temp = 1;
        Parent load = FXMLLoader.load(resource);
        receptionAnchorpne.getChildren().clear();
        receptionAnchorpne.getChildren().add(load);
    }

    public void getMoneyPatient(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MoneyDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        receptionAnchorpne.getChildren().clear();
        receptionAnchorpne.getChildren().add(load);
    }
}
