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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateLabFormController {
    public AnchorPane updateLabDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtLabId;
    public TextField txtLabType;
    public JFXButton updateBtn;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
        });
    }


    public void updateLab(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtLabType.getText() != null) {

                ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Success", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();


                ManageLab.updateLab(txtLabId.getText(), txtLabType.getText(), value);

                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Available Type.").show();
        }

    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateLabForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateLabDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToLabDet(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void type(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtLabType.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtLabType.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtLabType.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }
}
