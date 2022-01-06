package controller;

import bo.BOFactory;
import bo.custom.DetailBo;
import bo.custom.ProgramBo;
import bo.custom.StudentBo;
import entity.Student;
import entity.StudentProgramDetail;
import entity.TrainingProgram;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 1:30 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class AddDetailController implements Initializable {
    private static Stage thisstage;
    public AnchorPane root;
    public TextField txtMoney;
    public ComboBox studentIdCombo;
    public ComboBox programIdCombo;
    public Button addBtn;
    public Separator sepMoney;
    public Label txtDate;
    Student student=null;
    TrainingProgram trainingProgram=null;

    DetailBo detailBo = (DetailBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.DETAIL);
    StudentBo studentBo = (StudentBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.STUDENT);
    ProgramBo programBo = (ProgramBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PROGRAM);
    private String tem = null;
    private String value;
    private int a;

    public static void setStage(Stage stage) {
        thisstage=stage;
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        StudentProgramDetail detail = detailBo.getDetail(student, trainingProgram);

        if(detail==null) {
            TrainingProgram program = programBo.getProgram(value);
            String time = setTimeLeft(program.getDuration());

            StudentProgramDetail studentProgramDetail = new StudentProgramDetail();
            studentProgramDetail.setId(detailBo.getId());
            studentProgramDetail.setStudent(student);
            studentProgramDetail.setTrainingProgram(trainingProgram);
            studentProgramDetail.setGivenMoney(Double.parseDouble(txtMoney.getText()));
            studentProgramDetail.setTimeleft(time);
            studentProgramDetail.setOngoingPayment(trainingProgram.getFee() - Double.parseDouble(txtMoney.getText()));
            studentProgramDetail.setDate(txtDate.getText());

            detailBo.save(studentProgramDetail);
            thisstage.close();
            return;
        }else{
            new Alert(Alert.AlertType.WARNING, "Already exists.").show();
        }
    }

    private String setTimeLeft(String duration) {

        int i=Integer.parseInt(duration)*30;
        return String.valueOf(i);
    }

    public void closeOnAction(ActionEvent actionEvent) {
        thisstage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtDate.setText(date());

        try {
            loadIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        studentIdCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tem = (String) newValue;
            try {
                setStudentId(tem);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        programIdCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                value = (String) newValue;
                setProgramData(value);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private String date() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private void loadIds() throws SQLException, ClassNotFoundException {
        List<String> studentIds = studentBo.getAllStudentIds();
        studentIdCombo.getItems().addAll(studentIds);

        List<String> allProgramId = programBo.getAllProgramId();
        programIdCombo.getItems().addAll(allProgramId);
    }

    private void setProgramData(String newValue) throws SQLException, ClassNotFoundException {
        trainingProgram = programBo.getProgram(newValue);
        System.out.println(trainingProgram.getProgramId());
        System.out.println(trainingProgram.getPrograme());
    }

    private void setStudentId(String tem) throws SQLException, ClassNotFoundException {
        student = studentBo.getStudentOriginal(tem);
    }

    private void setbtn(boolean b) {
        addBtn.setDisable(a != 1 || value.equals(null) || tem .equals( null));
    }

    public void givenMoney(KeyEvent keyEvent) {
        addBtn.setDisable(true);
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
}
