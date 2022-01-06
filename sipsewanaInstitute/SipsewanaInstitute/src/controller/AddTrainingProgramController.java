package controller;

import bo.BOFactory;
import bo.custom.ProgramBo;
import entity.TrainingProgram;
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
 * @Time_: 1:18 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class AddTrainingProgramController implements Initializable {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtName;
    public TextField txtDuration;
    public TextField txtFee;
    public Label txtProgramId;
    public Button addBtn;
    public Separator sepName;
    public Separator sepDuration;
    public Separator sepFee;

    ProgramBo programBo= (ProgramBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PROGRAM);
    private int n;
    private int a;
    private int c;

    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        TrainingProgram trainingProgram=new TrainingProgram();
        trainingProgram.setProgramId(txtProgramId.getText());
        trainingProgram.setPrograme(txtName.getText());
        trainingProgram.setDuration(txtDuration.getText());
        trainingProgram.setFee(Double.parseDouble(txtFee.getText()));

        boolean save = programBo.save(trainingProgram);

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
            txtProgramId.setText(programBo.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txtName(KeyEvent keyEvent) {
        addBtn.setDisable(true);
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
        addBtn.setDisable(a != 1 || n != 1 || c != 1);
    }

    public void txtDuration(KeyEvent keyEvent) {
        addBtn.setDisable(true);
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
        addBtn.setDisable(true);
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
