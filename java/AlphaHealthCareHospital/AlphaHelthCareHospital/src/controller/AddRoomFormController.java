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

public class AddRoomFormController {
    public AnchorPane addRoomDashBoard;
    public TextField txtRoomType;
    public TextField txtRoomId;
    public TextField txtRoomPrice;
    public JFXComboBox cmbAvailability;
    public JFXButton addBtn;
    int t = 0;
    int p = 0;
    private String value;

    public void initialize() throws SQLException, ClassNotFoundException {
        addBtn.setDisable(true);

        txtRoomId.setText(ManageRoom.getRoomId());

        cmbAvailability.getItems().addAll("Available", "NotAvailable");

        cmbAvailability.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
            setbtn(false);
        });
    }

    public void addRoom(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (value != null) {
            if (txtRoomType.getText() != null && txtRoomPrice.getText() != null) {
                ManageRoom.addNewRoom(txtRoomId.getText(), txtRoomType.getText(), value, txtRoomPrice.getText());
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
        notifications.text("Room Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    public void backToRoomList(ActionEvent actionEvent) throws IOException {
        back();
    }

    private void back() throws IOException {
        URL resource = getClass().getResource("../view/addRoomForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) addRoomDashBoard.getScene().getWindow();
        window.setScene(new Scene(load));
        window.close();
    }

    public void roomType(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{4,20}$";
        String typeText = txtRoomType.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            t = 1;
            txtRoomType.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            t = 0;
            txtRoomType.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                addBtn.setDisable(true);
                txtRoomPrice.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(t != 1 || p != 1 || value == null);
    }

    public void roomPrice(KeyEvent keyEvent) {
        addBtn.setDisable(true);
        String regEx = "^[1-9][0-9]{1,5}([.][0-9]{2})?$";
        String typeText = txtRoomPrice.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            txtRoomPrice.getParent().setStyle("-fx-border-color: green");
            setbtn(false);
        } else {
            p = 0;
            txtRoomPrice.getParent().setStyle("-fx-border-color: red");
            setbtn(true);
        }
    }
}
