package controller;

import bo.BOFactory;
import bo.custom.StudentBo;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 12:29 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class AddNewStudentController implements Initializable {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtFirstName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtMarks;
    public Label txtStudentId;
    public Button registerBtn;
    public Separator sepName;
    public Separator sepAddress;
    public Separator sepContact;
    public Separator sepMark;

    StudentBo studentBo = (StudentBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.STUDENT);
    private int a;
    private int n;
    private int c;
    private int m;

    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void RegisterOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Student student=new Student();
        student.setsId(txtStudentId.getText());
        student.setName(txtFirstName.getText());
        student.setAddress(txtAddress.getText());
        student.setContact(txtContact.getText());
        student.setInterviewMark(Integer.parseInt(txtMarks.getText()));

        boolean save = studentBo.save(student);
        if(save){
            new Alert(Alert.AlertType.INFORMATION, "Done..").showAndWait();
            thisstage.close();
        }else{
            new Alert(Alert.AlertType.WARNING, "Sorry.").show();
        }
    }

    public void closeOnAction(ActionEvent actionEvent) {
        thisstage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txtStudentId.setText(studentBo.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void stAddress(KeyEvent keyEvent) {
        registerBtn.setDisable(true);
        String regEx = "[a-zA-Z0-9]{4,}[ ][a-zA-Z]{4,}";
        String typeText = txtAddress.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            sepAddress.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            a = 0;
            sepAddress.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (matches) {
                txtContact.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        registerBtn.setDisable(a != 1 || n != 1 || c != 1 || m != 1);
    }

    public void stName(KeyEvent keyEvent) {
        registerBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtFirstName.getText();

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
                txtAddress.requestFocus();
            }
        }
    }

    public void stContact(KeyEvent keyEvent) {
        registerBtn.setDisable(true);
        String regEx = "^(077|071|078|075|076|072|074)[-]?[0-9]{6}$";
        String typeText = txtContact.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            c = 1;
            sepContact.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            c = 0;
            sepContact.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtMarks.requestFocus();
            }
        }
    }

    public void stMark(KeyEvent keyEvent) {
        registerBtn.setDisable(true);
        String regEx = "^[0-9][0-9]{0}$";
        String typeText = txtMarks.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            m = 1;
            sepMark.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            m = 0;
            sepMark.setStyle("-fx-background-color: red");
            setbtn(true);
        }
    }
}
