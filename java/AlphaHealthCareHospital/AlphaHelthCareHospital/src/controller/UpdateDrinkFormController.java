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

public class UpdateDrinkFormController {
    public AnchorPane updateDrinkDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtDrinkId;
    public TextField txtDrinkName;
    public TextField txtDrinkPrice;
    public JFXButton updateBtn;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbAvailability.getItems().addAll("Available","NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable ,oldValue,newValue)->{
            value = (String) newValue;
        } );
    }

    public void updateDrink(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if(value!=null){
            if(txtDrinkName.getText()!=null && txtDrinkPrice.getText()!=null) {

                ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Success", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp=result.toString();


                ManageDrink.updateDrink(txtDrinkId.getText(), txtDrinkName.getText(),value, txtDrinkPrice.getText());

                back();
            }else{
                new Alert(Alert.AlertType.WARNING,"Please Fill All." ).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please Select Available Type." ).show();
        }
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateDrinkForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateDrinkDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToDrinkList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void name(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtDrinkName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDrinkName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDrinkName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                updateBtn.setDisable(true);
                txtDrinkPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }

    public void price(KeyEvent keyEvent) {
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = txtDrinkPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtDrinkPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtDrinkPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
