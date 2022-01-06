package controller;

import com.jfoenix.controls.JFXButton;
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

public class AddNurseFormController {
    public AnchorPane addNurseDashBoard;
    public TextField txtNurseId;
    public TextField txtNurseName;
    public TextField txtNurseAddress;
    public TextField txtNurseNic;
    public TextField txtNurseContact;
    public JFXButton addBtn;
    int n = 0;
    int a = 0;
    int nic = 0;
    int c = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);

        txtNurseId.setText(ManageNurse.getNurseId());

    }

    public void addNurse(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (txtNurseAddress.getText() != null && txtNurseName.getText() != null && txtNurseNic.getText() != null && txtNurseContact.getText() != null) {
            ManageNurse.addNewNurse(txtNurseId.getText(), txtNurseName.getText(), txtNurseAddress.getText(), txtNurseNic.getText(), txtNurseContact.getText());
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
        notifications.text("Nurse Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void backToNurseList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void back() throws IOException {
        URL resource = getClass().getResource("../view/addNurseForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addNurseDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void nurseName(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtNurseName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            txtNurseName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            txtNurseName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtNurseAddress.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(n != 1 || a != 1 || c != 1 || nic != 1);
    }

    public void nurseAddress(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtNurseAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            txtNurseAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            txtNurseAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtNurseNic.requestFocus();
            }
        }
    }

    public void nurseNic(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "[0-9][0-9]{11}";
        String typeText = txtNurseNic.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            nic = 1;
            txtNurseNic.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            nic = 0;
            txtNurseNic.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtNurseContact.requestFocus();
            }
        }
    }

    public void nurseContact(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{7}$";
        String typeText = txtNurseContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            c = 1;
            txtNurseContact.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            c = 0;
            txtNurseContact.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
