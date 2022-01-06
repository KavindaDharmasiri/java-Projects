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

public class AddEquipmentFormController {
    public AnchorPane addEquipmentDashBoard;
    public JFXComboBox cmbAvailability;
    public TextField txtEqipmentId;
    public TextField txtEquipmentName;
    public TextField txtEquipmentPrice;
    public JFXButton addBtn;
    public TextField txtQty;
    int n = 0;
    int p = 0;
    int q = 0;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);

        txtEqipmentId.setText(ManageEq.getEquipmentId());

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            setbtn(false);
        });
    }

    public void addEquipment(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtEquipmentName.getText() != null && txtEquipmentPrice.getText() != null && txtQty.getText() != null) {
                ManageEq.addNewEquipment(txtEqipmentId.getText(), txtEquipmentName.getText(), value, txtQty.getText(), txtEquipmentPrice.getText());
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
        notifications.text("Equipment Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addEquipmentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addEquipmentDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void backToEquipmentList(ActionEvent actionEvent) throws IOException {
        back();
    }

    public void eqName(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtEquipmentName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            txtEquipmentName.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            n = 0;
            txtEquipmentName.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                addBtn.setDisable(true);
                txtEquipmentPrice.requestFocus();
            }
        }
    }

    public void eqPrice(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[1-9][0-9]{1,3}([.][0-9]{2})?$";
        String typeText = txtEquipmentPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            txtEquipmentPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            txtEquipmentPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                addBtn.setDisable(true);
                txtQty.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(n != 1 || p != 1 || q != 1 || value == null);
    }

    public void qty(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[1-9][0-9]{0,2}$";
        String typeText = txtQty.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            q = 1;
            txtQty.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            q = 0;
            txtQty.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
