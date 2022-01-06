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

public class UpdateNurseFormController {
    public AnchorPane updateNurseDashBoard;
    public TextField txtNurseId;
    public TextField txtNurseName;
    public TextField txtNurseAddress;
    public TextField txtNurseNic;
    public TextField txtNurseContact;
    public JFXButton updateBtn;

    public void updateNurse(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (txtNurseName.getText() != null && txtNurseAddress.getText() != null && txtNurseNic.getText() != null && txtNurseContact.getText() != null) {

            ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                    , "Success", yes, no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            String temp = result.toString();


            ManageNurse.updateNurse(txtNurseId.getText(), txtNurseName.getText(), txtNurseAddress.getText(), txtNurseNic.getText(), txtNurseContact.getText());

            back();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
        }
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateNurseForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateNurseDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToNurseList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void name(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z_]{5,30}$";
        String typeText = txtNurseName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtNurseName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
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
        updateBtn.setDisable(b);
    }

    public void address(KeyEvent keyEvent) {
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtNurseAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtNurseAddress.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtNurseAddress.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtNurseNic.requestFocus();
            }
        }
    }

    public void nic(KeyEvent keyEvent) {
        String regEx = "[0-9][0-9]{11}";
        String typeText = txtNurseNic.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtNurseNic.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtNurseNic.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtNurseContact.requestFocus();
            }
        }
    }

    public void contact(KeyEvent keyEvent) {
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{7}$";
        String typeText = txtNurseContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtNurseContact.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtNurseContact.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
