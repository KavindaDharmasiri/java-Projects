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

public class UpdateClothFormController {
    public AnchorPane updateClothDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtClothId;
    public TextField txtClothName;
    public TextField txtClothPrice;
    public JFXButton updateBtn;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {

        cmbAvailability.getItems().addAll("Available","NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable ,oldValue,newValue)->{
            value = (String) newValue;
        } );
    }

    public void updateCloth(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if(value!=null){
            if(txtClothName.getText()!=null && txtClothPrice.getText()!=null) {

                ButtonType yes = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Success", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp=result.toString();


                ManageCloth.updateCloth(txtClothId.getText(), txtClothName.getText(),value, txtClothPrice.getText());

                back();
            }else{
                new Alert(Alert.AlertType.WARNING,"Please Fill All." ).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please Select Available Type." ).show();
        }
    }

    public void backToClothList(ActionEvent actionEvent) throws IOException {
    back();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/updateClothForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) updateClothDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }


    public void name(KeyEvent keyEvent) {
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtClothName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtClothName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtClothName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                updateBtn.setDisable(true);
                txtClothPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(b);
    }

    public void price(KeyEvent keyEvent) {
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = txtClothPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            txtClothPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            txtClothPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
