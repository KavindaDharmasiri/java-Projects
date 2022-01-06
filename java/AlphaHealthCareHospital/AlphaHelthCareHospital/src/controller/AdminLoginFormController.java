package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import module.loginDetail;
import org.controlsfx.control.Notifications;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class AdminLoginFormController {
    public AnchorPane admin;
    public TextField userName;
    public TextField password;
    public Button btnLog;
    public ImageView pic;
    public VBox vBox;
    public Separator sepUName;
    public Separator sepPass;
    public PasswordField txtHidePassword;
    public ImageView eyeClose;
    public ImageView eyeOpen;
    int n = 0;
    int p = 0;
    private String password1;

    public void initialize() {
        password.setVisible(false);
        eyeOpen.setVisible(false);

        btnLog.setDisable(true);
    }

    public void adminDashBoard(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        logOnAction();
    }

    public void name(KeyEvent keyEvent) {
        btnLog.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        String typeText = userName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            sepUName.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            n = 0;
            sepUName.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                btnLog.setDisable(true);
                txtHidePassword.requestFocus();
            }
        }

    }

    private void setbtn(boolean b) {

        btnLog.setDisable(p != 1 || n != 1);
    }

    public void password(KeyEvent keyEvent) {

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = password.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            sepPass.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            p = 0;
            sepPass.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        password1 = password.getText();
        txtHidePassword.setText(password1);
    }

    private void logOnAction() throws IOException, SQLException, ClassNotFoundException {
        List<loginDetail> temp = ManageLogin.getDetail(userName, "Admin");

        for (int i = 0; i < temp.size(); i++) {
            String pWod = DecryptPasswrd(temp.get(i).getPassword());
            if (userName.getText().equals(temp.get(i).getUserName())) {


                if (password.getText().equals(pWod)) {
                    loginFormController.prime.hide();

                    URL resource = getClass().getResource("../view/animate.fxml");
                    if (resource == null) {
                        resource = new URL("file:/F:/AlphaHelthCareHospital/out/production/AlphaHelthCareHospital/view/animate.fxml");
                    }
                    AnimateController.setValue("../view/adminDashBoardForm.fxml", userName.getText());
                    Parent root1 = FXMLLoader.load(resource);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.show();
                    notification();
                    return;

                } else {
                    new Alert(Alert.AlertType.WARNING, "Password Incorrect.").show();
                    return;
                }
            }
        }
        new Alert(Alert.AlertType.WARNING, "Can't find your Name.").show();

    }

    private void notification() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/sample/audio/success-notification-alert_A_major.wav"));

        Image image = new Image("/sample/Images/icons8-ok-30.png");
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text("Admin Login Success..");
        notifications.title("Success Massage");
        notifications.hideAfter(Duration.seconds(20));
        notifications.darkStyle();
        notifications.position(Pos.BASELINE_RIGHT);
        notifications.show();

        clip.play();
    }

    private String DecryptPasswrd(String password) {
        return new String(Base64.getMimeDecoder().decode(password));
    }

    public void HidePasswordOnAction(KeyEvent keyEvent) {
        password1 = txtHidePassword.getText();
        password.setText(password1);

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtHidePassword.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            p = 1;
            sepPass.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            p = 0;
            sepPass.setStyle("-fx-background-color: red");
            setbtn(true);
        }
    }

    public void closeEye(MouseEvent mouseEvent) {
        password.setVisible(true);
        eyeOpen.setVisible(true);
        eyeClose.setVisible(false);
        txtHidePassword.setVisible(false);
    }

    public void openEye(MouseEvent mouseEvent) {
        password.setVisible(false);
        eyeOpen.setVisible(false);
        eyeClose.setVisible(true);
        txtHidePassword.setVisible(true);
    }
}
