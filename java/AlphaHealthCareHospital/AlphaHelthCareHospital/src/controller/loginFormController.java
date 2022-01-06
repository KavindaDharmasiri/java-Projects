package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginFormController implements Initializable {

    public static Stage prime;
    public AnchorPane movableAnchrPane;
    public AnchorPane mainAnchrPan;
    public AnchorPane slid;
    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;


    public static void setPrimary(Stage primea) {
        prime = primea;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        mainAnchrPan.setOnMouseDragged(event -> event.setDragDetect(true));

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

    public void min(javafx.scene.input.MouseEvent mouseEvent) {
        Stage s = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    public void adminLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/adminLoginForm.fxml");
        if (resource == null) {
            resource = new URL("file:/F:/AlphaHelthCareHospital/out/production/AlphaHelthCareHospital/view/adminLoginForm.fxml");
        }

        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void cashierLogin(ActionEvent actionEvent) throws IOException, URISyntaxException {
        URL resource = getClass().getResource("../view/cashierLoginForm.fxml");
        if (resource == null) {
            resource = new URL("file:/F:/AlphaHelthCareHospital/out/production/AlphaHelthCareHospital/view/cashierLoginForm.fxml");
        }

        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void doctorLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/doctorLoginForm.fxml");
        if (resource == null)
            resource = new URL("file:/F:/AlphaHelthCareHospital/out/production/AlphaHelthCareHospital/view/doctorLoginForm.fxml");

        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void createAccount(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/createAccountForm.fxml");
        if (resource == null) {
            resource = new URL("file:/F:/AlphaHelthCareHospital/out/production/AlphaHelthCareHospital/view/createAccountForm.fxml");
        }

        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);
    }

    public void aboutHospital(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/aboutHospitalForm.fxml");
        if (resource == null) {
            resource = new URL("file:/F:/AlphaHelthCareHospital/out/production/AlphaHelthCareHospital/view/aboutHospitalForm.fxml");
        }

        Parent load = FXMLLoader.load(resource);
        movableAnchrPane.getChildren().clear();
        movableAnchrPane.getChildren().add(load);

    }

}
