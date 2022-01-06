package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import module.loginDetail;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class CreateAccountFormController {
    public TextField txtUserName;
    public TextField txtPassword;
    public JFXComboBox cmbUserType;
    public HBox userNam;
    public HBox passwo;
    public Button btnSave;
    public PasswordField txtHidePassword;
    public ImageView eyeOpen;
    public ImageView eyeClose;
    public Separator nameSep;
    public Separator passSep;
    String value;
    String password;
    int n = 0;
    int p = 0;

    public void initialize() {
        txtPassword.setVisible(false);
        eyeOpen.setVisible(false);
        btnSave.setDisable(true);

        cmbUserType.getItems().addAll("Admin", "Doctor", "Reception");

        cmbUserType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            value = (String) newValue;
        });
    }

    public void userNameOnAction(KeyEvent keyEvent) {
        btnSave.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        String typeText = txtUserName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            nameSep.setStyle("-fx-background-color: green");
            setBtn(false);
        } else {
            n = 0;
            nameSep.setStyle("-fx-background-color: red");
            setBtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                setBtn(true);
                txtHidePassword.requestFocus();
            }
        }
    }

    private void setBtn(boolean b) {
        btnSave.setDisable(p != 1 || n != 1 && value!=null);
    }

    public void psswordOnAction(KeyEvent keyEvent) {

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtPassword.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            passSep.setStyle("-fx-background-color: green");
            setBtn(false);
        } else {
            p = 0;
            passSep.setStyle("-fx-background-color: red");
            setBtn(true);
        }

        password = txtPassword.getText();
        txtHidePassword.setText(password);
    }

    public void saveDetail(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (value != null) {

            String psswd = encryptPassword(txtPassword.getText());

            List<loginDetail> temp = ManageLogin.searchuserName(txtUserName.getText());
            if (temp.size() != 0) {
                new Alert(Alert.AlertType.INFORMATION, "This User Also In Our System. Please Use Another Name.").show();
            } else {
                notification();
                ManageLogin.addNewUser(value, txtUserName, psswd);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select Login Type.").show();
        }
    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("New Member Adding Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    private String encryptPassword(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }


    public void HidePasswordOnAction(KeyEvent keyEvent) {
        password = txtHidePassword.getText();
        txtPassword.setText(password);

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtHidePassword.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            passSep.setStyle("-fx-background-color: green");
            setBtn(false);
        } else {
            p = 0;
            passSep.setStyle("-fx-background-color: red");
            setBtn(true);
        }
    }

    public void closeEye(MouseEvent mouseEvent) {
        txtPassword.setVisible(true);
        eyeOpen.setVisible(true);
        eyeClose.setVisible(false);
        txtHidePassword.setVisible(false);
    }

    public void openEye(MouseEvent mouseEvent) {
        txtPassword.setVisible(false);
        eyeOpen.setVisible(false);
        eyeClose.setVisible(true);
        txtHidePassword.setVisible(true);
    }
}
