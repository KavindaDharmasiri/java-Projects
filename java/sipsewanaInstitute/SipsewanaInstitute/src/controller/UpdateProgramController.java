package controller;

import bo.BOFactory;
import bo.custom.ProgramBo;
import entity.TrainingProgram;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 1:42 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class UpdateProgramController {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtName;
    public TextField txtDuration;
    public TextField txtFee;
    public Label txtProgramId;
    public Button updateBtn;
    public Separator sepName;
    public Separator sepFee;
    public Separator sepDuration;

    ProgramBo programBo = (ProgramBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PROGRAM);
    private int n;
    private int a;
    private int c;

    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TrainingProgram trainingProgram = new TrainingProgram();
        trainingProgram.setProgramId(txtProgramId.getText());
        trainingProgram.setPrograme(txtName.getText());
        trainingProgram.setDuration(txtDuration.getText());
        trainingProgram.setFee(Double.parseDouble(txtFee.getText()));

        boolean update = programBo.updateProgram(trainingProgram);

        if(update){
            new Alert(Alert.AlertType.INFORMATION, "Done..").showAndWait();
            thisstage.close();
        }else{
            new Alert(Alert.AlertType.WARNING, "Sorry.").show();
        }
    }

    public void closeOnAction(ActionEvent actionEvent) {
        thisstage.close();
    }

    public void txtName(KeyEvent keyEvent) {
        updateBtn.setDisable(true);
        String regEx = "^[A-Za-z][A-Za-z_]{3,30}$";
        String typeText = txtName.getText();

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
                txtDuration.requestFocus();
            }
        }
    }

    private void setbtn(boolean b) {
        updateBtn.setDisable(a != 1 || n != 1 || c != 1);
    }

    public void txtDuration(KeyEvent keyEvent) {
        updateBtn.setDisable(true);
        String regEx = "[0-9]{0,}";
        String typeText = txtDuration.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            c = 1;
            sepDuration.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            c = 0;
            sepDuration.setStyle("-fx-background-color: red");
            setbtn(true);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (matches) {
                txtFee.requestFocus();
            }
        }
    }

    public void txtFee(KeyEvent keyEvent) {
        updateBtn.setDisable(true);
        String regEx = "[0-9]{4,}";
        String typeText = txtFee.getText();

        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(typeText).matches();

        if (matches) {
            a = 1;
            sepFee.setStyle("-fx-background-color: green");
            setbtn(false);
        } else {
            a = 0;
            sepFee.setStyle("-fx-background-color: red");
            setbtn(true);
        }
    }
}
