package controller;

import bo.BOFactory;
import bo.custom.CreateAccountBo;
import bo.custom.impl.CreateAccountBoImpl;
import entity.Login;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 1:56 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class CreateAccountController {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public TextField txtUserName;
    public Button add;
    public Separator sepName;
    public Separator sepPass;
    private int n;
    private int p;
    private int q;

    CreateAccountBo createAccountBo = (CreateAccountBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CREATE_ACCOUNT);

    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String pass=encryptPassword(txtPassword.getText());
        Login log=new Login();
        log.setName(txtUserName.getText());
        log.setPassword(pass);
        boolean save = createAccountBo.save(log);

        if(save){
            thisstage.close();
        }else{
            new Alert(Alert.AlertType.WARNING, "Sorry.").show();
        }
    }

    private String encryptPassword(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public void txtUserName(KeyEvent keyEvent) {
        add.setDisable(true);
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
                add.setDisable(true);
                txtPassword.requestFocus();
            }
        }

    }

    private void setbtn(boolean b) {
        add.setDisable(p != 1 || n != 1 || q != 1);
    }

    public void closeOnAction(ActionEvent actionEvent) {
        thisstage.close();
    }

    public void txtPassword(KeyEvent keyEvent) {
        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtPassword.getText();

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

    public void txtConfirm(KeyEvent keyEvent) {
        String regEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.[a-zA-Z]).{8,}$";
        String typeText = txtConfirmPassword.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            q = 1;
            setbtn(false);
        } else {
            if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
                q = 0;
                setbtn(true);
            }
        }
    }
}
