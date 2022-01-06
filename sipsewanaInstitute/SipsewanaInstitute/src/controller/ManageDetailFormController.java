package controller;

import bo.BOFactory;
import bo.custom.DetailBo;
import bo.custom.ProgramBo;
import bo.custom.StudentBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.DetailDto;
import dto.StudentDto;
import entity.Student;
import entity.StudentProgramDetail;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 12:08 PM
 * @Project_Name : SipsewanaInstitute
 **/

public class ManageDetailFormController {
    public AnchorPane root;
    public JFXButton btnAddNewDetail;
    public TableView<DetailDto> tblDetail;
    public TableColumn colStudentId;
    public TableColumn colProgramId;
    public TableColumn colGivenMoney;
    public TableColumn colOnGoingPayment;
    public TableColumn colTimeLeft;
    public TableColumn colEdit;
    public Label txtDate;

    DetailBo detailBo = (DetailBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.DETAIL);
    StudentBo studentBo = (StudentBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.STUDENT);
    ProgramBo programBo = (ProgramBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PROGRAM);

    public void initialize() {
        txtDate.setText(date());

        tblDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("trainingProgramId"));
        tblDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("givenMoney"));
        tblDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("ongoingPayment"));
        tblDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("timeleft"));

        tblDetail.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/view/assests/image/icons8-edit-30.png");
            ImageView delete = new ImageView("/view/assests/image/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {
                DetailDto detailDto = tblDetail.getSelectionModel().getSelectedItem();
                StudentProgramDetail studentProgramDetail=null;
                try {
                    studentProgramDetail = detailBo.getDetail(studentBo.getStudentOriginal(detailDto.getStudentId()) , programBo.getProgram(detailDto.getTrainingProgramId()));
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UpdateDetail.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateDetailController controller = fxmlLoader.getController();
                controller.txtStudentId.setText(studentProgramDetail.getStudent().getsId());
                controller.txtProgramId.setText(studentProgramDetail.getTrainingProgram().getProgramId());
                controller.txtMoney.setText(String.valueOf(studentProgramDetail.getOngoingPayment()));
                controller.tem=studentProgramDetail;

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                UpdateDetailController.setStage(stage);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                DetailDto detailDto = tblDetail.getSelectionModel().getSelectedItem();

                StudentProgramDetail studentProgramDetail=null;
                try {
                    studentProgramDetail = detailBo.getDetail(studentBo.getStudentOriginal(detailDto.getStudentId()) , programBo.getProgram(detailDto.getTrainingProgramId()));
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        detailBo.delete(studentProgramDetail);
                        loadData();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            return new ReadOnlyObjectWrapper(new HBox(10, edit, delete));
        });

        tblDetail.getColumns().setAll(colStudentId, colProgramId , colGivenMoney , colOnGoingPayment , colTimeLeft, colEdit);
        tblDetail.getItems().setAll(loadTableData());
    }

    private String date() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private ObservableList<DetailDto> loadTableData() {
        try {

            List<StudentProgramDetail> strings = detailBo.getAllProgram();
            ObservableList<DetailDto> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < strings.size(); i++) {

                DetailDto detailDto = new DetailDto();
                detailDto.setStudentId(strings.get(i).getStudent().getsId());
                detailDto.setTrainingProgramId(strings.get(i).getTrainingProgram().getProgramId());
                detailDto.setGivenMoney(strings.get(i).getGivenMoney());
                detailDto.setOngoingPayment(strings.get(i).getOngoingPayment());
                String z = setTimeLeft(strings.get(i).getTimeleft() , strings.get(i).getDate());
                detailDto.setTimeleft(z);

                tableData.add(detailDto);
            }
            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String setTimeLeft(String timeleft, String date) {
        String date1 = date();
        String day = date1.substring(0, 2);
        String month = date1.substring(3, 5);
        String year = date1.substring(6, 10);

        String day1 = date.substring(0, 2);
        String month1 = date.substring(3, 5);
        String year1 = date.substring(6, 10);

        int d=0;
        if(Integer.parseInt(day)>Integer.parseInt(day1)){
            int min = Integer.parseInt(day)-Integer.parseInt(day1);
            d=min;
        }

        int m=0;
        if(Integer.parseInt(month)>Integer.parseInt(month1)){
            int min = Integer.parseInt(month)-Integer.parseInt(month1);
            m=min*30;
        }

        int y=0;
        if(Integer.parseInt(year)>Integer.parseInt(year1)){
            int min = Integer.parseInt(year)-Integer.parseInt(year1);
            y=min*365;
        }

        int full =y+m+d;
        int z=Integer.parseInt(timeleft)-full;
        return String.valueOf(z);
    }


    private void loadData() {
        tblDetail.getItems().setAll(loadTableData());
    }

    public void navigateToHome(MouseEvent event) throws IOException {
        URL resource = getClass().getResource("/view/homeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddDetail.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        AddDetailController.setStage(stage);
        stage.showAndWait();
        loadData();
    }
}
