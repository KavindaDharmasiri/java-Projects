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

public class AddDocFormController {
    public AnchorPane addDocDashBoard;
    public TextField txtDocId;
    public TextField txtDocName;
    public TextField txtDocAddress;
    public TextField txtDocNic;
    public TextField txtDocContact;
    public TextField txtDocSpecialist;
    public JFXButton addBtn;
    int n = 0;
    int a = 0;
    int i = 0;
    int con = 0;
    int spe = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);
        txtDocId.setText(ManageDoctor.getDocId());

    }

    public void addDoc(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        if (txtDocAddress.getText() != null && txtDocName.getText() != null && txtDocNic.getText() != null && txtDocContact.getText() != null && txtDocSpecialist.getText() != null) {
            ManageDoctor.addNewDoc(txtDocId.getText(), txtDocName.getText(), txtDocSpecialist.getText(), txtDocAddress.getText(), txtDocNic.getText(), txtDocContact.getText());
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
        notifications.text("Doctor Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void backToDocList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void back() throws IOException {
        URL resource = getClass().getResource("../view/addDocForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addDocDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void docName(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtDocName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            n = 1;
            txtDocName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            txtDocName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtDocAddress.requestFocus();
            }
        }
    }

    public void docAddress(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtDocAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            a = 1;
            txtDocAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            txtDocAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtDocNic.requestFocus();
            }
        }
    }

    public void docNic(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "[0-9][0-9]{11}";
        String typeText = txtDocNic.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            i = 1;
            txtDocNic.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            i = 0;
            txtDocNic.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtDocContact.requestFocus();
            }
        }
    }

    public void docContact(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{7}$";
        String typeText = txtDocContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            con = 1;
            txtDocContact.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            con = 0;
            txtDocContact.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtDocSpecialist.requestFocus();
            }
        }
    }

    public void docSpecialist(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{5,30}$";
        String typeText = txtDocSpecialist.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            spe = 1;
            txtDocSpecialist.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            spe = 0;
            txtDocSpecialist.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(spe != 1 || con != 1 || i != 1 || a != 1 || n != 1);
    }
}
