package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateDocFormController {
    public AnchorPane updateDocDashBoard;
    public TextField txtDocId;
    public TextField txtDocName;
    public TextField txtDocAddress;
    public TextField txtDocNic;
    public TextField txtDocContact;
    public TextField txtDocSpecialist;
    public JFXButton updateBtn;

    public void updateDoc(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (txtDocName.getText() != null && txtDocAddress.getText() != null && txtDocNic.getText() != null && txtDocContact.getText() != null && txtDocSpecialist.getText() != null) {

            ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                    , "Success", yes, no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();


            ManageDoctor.updateDoctor(txtDocId.getText(), txtDocName.getText(), txtDocSpecialist.getText(), txtDocAddress.getText(), txtDocNic.getText(), txtDocContact.getText());

            back();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
        }
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateDocForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateDocDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToDocList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void name(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z_]{5,30}$";
        String typeText = txtDocName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDocName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDocName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtDocAddress.requestFocus();
            }
        }
    }

    public void address(KeyEvent keyEvent) {
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtDocAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDocAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDocAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtDocNic.requestFocus();
            }
        }
    }

    public void nic(KeyEvent keyEvent) {
        String regEx = "[0-9][0-9]{11}";
        String typeText = txtDocNic.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDocNic.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDocNic.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtDocContact.requestFocus();
            }
        }
    }

    public void contatc(KeyEvent keyEvent) {
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{7}$";
        String typeText = txtDocContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDocContact.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDocContact.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtDocSpecialist.requestFocus();
            }
        }
    }

    public void specialist(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z_]{5,30}$";
        String typeText = txtDocSpecialist.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDocSpecialist.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDocSpecialist.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }
}
