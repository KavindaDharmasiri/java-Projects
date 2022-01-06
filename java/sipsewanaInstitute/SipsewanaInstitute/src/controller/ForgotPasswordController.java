package controller;

import bo.BOFactory;
import bo.custom.CreateAccountBo;
import bo.custom.impl.CreateAccountBoImpl;
import entity.Login;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 2:03 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class ForgotPasswordController implements Initializable {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtName;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public Button add;
    public Separator sepPass;

    CreateAccountBo createAccountBo = (CreateAccountBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.CREATE_ACCOUNT);
    private int p;


    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Login log=new Login(txtName.getText(),encryptPassword(txtPassword.getText()));
        boolean update = createAccountBo.update(log);
        if(update){
            thisstage.close();
        }else {
            new Alert(Alert.AlertType.WARNING, "Sorry.").show();
        }
    }

    private String encryptPassword(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public void closeOnAction(ActionEvent actionEvent) {
        thisstage.close();
    }

    public void SearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        List<Login> search = createAccountBo.search(txtName.getText());
        if(search.size() != 0){
            txtPassword.setDisable(false);
            txtConfirmPassword.setDisable(false);
            add.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add.setDisable(true);
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

    private void setbtn(boolean b) {
        add.setDisable(p != 1);
    }
}
