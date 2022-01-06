package controller;

import bo.BOFactory;
import bo.custom.StudentBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.StudentDto;
import entity.Student;
import entity.TrainingProgram;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.List;
import java.util.Optional;

/**
 * @author : Sanu Vithanage
 * @since : 0.1.0
 **/

public class manageStudentController {
    public AnchorPane root;
    public TableView<StudentDto> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colMark;
    public TableColumn colEdit;
    public JFXTextField txtStudentId;

    StudentBo studentBo= (StudentBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.STUDENT);

    public void initialize() {

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("marks"));

        tblStudent.getColumns().get(5).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/view/assests/image/icons8-edit-30.png");
            ImageView delete = new ImageView("/view/assests/image/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {

                StudentDto studentDto = tblStudent.getSelectionModel().getSelectedItem();
                Student studentOriginal=null;
                try {
                    studentOriginal = studentBo.getStudentOriginal(studentDto.getStudentId());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UpdateStudent.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateStudentController controller = fxmlLoader.getController();
                controller.txtStudentId.setText(studentOriginal.getsId());
                controller.txtFirstName.setText(studentOriginal.getName());
                controller.txtContact.setText(studentOriginal.getContact());
                controller.txtAddress.setText(studentOriginal.getAddress());
                controller.txtMarks.setText(String.valueOf(studentOriginal.getInterviewMark()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                UpdateStudentController.setStage(stage);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                StudentDto studentDto = tblStudent.getSelectionModel().getSelectedItem();

                Student student = null;
                try {
                    student=studentBo.getStudentOriginal(studentDto.getStudentId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
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
                        studentBo.deleteStudent(student);
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

        tblStudent.getColumns().setAll(colStudentId, colStudentName, colAddress, colContact, colMark, colEdit);
        tblStudent.getItems().setAll(loadTableData());
    }


    private ObservableList<StudentDto> loadTableData() {
        try {

            List<String> strings = studentBo.getAllStudentIds();
            ObservableList<StudentDto> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < strings.size(); i++) {

                tableData.add(studentBo.getStudent(strings.get(i)));
            }
            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData() {
        tblStudent.getItems().setAll(loadTableData());
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = getClass().getResource("/view/homeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root.getChildren().clear();
        root.getChildren().add(load);

    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddNewStudent.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        AddNewStudentController.setStage(stage);
        stage.showAndWait();
        loadData();
    }

    public void searchStudent(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        tblStudent.getItems().clear();
        List<StudentDto> strings = studentBo.search(txtStudentId.getText());

        ObservableList<StudentDto> tableData = FXCollections.observableArrayList();
        for (int i = 0; i < strings.size(); i++) {
            tableData.add(strings.get(i));
        }
        tblStudent.getItems().setAll(tableData);
    }
}
