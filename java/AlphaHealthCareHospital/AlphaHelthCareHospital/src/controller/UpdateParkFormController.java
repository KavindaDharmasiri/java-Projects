package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

public class UpdateParkFormController {
    public AnchorPane updateParkDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtparkingSlot;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
        });
    }

    public void updateParkingSlot(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {

            ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                    , "Success", yes, no);
            alert.setTitle("Confirmation Alert");
            Optional<ButtonType> result = alert.showAndWait();
            String temp = result.toString();


            hospitalDetailController.updatePark(txtparkingSlot.getText(), value);

            back();
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Available Type.").show();
        }
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateParkForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateParkDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToparkList(ActionEvent actionEvent) throws IOException {
        back();
    }
}
