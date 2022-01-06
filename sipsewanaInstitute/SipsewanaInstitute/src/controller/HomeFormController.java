package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/15/2021
 * @Time_: 4:22 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class HomeFormController {
    public AnchorPane mainAnchor;
    @FXML
    private AnchorPane root2;
    @FXML
    private ImageView imgStudent;
    @FXML
    private ImageView imgprograme;
    @FXML
    private ImageView imgdetail;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblDescription;


    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root2);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgStudent":
                    lblMenu.setText("Manage Student");
                    lblDescription.setText("Click to add, edit, delete, search or view student");
                    break;
                case "imgprograme":
                    lblMenu.setText("Manage Programmes");
                    lblDescription.setText("Click to add, edit, delete, search or view programmes");
                    break;
                case "imgdetail":
                    lblMenu.setText("Place Student to Program");
                    lblDescription.setText("Click here if you want to place a new Program-student detail");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            URL root = null;

            switch (icon.getId()) {
                case "imgStudent":
                    root = this.getClass().getResource("/view/manageStudentForm.fxml");
                    break;
                case "imgprograme":
                    root = this.getClass().getResource("/view/manageTrainingProgramsForm.fxml");
                    break;
                case "imgdetail":
                    root = this.getClass().getResource("/view/manageDetailForm.fxml");
                    break;
            }

            if (root != null) {

                URL resource = root;
                Parent load = FXMLLoader.load(resource);
                root2.getChildren().clear();
                root2.getChildren().add(load);

                Scene subScene = new Scene(FXMLLoader.load(root));

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }

}
