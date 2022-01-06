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
import java.util.List;
import java.util.regex.Pattern;

public class AddPatientFormController {
    public AnchorPane addPatientDashBoard;
    public TextField txtPatientId;
    public TextField txtPatientName;
    public TextField txtPatientAddress;
    public TextField txtPatientNic;
    public TextField txtPatientContact;
    public TextField txtPatientAge;
    public JFXComboBox cmbRoomId;
    public JFXComboBox cmbParkingSlot;
    public JFXButton addBtn;
    int n = 0;
    int a = 0;
    int nic = 0;
    int c = 0;
    int age = 0;
    private String valueOne;
    private String valueTwo;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);

        txtPatientId.setText(ManagePatient.getPatId());

        loadRoomIds();
        loadParkingSlots();

        cmbRoomId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueOne = (String) newValue;
        });

        cmbParkingSlot.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valueTwo = (String) newValue;
        });

    }

    private void loadParkingSlots() throws SQLException, ClassNotFoundException {
        List<String> parkSlot = new hospitalDetailController().getAllParkSlots();
        cmbParkingSlot.getItems().addAll(parkSlot);
    }

    private void loadRoomIds() throws SQLException, ClassNotFoundException {
        List<String> roomIds = new ManageRoom().getAllRoomIds();
        cmbRoomId.getItems().addAll(roomIds);
    }

    public void addPatient(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if (valueOne != null) {
            ManageRoom.addNewRoomDetail(txtPatientId, valueOne);
        }
        if (valueTwo != null) {
            hospitalDetailController.addparkingdetail(txtPatientId.getText(), valueTwo);
        }

        if (txtPatientAddress.getText() != null && txtPatientName.getText() != null && txtPatientNic.getText() != null && txtPatientContact.getText() != null && txtPatientAge.getText() != null) {
            ManagePatient.addNewPat(txtPatientId.getText(), txtPatientName.getText(), txtPatientAddress.getText(), txtPatientAge.getText(), txtPatientNic.getText(), txtPatientContact.getText());
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
        notifications.text("Patient Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void backToPatientList(ActionEvent actionEvent) throws IOException {
        back();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addPatientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addPatientDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void patName(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtPatientName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            txtPatientName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            txtPatientName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientAddress.requestFocus();
            }
        }
    }

    public void patAddress(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtPatientAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            txtPatientAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            a = 0;
            txtPatientAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientNic.requestFocus();
            }
        }
    }

    public void patNic(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "[0-9][0-9]{11}";
        String typeText = txtPatientNic.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            nic = 1;
            txtPatientNic.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            nic = 0;
            txtPatientNic.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientContact.requestFocus();
            }
        }
    }

    public void patContact(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{7}$";
        String typeText = txtPatientContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            c = 1;
            txtPatientContact.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            c = 0;
            txtPatientContact.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientAge.requestFocus();
            }
        }
    }

    public void patAge(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[0-9][0-9]{1}$";
        String typeText = txtPatientAge.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            age = 1;
            txtPatientAge.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            age = 0;
            txtPatientAge.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(n != 1 || a != 1 || nic != 1 || age != 1);
    }
}
