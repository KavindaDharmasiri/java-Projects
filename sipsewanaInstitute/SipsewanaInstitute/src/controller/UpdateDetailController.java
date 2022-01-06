package controller;

import bo.BOFactory;
import bo.custom.DetailBo;
import bo.custom.ProgramBo;
import bo.custom.StudentBo;
import entity.StudentProgramDetail;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 1:45 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class UpdateDetailController {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtStudentId;
    public TextField txtProgramId;
    public TextField txtMoney;
    public StudentProgramDetail tem;
    public Separator sepMoney;
    public Button updateBtn;

    DetailBo detailBo = (DetailBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.DETAIL);
    StudentBo studentBo = (StudentBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.STUDENT);
    ProgramBo programBo = (ProgramBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PROGRAM);
    private int a;

    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentProgramDetail studentProgramDetail = new StudentProgramDetail();
        studentProgramDetail.setStudent(studentBo.getStudentOriginal(txtStudentId.getText()));
        studentProgramDetail.setTrainingProgram(programBo.getProgram(txtProgramId.getText()));
        studentProgramDetail.setGivenMoney(tem.getGivenMoney()+Double.parseDouble(txtMoney.getText()));
        studentProgramDetail.setOngoingPayment(tem.getOngoingPayment()-Double.parseDouble(txtMoney.getText()));
        studentProgramDetail.setTimeleft(tem.getTimeleft());
        studentProgramDetail.setDate(tem.getDate());

        detailBo.update(studentProgramDetail);
        thisstage.close();

    }


    private void setbtn(boolean b) {
        updateBtn.setDisable(a != 1);
    }

    public void givenMoney(KeyEvent keyEvent) {
        updateBtn.setDisable(true);
        String regEx = "[0-9]{4,}";
        String typeText = txtMoney.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            sepMoney.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            a = 0;
            sepMoney.setStyle("-fx-background-color: red");
            setbtn(true);
        }
    }

    public void closeOnAction(ActionEvent actionEvent) {
        thisstage.close();
    }

}
