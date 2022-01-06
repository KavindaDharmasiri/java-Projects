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

public class AddLabFormController {
    public AnchorPane addLabDashBoard;
    public TextField txtLabId;
    public TextField txtLabType;
    public JFXComboBox cmbAvailability;
    public JFXButton addBtn;
    int t = 0;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);

        txtLabId.setText(ManageLab.getLabId());

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            setbtn(false);
        });
    }

    public void addLab(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtLabType.getText() != null) {
                ManageLab.addLab(txtLabId.getText(), txtLabType.getText(), value);
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
        notifications.text("Lab Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addLabForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addLabDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToLabDet(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void labType(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtLabType.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            t = 1;
            txtLabType.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            t = 0;
            txtLabType.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                addBtn.setDisable(true);
                txtLabType.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(value == null || t != 1);
    }
}
