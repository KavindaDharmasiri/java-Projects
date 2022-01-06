package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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

public class UpdateApoinmentFormController {
    public AnchorPane updateApoinmentDashBoard;
    public TextField txtApoinmentDate;
    public TextField txtApoinmentTime;
    public Label lblPatientId;
    public Label lblDoctorId;
    public Label lblNurseId;
    public Label lblApoinmentId;
    public JFXButton updateBtn;

    public void addApoinment(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (txtApoinmentTime.getText() != null && txtApoinmentDate.getText() != null) {
            ManageApoinment.updateApoinment(lblApoinmentId.getText(), txtApoinmentTime, txtApoinmentDate);
            notification();
            back();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
        }

    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Update Appointment Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void backToApoinmentList(ActionEvent actionEvent) throws IOException {
        back();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateApoinmentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateApoinmentDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void date(KeyEvent keyEvent) {
        String regEx = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        String typeText = txtApoinmentDate.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtApoinmentDate.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtApoinmentDate.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtApoinmentTime.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }

    public void time(KeyEvent keyEvent) {
        String regEx = "^(0?[1-9]|1[0-2]):[0-5][0-9]$";
        String typeText = txtApoinmentTime.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtApoinmentTime.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtApoinmentTime.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
