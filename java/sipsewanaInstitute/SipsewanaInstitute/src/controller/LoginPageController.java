package controller;

import bo.BOFactory;
import bo.custom.CreateAccountBo;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/15/2021
 * @Time_: 1:04 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class LoginPageController {

    public AnchorPane root;
    public TextField txtShowPassword;
    public FontAwesomeIconView openeye;
    public ImageView closeeye;
    public PasswordField txtHidePaswword;
    public TextField txtUserName;
    public Button log;
    public Text highlite;
    public Separator sepPass;
    public Separator sepName;
    private int n;
    private int p;
    private String password;
   CreateAccountBo createAccountBo = (CreateAccountBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CREATE_ACCOUNT);


    public void initialize() {
        txtShowPassword.setVisible(false);
        openeye.setVisible(false);
        log.setDisable(true);
    }

    public void exit(MouseEvent event) {
        System.exit(0);
    }

    public void createAccount(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CreateAccount.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        CreateAccountController.setStage(stage);
        stage.showAndWait();
    }

    public void ForgotPassword(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ForgotPassword.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        ForgotPasswordController.setStage(stage);
        stage.showAndWait();
    }

    private String DecryptPasswrd(String password) {
        return new String(Base64.getMimeDecoder().decode(password));
    }


    public void txtUserName(KeyEvent keyEvent) {
        log.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z0-9_]{5,30}$";
        String typeText = txtUserName.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            n = 1;
            sepName.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            n = 0;
            sepName.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                log.setDisable(true);
                txtShowPassword.requestFocus();
            }
        }

    }

    private void setbtn(boolean b) {
        log.setDisable(p != 1 || n != 1);
    }

    public void txtShowPassword(KeyEvent keyEvent) {
        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtShowPassword.getText();

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

        password = txtShowPassword.getText();
        txtHidePaswword.setText(password);
    }

    public void txtHidePassword(KeyEvent keyEvent) {
        password = txtHidePaswword.getText();
        txtShowPassword.setText(password);

        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtHidePaswword.getText();

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

    public void openEye(MouseEvent event) {
        password = txtShowPassword.getText();
        txtHidePaswword.setText(password);

        txtShowPassword.setVisible(false);
        openeye.setVisible(false);
        closeeye.setVisible(true);
        txtHidePaswword.setVisible(true);
    }

    public void closeEye(MouseEvent event) {
        password = txtHidePaswword.getText();
        txtShowPassword.setText(password);

        txtShowPassword.setVisible(true);
        openeye.setVisible(true);
        closeeye.setVisible(false);
        txtHidePaswword.setVisible(false);
    }

    public void Login(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        List<Login> temp = createAccountBo.search(txtUserName.getText());

        for (int i = 0; i < temp.size(); i++) {
            String pWod = DecryptPasswrd(temp.get(i).getPassword());
            if (txtUserName.getText().equals(temp.get(i).getName())) {
                if (txtShowPassword.getText().equals(pWod)) {

                    URL resource = getClass().getResource("../view/main-form.fxml");
                    Parent load = FXMLLoader.load(resource);
                    root.getChildren().clear();
                    root.getChildren().add(load);

                    return;

                } else {
                    new Alert(Alert.AlertType.WARNING, "Password Incorrect.").show();
                    return;
                }
            }
        }
        new Alert(Alert.AlertType.WARNING, "No User Found.").show();
    }
}
