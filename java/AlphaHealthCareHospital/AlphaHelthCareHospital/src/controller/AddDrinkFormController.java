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
import java.util.regex.Pattern;

public class AddDrinkFormController {
    public AnchorPane addDrinkDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtDrinkId;
    public TextField txtDrinkName;
    public TextField txtDrinkPrice;
    public JFXButton addBtn;
    public TextField txtQtyOnHand;
    String value;
    int d = 0;
    int p = 0;
    int q = 0;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);
        txtDrinkId.setText(ManageDrink.getDrinkId());

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            setbtn(false);
        });
    }

    public void addDrink(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtDrinkName.getText() != null && txtDrinkPrice.getText() != null) {
                ManageDrink.addNewDrink(txtDrinkId.getText(), txtDrinkName.getText(), value, txtQtyOnHand.getText(), txtDrinkPrice.getText());
                notification();
                back();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Fill All.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Available Type.").show();
        }
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Drink Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addDrinkForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addDrinkDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToDrinkList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void drinkName(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtDrinkName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            d = 1;
            txtDrinkName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            d = 0;
            txtDrinkName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                addBtn.setDisable(true);
                txtDrinkPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(q != 1 || p != 1 || d != 1 || value == null);
    }

    public void drinkPrice(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = txtDrinkPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            p = 1;
            txtDrinkPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            txtDrinkPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                addBtn.setDisable(true);
                txtQtyOnHand.requestFocus();
            }
        }
    }

    public void Qty(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = txtQtyOnHand.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();
        if (matches) {
            q = 1;
            txtQtyOnHand.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            q = 0;
            txtQtyOnHand.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
