package controller;

import bo.BOFactory;
import bo.custom.ProgramBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.StudentDto;
import entity.Student;
import entity.TrainingProgram;
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
import java.util.List;
import java.util.Optional;

/**
 * @Created_By_: Kavinda Gimhan
 * @Date_: 12/16/2021
 * @Time_: 11:58 AM
 * @Project_Name : SipsewanaInstitute
 **/

public class ManageTrainingProgramsFormController {
    public AnchorPane root;
    public JFXButton btnAddNewPrograme;
    public JFXTextField txtProgramId;
    public TableView<TrainingProgram> tblProgram;
    public TableColumn colProgramId;
    public TableColumn colProgramName;
    public TableColumn colDuration;
    public TableColumn colFee;
    public TableColumn colEdit;

    ProgramBo programBo = (ProgramBo) BOFactory.getBOFactory().getBO(BOFactory.BoTypes.PROGRAM);

    public void initialize() {

        tblProgram.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("programId"));
        tblProgram.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("programe"));
        tblProgram.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("duration"));
        tblProgram.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("fee"));

        tblProgram.getColumns().get(4).setCellValueFactory((param) -> {
            ImageView edit = new ImageView("/view/assests/image/icons8-edit-30.png");
            ImageView delete = new ImageView("/view/assests/image/icons8-delete-bin-30.png");

            edit.setOnMouseClicked(event -> {

                TrainingProgram trainingProgram = tblProgram.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/UpdateProgram.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                UpdateProgramController controller = fxmlLoader.getController();
                controller.txtProgramId.setText(trainingProgram.getProgramId());
                controller.txtName.setText(trainingProgram.getPrograme());
                controller.txtDuration.setText(trainingProgram.getDuration());
                controller.txtFee.setText(String.valueOf(trainingProgram.getFee()));

                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                UpdateProgramController.setStage(stage);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.showAndWait();
                loadData();
            });

            delete.setOnMouseClicked(event -> {
                TrainingProgram trainingProgram = tblProgram.getSelectionModel().getSelectedItem();

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION
                        , "Are you suwr?", yes, no);
                alert.setTitle("Confirmation Alert");
                Optional<ButtonType> result = alert.showAndWait();
                String temp = result.toString();

                if (temp.equals("Optional[ButtonType [text=Yes, buttonData=OK_DONE]]")) {
                    try {
                        programBo.deleteProgram(trainingProgram);
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

        tblProgram.getColumns().setAll(colProgramId,colProgramName,colDuration,colFee, colEdit);
        tblProgram.getItems().setAll(loadTableData());
    }


    private ObservableList<TrainingProgram> loadTableData() {
        try {

            List<String> strings = programBo.getAllProgramId();
            ObservableList<TrainingProgram> tableData = FXCollections.observableArrayList();
            for (int i = 0; i < strings.size(); i++) {

                tableData.add(programBo.getProgram(strings.get(i)));
            }
            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData() {
        tblProgram.getItems().setAll(loadTableData());
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/AddTrainingProgram.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initStyle(StageStyle.TRANSPARENT);
        AddTrainingProgramController.setStage(stage);
        stage.showAndWait();
        loadData();
    }

    public void navigateToHome(MouseEvent event) throws IOException {
        URL resource = getClass().getResource("/view/homeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    public void searchProgram(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        tblProgram.getItems().clear();
        List<TrainingProgram> strings = programBo.search(txtProgramId.getText());

        ObservableList<TrainingProgram> tableData = FXCollections.observableArrayList();
        for (int i = 0; i < strings.size(); i++) {
            tableData.add(strings.get(i));
        }
        tblProgram.getItems().setAll(tableData);
    }
}
