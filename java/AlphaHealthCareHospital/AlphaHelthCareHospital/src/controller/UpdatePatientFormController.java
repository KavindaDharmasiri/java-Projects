package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class UpdatePatientFormController {
    public AnchorPane updatePatientDashBoard;
    public TextField txtPatientId;
    public TextField txtPatientName;
    public TextField txtPatientAddress;
    public TextField txtPatientNic;
    public TextField txtPatientContact;
    public TextField txtPatientAge;
    public JFXComboBox cmbRoomId;
    public JFXComboBox cmbParkingSlot;
    public JFXButton updateBtn;
    private String valueOne;
    private String valueTwo;

    public void initialize() throws SQLException, ClassNotFoundException {

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

    public void updatePatient(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (valueOne != null) {
            ManageRoom.addNewRoomDetail(txtPatientId, valueOne);
        }
        if (valueTwo != null) {
            hospitalDetailController.addparkingdetail(txtPatientId.getText(), valueTwo);
        }

        if (txtPatientAddress.getText() != null && txtPatientName.getText() != null && txtPatientNic.getText() != null && txtPatientContact.getText() != null && txtPatientAge.getText() != null) {
            new Alert(Alert.AlertType.INFORMATION, "Success").show();
            ManagePatient.updatePatient(txtPatientId.getText(), txtPatientName.getText(), txtPatientAddress.getText(), txtPatientAge.getText(), txtPatientNic.getText(), txtPatientContact.getText());

            back();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
        }
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updatePatientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updatePatientDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToPatientList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void name(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z_]{5,30}$";
        String typeText = txtPatientName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtPatientName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtPatientName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientAddress.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }

    public void address(KeyEvent keyEvent) {
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtPatientAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtPatientAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtPatientAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientNic.requestFocus();
            }
        }
    }

    public void nic(KeyEvent keyEvent) {
        String regEx = "[0-9][0-9]{11}";
        String typeText = txtPatientNic.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtPatientNic.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtPatientNic.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientContact.requestFocus();
            }
        }
    }

    public void contact(KeyEvent keyEvent) {
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{7}$";
        String typeText = txtPatientContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtPatientContact.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtPatientContact.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtPatientAge.requestFocus();
            }
        }
    }

    public void age(KeyEvent keyEvent) {
        String regEx = "^[0-9][0-9]{1}$";
        String typeText = txtPatientAge.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtPatientAge.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtPatientAge.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
