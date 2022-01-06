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

public class AddParkFormController {
    public AnchorPane addParkDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtparkingSlot;
    public JFXButton addBtn;
    int s = 0;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            setbtn(false);
        });
    }

    public void addParkingSlot(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtparkingSlot.getText() != null) {
                hospitalDetailController.addNewPark(txtparkingSlot.getText(), value);
                notification();
                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Available Type.").show();
        }
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Park Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void backToparkList(ActionEvent actionEvent) throws IOException {
        back();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addParkForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addParkDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void parkingSlot(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[0-9][0-9]{1}$";
        String typeText = txtparkingSlot.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            s = 1;
            txtparkingSlot.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            s = 0;
            txtparkingSlot.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                addBtn.setDisable(true);
                txtparkingSlot.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(s != 1 || value == null);
    }
}
