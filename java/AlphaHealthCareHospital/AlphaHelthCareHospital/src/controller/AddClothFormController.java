package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddClothFormController {
    public AnchorPane addClothDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtClothId;
    public TextField txtClothName;
    public TextField txtClothPrice;
    public JFXButton btnAdd;
    String value;
    int c = 0;
    int n = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        btnAdd.setDisable(true);
        txtClothId.setText(ManageCloth.getClothId());

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            setbtn(false);
        });
    }

    public void addCloth(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (value != null) {
            if (txtClothName.getText() != null && txtClothPrice.getText() != null) {
                ManageCloth.addNewCloth(txtClothId.getText(), value, txtClothName.getText(), txtClothPrice.getText());
                notification();
                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Available Type.").show();
        }
    }

    public void backToClothList(ActionEvent actionEvent) throws IOException {
        back();
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Cloth Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void back() throws IOException {
        URL resource = getClass().getResource("../view/addClothForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addClothDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void clothPrice(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        String regEx = "^[1-9][0-9]{1,4}([.][0-9]{2})?$";
        String typeText = txtClothPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            c = 1;
            txtClothPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            c = 0;
            txtClothPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    public void clothName(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtClothName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            n = 1;
            txtClothName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            txtClothName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                btnAdd.setDisable(true);
                txtClothPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        btnAdd.setDisable(c != 1 || n != 1 || value == null);
    }
}
