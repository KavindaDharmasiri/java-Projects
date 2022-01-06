package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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

public class UpdateMedicineFormController {
    public AnchorPane updateMedicineDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtMedicineId;
    public TextField txtMedicineName;
    public TextField txtMedicinePrice;
    public JFXButton updateBtn;
    public TextField txtQty;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
        });
    }

    public void updateMedicine(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtMedicineName.getText() != null && txtMedicinePrice.getText() != null && txtQty.getText() != null) {

                ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Success", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();


                ManageMedicine.updateMedicine(txtMedicineId.getText(), txtMedicineName.getText(), value, txtMedicinePrice.getText(), txtQty.getText());

                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Available Type.").show();
        }
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateMedicineForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateMedicineDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToMedicalDet(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void name(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtMedicineName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtMedicineName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtMedicineName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                updateBtn.setDisable(true);
                txtMedicinePrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }

    public void price(KeyEvent keyEvent) {
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = txtMedicinePrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtMedicinePrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtMedicinePrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                updateBtn.setDisable(true);
                txtQty.requestFocus();
            }
        }
    }

    public void Qty(KeyEvent keyEvent) {
        updateBtn.setDisable(true);
        String regEx = "^[1-9][0-9]{1,2}$";
        String typeText = txtQty.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtQty.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtQty.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
